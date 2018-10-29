package db.Managers;

import db.DBResult;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;



public abstract class ATableManager<T> implements ITableManager<T> {

    protected IDBManager db;
    public final String TABLE_NAME;

    protected abstract List<T> transformListMapToList(List<Map<String,String>> listMap);
    protected abstract PreparedStatement getInsertPreparedStatement(T object, Connection connection);

    //TODO - implement here getDeletePreparedStatement
    protected abstract PreparedStatement getDeletePreparedStatement(String where, Connection connection);

    //TODO - implement here getUpdatePreparedStatement
    protected abstract PreparedStatement getUpdatePreparedStatement(String[] set, String[] values, String[] where, Connection connection);


   // protected abstract PreparedStatement getDeletePreparedStatement(String id, Connection connection);

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
        System.out.println(sqlQuery);
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

    //TODO - merge with delete function
    @Override
    public DBResult updateData(String[] set, String[] values, String[] where) {
        DBResult result = DBResult.NONE;
        Connection connection = db.connect();
        if (connection != null) {
            PreparedStatement preparedStatement = getUpdatePreparedStatement(set, values, where, connection);
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

    protected DBResult createTable(String[] parameters){
        return db.createTable(TABLE_NAME, parameters);
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
                    /*
                    errorCode(vendorCode) 19 is: name = SQLITE_CONSTRAINT_PRIMARYKEY, messeage =  A PRIMARY KEY constraint failed.
                     */
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

    public DBResult deleteFromTable(String where) {
        DBResult result = DBResult.NONE;
        Connection connection = db.connect();
        if (connection != null) {
            PreparedStatement preparedStatement = getDeletePreparedStatement(where, connection);
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
