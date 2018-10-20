package db.Managers;

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

    protected ATableManager(IDBManager db, String table_name) {
        this.db = db;
        TABLE_NAME = table_name;

    }

    protected boolean createTable(String[] parameters){
        return db.createTable(TABLE_NAME, parameters);
    }

    @Override
    public boolean InsertToTable(T object) {
        boolean worked = false;
        Connection connection = db.connect();
        if(connection != null) {
            PreparedStatement preparedStatement = getInsertPreparedStatement(object, connection);
            if(preparedStatement != null){
                try{
                    worked = 1 == preparedStatement.executeUpdate();
                    preparedStatement.close();
                }catch (SQLException e){
                    /*
                    errorCode(vendorCode) 19 is: name = SQLITE_CONSTRAINT_PRIMARYKEY, messeage =  A PRIMARY KEY constraint failed.
                     */
                    int errorCode = e.getErrorCode();
                    e.printStackTrace();
                }finally {
                    closeStatement(preparedStatement);
                    db.closeConnection(connection);

                }
            }
        }
        return worked;
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

    @Override
    public List<T> select(String projection, String selection, String orderBy) {
        String sqlQuery = createSQLSelect(projection, selection, orderBy);
        Connection connection = db.connect();
        List<T> list = null;
        if(connection != null){
            try {
                PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery);
                ResultSet resultSet = preparedStatement.executeQuery();
                list = getDataList(resultSet);
            }catch ( SQLException e){
                e.printStackTrace();
            }
            finally {
                db.closeConnection(connection);
            }
        }
        return list;
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

        sqlQuery += " FROM " + TABLE_NAME + " ";

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
