package Model.MessageCenter;

import java.util.LinkedList;
import java.util.List;

public class MessegesBox {
    private List<Message> messagesList;

    MessegesBox(){
        messagesList = new LinkedList<>();
    }

    public List<Message> getMessagesList() {
        return messagesList;
    }

    public void resetMessegeBox(){
        messagesList.clear();
    }
}
