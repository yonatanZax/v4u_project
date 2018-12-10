package Model.Request;

import MainPackage.Enum_RequestState;
import Model.ACRUDModel;
import Model.User.UserModel;
import Model.Vacation.Vacation;
import Model.Vacation.VacationModel;
import db.DBResult;
import db.Tables.RequestTable;
import db.Tables.VacationTable;

import java.time.LocalDateTime;
import java.util.List;

public class RequestModel extends ACRUDModel<Request> {

    public RequestModel() {
        super.setTableManager(RequestTable.getInstance());
    }
    private VacationModel vacationModel = new VacationModel();

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
                RequestTable.COLUMN_REQUESTTABLE_STATUS,
                RequestTable.COLUMN_REQUESTTABLE_APPROVED,
                RequestTable.COLUMN_REQUESTTABLE_TIMESTAMP
                };
        String[] values = {request.getVacationKey(),
                request.getSellerKey(),
                request.getBuyerKey(),
                request.getState(),
                String.valueOf(request.getApproved()),
                String.valueOf(request.getTimestamp())};

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

    public void finishPurchase(Request request){
        String[][] requestParameters = {{RequestTable.COLUMN_REQUESTTABLE_VACATIONKEY},{request.getVacationKey()}};
        List<Request> list = readDataFromDB(requestParameters);
        for (Request req : list){
            req.setState(Request.states[3]);
            updateTable(req);
        }
        request.setState(Request.states[2]);
        updateTable(request);
    }

    public void updateApprovedRequest(Request request){
        request.setState(Request.states[1]);
        request.setApproved(true);
        updateTable(request);
        String[][] vacationParameters = {{VacationTable.COLUMN_VACATIONTABLE_KEY},{request.getVacationKey()}};
        List<Vacation> vacationList = vacationModel.readDataFromDB(vacationParameters);
        Vacation vacation = vacationList.get(0);
        vacation.setVisible(false);
        vacationModel.updateTable(vacation);
        String[][] requestParameters = {{RequestTable.COLUMN_REQUESTTABLE_VACATIONKEY},{request.getVacationKey()}};
        List<Request> list = readDataFromDB(requestParameters);
        for (Request req : list){
            req.setState(Request.states[1]);
            updateTable(req);
        }
    }
}
