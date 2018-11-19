package db.Tables;

import Model.Purchase.Purchase;
import db.DBResult;
import db.Managers.ATableManager;
import db.Managers.DBManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public class PurchaseTable extends ATableManager<Purchase> {

    private static PurchaseTable ourInstance;

    // Todo - add more columns


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


    // Todo - implement
    @Override
    protected List<Purchase> transformListMapToList(List<Map<String, String>> listMap) {
        return null;
    }



    @Override
    public DBResult createTable() {
        // Todo - add parameters
        String[] parameters = {COLUMN_TABLE_KEY + " text PRIMARY KEY"
//                , COLUMN_USERTABLE_PASS + " text NOT NULL"};
        };
        return super.createTable(parameters);
    }

    @Override
    protected PreparedStatement getInsertPreparedStatement(Purchase object, Connection connection) {
        String sql = "INSERT INTO " + TABLE_NAME + "(" + COLUMN_TABLE_KEY + "," + ") VALUES(?,)";
        PreparedStatement pstmt = null;
        if (connection != null) {
            try {
                pstmt = connection.prepareStatement(sql);
                pstmt.setString(1, object.getPurchaseKey());
                // Todo - add more parameters here
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
