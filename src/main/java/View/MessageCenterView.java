package View;

import Model.MessageCenter.ListMessageContent;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import java.util.Observable;

public class MessageCenterView extends Observable {

    public TableView<ListMessageContent> messageCenter_tableList;
    public TableColumn<ListMessageContent, String> messageType_col;
    public TableColumn<ListMessageContent, String> info_col;

    // todo - add buttons to third row (APPROVE, NOT APPROVE, START CONVERSATION (OPTIONAL)

    public void viewConversations() {
        setChanged();
        notifyObservers("history");
    }

    public void refresh() {
        setChanged();
        notifyObservers("refresh");
    }
}
