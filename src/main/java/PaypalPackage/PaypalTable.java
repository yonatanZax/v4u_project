package PaypalPackage;


import db.DBResult;
import db.Managers.ATableManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class PaypalTable extends ATableManager<PaypalPayment>{

    private static PaypalTable ourInstance;


    public static final String COLUMN_PAYPAL_TRANSACTIONID = "key";
    public static final String COLUMN_PAYPAL_PAIDBY_EMAIL = "PaidByEmail";
    public static final String COLUMN_PAYPAL_PAIDTO_EMAIL = "PaidToEmail";
    public static final String COLUMN_PAYPAL_AMOUNT = "amount";



    // Singleton
    public static PaypalTable getInstance() {
        if(ourInstance == null) {
            ourInstance = new PaypalTable();
        }
        return ourInstance;
    }

    private PaypalTable() {
        super(new PayPalDBManager(),"PaypalTable");
        createTable();
    }




    @Override
    protected List<PaypalPayment> transformListMapToList(List<Map<String, String>> listMap) {
        List<PaypalPayment> list = new ArrayList<>(listMap.size());
        for(Map<String,String> map : listMap){
            PaypalPayment paypalPayment = new PaypalPayment();
            for(Map.Entry<String,String> entry : map.entrySet()){
                String key = entry.getKey();
                switch (key){
                    case COLUMN_PAYPAL_TRANSACTIONID:
                        String transactionIDAsString = entry.getValue();
                        int transactionIDAsInt = Integer.parseInt(transactionIDAsString);
                        paypalPayment.setAmount(transactionIDAsInt);
                        break;

                    case COLUMN_PAYPAL_AMOUNT:
                        String amountAsString = entry.getValue();
                        double amountAsDouble = Double.parseDouble(amountAsString);
                        paypalPayment.setAmount(amountAsDouble);
                        break;

                    case COLUMN_PAYPAL_PAIDBY_EMAIL:
                        paypalPayment.setPaidByEmail(entry.getValue());
                        break;

                    case COLUMN_PAYPAL_PAIDTO_EMAIL:
                        paypalPayment.setPaidToEmail(entry.getValue());
                        break;

                }

            }
            list.add(paypalPayment);
        }
        return list;
    }

    @Override
    protected PreparedStatement getInsertPreparedStatement(PaypalPayment object, Connection connection) {
        String sql = "INSERT INTO " + TABLE_NAME + "(" + COLUMN_PAYPAL_TRANSACTIONID + "," +
                COLUMN_PAYPAL_PAIDBY_EMAIL+ "," +
                COLUMN_PAYPAL_PAIDTO_EMAIL+ "," +
                COLUMN_PAYPAL_AMOUNT +") VALUES(?,?,?,?)";
        PreparedStatement pstmt = null;
        if (connection != null) {
            try {
                pstmt = connection.prepareStatement(sql);
                pstmt.setString(1, object.getTransactionID());
                pstmt.setString(2, object.getPaidByEmail());
                pstmt.setString(3, object.getPaidToEmail());
                pstmt.setDouble(4, object.getAmount());
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
        String[] primaryKeys = {COLUMN_PAYPAL_TRANSACTIONID + " INTEGER PRIMARY KEY AUTOINCREMENT"};
        String[] foreignKeys = {};
        String[] stringFields = {COLUMN_PAYPAL_PAIDBY_EMAIL,COLUMN_PAYPAL_PAIDTO_EMAIL};
        String[] intFields = {};
        String[] doubleFields = {COLUMN_PAYPAL_AMOUNT};
        return super.createTable(primaryKeys, foreignKeys,stringFields,intFields,doubleFields);

    }


    @Override /* Has  INTEGER PRIMARY KEY AUTOINCREMENT */
    protected String getCreateTableSQLString(String[] primaryKeys, String[] foreignKeys, String[] stringFields,String[] intFields, String[] doubleFields) {
        if(primaryKeys != null) {
            String sql = "CREATE TABLE IF NOT EXISTS " + TABLE_NAME + " (\n";
            for (String key: primaryKeys)
                sql += key + " ,\n";

            for (String str: stringFields)
                sql += str + " TEXT NOT NULL,\n";


            for (String d: doubleFields)
                sql += d + " REAL NOT NULL\n";

            sql += "\n);";
            return sql;
        }
        return null;
    }
}
