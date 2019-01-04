package Model.MessageCenter;

import Model.Request.Request;
import Model.Request.RequestModel;
import Model.User.User;
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

    public MessageModel(RequestModel requestModel) {
        this.requestModel = requestModel;
        vacationModel = requestModel.getVacationModel();
        userModel = new UserModel();
        messagesList = new MessagesBox();
    }

    public String getUserName() {
        return userModel.getUserName();
    }

    public List<Vacation> getVacationFromKey(String key) {
        String[][] parameters = {{VacationTable.COLUMN_VACATIONTABLE_KEY}, {key}};
        return vacationModel.readDataFromDB(parameters);
    }

    /**
     * Message parameters: {messageType, info}
     */
    public List<Pair<Message, String[]>> createMessageParametersForController() { // TODO: 03/01/2019 if buyer wish to buy -> we dont need thr approval of seller!
        List<Pair<Message, String[]>> messageParameters = new LinkedList<>();
        for (Message message : messagesList.getMessagesList()) {
            boolean flag = true;
            if (message.isSeller()) {
                String[] s = {message.getMessageType(), ""};
                Vacation vacation = message.getRequest().getVacationToExchange() < 1 ? null : getVacationFromRequestExchange(message.getRequest().getVacationToExchange());
                if (message.getRequest().getState().equals(Request.states[0])) {
                    if (message.getRequest().getVacationToExchange()<1) {
                        s[1] = message.getBuyerName() + " wish to Buy from you the vacation to: " + message.getVacation().getDestination() + ", at the price " + message.getVacation().getPrice() + "$";
                    } else {
                        s[1] = message.getBuyerName() + " wish to Exchange with you the vacation to: " + message.getVacation().getDestination() + ", with the vacation to " + vacation.getDestination() + " at the price " + vacation.getPrice();
                    }
                } else if (message.getRequest().getState().equals(Request.states[1])) {
                    if (message.getRequest().getVacationToExchange()<1) {
                        s[1] = " Please approve that you received the payment from: " + message.getBuyerName() + ", for the vacation to: " + message.getVacation().getDestination() + ", at the price " + message.getVacation().getPrice() + "$";
                    } else {
//                        if (message.getRequest().getVacationToExchange().isVisible()){
                        if (vacation.isVisible()){
                        s[1] = "Please approve that you exchanged the vacation: " + message.getVacation().getDestination() + ", with the vacation to: " + vacation.getDestination() + ", from the user: " + message.getBuyerName();
                        } else {
                            flag = false;
                        }
                    }
                }
                if (flag) {
                    Pair<Message, String[]> pair = new Pair<>(message, s);
                    messageParameters.add(pair);
                }
            } else {
                if (message.getRequest().getState().equals(Request.states[1])) {
                    String[] s = {message.getMessageType(), ""};
//                    String contactInfo =
                    if (message.getRequest().getVacationToExchange()<1) {
                        s[1] = message.getSellerName() + " approved your request to Buy the vacation to: " + message.getVacation().getDestination() + ", at the price " + message.getVacation().getPrice() + "$, PLEASE CONTACT THE SELLER TO MAKE THE PURCHASE: " + userModel.getContactInfo(message.getSellerName());
                    } else {
//                        if (message.getRequest().getVacationToExchange().isVisible()) {
                        Vacation vacation = getVacationFromRequestExchange(message.getRequest().getVacationToExchange());
                        if (vacation.isVisible()){
                            s[1] = message.getSellerName() + " approved your request to Exchange the vacation to: " + message.getVacation().getDestination() + ", with the vacation to " + vacation.getDestination() + ", PLEASE CONTACT THE SELLER TO MAKE THE PURCHASE: " + userModel.getContactInfo(message.getSellerName());
                        } else {
                            flag = false;
                        }
                    }
                    if (flag) {
                        Pair<Message, String[]> pair = new Pair<>(message, s);
                        messageParameters.add(pair);
                    }
                }
            }
        }
        return messageParameters;
    }

    private Vacation getVacationFromRequestExchange(int vacationToExchange) {
        String[][] parameters = {{VacationTable.COLUMN_VACATIONTABLE_KEY},{String.valueOf(vacationToExchange)}};
        List<Vacation> vacations = vacationModel.readDataFromDB(parameters);
        Vacation vacation = vacations.get(0);
        return vacation;
    }

    public void setMessagesForUser() {
        resetMessagesBox();
        List<Request> requestsAsBuyer = getRequests(0);
        List<Request> requestsAsSeller = getRequests(1);

        for (Request request : requestsAsBuyer) {
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
        for (Request request : requestsAsSeller) {
            if (request.getState().equals(Request.states[0]) || request.getState().equals(Request.states[1])) {
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


    public void resetMessagesBox() {
        messagesList.resetMessegeBox();
    }


    /**
     * @param role : 0 -> Buyer, else is Seller
     */
    public List<Request> getRequests(int role) {
        List<Request> requestsList = new LinkedList<>();
        if (role == 0) {
            String[][] parameters = {{RequestTable.COLUMN_REQUESTTABLE_BUYERKEY}, {userModel.getUserName()}};
            requestsList = requestModel.readDataFromDB(parameters);
        } else {
            String[][] parameters = {{RequestTable.COLUMN_REQUESTTABLE_SELLERKEY}, {userModel.getUserName()}};
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
