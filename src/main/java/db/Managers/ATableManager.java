package db.Managers;

import com.sun.xml.internal.ws.api.addressing.WSEndpointReference;
import db.DBResult;

import java.sql.*;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;



public abstract class ATableManager<T> implements ITableManager<T> {

    protected IDBManager db;
    public final String TABLE_NAME;

    protected abstract List<T> transformListMapToList(List<Map<String,String>> listMap);
    protected abstract PreparedStatement getInsertPreparedStatement(T object, Connection connection);



    protected PreparedStatement getDeletePreparedStatement(String where, Connection connection){
        String sql = "DELETE FROM "+ TABLE_NAME + " WHERE " +  where ;
        PreparedStatement pstmt;
        if (connection != null) {
            try {
                pstmt = connection.prepareStatement(sql);
                return pstmt;
            } catch (SQLException e) {
                System.out.println(e.getMessage());
                try {
                    connection.rollback();
                } catch (SQLException ex) {
                    System.out.println(ex.getMessage());
                }
            }
        }
        return null;
    }

    protected DBResult executeQuery(String sql){
        Connection connection = db.connect();
        PreparedStatement pstmt = null;
        int  resultInt;
        DBResult result = DBResult.NONE;;
        if (connection != null) {
            try {
                pstmt = connection.prepareStatement(sql);
                resultInt = pstmt.executeUpdate();
                if (1 == resultInt)
                    result = DBResult.UPDATED;
            } catch (SQLException e) {
                int errorCode = e.getErrorCode();
                if (errorCode == 19)
                    result = DBResult.ALREADY_EXIST;
                else {
                    e.printStackTrace();
                    result = DBResult.ERROR;
                }
            } finally {
                closeStatement(pstmt);
                if (db.closeConnection(connection) != DBResult.CONNECTION_CLOSED)
                    result = DBResult.ERROR;
            }
        }
        return result;
    }





    protected PreparedStatement getUpdatePreparedStatement(String[] set, String[] values, String[] whereFields, String[] whereValues, Connection connection){
        String sql = "UPDATE " + TABLE_NAME + " SET ";
        sql += appendSql(set,values);
        sql += " WHERE " + appendWhereSQL(whereFields,whereValues);
        PreparedStatement pstmt = null;
        if (connection != null) {
            try {
                pstmt = connection.prepareStatement(sql);
                return pstmt;
            } catch (SQLException e) {
                System.out.println(e.getMessage());
                try {
                    connection.rollback();
                } catch (SQLException ex) {
                    System.out.println(ex.getMessage());
                }
            }
        }
        return null;
    }

    private String appendSql(String[] strings,String[] values) {
        String s = "";
        for (int i = 0; i < strings.length; i++) {
            s+= strings[i] + " = " + "\"" +values[i] + "\"";
            if (i<strings.length-1)
                s+=", ";
        }
        return s;
    }

    private String appendWhereSQL(String[] whereFields, String[] whereValues){
        String s = "";
        for (int i = 0; i < whereFields.length; i++) {
            s+= whereFields[i] + " = " + "\"" + whereValues[i] + "\"";
            if (i<whereFields.length-1)
                s+=" AND ";
        }
        return s;
    }




    protected ATableManager(IDBManager db, String table_name) {
        this.db = db;
        TABLE_NAME = table_name;

    }

    /**
     * @param projection
     * @param selection
     * @param orderBy
     * @return
     */
    @Override
    public List<T> select(String projection, String selection, String orderBy) {
        String sqlQuery = createSQLSelect(projection, selection, orderBy);
        Connection connection = db.connect();
        List<T> list = null;
        if (connection != null) {
            try {
                PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery);
                ResultSet resultSet = preparedStatement.executeQuery();
                list = getDataList(resultSet);
            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                db.closeConnection(connection);
            }
        }
        return list;
    }

    @Override
    public DBResult updateData(String[] set, String[] values, String[] whereFields, String[] whereValues) {
        DBResult result = DBResult.NONE;
        Connection connection = db.connect();
        if (connection != null) {
            PreparedStatement preparedStatement = getUpdatePreparedStatement(set, values, whereFields,whereValues, connection);
            if (preparedStatement != null) {
                try {
                    if (1 == preparedStatement.executeUpdate())
                        result = DBResult.UPDATED;
                } catch (SQLException e) {
                    int errorCode = e.getErrorCode();
                    if (errorCode == 19)
                        result = DBResult.ALREADY_EXIST;
                    else {
                        e.printStackTrace();
                        result = DBResult.ERROR;
                    }
                } finally {
                    closeStatement(preparedStatement);
                    if (db.closeConnection(connection) != DBResult.CONNECTION_CLOSED)
                        result = DBResult.ERROR;

                }
            }
        }
        return result;
    }


    protected String getCreateTableSQLString(String[] primaryKeys, String[] foreignKeys, String[] stringFields,String[] intFields, String[] doubleFields) {
        if(primaryKeys != null) {
            String sql = "CREATE TABLE IF NOT EXISTS " + TABLE_NAME + " (\n";
            for (String str: stringFields)
                sql += str + " TEXT NOT NULL,\n";
            for (String i: intFields)
                sql += i + " INTEGER NOT NULL,\n";
            for (String d: doubleFields)
                sql += d + " REAL NOT NULL,\n";

            sql += "primary key (";
            for (String primaryKey: primaryKeys)
                sql += primaryKey + ",";

            sql = sql.substring(0, sql.length() - 1);
            sql += ')';
            if (foreignKeys.length > 0)
                sql += ',';

            // foreign key (house_id) references houses(id),
            for (String foreignKey: foreignKeys)
                sql += "foreign key " + foreignKey + "\n";

            sql += "\n);";
            return sql;
        }
        return null;
    }



    protected DBResult createTable(String[] primaryKeys, String[] foreignKeys, String[] stringFields,String[] intFields, String[] doubleFields){
        String sql = getCreateTableSQLString(primaryKeys,foreignKeys,stringFields,intFields,doubleFields);
        return db.createTable(sql);
    }

    @Override
    public DBResult InsertToTable(T object) {
        DBResult result = DBResult.NONE;
        Connection connection = db.connect();
        if(connection != null) {
            PreparedStatement preparedStatement = getInsertPreparedStatement(object, connection);
            if(preparedStatement != null){
                try{
                    if(1 == preparedStatement.executeUpdate())
                        result = DBResult.ADDED;
                }catch (SQLException e){
                    int errorCode = e.getErrorCode();
                    if (errorCode == 19)
                        result = DBResult.ALREADY_EXIST;
                    else {
                    e.printStackTrace();
                        result = DBResult.ERROR;
                    }
                }finally {
                    closeStatement(preparedStatement);
                    if (db.closeConnection(connection) != DBResult.CONNECTION_CLOSED)
                        result = DBResult.ERROR;

                }
            }
        }
        return result;
    }

    public DBResult deleteFromTable(String key) {
        DBResult result = DBResult.NONE;
        Connection connection = db.connect();
        if (connection != null) {
            PreparedStatement preparedStatement = getDeletePreparedStatement(key, connection);
            if (preparedStatement != null) {
                try {
                    if (1 == preparedStatement.executeUpdate())
                        result = DBResult.DELETED;
                    else {
                        result = DBResult.NOTHING_TO_DELETE;
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                    result = DBResult.ERROR;
                } finally {
                    closeStatement(preparedStatement);
                    if (db.closeConnection(connection) != DBResult.CONNECTION_CLOSED)
                        result = DBResult.ERROR;
                }
            }
        }
        return result;
    }

    private void closeStatement(Statement statement){
        if(statement != null) {
            try {
                statement.closeOnCompletion();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    private void closeResultSet(ResultSet rs){
        if(rs != null ){
            try {
                rs.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }



    private List<T> getDataList(ResultSet rs){
        List<T> list = null;
        try {
            List<Map<String,String>>  mapList = map(rs);
            closeResultSet(rs);
            rs = null;
            list = transformListMapToList(mapList);
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            closeResultSet(rs);
        }
        return list;
    }


    private static List<Map<String, String>> map(ResultSet rs) throws SQLException {
        List<Map<String, String>> results = new ArrayList<Map<String, String>>();
        if(rs != null) {
            try {
                if (rs != null) {
                    ResultSetMetaData meta = rs.getMetaData();
                    int numColumns = meta.getColumnCount();
                    while (rs.next()) {
                        Map<String, String> row = new HashMap<String, String>();
                        for (int i = 1; i <= numColumns; ++i) {
                            String name = meta.getColumnName(i);
                            String value = rs.getString(i);
                            row.put(name, value);
                        }
                        results.add(row);
                    }
                }
            } finally {
                rs.close();
            }
        }
        return results;
    }


    private String createSQLSelect(String projection, String selection, String orderBy) {
        String sqlQuery = "SELECT ";
        // set the projection, if null or empty will auto assign '*'
        if (projection != null && !projection.equals("")){
            sqlQuery += projection + " ";
        }
        else{
            sqlQuery += "* ";
        }

        sqlQuery += "FROM " + TABLE_NAME + " ";

        // set the selection. where selection[i] suppose to refer selectionArgs[i]
        if(selection != null && selection.length() > 0 ){
            sqlQuery += "WHERE " + selection;
        }

        //set the Order By
        if(orderBy != null && orderBy != ""){
            sqlQuery += " ORDER BY " + orderBy;
        }
        return sqlQuery;
    }

}
