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

    // Todo - add more columns



    // Singleton
    public static VacationTable getInstance() {
        if(ourInstance == null) {
            ourInstance = new VacationTable();
        }
        return ourInstance;
    }

    private VacationTable() {
        super(DBManager.getInstance(),"vacationInfo");
        createTable();
    }

    @Override
    public DBResult createTable() {
        // Todo - add parameters
        String[] parameters = {COLUMN_TABLE_KEY + " text PRIMARY KEY"
//                , COLUMN_USERTABLE_PASS + " text NOT NULL"};
        };
        return super.createTable(parameters);
    }


    // Todo - implement
    @Override
    protected List<Vacation> transformListMapToList(List<Map<String, String>> listMap) {
        return null;
    }



    @Override
    protected PreparedStatement getInsertPreparedStatement(Vacation object, Connection connection) {
        /*
        * Key
        * SellerID
        * Price
        * FlightNo
        * FlightCompany
        * Origin
        * Dest
        * FromDate
        * ToDate
        * numOfTickets
        * FlightBack
        *
        *
        * */


        String sql = "INSERT INTO " + TABLE_NAME + "(" + COLUMN_TABLE_KEY + "," + ") VALUES(?,)";
        PreparedStatement pstmt = null;
        if (connection != null) {
            try {
                pstmt = connection.prepareStatement(sql);
                pstmt.setString(1, object.getVacationKey());
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
