package Model.Vacation;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Vacation {


    private StringProperty vacationKey;
    private StringProperty sellerKey;
    private StringProperty origin;
    private StringProperty destination;
    private boolean visible;
    private int timeStamp;
    private double price;
    private int departureDate;
    private boolean isExchangeable;

    public Vacation() {
    }

    public Vacation(String vacationKey, String sellerKey, String origin, String destination, boolean visible, int timeStamp, double price, int departureDate, boolean isExchangeable) {
        this.vacationKey = new SimpleStringProperty(vacationKey);
        this.sellerKey = new SimpleStringProperty(sellerKey);
        this.price = price;
        this.origin = new SimpleStringProperty(origin);
        this.destination = new SimpleStringProperty(destination);
        this.visible = visible;
        this.timeStamp = timeStamp;
        this.departureDate = departureDate;
        this.isExchangeable = isExchangeable;
    }

    public String getVacationKey() {
        return vacationKey.get();
    }

    public void setVacationKey(String vacationKey) {
        this.vacationKey = new SimpleStringProperty(vacationKey);
    }

    public String getSellerKey() {
        return sellerKey.get();
    }

    public void setSellerKey(String sellerKey) {
        this.sellerKey = new SimpleStringProperty(sellerKey);
    }

    public String getOrigin() {
        return origin.get();
    }

    public void setOrigin(String origin) {
        this.origin = new SimpleStringProperty(origin);
    }

    public String getDestination() {
        return destination.get();
    }

    public void setDestination(String destination) {
        this.destination = new SimpleStringProperty(destination);
    }

    public StringProperty vacationKeyProperty() {
        return vacationKey;
    }

    public StringProperty sellerKeyProperty() {
        return sellerKey;
    }

    public StringProperty originProperty() {
        return origin;
    }

    public StringProperty destinationProperty() {
        return destination;
    }

    public boolean isVisible() {
        return visible;
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
    }

    public void setVisible(String visible) {
        this.visible = Boolean.getBoolean(visible);
    }

    public int getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(int timeStamp) {
        this.timeStamp = timeStamp;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public boolean isExchangeable() {
        return isExchangeable;
    }

    public void setExchangeable(String exchange) {
        this.isExchangeable = Boolean.getBoolean(exchange);
    }

    public void setExchangeable(boolean exchangeable) {
        this.isExchangeable = exchangeable;
    }

    public int getDepartureDate() {
        return departureDate;
    }

    public void setDepartureDate(int departureDate) {
        this.departureDate = departureDate;
    }

    @Override
    public String toString() {
        String ans = "VacationDetails:\n";
        ans += "\tDestination: " + this.getDestination() + '\n';
        ans += "\tPrice: " + price + "\n";
        ans += "\tDeparture Date: " + departureDate + "\n";
        return ans;
    }
}
