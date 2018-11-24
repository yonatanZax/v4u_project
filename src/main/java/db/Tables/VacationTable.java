package db.Tables;

import Model.Vacation.Vacation;
import db.DBResult;
import db.Managers.ATableManager;
import db.Managers.DBManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public class VacationTable extends ATableManager<Vacation> {


    private static VacationTable ourInstance;
    public final String COLUMN_VACATIONTABLE_KEY = "key";
    public static final String COLUMN_VACATIONTABLE_SELLERKEY = "sellerKey";
    private final String FOREIGNKEY_SELLERKEY = "(" + COLUMN_VACATIONTABLE_SELLERKEY + ") references userInfo(key)";
    public static final String COLUMN_VACATIONTABLE_VISIBLE = "visible";
    public static final String COLUMN_VACATIONTABLE_DESTINATION = "destination";
    public static final String COLUMN_VACATIONTABLE_ORIGIN = "origin";
    public static final String COLUMN_VACATIONTABLE_TIMESTAMP = "timestamp";

    // Todo - add more columns


    // Singleton
    public static VacationTable getInstance() {
        if (ourInstance == null) {
            ourInstance = new VacationTable();
        }
        return ourInstance;
    }

    private VacationTable() {
        super(DBManager.getInstance(), "vacationInfo");
        createTable();
    }

    @Override
    public DBResult createTable() {
        String[] primaryKeys = {COLUMN_VACATIONTABLE_KEY};
        String[] foreignKeys = {FOREIGNKEY_SELLERKEY};
        String[] stringFields = {COLUMN_VACATIONTABLE_SELLERKEY, COLUMN_VACATIONTABLE_ORIGIN, COLUMN_VACATIONTABLE_DESTINATION};
        String[] intFields = {COLUMN_VACATIONTABLE_TIMESTAMP, COLUMN_VACATIONTABLE_VISIBLE};
        String[] doubleFields = {};
        return super.createTable(primaryKeys, foreignKeys, stringFields, intFields, doubleFields);
    }


    // Todo - implement
    @Override
    protected List<Vacation> transformListMapToList(List<Map<String, String>> listMap) {
        return null;
    }


    @Override
    protected PreparedStatement getInsertPreparedStatement(Vacation object, Connection connection) {
        /*
         * vacationKey
         * sellerKey
         * price
         * flightNo
         * flightCompany
         * origin
         * dest
         * fromDate
         * toDate
         * numOfTickets
         * flightBack
         *
         *
         * */
        String sql = "INSERT INTO " + TABLE_NAME + "("
                + COLUMN_VACATIONTABLE_KEY
                + "," + COLUMN_VACATIONTABLE_SELLERKEY
                + "," + COLUMN_VACATIONTABLE_ORIGIN
                + "," + COLUMN_VACATIONTABLE_DESTINATION
                + "," + COLUMN_VACATIONTABLE_TIMESTAMP
                + "," + COLUMN_VACATIONTABLE_VISIBLE
                + ") VALUES(?,?,?,?,?,?)";
        PreparedStatement pstmt = null;
        if (connection != null) {
            try {
                pstmt = connection.prepareStatement(sql);
                pstmt.setString(1, object.getVacationKey());
                pstmt.setString(2, object.getSellerKey());
                pstmt.setString(3, object.getOrigin());
                pstmt.setString(4, object.getDestination());
                pstmt.setObject(5, object.isVisible()? 1 : 0);
                pstmt.setObject(6, object.getTimeStamp());
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
