package Model.Request;

import MainPackage.Enum_RequestState;
import Model.ACRUDModel;
import Model.User.UserModel;
import Model.Vacation.Vacation;
import Model.Vacation.VacationModel;
import db.DBResult;
import db.Tables.RequestTable;

import java.time.LocalDateTime;
import java.util.List;

public class RequestModel extends ACRUDModel<Request> {

    public RequestModel() {
        super.setTableManager(RequestTable.getInstance());
    }


    @Override
    public void updateTable(Request request) {
        DBResult result = DBResult.NONE;

        String[] whereFields = {RequestTable.COLUMN_REQUESTTABLE_VACATIONKEY,
                RequestTable.COLUMN_REQUESTTABLE_SELLERKEY,
                RequestTable.COLUMN_REQUESTTABLE_BUYERKEY};
        String[] whereValues = {request.getVacationKey(), request.getSellerKey(), request.getBuyerKey()};

        String[] set = {RequestTable.COLUMN_REQUESTTABLE_VACATIONKEY,
                RequestTable.COLUMN_REQUESTTABLE_SELLERKEY,
                RequestTable.COLUMN_REQUESTTABLE_BUYERKEY,
                RequestTable.COLUMN_REQUESTTABLE_APPROVED,
                RequestTable.COLUMN_REQUESTTABLE_TIMESTAMP,
                RequestTable.COLUMN_REQUESTTABLE_STATUS};
        String[] values = {request.getVacationKey(),
                request.getSellerKey(),
                request.getBuyerKey(),
                String.valueOf(request.getApproved()),
                String.valueOf(request.getTimestamp()),
                request.getState()};

        result = tableManager.updateData(set, values, whereFields, whereValues);
        setChanged();
        notifyObservers(result);
    }

    private int getCurrentTimeStamp() {
        String date = LocalDateTime.now().getYear() + "" + LocalDateTime.now().getMonthValue() + "" + LocalDateTime.now().getDayOfMonth();
        return Integer.parseInt(date);
    }

    @Override
    public List<Request> readDataFromDB(String[][] parameters) {
        // Todo - implement --> BETTER!!
        String selection = parameters[0][0] + " IN (";
        for (int i = 0; i < parameters[1].length - 1; i++) {
            selection += "\"" + parameters[1][i] + "\",";
        }
        selection += "\"" + parameters[1][parameters[1].length - 1] + "\")";

        // Get the list of users from the database
        List<Request> dataList = tableManager.select(null, selection, null);
        return dataList;
    }

    public void insertRequestToTable(String vacationKey, String seller) {
        String userName = UserModel.getUserName();
        Request request = new Request(vacationKey, seller, userName, false, getCurrentTimeStamp(),Request.states[0]);
        createNewData(request);
    }
}
