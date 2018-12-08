package db.Tables;

import Model.Purchase.Purchase;
import Model.Request.Request;
import db.DBResult;
import db.Managers.ATableManager;
import db.Managers.DBManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class PurchaseTable extends ATableManager<Purchase> {

    private static PurchaseTable ourInstance;

    public static final String COLUMN_PURCHASETABLE_VACATIONKEY = "vacationKey";
    private final String FOREIGNKEY_VACATIONKEY = "(" + COLUMN_PURCHASETABLE_VACATIONKEY + ") references vacationInfo(key)";
    public static final String COLUMN_PURCHASETABLE_SELLERKEY = "sellerKey";
    private final String FOREIGNKEY_SELLERKEY = "(" + COLUMN_PURCHASETABLE_SELLERKEY + ") references userInfo(key)";
    public static final String COLUMN_PURCHASETABLE_BUYERKEY = "buyerKey";
    private final String FOREIGNKEY_BUYERKEY = "(" + COLUMN_PURCHASETABLE_BUYERKEY + ") references userInfo(key)";
    public static final String COLUMN_PURCHASETABLE_TIMESTAMP = "timestamp";


    // Singleton
    public static PurchaseTable getInstance() {
        if(ourInstance == null) {
            ourInstance = new PurchaseTable();
        }
        return ourInstance;
    }

    private PurchaseTable() {
        super(DBManager.getInstance(),"purchaseInfo");
        createTable();
    }


    @Override
    protected List<Purchase> transformListMapToList(List<Map<String, String>> listMap) {

        List<Purchase> list = new ArrayList<>(listMap.size());
        for(Map<String,String> map : listMap){
            Purchase purchase = new Purchase();
            for(Map.Entry<String,String> entry : map.entrySet()){
                String key = entry.getKey();
                switch (key){
                    case COLUMN_PURCHASETABLE_TIMESTAMP:
                        String timestampAsString = entry.getValue();
                        int timestampAsInt = Integer.parseInt(timestampAsString);
                        purchase.setTimestamp(timestampAsInt);
                        break;
                    case COLUMN_PURCHASETABLE_VACATIONKEY:
                        purchase.setVacationKey( entry.getValue());
                        break;

                    case COLUMN_PURCHASETABLE_SELLERKEY:
                        purchase.setSellerKey( entry.getValue());
                        break;

                    case COLUMN_PURCHASETABLE_BUYERKEY:
                        purchase.setBuyerKey( entry.getValue());
                        break;

                }

            }
            list.add(purchase);
        }
        return list;
    }



    @Override
    public DBResult createTable() {
        String[] primaryKeys = {COLUMN_PURCHASETABLE_VACATIONKEY,COLUMN_PURCHASETABLE_SELLERKEY,COLUMN_PURCHASETABLE_BUYERKEY};
        String[] foreignKeys = {FOREIGNKEY_VACATIONKEY,FOREIGNKEY_SELLERKEY,FOREIGNKEY_BUYERKEY};
        String[] stringFields = {COLUMN_PURCHASETABLE_VACATIONKEY,COLUMN_PURCHASETABLE_SELLERKEY,COLUMN_PURCHASETABLE_BUYERKEY};
        String[] intFields = {COLUMN_PURCHASETABLE_TIMESTAMP};
        String[] doubleFields = {};
        return super.createTable(primaryKeys, foreignKeys,stringFields,intFields,doubleFields);
    }

    @Override
    protected PreparedStatement getInsertPreparedStatement(Purchase object, Connection connection) {
        String sql = "INSERT INTO " + TABLE_NAME + "(" + COLUMN_PURCHASETABLE_VACATIONKEY + "," +
                COLUMN_PURCHASETABLE_SELLERKEY+ "," +
                COLUMN_PURCHASETABLE_BUYERKEY+ "," +
                COLUMN_PURCHASETABLE_TIMESTAMP + "," +") VALUES(?,?,?,?)";
        PreparedStatement pstmt = null;
        if (connection != null) {
            try {
                pstmt = connection.prepareStatement(sql);
                pstmt.setString(1, object.getVacationKey());
                pstmt.setString(2, object.getSellerKey());
                pstmt.setString(3, object.getBuyerKey());
                pstmt.setInt(4, object.getTimestamp());
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
}
