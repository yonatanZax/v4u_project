package MainPackage;

import Controllers.ControllerHome;
import Controllers.ControllerUserCRUD;

import Model.Request.Request;
import Model.Request.RequestModel;
import Model.User.User;
import Model.Vacation.Vacation;
import Model.Vacation.VacationModel;
import db.Tables.PurchaseTable;
import db.Tables.RequestTable;
import db.Tables.UserTable;
import db.Tables.VacationTable;
import javafx.application.Application;
import javafx.stage.Stage;

import java.io.File;
import java.util.List;

public class Main extends Application {



    public static String user = null;


    private void initProject(){
        File directory = new File("db");
        if (! directory.exists())
            directory.mkdir();

        UserTable.getInstance().createTable();
        VacationTable.getInstance().createTable();
        PurchaseTable.getInstance().createTable();
        RequestTable.getInstance().createTable();

        UserTable userTable = UserTable.getInstance();
        VacationTable vacationTable = VacationTable.getInstance();
        PurchaseTable purchaseTable = PurchaseTable.getInstance();
        RequestTable requestTable = RequestTable.getInstance();
        RequestModel requestModel = new RequestModel();
        VacationModel vacationModel = new VacationModel();
        User user1 = new User("user1","p","p","p","p",19920101);
        User user2 = new User("user2","p","p","p","p",19920101);
        userTable.InsertToTable(user1);
        userTable.InsertToTable(user2);
        Vacation vacation1 = new Vacation(null,"user1","TLV", "LAS",true,1025,200);
        Request request1 = new Request("TLV","user1","user2",false,1025);
        requestModel.createNewData(request1);
        vacationModel.createNewData(vacation1);
        request1.setTimestamp(2020);
        //requestModel.updateTable(request1);
        List<Request> list1 = requestModel.getAllData();
        String[][] deleteData = {{RequestTable.COLUMN_REQUESTTABLE_VACATIONKEY,"TLV"},{RequestTable.COLUMN_REQUESTTABLE_SELLERKEY,"user1"}};
        requestModel.deleteDataFromDB(deleteData);


        System.out.println("***     Init project successfully   ***");
    }

    @Override
    public void start(Stage primaryStage) throws Exception{

        initProject();
        ControllerHome home = new ControllerHome();
        home.showStage();

        //ControllerUserCRUD controllerUserCRUD = new ControllerUserCRUD();
        //controllerUserCRUD.showStage();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
