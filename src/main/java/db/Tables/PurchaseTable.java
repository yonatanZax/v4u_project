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
    public static final String COLUMN_PURCHASETABLE_SELLERINFO = "sellerInfo";
    public static final String COLUMN_PURCHASETABLE_BUYERKEY = "buyerKey";
    private final String FOREIGNKEY_BUYERKEY = "(" + COLUMN_PURCHASETABLE_BUYERKEY + ") references userInfo(key)";
    public static final String COLUMN_PURCHASETABLE_VACATIONKEYEXCHANGE = "vacationKeyToExchange";
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

                    case COLUMN_PURCHASETABLE_SELLERINFO:
                        purchase.setSellerInfo(entry.getValue());
                        break;

                    case COLUMN_PURCHASETABLE_VACATIONKEYEXCHANGE:
                        purchase.setBuyerVacationToExchange(entry.getValue());
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
        String[] stringFields = {COLUMN_PURCHASETABLE_VACATIONKEY,COLUMN_PURCHASETABLE_SELLERKEY,COLUMN_PURCHASETABLE_SELLERINFO,COLUMN_PURCHASETABLE_BUYERKEY,COLUMN_PURCHASETABLE_VACATIONKEYEXCHANGE};
        String[] intFields = {COLUMN_PURCHASETABLE_TIMESTAMP};
        String[] doubleFields = {};
        return super.createTable(primaryKeys, foreignKeys,stringFields,intFields,doubleFields);
    }

    @Override
    protected PreparedStatement getInsertPreparedStatement(Purchase object, Connection connection) {
        String sql = "INSERT INTO " + TABLE_NAME + "(" + COLUMN_PURCHASETABLE_VACATIONKEY + "," +
                COLUMN_PURCHASETABLE_SELLERKEY+ "," +
                COLUMN_PURCHASETABLE_SELLERINFO+ "," +
                COLUMN_PURCHASETABLE_BUYERKEY+ "," +
                COLUMN_PURCHASETABLE_VACATIONKEYEXCHANGE+ "," +
                COLUMN_PURCHASETABLE_TIMESTAMP /*+ ","*/ +") VALUES(?,?,?,?,?,?)";
        PreparedStatement pstmt = null;
        if (connection != null) {
            try {
                pstmt = connection.prepareStatement(sql);
                pstmt.setString(1, object.getVacationKey());
                pstmt.setString(2, object.getSellerKey());
                pstmt.setString(3, object.getSellerInfo());
                pstmt.setString(4, object.getBuyerKey());
                pstmt.setString(5, object.getBuyerVacationToExchange());
                pstmt.setInt(6 ,object.getTimestamp());
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
    protected String getCreateTableSQLString(String[] primaryKeys, String[] foreignKeys, String[] stringFields,String[] intFields, String[] doubleFields) {
        if(primaryKeys != null) {
            String sql = "CREATE TABLE IF NOT EXISTS " + TABLE_NAME + " (\n";

            for (int i=0; i < stringFields.length - 1; i++)
                sql += stringFields[i] + " TEXT NOT NULL,\n";

            sql += stringFields[stringFields.length - 1] + " TEXT,\n";

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
            for (int i = 0 ; i < foreignKeys.length ; i++){
                if( i == foreignKeys.length - 1){
                    sql += "foreign key " + foreignKeys[i] + "\n";
                    break;
                }
                sql += "foreign key " + foreignKeys[i] + ",\n";
            }

            sql += "\n);";
            return sql;
        }
        return null;
    }
}
