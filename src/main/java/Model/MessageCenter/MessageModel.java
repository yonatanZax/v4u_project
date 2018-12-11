package Model.MessageCenter;

import Model.Request.Request;
import Model.Request.RequestModel;
import Model.User.UserModel;
import Model.Vacation.Vacation;
import Model.Vacation.VacationModel;
import db.Tables.RequestTable;
import db.Tables.VacationTable;
import javafx.util.Pair;

import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;

public class MessageModel {

    private RequestModel requestModel;
    private UserModel userModel;
    private VacationModel vacationModel;
    private MessagesBox messagesList;

    public MessageModel() {
        this.requestModel = new RequestModel();
        this.userModel = new UserModel();
        this.vacationModel = new VacationModel();
        messagesList = new MessagesBox();
    }


    public String getUserName(){ return userModel.getUserName(); }

    public List<Vacation> getVacationFromKey(String key){
        String[][] parameters = {{VacationTable.COLUMN_VACATIONTABLE_KEY},{key}};
        return vacationModel.readDataFromDB(parameters);
    }

    /**
     * Message parameters: {messageType, info}
     */
    public List<Pair<Message, String[]>> createMessageParametersForController() {
        List<Pair<Message, String[]>> messageParameters = new LinkedList<>();
        for (Message message : messagesList.getMessagesList()){
            if (message.isSeller()) {
                if (message.getRequest().getState().equals(Request.states[0])) {
                String[] s = {message.getMessageType(), message.getBuyerName() + " wish to buy from you the vacation to: " + message.getVacation().getDestination() + ", at the price " + message.getVacation().getPrice()+ "$"};
                    Pair<Message, String[]> pair = new Pair<>(message, s);
                    messageParameters.add(pair);
                }
            } else {
                if (message.getRequest().getState().equals(Request.states[1])) {
                String[] s = {message.getMessageType(), message.getSellerName() + " approved your request to buy the vacation to: " + message.getVacation().getDestination() + ", at the price " + message.getVacation().getPrice()+ "$"};
                    Pair<Message, String[]> pair = new Pair<>(message, s);
                    messageParameters.add(pair);
                }
            }
        }
        return messageParameters;
    }

    public void setMessagesForUser(){
        resetMessagesBox();
        List<Request> requestsAsBuyer = getRequests(0);
        List<Request> requestsAsSeller = getRequests(1);

        for (Request request : requestsAsBuyer){
            if (request.getApproved() && request.getBuyerKey().equals(userModel.getUserName())) {
                String key = request.getVacationKey();
                List<Vacation> vacationsList = getVacationFromKey(key);
                if (!vacationsList.isEmpty()) {
                    Vacation vacation = vacationsList.get(0);
                    Message message = new Message(request.getSellerKey(), getUserName(), vacation, false, request);
                    messagesList.getMessagesList().add(message);
                }
            }
        }
        for (Request request : requestsAsSeller){
            if (request.getState().equals(Request.states[0])) {
                String key = request.getVacationKey();
                List<Vacation> vacationsList = getVacationFromKey(key);
                if (!vacationsList.isEmpty()) {
                    Vacation vacation = vacationsList.get(0);
                    Message message = new Message(getUserName(), request.getBuyerKey(), vacation, true, request);
                    messagesList.getMessagesList().add(message);
                }
            }
        }
    }


    public void resetMessagesBox(){
        messagesList.resetMessegeBox();
    }


    /**
     * @param role : 0 -> Buyer, else is Seller
     */
    public List<Request> getRequests(int role){
        List<Request> requestsList = new LinkedList<>();
        if(role == 0){
            String[][] parameters = {{RequestTable.COLUMN_REQUESTTABLE_BUYERKEY},{userModel.getUserName()}};
            requestsList = requestModel.readDataFromDB(parameters);
        } else {
            String[][] parameters = {{RequestTable.COLUMN_REQUESTTABLE_SELLERKEY},{userModel.getUserName()}};
            requestsList = requestModel.readDataFromDB(parameters);
        }
        return requestsList;
    }

    public List<Message> getMessagesList() {
        return messagesList.getMessagesList();
    }

    public int getCurrentTimeStamp() {
        String date = LocalDateTime.now().getYear() + "" + LocalDateTime.now().getMonthValue() + "" + LocalDateTime.now().getDayOfMonth();
        return Integer.parseInt(date);
    }

    private void checkListForDueDateApproval(List<Request> list) {
        String day;
        String todayDate;
        for (Request requestBuyer : list) {
            if (requestBuyer.getState().equals(Request.states[1])) {
                day = String.valueOf(requestBuyer.getTimestamp());
                day = day.substring(6);
                todayDate = String.valueOf(getCurrentTimeStamp());
                todayDate = todayDate.substring(6);
                if (Integer.parseInt(day) + 2 == Integer.parseInt(todayDate)) {
                    requestBuyer.setState(Request.states[0]);
                    requestBuyer.setApproved(false);
                    requestModel.updateTable(requestBuyer);
                }
            }
        }
    }

    public void checkIfApprovalDue() {
        List<Request> requestsAsBuyer = getRequests(0);
        List<Request> requestsAsSeller = getRequests(1);
        checkListForDueDateApproval(requestsAsBuyer);
        checkListForDueDateApproval(requestsAsSeller);
    }

}
