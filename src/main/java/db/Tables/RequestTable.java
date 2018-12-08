package db.Tables;

import Model.Request.Request;
import Model.User.User;
import db.DBResult;
import db.Managers.ATableManager;
import db.Managers.DBManager;
import db.Managers.IDBManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class RequestTable extends ATableManager<Request> {


    private static RequestTable ourInstance;
    public static final String COLUMN_REQUESTTABLE_VACATIONKEY = "vacationKey";
    private final String FOREIGNKEY_VACATIONKEY = "(" + COLUMN_REQUESTTABLE_VACATIONKEY + ") references vacationInfo(key)";
    public static final String COLUMN_REQUESTTABLE_SELLERKEY = "sellerKey";
    private final String FOREIGNKEY_SELLERKEY = "(" + COLUMN_REQUESTTABLE_SELLERKEY + ") references userInfo(key)";
    public static final String COLUMN_REQUESTTABLE_BUYERKEY = "buyerKey";
    private final String FOREIGNKEY_BUYERKEY = "(" + COLUMN_REQUESTTABLE_BUYERKEY + ") references userInfo(key)";
    public static final String COLUMN_REQUESTTABLE_APPROVED = "approved";
    public static final String COLUMN_REQUESTTABLE_TIMESTAMP = "timestamp";



    // Singleton
    public static RequestTable getInstance() {
        if(ourInstance == null) {
            ourInstance = new RequestTable();
        }
        return ourInstance;
    }


    protected RequestTable() {
        super(DBManager.getInstance(), "requestInfo");
    }

    @Override
    protected List<Request> transformListMapToList(List<Map<String, String>> listMap) {

        List<Request> list = new ArrayList<>(listMap.size());
        for(Map<String,String> map : listMap){
            Request request = new Request();
            for(Map.Entry<String,String> entry : map.entrySet()){
                String key = entry.getKey();
                switch (key){
                    case COLUMN_REQUESTTABLE_TIMESTAMP:
                        request.setTimestamp(entry.getValue());
                        break;
                    case COLUMN_REQUESTTABLE_VACATIONKEY:
                        request.setVacationKey( entry.getValue());
                        break;

                    case COLUMN_REQUESTTABLE_SELLERKEY:
                        request.setSellerKey( entry.getValue());
                        break;

                    case COLUMN_REQUESTTABLE_BUYERKEY:
                        request.setBuyerKey( entry.getValue());
                        break;

                    case COLUMN_REQUESTTABLE_APPROVED:
                        request.setApproved(entry.getValue());
                        break;
                }

            }
            list.add(request);
        }
        return list;
    }

    @Override
    protected PreparedStatement getInsertPreparedStatement(Request object, Connection connection) {
        String sql = "INSERT INTO " + TABLE_NAME + "(" + COLUMN_REQUESTTABLE_VACATIONKEY + "," + COLUMN_REQUESTTABLE_SELLERKEY+ "," + COLUMN_REQUESTTABLE_BUYERKEY+ "," + COLUMN_REQUESTTABLE_APPROVED + "," + COLUMN_REQUESTTABLE_TIMESTAMP + ") VALUES(?,?,?,?,?)";
        PreparedStatement pstmt = null;
        if (connection != null) {
            try {
                pstmt = connection.prepareStatement(sql);
                pstmt.setString(1, object.getVacationKey());
                pstmt.setString(2, object.getSellerKey());
                pstmt.setString(3, object.getBuyerKey());
                pstmt.setString(4, object.getApproved() + "");
                pstmt.setInt(5, object.getTimestamp());
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

    @Override
    public DBResult createTable() {
        String[] primaryKeys = {COLUMN_REQUESTTABLE_VACATIONKEY,COLUMN_REQUESTTABLE_SELLERKEY,COLUMN_REQUESTTABLE_BUYERKEY};
        String[] foreignKeys = {FOREIGNKEY_VACATIONKEY,FOREIGNKEY_SELLERKEY,FOREIGNKEY_BUYERKEY};
        String[] stringFields = {};
        String[] intFields = {COLUMN_REQUESTTABLE_APPROVED,COLUMN_REQUESTTABLE_TIMESTAMP};
        String[] doubleFields = {};
        return super.createTable(primaryKeys, foreignKeys,stringFields,intFields,doubleFields);
    }
}
