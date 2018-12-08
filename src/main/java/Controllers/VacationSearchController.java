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
public class VacationSearchController extends Observable implements Observer {

    private UserModel userModel;
    private VacationSearchView myView;
    private VacationModel vacationModel = new VacationModel(userModel);
    private String status;
    private Scene scene;
    private Parent root;
    private FXMLLoader fxmlLoader;

    private Vacation pickedVacation;

    public static final String BTN_ADD = "add_btn";

    public VacationSearchController() {
        fxmlLoader = new FXMLLoader(getClass().getResource("/vacation_search_view.fxml"));
        try {
            root = fxmlLoader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        myView = fxmlLoader.getController();

        // TODO - PAY ATTENTION: added to vacationTable price (double) -> constructor changed!

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
            if (arg.equals(VacationSearchView.VACATION_PICKED)){
                pickedVacation = myView.getPickedVacation();
                vacationPicked();
            }
            else if (arg.equals(VacationSearchView.BTN_ADD)){
                setChanged();
                notifyObservers(BTN_ADD);
            }
        }
    }
}