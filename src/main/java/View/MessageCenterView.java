package View;

import Controllers.ControllerMessageCenter;
import Controllers.VacationSearchController;
import Model.MessageCenter.ListMessageContent;
import Model.MessageCenter.Message;
import Model.Request.Request;
import Model.Vacation.Vacation;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;

import java.util.List;
import java.util.Observable;

public class MessageCenterView extends Observable {

    public TableView<ListMessageContent> messageCenter_tableList;
    public TableColumn<ListMessageContent, String> messageType_col;
    public TableColumn<ListMessageContent, String> info_col;

//    private Request pickedRequest;

    private ObservableList<Request> masterData = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        // set the table for double click
        messageCenter_tableList.setRowFactory(tv -> {
            TableRow<ListMessageContent> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2 && (! row.isEmpty()) ) {
                    Request rowData = row.getItem().getMessage().getRequest();
                    System.out.println("Request picked: " + rowData.getVacationKey() + ", Seller: " + rowData.getSellerKey() + ", Buyer: " + rowData.getBuyerKey());
                    this.setChanged();
//                    this.notifyObservers(ControllerMessageCenter.REQUEST_PICKED);
                    this.notifyObservers(rowData);
                }
            });
            return row ;
        });

        messageType_col.setCellValueFactory(cellData -> cellData.getValue().messageTypeProperty());

        info_col.setCellValueFactory(cellData -> cellData.getValue().infoProperty());

    }

    public void viewConversations() {
        setChanged();
        notifyObservers("history");
    }

    public void refresh() {
        setChanged();
        notifyObservers("refresh");
    }

//    public Request getPickedRequest() {
//        return pickedRequest;
//    }
}
