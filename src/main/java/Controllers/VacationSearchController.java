package Controllers;

import java.io.IOException;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

import Model.User.UserModel;
import Model.Vacation.Vacation;
import Model.Vacation.VacationModel;
import View.VacationSearchView;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.stage.Window;

/**

 */
public class VacationSearchController implements Observer {

    private UserModel userModel;
    private VacationSearchView myView;
    private VacationModel vacationModel = new VacationModel(userModel);
    private String status;
    private Scene scene;
    private Parent root;
    private FXMLLoader fxmlLoader;


    private Vacation pickedVacation;

    public VacationSearchController() {
        fxmlLoader = new FXMLLoader(getClass().getResource("/vacation_search_view.fxml"));
        try {
            root = fxmlLoader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
//        scene = new Scene(root);
        myView = fxmlLoader.getController();

        // TODO - PAY ATTENTION: added to vacationTable price (double) -> constructor changed!


//        myView.setVacations_listview(startVacationList());

        myView.addObserver(this);


        updateSubScene();

    }

    public Parent getRoot(){
        return root;
    }

    public void updateSubScene(){
        List<Vacation> vacationList = vacationModel.getAllData();
        myView.setVacations_listview(vacationList);
    }


    private void vacationPicked(){
        if (UserModel.isLoggedIn()){

        }
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