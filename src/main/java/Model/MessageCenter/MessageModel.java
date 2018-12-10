package Model.MessageCenter;

import Model.Request.Request;
import Model.Request.RequestModel;
import Model.User.UserModel;
import Model.Vacation.Vacation;
import Model.Vacation.VacationModel;
import db.Tables.RequestTable;
import db.Tables.VacationTable;
import MainPackage.Enum_RequestState;
import java.util.LinkedList;
import java.util.List;

public class MessageModel {

    private RequestModel requestModel;
    private UserModel userModel;
    private VacationModel vacationModel;
    private MessagesBox messagesList;

    public MessageModel(){}

    public MessageModel(UserModel userModel, RequestModel requestModel, VacationModel vacationModel) {
        this.requestModel = requestModel;
        this.userModel = userModel;
        this.vacationModel = vacationModel;
        messagesList = new MessagesBox();
    }


    public String getUserName(){ return userModel.getUserName(); }

    public List<Vacation> getVacationFromKey(String key){
        String[][] parameters = {{VacationTable.COLUMN_VACATIONTABLE_KEY,key}};
        return vacationModel.readDataFromDB(parameters);
    }

    /**
     * Message parameters: {messageType, info}
     */
    public List<String[]> createMessageParametersForController(){
        List<String[]> messageParameters = new LinkedList<>();
        for (Message message : messagesList.getMessagesList()){
            if (message.isSeller()) {
                String[] s = {message.getMessageType(), message.getBuyerName() + " wish to buy from you the vacation to: " + message.getVacation().getDestination() + ", at the price " + message.getVacation().getPrice()+ "$"};
                messageParameters.add(s);
            } else {
                String[] s = {message.getMessageType(), message.getSellerName() + " approved your request to buy the vacation to: " + message.getVacation().getDestination() + ", at the price " + message.getVacation().getPrice()+ "$"};
                messageParameters.add(s);
            }
        }
        return messageParameters;
    }

    public void setMessagesForUser(){
        List<Request> requestsAsBuyer = getRequests(getUserName(), 0);
        List<Request> requestsAsSeller = getRequests(getUserName(), 1);

        for (Request request : requestsAsBuyer){
            if (request.getApproved() && request.getBuyerKey().equals(userModel.getUserName())) {
                String key = request.getVacationKey();
                List<Vacation> vacationsList = getVacationFromKey(key);
                if (!vacationsList.isEmpty()) {
                    Vacation vacation = vacationsList.get(0);
                    Message message = new Message(request.getSellerKey(), getUserName(), vacation, false);
                    messagesList.getMessagesList().add(message);
                }
            }
        }
        for (Request request : requestsAsSeller){
            if (request.getState().equals(Enum_RequestState.PENDING)) {
                String key = request.getVacationKey();
                List<Vacation> vacationsList = getVacationFromKey(key);
                if (!vacationsList.isEmpty()) {
                    Vacation vacation = vacationsList.get(0);
                    Message message = new Message(getUserName(), request.getBuyerKey(), vacation, true);
                    messagesList.getMessagesList().add(message);
                }
            }
        }
    }

    // TODO - when logout will be created - use this function!
    public void resetMessagesBox(){
        messagesList.resetMessegeBox();
    }


    /**
     * @param role : 0 -> Seller, else is Buyer
     */
    public List<Request> getRequests(String UserName, int role){
        List<Request> requestsList = new LinkedList<>();
        if(role == 0){
            String[][] parameters = {{RequestTable.COLUMN_REQUESTTABLE_BUYERKEY,userModel.getUserName()}};
            requestsList = requestModel.readDataFromDB(parameters);
        } else {
            String[][] parameters = {{RequestTable.COLUMN_REQUESTTABLE_SELLERKEY,userModel.getUserName()}};
            requestsList = requestModel.readDataFromDB(parameters);
        }
        return requestsList;
    }

    public List<Message> getMessagesList() {
        return messagesList.getMessagesList();
    }
}
