package Controllers;

import java.io.IOException;
import java.util.List;
import java.util.Observable;
import java.util.Observer;
import java.util.Optional;

import Model.Request.RequestModel;
import Model.User.UserModel;
import Model.Vacation.Vacation;
import Model.Vacation.VacationModel;
import View.VacationSearchView;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DialogEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.Window;

/**

 */
public class VacationSearchController extends Observable implements Observer,SubScenable {

    private VacationSearchView myView;
    private VacationModel vacationModel = new VacationModel();
    private RequestModel requestModel = new RequestModel();
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

        updateSubScene();

    }

    public Parent getRoot(){
        return root;
    }

    public void updateSubScene(){
        List<Vacation> vacationList = vacationModel.getAllData();
        myView.setVacations_listview(vacationList);
    }


    private void vacationPicked(){

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
            updateSubScene();
            // ... user chose OK
            // TODO - show a message in the status bar

            requestModel.insertRequestToTable(pickedVacation.getVacationKey(),pickedVacation.getSellerKey());
            setChanged();
            notifyObservers(SEND_VACATION_PURCHASE_REQUEST);

        } else {
            // ... user chose CANCEL or closed the dialog
            updateSubScene();
        }

    }

    private void informationDialog(String title, String header, String content){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);

        alert.showAndWait();
    }

    @Override
    public void update(Observable o, Object arg) {
        if (o == myView){
            if (arg.equals(VACATION_PICKED)){
                if (UserModel.isLoggedIn()) {
                    pickedVacation = myView.getPickedVacation();
                    // todo - SELLER CAN'T PURCHASE HIS OWN VACATION!!!! - DONE
                    if (pickedVacation.getSellerKey().equals(UserModel.getUserName())){
                        informationDialog("Not Relevant for You",null, "Why would you want to buy your own vacation?!");
                    }else {
                        vacationPicked();
                    }
                }
                else {
                    Alert errorAlert = new Alert(Alert.AlertType.ERROR);
                    errorAlert.setHeaderText("You are NOT Logged in!");
                    errorAlert.setContentText("Only Registered User can file a request to buy a vacation.");
                    errorAlert.showAndWait();
                }
            }
            // todo Done - auto update list after vacation added to DB
            else if (arg.equals(BTN_ADD)){
                setChanged();
                notifyObservers(BTN_ADD);
            }
        }
    }
}