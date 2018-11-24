package Model.Purchase;

public class Purchase {

    private String vacationKey;
    private String sellerKey;
    private String buyerKey;
    private int timestamp;

    // Todo (DONE)- add more fields

    public Purchase(){}


    public Purchase(String vacationKey, String sellerKey, String buyerKey, int timestamp) {
        this.vacationKey = vacationKey;
        this.sellerKey = sellerKey;
        this.buyerKey = buyerKey;
        this.timestamp = timestamp;
    }

    public String getVacationKey() {
        return vacationKey;
    }

    public void setVacationKey(String vacationKey) {
        this.vacationKey = vacationKey;
    }

    public String getSellerKey() {
        return sellerKey;
    }

    public void setSellerKey(String sellerKey) {
        this.sellerKey = sellerKey;
    }

    public String getBuyerKey() {
        return buyerKey;
    }

    public void setBuyerKey(String buyerKey) {
        this.buyerKey = buyerKey;
    }

    public int getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(int timestamp) {
        this.timestamp = timestamp;
    }
}
