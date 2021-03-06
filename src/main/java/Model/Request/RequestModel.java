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

    private VacationModel vacationModel;

    public RequestModel(VacationModel vacationModel) {
        this.vacationModel = vacationModel;
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

    @Override
    public List<Request> readDataFromDB(String[][] parameters) {
        String selection = parameters[0][0] + " IN (";
        for (int i = 0; i < parameters[1].length - 1; i++) {
            selection += "\"" + parameters[1][i] + "\",";
        }
        selection += "\"" + parameters[1][parameters[1].length - 1] + "\")";

        // Get the list of users from the database
        List<Request> dataList = tableManager.select(null, selection, null);
        return dataList;
    }

    public void insertRequestToTable(String vacationKey, String seller, int vacToExchange) {
        String userName = UserModel.getUserName();
        Request request = new Request(vacationKey, seller, userName, false, getCurrentTimeStamp(), Request.states[0], vacToExchange);
        createNewData(request);
    }

    public void finishPurchase(Request request) {

        String[][] requestParameters = {{RequestTable.COLUMN_REQUESTTABLE_VACATIONKEY}, {request.getVacationKey()}};
        List<Request> list = readDataFromDB(requestParameters);

        // set vacation to Visible = false, if vacation exchanged change the exchanged to Visible = false also.
        String[][] vacationParameters = {{VacationTable.COLUMN_VACATIONTABLE_KEY}, {request.getVacationKey()}};
        List<Vacation> vacationList = vacationModel.readDataFromDB(vacationParameters);
        Vacation vacation = vacationList.get(0);
        vacation.setVisible(false);
        vacationModel.updateTable(vacation);

        if (request.getVacationToExchange() != 0) {
            vacationParameters[1][0] = String.valueOf(request.getVacationToExchange());
            vacationList = vacationModel.readDataFromDB(vacationParameters);
            vacation = vacationList.get(0);
            vacation.setVisible(false);
            vacationModel.updateTable(vacation);
        }

        for (Request req : list) {
            req.setState(Request.states[3]);
            updateTable(req);
        }
        request.setState(Request.states[2]);
        updateTable(request);
    }

    public void updateApprovedRequest(Request request) {
        request.setState(Request.states[1]);
        request.setApproved(true);
        updateTable(request);
        String[][] requestParameters = {{RequestTable.COLUMN_REQUESTTABLE_VACATIONKEY}, {request.getVacationKey()}};
        List<Request> list = readDataFromDB(requestParameters);
        for (Request req : list) {
                req.setState(Request.states[1]);
                updateTable(req);
        }
    }

    public VacationModel getVacationModel() {
        return vacationModel;
    }
}
