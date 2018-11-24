package Model.Request;

public class Request {

    private String vacationKey;
    private String sellerKey;
    private String buyerKey;
    private boolean approved;
    private int timestamp;

    public Request(){}

    public Request(String vacationKey, String sellerKey, String buyerKey, boolean approved, int timestamp) {
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

    public boolean getApproved() {
        return approved;
    }

    public void setApproved(String approved) {
        this.approved = Boolean.getBoolean(approved);
    }

    public void setApproved(boolean approved) {
        this.approved = approved;
    }

    public int getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(int timestamp) {
        this.timestamp = timestamp;
    }

    public void setTimestamp(String timestamp){
        this.timestamp = Integer.valueOf(timestamp);
    }
}
