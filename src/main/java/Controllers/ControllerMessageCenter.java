package Controllers;

import Model.MessageCenter.ListMessageContent;
import Model.MessageCenter.Message;
import Model.MessageCenter.MessageModel;
import Model.Purchase.Purchase;
import Model.Purchase.PurchaseModel;
import Model.Request.Request;
import Model.Request.RequestModel;
import Model.User.UserModel;
import Model.Vacation.Vacation;
import Model.Vacation.VacationModel;
import PaypalPackage.PayPalDBManager;
import PaypalPackage.PaypalPayment;
import PaypalPackage.PaypalTable;
import View.MessageCenterView;
import db.Tables.PurchaseTable;
import db.Tables.VacationTable;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.util.Pair;

import java.io.IOException;
import java.util.List;
import java.util.Observable;
import java.util.Observer;
import java.util.Optional;

public class ControllerMessageCenter extends Observable implements Observer, SubScenable {

    MessageCenterView messageCenterView;

    private UserModel userModel = new UserModel();
    private RequestModel requestModel = new RequestModel();
    private PurchaseModel purchaseModel = new PurchaseModel();
    private MessageModel messageModel = new MessageModel();
    private Parent root;
    private FXMLLoader fxmlLoader;

    private Request pickedRequest;

    public static final String REQUEST_PICKED = "request_picked";

    public ControllerMessageCenter() {
        fxmlLoader = new FXMLLoader(getClass().getResource("/messageCenter_view.fxml"));
        try {
            root = fxmlLoader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }

        messageCenterView = fxmlLoader.getController();
        messageCenterView.addObserver(this);
    }


    @Override
    public Parent getRoot() {
        return root;
    }

    @Override
    public void updateSubScene() {
        messageCenterView.messageCenter_tableList.setItems(null);
        fillTableList();
    }

    // todo - cant send PURCHASE REQUEST TWICE.. need to be checked!!!!!
    // todo - change table list colours to be different than the search list (home page)

    private void fillTableList() {
        messageModel.checkIfApprovalDue();
        messageModel.setMessagesForUser();
        List<Pair<Message, String[]>> messagesParameters = messageModel.createMessageParametersForController();
        ObservableList<ListMessageContent> data = FXCollections.observableArrayList();
        for (Pair<Message, String[]> parameter : messagesParameters) {
            ListMessageContent messageContent = new ListMessageContent(parameter.getValue()[0], parameter.getValue()[1], parameter.getKey());
            data.add(messageContent);
        }
        messageCenterView.messageCenter_tableList.setItems(data);
    }


    public void informationDialog(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Under Construction");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private String confirmPaymentPurchase() {
        Dialog<Pair<String, String>> dialog = new Dialog<>();
        dialog.setTitle("Real PayPal Login");

        // Set the button types.
        ButtonType loginButtonType = new ButtonType("OK", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(loginButtonType, ButtonType.CANCEL);

        GridPane gridPane = new GridPane();
        gridPane.setHgap(10);
        gridPane.setPadding(new Insets(20, 150, 10, 10));

        TextField account = new TextField();
        account.setText(messageModel.getUserName() + "@RealPayPalAccount.com");
        PasswordField password = new PasswordField();
        password.setText("Password");

        gridPane.add(new Label("PayPal account:"), 0, 0);
        gridPane.add(account, 1, 0);
        gridPane.add(new Label("Password:"), 0, 1);
        gridPane.add(password, 1, 1);

        dialog.getDialogPane().setContent(gridPane);


        // Convert the result to a username-password-pair when the login button is clicked.
        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == loginButtonType) {
                return new Pair<>(account.getText(), password.getText());
            }
            return null;
        });
        dialog.showAndWait();
        if (dialog.getResult() != null) {
            return dialog.getResult().getKey();
        } else {
            return "";
        }

    }

    private String confirmPurchase() {
        TextInputDialog dialog = new TextInputDialog(messageModel.getUserName() + "@RealPayPalAccount.com");
        dialog.setResizable(true);
        dialog.setTitle("Confirm Purchase");
        dialog.setHeaderText("Do you wish to confirm purchase for this seller?");
        dialog.setContentText("Please enter your payPal account to receive payment:");
// Traditional way to get the response value.
        Optional<String> result = dialog.showAndWait();
        if (result.isPresent()) {
            return result.get();
        }
        return "";
    }

    public boolean confirmDialog(String message) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation Dialog");
        alert.setHeaderText(null);
        alert.setContentText(message);

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {
            return true;
        } else {
            return false;
        }
    }

//    @Override
//    public void update(Observable o, Object arg) {
//        if (o.equals(messageCenterView)) {
//            if (arg.equals("history")) {
//                informationDialog("This process is still under construction...");
//            } else if (arg.equals(REQUEST_PICKED)){
//                pickedRequest = messageCenterView.getPickedRequest();
//                if (pickedRequest.getSellerKey().equals(messageModel.getUserName())){
//                    String paymentAccount = confirmPurchase();
//                    if (!paymentAccount.equals("")){
//                        initPurchase(paymentAccount);
//                        fillTableList();
//                    }
//                } else {
//                    String paymentAccount = confirmPaymentPurchase();
//                    if (!paymentAccount.equals("")) {
//                        updatePurchase(paymentAccount);
//                        fillTableList();
//                    }
//                }
//            }
//        }
//    }

    @Override
    public void update(Observable o, Object arg) {
        if (o.equals(messageCenterView)) {
            if (arg.equals("history")) {
                informationDialog("This process is still under construction...");
            } else if (arg.equals(REQUEST_PICKED)) {
                pickedRequest = messageCenterView.getPickedRequest();
                if (pickedRequest.getSellerKey().equals(messageModel.getUserName())) {
                    if (pickedRequest.getApproved()) {
                        boolean approved = confirmDialog("Do you APPROVE to make this deal?");
                        if (approved) {
                            initPurchase();
                            fillTableList();
                        }
                    } else {
                        boolean approved = confirmDialog("ARE YOU SURE THAT THE DEAL IS MADE WITH THE BUYER?");
                        if (approved) {
                            requestModel.finishPurchase(pickedRequest);
                            fillTableList();
                        }
                    }
                }
            }
        }
    }

    private void initPurchase() {
        String sellerName = pickedRequest.getSellerKey();
        if (!pickedRequest.isExchange()) {
            purchaseModel.insertPurchaseToTable(pickedRequest.getVacationKey(), pickedRequest.getSellerKey(), userModel.getContactInfo(sellerName), pickedRequest.getBuyerKey(), null);
        } else {
            purchaseModel.insertPurchaseToTable(pickedRequest.getVacationKey(), pickedRequest.getSellerKey(), userModel.getContactInfo(sellerName), pickedRequest.getBuyerKey(), pickedRequest.getVacationToExchange().getVacationKey());
        }
        requestModel.updateApprovedRequest(pickedRequest);
    }


//    private void updatePurchase(String buyerPaymentAccount) {
//        String[][] parameters = {{PurchaseTable.COLUMN_PURCHASETABLE_VACATIONKEY}, {pickedRequest.getVacationKey()}};
//        List<Purchase> purchaseList = purchaseModel.readDataFromDB(parameters);
//        Purchase purchase = purchaseList.get(0);
//        purchase.setBuyerEmail(buyerPaymentAccount);
//        purchaseModel.updateTable(purchase);
//        requestModel.finishPurchase(pickedRequest);
//
//        // DEMO FOR PAYPAL API USAGE
//
//        paypalAPI = PaypalTable.getInstance();
////        String transactionId = String.valueOf((int)(Math.random() * 10000000 + 1));
//        String sellerAccount = purchase.getSellerEmail();
//        String[][] vacationParameters = {{VacationTable.COLUMN_VACATIONTABLE_KEY}, {purchase.getVacationKey()}};
//        List<Vacation> vacationList = vacationModel.readDataFromDB(vacationParameters);
//        Double amount = vacationList.get(0).getPrice();
//        PaypalPayment payment = new PaypalPayment(null, buyerPaymentAccount, sellerAccount, amount);
//        paypalAPI.InsertToTable(payment);
//    }

//    private void initPurchase(String paymentAccount) {
//        purchaseModel.insertPurchaseToTable(pickedRequest.getVacationKey(), pickedRequest.getSellerKey(), paymentAccount, pickedRequest.getBuyerKey());
//        requestModel.updateApprovedRequest(pickedRequest);
//    }
}
