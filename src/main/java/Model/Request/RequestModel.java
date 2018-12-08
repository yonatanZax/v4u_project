package Model.Request;

import Model.ACRUDModel;
import Model.Purchase.Purchase;
import db.DBResult;
import db.Tables.PurchaseTable;
import db.Tables.RequestTable;
import db.Tables.UserTable;

import java.util.List;

public class RequestModel extends ACRUDModel<Request> {

    public RequestModel() {
        super.setTableManager(RequestTable.getInstance());
    }


    @Override
    public void updateTable(Request request) {
        DBResult result = DBResult.NONE;

        String [] whereFields = {RequestTable.COLUMN_REQUESTTABLE_VACATIONKEY,
                RequestTable.COLUMN_REQUESTTABLE_SELLERKEY,
                RequestTable.COLUMN_REQUESTTABLE_BUYERKEY};
        String[] whereValues = {request.getVacationKey(),request.getSellerKey(),request.getBuyerKey()};

        // Todo (DONE) - implement set and values
        String [] set = {RequestTable.COLUMN_REQUESTTABLE_VACATIONKEY,
                RequestTable.COLUMN_REQUESTTABLE_SELLERKEY,
                RequestTable.COLUMN_REQUESTTABLE_BUYERKEY,
                RequestTable.COLUMN_REQUESTTABLE_APPROVED,
                RequestTable.COLUMN_REQUESTTABLE_TIMESTAMP};
        String [] values = {request.getVacationKey(),
                request.getSellerKey(),
                request.getBuyerKey(),
                String.valueOf(request.getApproved()),
                String.valueOf(request.getTimestamp())};


        result = tableManager.updateData(set , values, whereFields, whereValues);
        setChanged();
        notifyObservers(result);
    }

    @Override
    public List<Request> readDataFromDB(String[][] parameters) {
        // Todo - implement --> BETTER!!
        String selection = parameters[0] + " IN (";
        for (int i=0 ; i < parameters.length - 1; i++) {
            selection += "\"" + parameters[i][1] + "\",";
    }
        selection += "\"" + parameters[parameters.length-1][1] + "\")";

        // Get the list of users from the database
        List<Request> dataList = tableManager.select(null,selection,null);
        return dataList;
    }
}
