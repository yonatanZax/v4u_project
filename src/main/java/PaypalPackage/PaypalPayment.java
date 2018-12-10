package PaypalPackage;

public class PaypalPayment {

    private String transactionID;
    private String paidByEmail;
    private String paidToEmail;
    private double amount;

    public PaypalPayment() {
    }

    public PaypalPayment(String transactionID, String paidByEmail, String paidToEmail, double amount) {
        this.transactionID = transactionID;
        this.paidByEmail = paidByEmail;
        this.paidToEmail = paidToEmail;
        this.amount = amount;
    }

    public String getTransactionID() {
        return transactionID;
    }

    public void setTransactionID(String transactionID) {
        this.transactionID = transactionID;
    }

    public String getPaidByEmail() {
        return paidByEmail;
    }

    public void setPaidByEmail(String paidByEmail) {
        this.paidByEmail = paidByEmail;
    }

    public String getPaidToEmail() {
        return paidToEmail;
    }

    public void setPaidToEmail(String paidToEmail) {
        this.paidToEmail = paidToEmail;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }
}
