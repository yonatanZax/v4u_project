package MainPackage;

import Controllers.ControllerHome;
import Model.User.User;
import Model.Vacation.Vacation;
import PaypalPackage.PaypalTable;
import db.Tables.*;
import javafx.application.Application;
import javafx.stage.Stage;

import java.io.File;

public class Main extends Application {
    private static void initProject() {
        File directory = new File("db");
        if (!directory.exists()) {
            directory.mkdir();
            PaypalTable paypalTable = PaypalTable.getInstance();
            UserTable userTable = UserTable.getInstance();
            VacationTable vacationTable = VacationTable.getInstance();
            PurchaseTable purchaseTable = PurchaseTable.getInstance();
            RequestTable requestTable = RequestTable.getInstance();
            userTable.InsertToTable(new User("MightySalmon", "goldFish", "Adir", "Salomon", "Beer Sheva", 19900101));
            userTable.InsertToTable(new User("LiorTheTester", "youGetOneHundred", "Lior", "Ben Eliezer", "Beer Sheva", 19900202));
            userTable.InsertToTable(new User("DUnknown", "UML4life", "Rami", "Puzis", "Beer Sheva", 19900303));
            userTable.InsertToTable(new User("ForShizelMyNizel", "UltimatePassword", "Daniel", "Nahmias", "Beer Sheva", 19900404));
            vacationTable.InsertToTable(new Vacation(null, "MightySalmon", "Tel Aviv", "New York", true, 20181211, 2000, 20191210,true));
            vacationTable.InsertToTable(new Vacation(null, "DUnknown", "Tel Aviv", "Moscow", true, 20181211, 350, 20190307,true));
            vacationTable.InsertToTable(new Vacation(null, "ForShizelMyNizel", "Tel Aviv", "Delhi", true, 20181210, 350, 20190604,true));
            vacationTable.updateTableByDate();
            System.out.println("*     Init project successfully   *");
        }
        VacationTable.getInstance().updateTableByDate();
    }


    public static void main(String[] args) {
        initProject();
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        new ControllerHome();
    }
}
