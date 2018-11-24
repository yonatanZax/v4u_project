package Controllers;

import java.io.IOException;
import java.util.Observable;
import java.util.Observer;

import Model.Vacation.Vacation;
import View.VacationSearchView;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

/**

 */
public class VacationSearchController extends Application implements Observer {

    private VacationSearchView myView;

    private String status;
    private Stage stage;
    private Parent root;
    private FXMLLoader fxmlLoader;

    private Vacation pickedVacation;

    public VacationSearchController() {
        stage = new Stage();
        fxmlLoader = new FXMLLoader(getClass().getResource("/vacation_search_view.fxml"));
        try {
            root = fxmlLoader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Scene scene = new Scene(root);
        stage.setScene(scene);
        myView = fxmlLoader.getController();

        myView.setVacations_listview(startVacationList());

        myView.addObserver(this);
        stage.show();
    }

    public void start(Stage primaryStage) {
/*        primaryStage.setTitle("Sorting and Filtering");

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/vacation_search_view.fxml"));
            BorderPane page = (BorderPane) loader.load();
            Scene scene = new Scene(page);
            primaryStage.setScene(scene);
            myView = loader.getController();
            myView.addObserver(this);
            myView.setVacations_listview(startVacationList());

            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }*/

    }

    private Vacation[] startVacationList() {
        Vacation[] vacations = new Vacation[6];
        int i = 0;
        for ( i = 0;i <5; i++){
            vacations[i] = new Vacation(""+i,""+i,""+i,""+i,true,i);

        }
        vacations[5] =  new Vacation(""+i,""+i,""+i,""+i,false,i);
        return vacations;
    }

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void update(Observable o, Object arg) {
        if (o == myView){
            if (arg.equals("vacationPicked")){
                pickedVacation = myView.getPickedVacation();
                System.out.println("VacationController - update");
            }
        }
    }
}