package View;

import javafx.event.ActionEvent;
import javafx.scene.control.Label;

import java.util.Observable;

public class HomeView extends Observable{

    public Label isLoged_lbl;


    public void setLogedLabel(String name){
        this.isLoged_lbl.setText(name);
    }

    public void userCRUD(ActionEvent event) {
        setChanged();
        notifyObservers("UserCRUD");
    }

    public void login(ActionEvent event) {
        setChanged();
        notifyObservers("Login");
    }

    public void search(ActionEvent event) {
        setChanged();
        notifyObservers("Search");
    }

    public void createVacation(ActionEvent event) {
        setChanged();
        notifyObservers("CreateVacation");
    }
}
