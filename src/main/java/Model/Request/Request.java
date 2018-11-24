package Model.Request;

public class Request {

    private String vacationKey;
    private String sellerKey;
    private String buyerKey;
    private String approved;
    private int timestamp;

    public Request(){}

    public Request(String vacationKey, String sellerKey, String buyerKey, String approved, int timestamp) {
        this.vacationKey = vacationKey;
        this.sellerKey = sellerKey;
        this.buyerKey = buyerKey;
        this.approved = approved;
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

    public String getApproved() {
        return approved;
    }

    public void setApproved(String approved) {
        this.approved = approved;
    }

    public int getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(int timestamp) {
        this.timestamp = timestamp;
    }
}
