package Model.Vacation;

import Model.ACRUDModel;
import Model.User.UserModel;
import db.DBResult;
import db.Tables.VacationExchangeTable;
import db.Tables.VacationTable;

import java.time.LocalDateTime;
import java.util.List;

public class VacationModel extends ACRUDModel<Vacation> {


    VacationExchangeTable vacationExchangeTable;

    private int getCurrentTimeStamp() {
        String date = LocalDateTime.now().getYear() + "" + LocalDateTime.now().getMonthValue() + "" + LocalDateTime.now().getDayOfMonth();
        return Integer.parseInt(date);
    }

    public VacationModel() {
        super.setTableManager(VacationTable.getInstance());
        vacationExchangeTable = VacationExchangeTable.getInstance();
    }


    public void insertVacationToTable(String destination, double price, int departure) {
        String userName = UserModel.getUserName();
        Vacation vacation = new Vacation(null, userName, "TLV", destination, true, getCurrentTimeStamp(), price, departure);
        createNewData(vacation);

    }

    @Override
    public void updateTable(Vacation vacation) {
        DBResult result = DBResult.NONE;
        String [] whereFields = {VacationTable.COLUMN_VACATIONTABLE_KEY};
        String [] whereValues = {vacation.getVacationKey()};

        String [] set = {VacationTable.COLUMN_VACATIONTABLE_KEY,
                VacationTable.COLUMN_VACATIONTABLE_SELLERKEY,
                VacationTable.COLUMN_VACATIONTABLE_PRICE,
                VacationTable.COLUMN_VACATIONTABLE_ORIGIN,
                VacationTable.COLUMN_VACATIONTABLE_DESTINATION,
                VacationTable.COLUMN_VACATIONTABLE_TIMESTAMP,
                VacationTable.COLUMN_VACATIONTABLE_VISIBLE,
                VacationTable.COLUMN_VACATIONTABLE_DEPARTUREDATE};
        String [] values = {vacation.getVacationKey(),
                vacation.getSellerKey(),
                String.valueOf(vacation.getPrice()),
                vacation.getOrigin(),
                vacation.getDestination(),
                String.valueOf(vacation.getTimeStamp()),
                String.valueOf(vacation.isVisible()),
                String.valueOf(vacation.getDepartureDate())};


        result = tableManager.updateData(set , values, whereFields,whereValues);
        setChanged();
        notifyObservers(result);
    }

    public String getUserName() {
        return UserModel.getUserName();
    }

    @Override
    public List<Vacation> readDataFromDB(String[][] parameters) {
        //TODO - implement --> BETTER!
        String selection = parameters[0][0] + " IN (";
        for (int i=0 ; i < parameters[1].length - 1; i++) {
            selection += "\"" + parameters[1][i] + "\",";
        }
        selection += "\"" + parameters[1][parameters[1].length-1] + "\")";

        // Get the list of users from the database
        return tableManager.select(null,selection,null);
    }



    // VACATION EXCHANGE


    public DBResult insertVacationExchange(VacationExchange vacationExchange){
        DBResult result = DBResult.NONE;
        int v1 = vacationExchange.getV1();
        int v2 = vacationExchange.getV2();
        VacationExchange exchange1 = new VacationExchange(v1,v2);
        VacationExchange exchange2 = new VacationExchange(v2,v1);
        result = vacationExchangeTable.InsertToTable(exchange1);
        if (result == DBResult.ERROR)
            return result;
        result = vacationExchangeTable.InsertToTable(exchange2);
        return result;
    }



}
