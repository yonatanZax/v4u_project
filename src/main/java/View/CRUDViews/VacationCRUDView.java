package View.CRUDViews;

import MainPackage.Enum_CRUD;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.InputMethodEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class VacationCRUDView extends ACRUDView {


    public TextField price;
    public TextField destination;
    public Button cancel_btn;
    public Label priceInfo_lbl;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        price.setText("500");
        destination.setText("Spain");
    }


    @Override
    public void notifyController(Enum_CRUD arg) {
        System.out.println("VacationCRUDView: " + arg);
        setChanged();
        notifyObservers(arg);

    }

    // Todo - implementation in the controller
    public void handleSubmitButtonAction(ActionEvent event) {
        notifyController(Enum_CRUD.CREATE);
    }

    public void closeWindow() {
        Stage stage = (Stage) cancel_btn.getScene().getWindow();
        stage.close();
    }

    public void handleCancelButtonAction(ActionEvent event) {
        closeWindow();
    }

    public void createVacationSetPriceError(boolean isError) {
        if (isError) {
            priceInfo_lbl.setStyle("-fx-text-fill: #ff0000");
            priceInfo_lbl.setText("Check Price input");
        } else {
            priceInfo_lbl.setText("in U.S Dollars");
            priceInfo_lbl.setStyle("-fx-text-fill: #000000");
        }
    }

    public void createVacationDefaultPriceLabel() {
        createVacationSetPriceError(false);
    }
}
