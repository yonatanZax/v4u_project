package db.Tables;

import Model.Purchase.Purchase;
import Model.Vacation.VacationExchange;
import db.DBResult;
import db.Managers.ATableManager;
import db.Managers.DBManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class VacationExchangeTable extends ATableManager<VacationExchange> {

    private static VacationExchangeTable ourInstance;

    public static final String COLUMN_VACATIONEXCHANGETABLE_VACATION1 = "vacation1";
    public static final String COLUMN_VACATIONEXCHANGETABLE_VACATION2 = "vacation2";

    // Singleton
    public static VacationExchangeTable getInstance() {
        if(ourInstance == null) {
            ourInstance = new VacationExchangeTable();
        }
        return ourInstance;
    }

    private VacationExchangeTable() {
        super(DBManager.getInstance(),"vacationExchangeInfo");
        createTable();
    }

    @Override
    protected List<VacationExchange> transformListMapToList(List<Map<String, String>> listMap) {
        List<VacationExchange> list = new ArrayList<>(listMap.size());
        for(Map<String,String> map : listMap){
            VacationExchange vacationExchange = new VacationExchange();
            for(Map.Entry<String,String> entry : map.entrySet()){
                String key = entry.getKey();
                switch (key){

                    case COLUMN_VACATIONEXCHANGETABLE_VACATION1:
                        vacationExchange.setV1(Integer.valueOf(entry.getValue()));
                        break;

                    case COLUMN_VACATIONEXCHANGETABLE_VACATION2:
                        vacationExchange.setV2(Integer.valueOf(entry.getValue()));
                        break;

                }

            }
            list.add(vacationExchange);
        }
        return list;
    }

    @Override
    protected PreparedStatement getInsertPreparedStatement(VacationExchange object, Connection connection) {
        String sql = "INSERT INTO " + TABLE_NAME + "(" + COLUMN_VACATIONEXCHANGETABLE_VACATION1 + "," +
                COLUMN_VACATIONEXCHANGETABLE_VACATION2  /*+ ","*/ +") VALUES(?,?)";
        PreparedStatement pstmt = null;
        if (connection != null) {
            try {
                pstmt = connection.prepareStatement(sql);
                pstmt.setInt(1, object.getV1());
                pstmt.setInt(2, object.getV2());
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
        String[] primaryKeys = {};
        String[] foreignKeys = {};
        String[] stringFields = {};
        String[] intFields = {COLUMN_VACATIONEXCHANGETABLE_VACATION1,COLUMN_VACATIONEXCHANGETABLE_VACATION2};
        String[] doubleFields = {};
        return super.createTable(primaryKeys, foreignKeys,stringFields,intFields,doubleFields);

    }

    @Override
    protected String getCreateTableSQLString(String[] primaryKeys, String[] foreignKeys, String[] stringFields,String[] intFields, String[] doubleFields) {
        if(primaryKeys != null) {
            String sql = "CREATE TABLE IF NOT EXISTS " + TABLE_NAME + " (\n";
            sql += intFields[0] + " INTEGER,\n";
            sql += intFields[1] + " INTEGER,\n";
            sql += "FOREIGN KEY ("+ intFields[0] + ") REFERENCES vacationInfo (" + VacationTable.COLUMN_VACATIONTABLE_KEY + "),\n";
            sql += "FOREIGN KEY ("+ intFields[1] + ") REFERENCES vacationInfo (" + VacationTable.COLUMN_VACATIONTABLE_KEY + "),\n";
            sql += "UNIQUE ("+ intFields[0] + "," + intFields[1] + ")";
            sql += "\n);";
            return sql;
        }
        return null;
    }
}
