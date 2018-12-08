package Model.MessageCenter;

import javafx.beans.property.SimpleStringProperty;

public class ListMessageContent {
    private SimpleStringProperty messageType;
    private SimpleStringProperty info;

    public ListMessageContent(String messageType, String info){
        this.messageType = new SimpleStringProperty(messageType);
        this.info = new SimpleStringProperty(info);
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
}
