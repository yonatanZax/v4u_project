package Model.Vacation;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Vacation {

    // Todo - add more fields ( also getters and setters )
    private StringProperty vacationKey;
    private StringProperty sellerKey;
    private StringProperty origin;
    private StringProperty destination;
    private boolean visible;
    private int timeStamp;

    public Vacation() {}

    public Vacation(String vacationKey, String sellerKey, String origin, String destination, boolean visible, int timeStamp) {
        this.vacationKey = new SimpleStringProperty(vacationKey);
        this.sellerKey = new SimpleStringProperty(sellerKey);
        this.origin = new SimpleStringProperty(origin);
        this.destination = new SimpleStringProperty(destination);
        this.visible = visible;
        this.timeStamp = timeStamp;
    }

    public String getVacationKey() {
        return vacationKey.get();
    }

    public void setVacationKey(String vacationKey) {
        this.vacationKey.setValue(vacationKey);
    }

    public String getSellerKey() {
        return sellerKey.get();
    }

    public void setSellerKey(String sellerKey) {
        this.sellerKey.setValue(sellerKey);
    }

    public String getOrigin() {
        return origin.get();
    }

    public void setOrigin(String origin) {
        this.origin.setValue(origin);
    }

    public String getDestination() {
        return destination.get();
    }

    public void setDestination(String destination) {
        this.destination.setValue(destination);
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
}