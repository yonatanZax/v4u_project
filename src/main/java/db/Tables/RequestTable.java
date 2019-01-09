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
    public static final String COLUMN_REQUESTTABLE_STATUS = "status";
    public static final String COLUMN_REQUESTTABLE_VACATIONKEYTOEXCHANGE = "vacationKeyToExchange";


    // Singleton
    public static RequestTable getInstance() {
        if (ourInstance == null) {
            ourInstance = new RequestTable();
        }
        return ourInstance;
    }


    protected RequestTable() {
        super(DBManager.getInstance(), "requestInfo");
        createTable();
    }

//    @Override
//    public PreparedStatement getUpdatePreparedStatement(String[] set, String[] values, String[] whereFields, String[] whereValues, Connection connection) {
//        String sql = "UPDATE " + TABLE_NAME + " SET ";
//        sql += appendSql(set);
//        sql += " WHERE " + appendWhereSQL(whereFields);
//        PreparedStatement pstmt = null;
//        if (connection != null) {
//            try {
//                pstmt = connection.prepareStatement(sql);
////                for (int i = 0; i < values.length; i++) {
////                    if (i < values.length - 1)
////                        pstmt.setString(i + 1, values[i]);
////                    else
////                        pstmt.setInt(i + 1, Integer.valueOf(values[i]));
////                }
//                pstmt.setString(1, values[0]);
//                pstmt.setString(2, values[1]);
//                pstmt.setString(3, values[2]);
//                pstmt.setString(4, values[3]);
//                pstmt.setString(5, values[4]);
//                pstmt.setInt(6, Integer.valueOf(values[5]));
//                int j = 7;
//                for (int i = 0; i < whereValues.length; i++) {
//                    pstmt.setObject(j++, whereValues[i]);
//                }
//                return pstmt;
//            } catch (SQLException e) {
//                System.out.println(e.getMessage());
//                try {
//                    connection.rollback();
//                } catch (SQLException ex) {
//                    System.out.println(ex.getMessage());
//                }
//            }
//        }
//        return null;
//    }

    @Override
    protected List<Request> transformListMapToList(List<Map<String, String>> listMap) {

        List<Request> list = new ArrayList<>(listMap.size());
        for (Map<String, String> map : listMap) {
            Request request = new Request();
            for (Map.Entry<String, String> entry : map.entrySet()) {
                String key = entry.getKey();
                switch (key) {
                    case COLUMN_REQUESTTABLE_TIMESTAMP:
                        request.setTimestamp(entry.getValue());
                        break;
                    case COLUMN_REQUESTTABLE_VACATIONKEY:
                        request.setVacationKey(entry.getValue());
                        break;

                    case COLUMN_REQUESTTABLE_SELLERKEY:
                        request.setSellerKey(entry.getValue());
                        break;

                    case COLUMN_REQUESTTABLE_BUYERKEY:
                        request.setBuyerKey(entry.getValue());
                        break;

                    case COLUMN_REQUESTTABLE_APPROVED:
//                        request.setApproved(entry.getValue());
                        if (entry.getValue().equals("false")) {
                            request.setApproved(false);
                        } else {
                            request.setApproved(true);
                        }
                        break;
                    case COLUMN_REQUESTTABLE_STATUS:
                        request.setState(entry.getValue());
                        break;
                    case COLUMN_REQUESTTABLE_VACATIONKEYTOEXCHANGE:
                        request.setVacationToExchange(Integer.valueOf(entry.getValue()));
                }

            }
            list.add(request);
        }
        return list;
    }

    @Override
    protected PreparedStatement getInsertPreparedStatement(Request object, Connection connection) {
        String sql = "INSERT INTO " + TABLE_NAME + "(" + COLUMN_REQUESTTABLE_VACATIONKEY + "," + COLUMN_REQUESTTABLE_SELLERKEY + "," + COLUMN_REQUESTTABLE_BUYERKEY + "," + COLUMN_REQUESTTABLE_STATUS + "," + COLUMN_REQUESTTABLE_APPROVED + "," + COLUMN_REQUESTTABLE_TIMESTAMP + "," + COLUMN_REQUESTTABLE_VACATIONKEYTOEXCHANGE + ") VALUES(?,?,?,?,?,?,?)";
        PreparedStatement pstmt = null;
        if (connection != null) {
            try {
                pstmt = connection.prepareStatement(sql);
                pstmt.setString(1, object.getVacationKey());
                pstmt.setString(2, object.getSellerKey());
                pstmt.setString(3, object.getBuyerKey());
                pstmt.setString(4, object.getState().toString());
                pstmt.setString(5, object.getApproved() + "");
                pstmt.setInt(6, object.getTimestamp());
                pstmt.setString(7, String.valueOf(object.getVacationToExchange()));
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
        String[] primaryKeys = {COLUMN_REQUESTTABLE_VACATIONKEY, COLUMN_REQUESTTABLE_SELLERKEY, COLUMN_REQUESTTABLE_BUYERKEY};
        String[] foreignKeys = {FOREIGNKEY_VACATIONKEY, FOREIGNKEY_SELLERKEY, FOREIGNKEY_BUYERKEY};
        String[] stringFields = {COLUMN_REQUESTTABLE_VACATIONKEY, COLUMN_REQUESTTABLE_SELLERKEY, COLUMN_REQUESTTABLE_BUYERKEY, COLUMN_REQUESTTABLE_STATUS,COLUMN_REQUESTTABLE_APPROVED,COLUMN_REQUESTTABLE_VACATIONKEYTOEXCHANGE};
        String[] intFields = {COLUMN_REQUESTTABLE_TIMESTAMP};
        String[] doubleFields = {};
        return super.createTable(primaryKeys, foreignKeys, stringFields, intFields, doubleFields);
    }
}
