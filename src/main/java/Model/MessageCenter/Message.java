package Model.MessageCenter;

import Model.Vacation.Vacation;

public class Message {

    private String messageType = "Purchase Request";
    private boolean isSeller;
    private String sellerName;
    private String buyerName;
    private Vacation vacation;

    Message(String seller, String buyer, Vacation vac, boolean isSeller){
        sellerName = seller;
        buyerName = buyer;
        vacation = vac;
        this.isSeller = isSeller;
    }

    public Vacation getVacation() {
        return vacation;
    }

    public String getBuyerName() {
        return buyerName;
    }

    public String getSellerName() {
        return sellerName;
    }

    public boolean isSeller() {
        return isSeller;
    }

    public String getMessageType() {
        return messageType;
    }
}
