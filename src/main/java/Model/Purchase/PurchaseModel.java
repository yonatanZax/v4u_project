package Model.Purchase;

import Model.ACRUDModel;
import db.DBResult;
import db.Tables.PurchaseTable;

import java.time.LocalDateTime;
import java.util.List;

public class PurchaseModel extends ACRUDModel<Purchase> {


    public PurchaseModel(){ super.setTableManager(PurchaseTable.getInstance());}


    @Override
    public void updateTable(Purchase purchase) {
        DBResult result = DBResult.NONE;

        String [] whereFields = { PurchaseTable.COLUMN_PURCHASETABLE_VACATIONKEY,
                PurchaseTable.COLUMN_PURCHASETABLE_SELLERKEY,
                                    PurchaseTable.COLUMN_PURCHASETABLE_SELLEREMAIL,
                                    PurchaseTable.COLUMN_PURCHASETABLE_BUYERKEY,
                                    PurchaseTable.COLUMN_PURCHASETABLE_BUYEREMAIL};

        String[] whereValues = {purchase.getVacationKey(),
                                purchase.getSellerKey(),
                                purchase.getSellerEmail(),
                                purchase.getBuyerKey(),
                                purchase.getBuyerEmail()};


        String [] set = {PurchaseTable.COLUMN_PURCHASETABLE_VACATIONKEY,
                PurchaseTable.COLUMN_PURCHASETABLE_SELLERKEY,
                            PurchaseTable.COLUMN_PURCHASETABLE_SELLEREMAIL,
                PurchaseTable.COLUMN_PURCHASETABLE_BUYERKEY,
                            PurchaseTable.COLUMN_PURCHASETABLE_BUYEREMAIL,
                PurchaseTable.COLUMN_PURCHASETABLE_TIMESTAMP};
        String [] values = {purchase.getVacationKey(),
                purchase.getSellerKey(),
                            purchase.getSellerEmail(),
                purchase.getBuyerKey(),
                            purchase.getBuyerEmail(),
                String.valueOf(purchase.getTimestamp())};


        result = tableManager.updateData(set , values, whereFields,whereValues);
        setChanged();
        notifyObservers(result);
    }

    @Override
    public List<Purchase> readDataFromDB(String[][] parameters) {
        String selection = parameters[0][0] + " IN (";
        for (int i = 0; i < parameters[1].length - 1; i++) {
            selection += "\"" + parameters[1][i] + "\",";
        }
        selection += "\"" + parameters[1][parameters[1].length - 1] + "\")";

        // Get the list of users from the database
        List<Purchase> dataList = tableManager.select(null, selection, null);
        return dataList;
    }

    @Override
    public void deleteDataFromDB(String[][] keys) {
        // Todo - implement
        String where = "";
        for (int i = 0; i < keys.length - 1 ; i++)
            where += keys[i][0] + " = " + keys[i][1] + "AND \n";
        where += keys[keys.length - 1][0] + " = " + keys[keys.length - 1][1];
        tableManager.deleteFromTable(where);
    }

    public int getCurrentTimeStamp() {
        String date = LocalDateTime.now().getYear() + "" + LocalDateTime.now().getMonthValue() + "" + LocalDateTime.now().getDayOfMonth();
        return Integer.parseInt(date);
    }

    public void insertPurchaseToTable(String vacationKey, String seller, String sellerAccount, String buyer) {
        Purchase purchase = new Purchase(vacationKey,seller,sellerAccount,buyer,null,getCurrentTimeStamp());
        createNewData(purchase);
        System.out.println("Purchase Added to DB: " + "VacationID: "+ vacationKey + "Seller: "+ seller + "Seller Account: "+ sellerAccount + ", Buyer: "+ buyer);
    }
}
