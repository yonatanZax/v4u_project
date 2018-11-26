package Controllers;

import MainPackage.Enum_CRUD;
import Model.Vacation.VacationModel;
import View.CRUDViews.VacationCRUDView;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Observable;
import java.util.Observer;

public class ControllerCreateVacation implements Observer {

    private Stage stage;
    private Parent root;
    private FXMLLoader fxmlLoader;
    public VacationCRUDView vacationView;
    public VacationModel vacationModel;

    // Todo - implement here

    public void showStage() {
        vacationView.price.setText("500");
        vacationView.destination.setText("Spain");
        stage.show();
    }

    public ControllerCreateVacation(VacationModel model){
        stage = new Stage();
        fxmlLoader = new FXMLLoader(getClass().getResource("/createVacation_view.fxml"));
        try {
            root = fxmlLoader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Scene scene = new Scene(root);
        stage.setScene(scene);
        vacationModel = model;
        vacationView = fxmlLoader.getController();
        vacationView.addObserver(this);
    }

    private boolean checkNumber(String num){
        try{
            Double.parseDouble(num);
            return true;
        } catch (NumberFormatException e){
            return false;
        }
    }

    @Override
    public void update(Observable o, Object arg) {

        if (o.equals(vacationModel)){

        }else if (o.equals(vacationView)){
            if (arg.equals(Enum_CRUD.CREATE)){
                if(!checkNumber(vacationView.price.getText())){
                    vacationView.createVacationSetPriceError(true);
                } else { // TODO - how do we check the destination
                vacationView.closeWindow();
                }
            }
        }
    }
}
