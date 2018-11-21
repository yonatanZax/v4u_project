package MainPackage;

import Controllers.ControllerUserCRUD;

import db.Tables.RequestTable;
import db.Tables.VacationTable;
import javafx.application.Application;
import javafx.stage.Stage;

import java.io.File;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{

        File directory = new File("db");
        if (! directory.exists())
            directory.mkdir();
        ControllerUserCRUD controllerUserCRUD = new ControllerUserCRUD();
        controllerUserCRUD.showStage();
        VacationTable.getInstance().createTable();
        RequestTable requestTable = RequestTable.getInstance();
        requestTable.createTable();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
