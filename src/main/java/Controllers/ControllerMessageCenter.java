package Controllers;

import Model.MessageCenter.ListMessageContent;
import Model.MessageCenter.Message;
import Model.MessageCenter.MessageModel;
import Model.Request.Request;
import Model.Vacation.Vacation;
import View.MessageCenterView;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.List;
import java.util.Observable;
import java.util.Observer;
import java.util.Optional;

public class ControllerMessageCenter extends Observable implements Observer,SubScenable {

    MessageCenterView messageCenterView;
    MessageModel messageModel = new MessageModel();
    private Parent root;
    private FXMLLoader fxmlLoader;

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

    // todo - check the other todo in other classes.. if not relevant -> continue as is..
    // TODO - first thing to do --> see that the list is updated with all requests
    // TODO - second thing to do --> add the RequestTable STATUS: APPROVED, PAID, PENDING
    // TODO - third thing to do --> combine working with multiple requests for the SAME VACATION --> if APPROVED --> remove from list and all the same vacation requests
    // NOT TODO NOT NOT NOT --> THERE WILL BE NO CANCELLATION FOR REQUESTS!
    // TODO - IF THE PAYMENT DIDN'T HAPPEN IN 48 HOURS --> ALL THE SAME REQUESTS RETURN TO STATUS --> PENDING
    // TODO - forth thing to do --> deal with refresh button
    // todo - check that vacations that approved are removed from the search list (home page)
    // todo - change table list colours to be different than the search list (home page)

    private void fillTableList() {
        messageModel.setMessagesForUser();
        List<String[]> messagesParameters = messageModel.createMessageParametersForController();
        ObservableList<ListMessageContent> data = FXCollections.observableArrayList();
        for (String[] parameter : messagesParameters){
            ListMessageContent messageContent = new ListMessageContent(parameter[0], parameter[1], null);
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


    public void confirmDialog(String message) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation Dialog");
        alert.setHeaderText(null);
        alert.setContentText(message);

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {
            // ... user chose OK
        }
    }

    @Override
    public void update(Observable o, Object arg) {
        if (o.equals(messageCenterView)) {
            if (arg.equals("history")) {
                informationDialog("This process is still under construction...");
            }
        }
    }


}
