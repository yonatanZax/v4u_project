package Controllers;

import java.io.IOException;
import java.util.List;
import java.util.Observable;
import java.util.Observer;
import java.util.Optional;


import Model.User.UserModel;
import Model.Vacation.Vacation;
import Model.Vacation.VacationModel;
import View.VacationSearchView;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;

public class VacationSearchController extends Observable implements Observer, SubScenable {

    private VacationSearchView myView;
    private VacationModel vacationModel = new VacationModel();
    private Parent root;
    private FXMLLoader fxmlLoader;

    private Vacation pickedVacation;

    public static final String BTN_ADD = "add_btn";
    public static final String VACATION_PICKED = "vacation_picked";
    public static final String SEND_VACATION_PURCHASE_REQUEST = "send_vacation_purchase_request";

    public VacationSearchController() {
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

    private void vacationPicked() {
        Alert alert = new Alert(Alert.AlertType.NONE);
        alert.setTitle("Confirmation Dialog");
        alert.setHeaderText(null);
        String alertContentString = "Are you sure you want sent a request for buying this vacation?\n";
        alertContentString += pickedVacation.toString();

        ButtonType buttonTypeOK = new ButtonType("OK");
        ButtonType buttonTypeCancel = new ButtonType("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE);

        alert.getButtonTypes().setAll(buttonTypeOK, buttonTypeCancel);
        alert.getButtonTypes();
        alert.setContentText(alertContentString);

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == buttonTypeOK) {
            // ... user chose OK
            updateSubScene();

            setChanged();
            notifyObservers(SEND_VACATION_PURCHASE_REQUEST);

        } else {
            // ... user chose CANCEL or closed the dialog
            updateSubScene();
        }

    }

    private void informationDialog(String title, String header, String content) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);

        alert.showAndWait();
    }

    public String getVacationPickedKey() {
        if (pickedVacation != null) {
            return pickedVacation.getVacationKey();
        }
        return null;
    }

    public String getVacationPickedSeller() {
        if (pickedVacation != null) {
            return pickedVacation.getSellerKey();
        }
        return null;
    }

    @Override
    public void update(Observable o, Object arg) {
        if (o == myView) {
            if (arg.equals(VACATION_PICKED)) {
                if (UserModel.isLoggedIn()) {
                    pickedVacation = myView.getPickedVacation();
                    if (pickedVacation.getSellerKey().equals(UserModel.getUserName())) {
                        informationDialog("Not Relevant for You", null, "Why would you want to buy your own vacation?!");
                    } else {
                        if (checkIfAlreadyRequested()) {
                            informationDialog("Request Already Made", null, "You already sent a request for this vacation");
                        } else {
                            vacationPicked();
                        }
                    }
                } else {
                    Alert errorAlert = new Alert(Alert.AlertType.ERROR);
                    errorAlert.setHeaderText("You are NOT Logged in!");
                    errorAlert.setContentText("Only Registered User can file a request to buy a vacation.");
                    errorAlert.showAndWait();
                }
            } else if (arg.equals(BTN_ADD)) {
                setChanged();
                notifyObservers(BTN_ADD);
            }
        }
    }

    private boolean checkIfAlreadyRequested() {
        return false;
    }
}