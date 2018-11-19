package Model.Purchase;

public class Purchase {

    private String purchaseKey;
    // Todo - add more fields

    public Purchase(String purchaseKey){
        this.purchaseKey = purchaseKey;
    }

    public String getPurchaseKey() {
        return purchaseKey;
    }

    public void setPurchaseKey(String purchaseKey) {
        this.purchaseKey = purchaseKey;
    }
}
