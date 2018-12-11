package Model.MessageCenter;

import java.util.LinkedList;
import java.util.List;

public class MessagesBox {
    private List<Message> messagesList;

    MessagesBox() {
        messagesList = new LinkedList<>();
    }

    public List<Message> getMessagesList() {
        return messagesList;
    }

    public void resetMessegeBox() {
        messagesList.clear();
    }
}
