package Model.Vacation;

public class Vacation {

    private String vacationKey;
    private String sellerKey;
    private String origin;
    private String destination;
    private boolean visible;
    private int timeStamp;

    public Vacation() {}

    public Vacation(String vacationKey, String sellerKey, String origin, String destination, boolean visible, int timeStamp) {
        this.vacationKey = vacationKey;
        this.sellerKey = sellerKey;
        this.origin = origin;
        this.destination = destination;
        this.visible = visible;
        this.timeStamp = timeStamp;
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

    public String getOrigin() {
        return origin;
    }

    public void setOrigin(String origin) {
        this.origin = origin;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
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
