package View.CRUDViews;

import MainPackage.Enum_CRUD;

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
}
