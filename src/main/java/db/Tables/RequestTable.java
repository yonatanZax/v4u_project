package db.Tables;

import Model.Request;
import db.DBResult;
import db.Managers.ATableManager;
import db.Managers.DBManager;
import db.Managers.IDBManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
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
        return null;
    }

    @Override
    protected PreparedStatement getInsertPreparedStatement(Request object, Connection connection) {
        String sql = "INSERT INTO " + TABLE_NAME + "(" + COLUMN_REQUESTTABLE_VACATIONKEY + "," + COLUMN_REQUESTTABLE_SELLERKEY+ "," + COLUMN_REQUESTTABLE_BUYERKEY+ "," + COLUMN_REQUESTTABLE_APPROVED + "," + COLUMN_REQUESTTABLE_TIMESTAMP + ") VALUES(?,?,?,?,?,?)";
        PreparedStatement pstmt = null;
        if (connection != null) {
            try {
                pstmt = connection.prepareStatement(sql);
                pstmt.setString(1, object.getVacationKey());
                pstmt.setString(2, object.getSellerKey());
                pstmt.setString(3, object.getBuyerKey());
                pstmt.setString(4, object.getApproved());
                pstmt.setObject(5, object.getTimeStamp());
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
        // Todo - add parameters
        String[] primaryKeys = {COLUMN_REQUESTTABLE_VACATIONKEY,COLUMN_REQUESTTABLE_SELLERKEY,COLUMN_REQUESTTABLE_BUYERKEY};
        String[] foreignKeys = {FOREIGNKEY_VACATIONKEY,FOREIGNKEY_SELLERKEY,FOREIGNKEY_BUYERKEY};
        String[] stringFields = {COLUMN_REQUESTTABLE_APPROVED};
        String[] intFields = {COLUMN_REQUESTTABLE_TIMESTAMP};
        String[] doubleFields = {};
        return super.createTable(primaryKeys, foreignKeys,stringFields,intFields,doubleFields);
    }
}
