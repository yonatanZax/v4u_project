package Model.MessageCenter;

import Model.Request.Request;
import javafx.beans.property.SimpleStringProperty;

public class ListMessageContent {
    private SimpleStringProperty messageType;
    private SimpleStringProperty info;
    private Message message;

    public ListMessageContent(String messageType, String info,Message message){
        this.messageType = new SimpleStringProperty(messageType);
        this.info = new SimpleStringProperty(info);
        this.message = message;
    }


    public String getMessageType() {
        return messageType.get();
    }

    public String getInfo() {
        return info.get();
    }

    public void setMessageType(String messageType) {
        this.messageType.set(messageType);
    }

    public void setInfo(String info) {
        this.info.set(info);
    }

    public SimpleStringProperty messageTypeProperty() {
        return messageType;
    }

    public SimpleStringProperty infoProperty() {
        return info;
    }

    public Message getMessage() {
        return message;
    }

    public void setMessage(Message message) {
        this.message = message;
    }
}
