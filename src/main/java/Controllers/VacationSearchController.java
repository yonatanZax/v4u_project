package Controllers;

import java.io.IOException;
import java.util.*;

import Model.User.UserModel;
import Model.Vacation.Vacation;
import Model.Vacation.VacationModel;
import View.VacationSearchView;
import db.Tables.VacationTable;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.*;
import org.apache.commons.lang3.tuple.MutablePair;

/**
 *
 */
public class VacationSearchController extends Observable implements Observer, SubScenable {

    private VacationSearchView myView;
    private VacationModel vacationModel;
    private Parent root;
    private FXMLLoader fxmlLoader;

    private String exchangedKey;

    public static final String BTN_ADD = "add_btn";
    public static final String VACATION_PICKED = "vacation_picked";
    public static final String SEND_VACATION_PURCHASE_REQUEST = "send_vacation_purchase_request";
    public static final String EXCHANGE = "exchange";

    public VacationSearchController(VacationModel vacationModel) {
        this.vacationModel = vacationModel;
        fxmlLoader = new FXMLLoader(getClass().getResource("/vacation_search_view.fxml"));
        try {
            root = fxmlLoader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        myView = fxmlLoader.getController();
        myView.addObserver(this);

    }

    public Parent getRoot() {
        return root;
    }

    public void updateSubScene() {
        List<Vacation> vacationList = vacationModel.getAllData();
        myView.setVacations_listview(vacationList);
    }

    private void showBuyVacationWindow(Vacation vacation) {
        Alert alert = new Alert(Alert.AlertType.NONE);
        alert.setTitle("Buy Confirmation Dialog");
        alert.setHeaderText(null);
        String alertContentString = "Are you sure you want sent a request for buying this vacation?\n\n";
        alertContentString += vacation.toString();

        ButtonType buttonTypeOK = new ButtonType("OK");
        ButtonType buttonTypeCancel = new ButtonType("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE);

        alert.getButtonTypes().setAll(buttonTypeOK, buttonTypeCancel);
        alert.getButtonTypes();
        alert.setContentText(alertContentString);

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == buttonTypeOK) {
            updateSubScene();
            // ... user chose OK

            setChanged();
            notifyObservers(new MutablePair<String,Vacation>(SEND_VACATION_PURCHASE_REQUEST,vacation));

        } else {
            // ... user chose CANCEL or closed the dialog
            updateSubScene();
        }
    }

    private void showExchangeOrBuyVacationWindow(Vacation vacation) {
        Alert alert = new Alert(Alert.AlertType.NONE);
        alert.setTitle("Exchange Confirmation Dialog");
        alert.setHeaderText(null);
        String alertContentString = "Would you like to Buy this vacation?\n" +
                "or would you want to exchange this vacation with one of your own?\n\n";
        alertContentString += vacation.toString() + "\n";

        ButtonType buttonTypeBuy = new ButtonType("Buy");
        ButtonType buttonTypeExchange = new ButtonType("Exchange");
        ButtonType buttonTypeCancel = new ButtonType("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE);

        alert.getButtonTypes().setAll(buttonTypeBuy, buttonTypeExchange, buttonTypeCancel);
        alert.getButtonTypes();
        alert.setContentText(alertContentString);

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == buttonTypeBuy) {
            showBuyVacationWindow(vacation);

        } else if (result.get() == buttonTypeExchange) {
            // ... user chose CANCEL or closed the dialog
            showExchangeVacationWindow(vacation);
        }

        updateSubScene();
    }

    private void vacationPicked(Vacation vacation) {
        if (vacation != null && vacation.isExchangeable()) {
            showExchangeOrBuyVacationWindow(vacation);
        } else if (vacation != null) {
            showBuyVacationWindow(vacation);
        }


    }


    private void showExchangeVacationWindow(Vacation vacation) {
        String userName = vacationModel.getUserName();
        String[][] parameters = {{VacationTable.COLUMN_VACATIONTABLE_SELLERKEY}, {userName}};
        List<Vacation> vacationList = vacationModel.readDataFromDB(parameters);
        HashMap<String, Vacation> vacationDestinations = new HashMap<>();
        int counter = 1;
        for (int i = 0; i < vacationList.size(); i++) {
            if (vacationList.get(i).isVisible()) {
                vacationDestinations.put(counter + ". " + vacationList.get(i).getDestination() + " - " + vacationList.get(i).getPrice(), vacationList.get(i));
                counter++;
            }
        }

        ChoiceDialog<String> dialog = new ChoiceDialog<>("Choose vacation", vacationDestinations.keySet());
        dialog.setTitle("Exchange Vacation");
        dialog.setHeaderText("You can ask the vacation seller to \nexchange the vacation you picked \nwith any of your vacations\n ");
        dialog.setContentText("Choose your Vacation:");

        // Traditional way to get the response value.
        Optional<String> result = dialog.showAndWait();
        if (result.isPresent()) {
            String ans = result.get();
            if (!ans.equals("Choose vacation")) {
                Vacation pickedVacationForExchange = vacationDestinations.get(ans);
                this.exchangedKey = pickedVacationForExchange.getVacationKey();
                setChanged();
                notifyObservers(new MutablePair<String,Vacation>(EXCHANGE,vacation));

            }
        }
    }

    public String getExchangedKey() {
        return this.exchangedKey;
    }

    private void informationDialog(String title, String header, String content) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);

        alert.showAndWait();
    }

    @Override
    public void update(Observable o, Object arg) {
        if (o == myView) {
            if (arg instanceof MutablePair) {
                MutablePair<String, Vacation> updatePair = (MutablePair<String, Vacation>) arg;
                Vacation vacation = updatePair.right;
                if (UserModel.isLoggedIn()) {
                    //pickedVacation = myView.getPickedVacation();
//                    if (myView.getPickedVacation() != null && myView.getPickedVacation().getSellerKey().equals(UserModel.getUserName())){
                    if (vacation.getSellerKey().equals(UserModel.getUserName())) {
                        informationDialog("Not Relevant for You", null, "Why would you want to buy your own vacation?!");
                    } else {
                        if (checkIfAlreadyRequested()) {
                            informationDialog("Request Already Made", null, "You already sent a request for this vacation");
                        } else {
                            vacationPicked(vacation);
                        }
                    }
                } else {
                    Alert errorAlert = new Alert(Alert.AlertType.ERROR);
                    errorAlert.setHeaderText("You are NOT Logged in!");
                    errorAlert.setContentText("Only Registered User can file a request to buy a vacation.");
                    errorAlert.showAndWait();
                }
            }else if (arg.equals(BTN_ADD)) {
                setChanged();
                notifyObservers(BTN_ADD);
            }
        }
    }

    private boolean checkIfAlreadyRequested() {
        return false;
    }

}