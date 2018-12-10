package Model.Purchase;

public class Purchase {

    private String vacationKey;
    private String sellerKey;
    private String sellerEmail;
    private String buyerKey;
    private String buyerEmail;
    private int timestamp;


    public Purchase(){}

    public Purchase(String vacationKey, String sellerKey, String sellerEmail, String buyerKey, String buyerEmail, int timestamp) {
        this.vacationKey = vacationKey;
        this.sellerKey = sellerKey;
        this.sellerEmail = sellerEmail;
        this.buyerKey = buyerKey;
        this.buyerEmail = buyerEmail;
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

    public String getSellerEmail() {
        return sellerEmail;
    }

    public void setSellerEmail(String sellerEmail) {
        this.sellerEmail = sellerEmail;
    }

    public String getBuyerKey() {
        return buyerKey;
    }

    public void setBuyerKey(String buyerKey) {
        this.buyerKey = buyerKey;
    }

    public String getBuyerEmail() {
        return buyerEmail;
    }

    public void setBuyerEmail(String buyerEmail) {
        this.buyerEmail = buyerEmail;
    }

    public int getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(int timestamp) {
        this.timestamp = timestamp;
    }
}
