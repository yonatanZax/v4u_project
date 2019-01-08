package db.Tables;

import Model.Vacation.Vacation;
import db.DBResult;
import db.Managers.ATableManager;
import db.Managers.DBManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class VacationTable extends ATableManager<Vacation> {


    private static VacationTable ourInstance;
    private final String FOREIGNKEY_SELLERKEY = "(" + COLUMN_VACATIONTABLE_SELLERKEY + ") references userInfo(key)";
    public static final String COLUMN_VACATIONTABLE_KEY = "key";
    public static final String COLUMN_VACATIONTABLE_SELLERKEY = "sellerKey";
    public static final String COLUMN_VACATIONTABLE_PRICE = "price";
    public static final String COLUMN_VACATIONTABLE_VISIBLE = "visible";
    public static final String COLUMN_VACATIONTABLE_EXCHANGEABLE = "exchange";
    public static final String COLUMN_VACATIONTABLE_DESTINATION = "destination";
    public static final String COLUMN_VACATIONTABLE_ORIGIN = "origin";
    public static final String COLUMN_VACATIONTABLE_DEPARTUREDATE = "departure_date";
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
        String[] primaryKeys = {COLUMN_VACATIONTABLE_KEY + " INTEGER PRIMARY KEY AUTOINCREMENT"};
        String[] foreignKeys = {FOREIGNKEY_SELLERKEY};
        String[] stringFields = {COLUMN_VACATIONTABLE_SELLERKEY, COLUMN_VACATIONTABLE_ORIGIN, COLUMN_VACATIONTABLE_DESTINATION, COLUMN_VACATIONTABLE_VISIBLE, COLUMN_VACATIONTABLE_EXCHANGEABLE};
        String[] intFields = {COLUMN_VACATIONTABLE_TIMESTAMP, COLUMN_VACATIONTABLE_DEPARTUREDATE};
        String[] doubleFields = {COLUMN_VACATIONTABLE_PRICE};
        return super.createTable(primaryKeys, foreignKeys, stringFields, intFields, doubleFields);
    }

    private int getCurrentTimeStamp() {
        String date = LocalDateTime.now().getYear() + "" + LocalDateTime.now().getMonthValue() + "" + LocalDateTime.now().getDayOfMonth();
        return Integer.parseInt(date);
    }

    public DBResult updateTableByDate(){
        String sql = "UPDATE " + TABLE_NAME +
                    " SET " + COLUMN_VACATIONTABLE_VISIBLE + " = 'false'" +
                    " WHERE " + COLUMN_VACATIONTABLE_DEPARTUREDATE + " <= " + getCurrentTimeStamp();
        DBResult result = executeQuery(sql);
        return result;

    }



    @Override /* Has  INTEGER PRIMARY KEY AUTOINCREMENT */
    protected String getCreateTableSQLString(String[] primaryKeys, String[] foreignKeys, String[] stringFields, String[] intFields, String[] doubleFields) {
        if (primaryKeys != null) {
            String sql = "CREATE TABLE IF NOT EXISTS " + TABLE_NAME + " (\n";
            for (String key : primaryKeys)
                sql += key + " ,\n";

            for (String str : stringFields)
                sql += str + " TEXT NOT NULL,\n";
            for (String i : intFields)
                sql += i + " INTEGER NOT NULL,\n";
            for (String d : doubleFields)
                sql += d + " REAL NOT NULL,\n";

            // foreign key (house_id) references houses(id),
            for (int i = 0; i < foreignKeys.length - 1; i++)
                sql += "foreign key " + foreignKeys[i] + ",\n";
            if (foreignKeys.length > 0)
                sql += "foreign key " + foreignKeys[foreignKeys.length - 1] + "\n";


            sql += "\n);";
            return sql;
        }
        return null;
    }


    @Override
    protected List<Vacation> transformListMapToList(List<Map<String, String>> listMap) {
        List<Vacation> list = new ArrayList<>(listMap.size());
        for (Map<String, String> map : listMap) {
            Vacation vacation = new Vacation();
            for (Map.Entry<String, String> entry : map.entrySet()) {
                String key = entry.getKey();
                switch (key) {
                    case COLUMN_VACATIONTABLE_TIMESTAMP:
                        String timestampAsString = entry.getValue();
                        int timestampAsInt = Integer.parseInt(timestampAsString);
                        vacation.setTimeStamp(timestampAsInt);
                        break;

                    case COLUMN_VACATIONTABLE_KEY:
                        vacation.setVacationKey(entry.getValue());
                        break;

                    case COLUMN_VACATIONTABLE_SELLERKEY:
                        vacation.setSellerKey(entry.getValue());
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
                    case COLUMN_VACATIONTABLE_EXCHANGEABLE:
//                        vacation.setExchangeable(entry.getValue());
                        vacation.setExchangeable(entry.getValue().equals("true"));
                        break;
                    case COLUMN_VACATIONTABLE_PRICE:
                        vacation.setPrice(Double.parseDouble(entry.getValue()));
                        break;
                    case COLUMN_VACATIONTABLE_DEPARTUREDATE:
                        String departureDateAsString = entry.getValue();
                        int departureDateAsInt = Integer.parseInt(departureDateAsString);
                        vacation.setDepartureDate(departureDateAsInt);
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
                + "," + COLUMN_VACATIONTABLE_VISIBLE
                + "," + COLUMN_VACATIONTABLE_EXCHANGEABLE
                + "," + COLUMN_VACATIONTABLE_TIMESTAMP
                + "," + COLUMN_VACATIONTABLE_DEPARTUREDATE
                + "," + COLUMN_VACATIONTABLE_PRICE
                + ") VALUES(?,?,?,?,?,?,?,?,?)";
        PreparedStatement pstmt = null;
        if (connection != null) {
            try {
                pstmt = connection.prepareStatement(sql);
                pstmt.setString(1, object.getVacationKey());
                pstmt.setString(2, object.getSellerKey());
                pstmt.setString(3, object.getOrigin());
                pstmt.setString(4, object.getDestination());
                pstmt.setString(5, object.isVisible() + "");
                pstmt.setString(6, object.isExchangeable() + "");
                pstmt.setInt(7, object.getTimeStamp());
                pstmt.setInt(8, object.getDepartureDate());
                pstmt.setDouble(9, object.getPrice());
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
