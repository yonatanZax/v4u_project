package View;

import javafx.event.ActionEvent;
import javafx.scene.control.TableView;

import java.util.Observable;

public class MessageCenterView extends Observable {

    public TableView messageCenter_tableList;




    public void viewConversations() {
        setChanged();
        notifyObservers("history");
    }
}
