package View.CRUDViews;

import MainPackage.Enum_CRUD;
import javafx.event.ActionEvent;

import java.net.URL;
import java.util.ResourceBundle;

public class VacationCRUDView extends ACRUDView {



    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }


    @Override
    public void notifyController(Enum_CRUD arg) {
        System.out.println("VacationCRUDView: " + arg);
        setChanged();
        notifyObservers(arg);

    }

    public void handleCancelButtonAction(ActionEvent event) {
        // Todo - implement "close stage"
    }

    public void handleSubmitButtonAction(ActionEvent event) {
        // Todo - implementation in the controller
        notifyController(Enum_CRUD.CREATE);
    }
}
