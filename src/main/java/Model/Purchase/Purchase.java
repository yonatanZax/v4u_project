package Model.Purchase;

public class Purchase {

    private String vacationKey;
    private String sellerKey;
    private String sellerInfo;
    private String buyerKey;
    private String buyerVacationToExchange;
    private int timestamp;


    public Purchase(){}

    public Purchase(String vacationKey, String sellerKey, String sellerInfo, String buyerKey, String buyerVacEx, int timestamp) {
        this.vacationKey = vacationKey;
        this.sellerKey = sellerKey;
        this.sellerInfo = sellerInfo;
        this.buyerKey = buyerKey;
        this.buyerVacationToExchange = buyerVacEx;
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

    public String getSellerInfo() {
        return sellerInfo;
    }

    public void setSellerInfo(String sellerInfo) {
        this.sellerInfo = sellerInfo;
    }

    public String getBuyerVacationToExchange() {
        return buyerVacationToExchange;
    }

    public void setBuyerVacationToExchange(String buyerVacationToExchange) {
        this.buyerVacationToExchange = buyerVacationToExchange;
    }
}
