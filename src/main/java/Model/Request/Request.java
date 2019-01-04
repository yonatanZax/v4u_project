package Model.Request;

import Model.Vacation.Vacation;

public class Request {

    private String vacationKey;
    private String sellerKey;
    private String buyerKey;
    private boolean approved;
    private int timestamp;
    private String state;
//    private boolean exchange;
    private int vacationToExchange = 0;

    public static String[] states = {"PENDING","HOLD","SOLD","DENIED"};

    public Request(){}

    public Request(String vacationKey, String sellerKey, String buyerKey, boolean approved, int timestamp, String status, int vacToExchange) {
        this.vacationKey = vacationKey;
        this.sellerKey = sellerKey;
        this.buyerKey = buyerKey;
        this.approved = approved;
        this.timestamp = timestamp;
        this.state = status;
        this.vacationToExchange = vacToExchange;
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

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

//    public boolean isExchange() {
//        return exchange;
//    }

//    public void setExchange(boolean exchange) {
//        this.exchange = exchange;
//    }

    public int getVacationToExchange() {
        return vacationToExchange;
    }

    public void setVacationToExchange(int vacationToExchange) {
        this.vacationToExchange = vacationToExchange;
    }
}
