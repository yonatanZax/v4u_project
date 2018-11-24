package db.Tables;

import Model.Vacation.Vacation;
import db.DBResult;
import db.Managers.ATableManager;
import db.Managers.DBManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class VacationTable extends ATableManager<Vacation> {


    private static VacationTable ourInstance;
    public static final String COLUMN_VACATIONTABLE_KEY = "key";
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
        String[] stringFields = {COLUMN_VACATIONTABLE_SELLERKEY, COLUMN_VACATIONTABLE_ORIGIN, COLUMN_VACATIONTABLE_DESTINATION, COLUMN_VACATIONTABLE_VISIBLE};
        String[] intFields = {COLUMN_VACATIONTABLE_TIMESTAMP};
        String[] doubleFields = {};
        return super.createTable(primaryKeys, foreignKeys, stringFields, intFields, doubleFields);
    }


    // Todo - implement
    @Override
    protected List<Vacation> transformListMapToList(List<Map<String, String>> listMap) {
        List<Vacation> list = new ArrayList<>(listMap.size());
        for(Map<String,String> map : listMap){
            Vacation vacation = new Vacation();
            for(Map.Entry<String,String> entry : map.entrySet()){
                String key = entry.getKey();
                switch (key){
                    case COLUMN_VACATIONTABLE_TIMESTAMP:
                        String timestampAsString = entry.getValue();
                        int timestampAsInt = Integer.parseInt(timestampAsString);
                        vacation.setTimeStamp(timestampAsInt);
                        break;

                    case COLUMN_VACATIONTABLE_KEY:
                        vacation.setVacationKey( entry.getValue());
                        break;

                    case COLUMN_VACATIONTABLE_SELLERKEY:
                        vacation.setSellerKey( entry.getValue());
                        break;

                    case COLUMN_VACATIONTABLE_DESTINATION:
                        vacation.setDestination(entry.getValue());
                        break;

                    case COLUMN_VACATIONTABLE_ORIGIN:
                        vacation.setOrigin(entry.getValue());
                        break;

                    case COLUMN_VACATIONTABLE_VISIBLE:
                        vacation.setVisible(entry.getValue().equals("true"));
                        break;
                }

            }
            list.add(vacation);
        }
        return list;


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
                pstmt.setString(5, object.isVisible()+"");
                pstmt.setInt(6, object.getTimeStamp());
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
