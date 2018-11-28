package View;

import Model.MessageCenter.ListMessageContent;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import java.util.Observable;

public class MessageCenterView extends Observable {

    public TableView<ListMessageContent> messageCenter_tableList;
    public TableColumn<ListMessageContent, String> messageType_col;
    public TableColumn<ListMessageContent, String> info_col;


    public void viewConversations() {
        setChanged();
        notifyObservers("history");
    }

    public void refresh() {
        setChanged();
        notifyObservers("refresh");
    }
}
