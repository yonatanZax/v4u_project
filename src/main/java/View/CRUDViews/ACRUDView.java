package View.CRUDViews;

import MainPackage.Enum_CRUD;
import javafx.fxml.Initializable;

import java.util.Observable;

public abstract class ACRUDView extends Observable implements Initializable {


    abstract public void notifyController(Enum_CRUD arg);

    public void read() {
        notifyController(Enum_CRUD.READ);
    }

    public void create() {
        notifyController(Enum_CRUD.CREATE);
    }

    public void update() {
        notifyController(Enum_CRUD.UPDATE);
    }

    public void delete() {
        notifyController(Enum_CRUD.DELETE);
    }


}
