package Controllers;

import Model.MessageCenter.ListMessageContent;
import Model.MessageCenter.Message;
import Model.MessageCenter.MessageModel;
import Model.Request.Request;
import View.MessageCenterView;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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

public class ControllerMessageCenter implements Observer {

    MessageCenterView messageCenterView;
    MessageModel messageModel;
    private Stage stage;
    private Parent root;
    private FXMLLoader fxmlLoader;

    public ControllerMessageCenter(MessageModel model) {
        stage = new Stage();
        fxmlLoader = new FXMLLoader(getClass().getResource("/messageCenter_view.fxml"));
        try {
            root = fxmlLoader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Scene scene = new Scene(root);
        stage.setScene(scene);
        messageModel = model;
        messageCenterView = fxmlLoader.getController();
        messageCenterView.addObserver(this);
    }


    public void showStage() {
        messageCenterView.messageCenter_tableList.setItems(null);
        fillTableList();
        stage.show();
    }

    private void fillTableList() {
        messageModel.setMessagesForUser();
        List<String[]> messagesParameters = messageModel.createMessageParametersForController();
        ObservableList<ListMessageContent> data = FXCollections.observableArrayList();
        for (String[] parameter : messagesParameters){
            ListMessageContent messageContent = new ListMessageContent(parameter[0], parameter[1]);
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
