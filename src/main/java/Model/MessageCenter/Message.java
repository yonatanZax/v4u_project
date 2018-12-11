package Model.MessageCenter;

import Model.Request.Request;
import Model.Vacation.Vacation;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Message {

    private StringProperty messageType = new SimpleStringProperty("Purchase Message");
    private boolean isSeller;
    private StringProperty sellerName;
    private StringProperty buyerName;
    private Vacation vacation;
    private Request request;

    Message(String seller, String buyer, Vacation vac, boolean isSeller, Request request) {
        sellerName = new SimpleStringProperty(seller);
        buyerName = new SimpleStringProperty(buyer);
        vacation = vac;
        this.isSeller = isSeller;
        this.request = request;
    }

    public Vacation getVacation() {
        return vacation;
    }

    public StringProperty getBuyerNameProperty() {
        return buyerName;
    }

    public String getBuyerName() {
        return buyerName.get();
    }

    public StringProperty getSellerNameProperty() {
        return sellerName;
    }

    public String getSellerName() {
        return sellerName.get();
    }

    public boolean isSeller() {
        return isSeller;
    }

    public StringProperty getMessageTypeProperty() {
        return messageType;
    }

    public String getMessageType() {
        return messageType.get();
    }

    public Request getRequest() {
        return request;
    }

    public void setRequest(Request request) {
        this.request = request;
    }
}
