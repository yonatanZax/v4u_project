package db.Tables;

import MainPackage.Payment;
import Model.Purchase.Purchase;
import db.DBResult;
import db.Managers.ATableManager;
import db.Managers.DBManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class PaymentTable extends ATableManager<Payment> {

    private static PaymentTable ourInstance;


    public static final String COLUMN_PAYMENT_TRANSACTIONID = "key";
    public static final String COLUMN_PAYMENT_PAIDBY = "PaidByKey";
    private final String FOREIGNKEY_PAIDBY = "(" + COLUMN_PAYMENT_PAIDBY + ") references userInfo(key)";
    public static final String COLUMN_PAYMENT_PAIDTO = "PaidToKey";
    private final String FOREIGNKEY_PAIDTO = "(" + COLUMN_PAYMENT_PAIDTO + ") references userInfo(key)";
    public static final String COLUMN_PAYMENT_AMOUNT = "amount";



    // Singleton
    public static PaymentTable getInstance() {
        if(ourInstance == null) {
            ourInstance = new PaymentTable();
        }
        return ourInstance;
    }

    private PaymentTable() {
        super(DBManager.getInstance(),"paymentInfo");
        createTable();
    }




    @Override
    protected List<Payment> transformListMapToList(List<Map<String, String>> listMap) {
        List<Payment> list = new ArrayList<>(listMap.size());
        for(Map<String,String> map : listMap){
            Payment payment = new Payment();
            for(Map.Entry<String,String> entry : map.entrySet()){
                String key = entry.getKey();
                switch (key){
                    case COLUMN_PAYMENT_TRANSACTIONID:
                        String transactionIDAsString = entry.getValue();
                        int transactionIDAsInt = Integer.parseInt(transactionIDAsString);
                        payment.setAmount(transactionIDAsInt);
                        break;

                    case COLUMN_PAYMENT_AMOUNT:
                        String amountAsString = entry.getValue();
                        double amountAsDouble = Double.parseDouble(amountAsString);
                        payment.setAmount(amountAsDouble);
                        break;

                    case COLUMN_PAYMENT_PAIDBY:
                        payment.setPaidBy(entry.getValue());
                        break;

                    case COLUMN_PAYMENT_PAIDTO:
                        payment.setPaidTo(entry.getValue());
                        break;

                }

            }
            list.add(payment);
        }
        return list;
    }

    @Override
    protected PreparedStatement getInsertPreparedStatement(Payment object, Connection connection) {
        String sql = "INSERT INTO " + TABLE_NAME + "(" + COLUMN_PAYMENT_TRANSACTIONID + "," +
                COLUMN_PAYMENT_PAIDBY+ "," +
                COLUMN_PAYMENT_PAIDTO+ "," +
                COLUMN_PAYMENT_AMOUNT + "," +") VALUES(?,?,?,?)";
        PreparedStatement pstmt = null;
        if (connection != null) {
            try {
                pstmt = connection.prepareStatement(sql);
                pstmt.setInt(1, object.getTransactionID());
                pstmt.setString(2, object.getPaidBy());
                pstmt.setString(3, object.getPaidTo());
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
        String[] primaryKeys = {COLUMN_PAYMENT_TRANSACTIONID + " INTEGER PRIMARY KEY AUTOINCREMENT"};
        String[] foreignKeys = {FOREIGNKEY_PAIDBY,FOREIGNKEY_PAIDTO};
        String[] stringFields = {COLUMN_PAYMENT_PAIDBY,COLUMN_PAYMENT_PAIDTO};
        String[] intFields = {};
        String[] doubleFields = {COLUMN_PAYMENT_AMOUNT};
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
            for (String i: intFields)
                sql += i + " INTEGER NOT NULL,\n";
            for (String d: doubleFields)
                sql += d + " REAL NOT NULL,\n";

            // foreign key (house_id) references houses(id),
            for (int i = 0 ; i < foreignKeys.length - 1; i++)
                sql += "foreign key " + foreignKeys[i] + ",\n";

            if( foreignKeys.length > 0)
                sql += "foreign key " + foreignKeys[foreignKeys.length-1] + "\n";

            sql += "\n);";
            return sql;
        }
        return null;
    }
}
