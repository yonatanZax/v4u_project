package MainPackage;

import Model.Purchase.Purchase;
import Model.Request.Request;
import Model.Request.RequestModel;
import Model.User.User;
import Model.User.UserModel;
import Model.Vacation.Vacation;
import Model.Vacation.VacationModel;
import PaypalPackage.PaypalPayment;
import PaypalPackage.PaypalTable;
import db.Tables.*;
import javafx.application.Application;
import javafx.stage.Stage;

import java.io.File;
import java.util.List;

public class Main extends Application {

    private void initProject(){
        File directory = new File("db");
        if (! directory.exists())
            directory.mkdir();

        PaypalTable paypalTable = PaypalTable.getInstance();
        PaypalPayment paypalPayment = new PaypalPayment(null,"email1@gmail.com","email2@gmail.com",2000);
        paypalTable.InsertToTable(paypalPayment);



        UserTable.getInstance();
        VacationTable.getInstance();
        PurchaseTable.getInstance();
        RequestTable.getInstance() ;

        UserTable userTable = UserTable.getInstance();
        VacationTable vacationTable = VacationTable.getInstance();
        PurchaseTable purchaseTable = PurchaseTable.getInstance();


        RequestTable requestTable = RequestTable.getInstance();
        UserModel userModel = new UserModel();
        RequestModel requestModel = new RequestModel();
        VacationModel vacationModel = new VacationModel();
        User user1 = new User("user1","p","p","p","p",19920101);
        User user2 = new User("user2","p","p","p","p",19920101);
        User user3 = new User("user3","p","p","p","p",19920101);
        userTable.InsertToTable(user1);
        userTable.InsertToTable(user2);
        userTable.InsertToTable(user3);
//        Vacation vacation1 = new Vacation(null,"user1","TLV", "NYC",true,20181025,200);
//        Vacation vacation2 = new Vacation(null,"user1","TLV", "LAS",true,20181025,200);
//        String key = vacation2.getVacationKey();
//        Request request1 = new Request(key,"user1","user2",false,20181025,Enum_RequestState.PENDING);
//        requestModel.createNewData(request1);
//        vacationModel.createNewData(vacation1);
//        vacationModel.createNewData(vacation2);
//        request1.setTimestamp(2020);
//        requestModel.updateTable(request1);
//        List<Request> list1 = requestModel.getAllData();
//        String[][] deleteData = {{RequestTable.COLUMN_REQUESTTABLE_VACATIONKEY,"TLV"},{RequestTable.COLUMN_REQUESTTABLE_SELLERKEY,"user1"}};
//        requestModel.deleteDataFromDB(deleteData);


        System.out.println("***     Init project successfully   ***");
    }

    @Override
    public void start(Stage primaryStage) throws Exception{
        initProject();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
