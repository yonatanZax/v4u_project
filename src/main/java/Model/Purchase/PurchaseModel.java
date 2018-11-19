package Model.Purchase;

import Model.ACRUDModel;
import db.DBResult;
import db.Tables.PurchaseTable;

public class PurchaseModel extends ACRUDModel<Purchase> {

    private PurchaseTable purchaseTable;

    public PurchaseModel(){ purchaseTable.getInstance();}


    @Override
    public void updateTable(Purchase purchase) {
        DBResult result = DBResult.NONE;
        String [] where = {purchaseTable.COLUMN_TABLE_KEY};

        // Todo - implement set and values
        String [] set = {purchaseTable.COLUMN_TABLE_KEY};
        String [] values = {purchase.getPurchaseKey()};


        result = purchaseTable.updateData(set , values, where);
        setChanged();
        notifyObservers(result);
    }
}
