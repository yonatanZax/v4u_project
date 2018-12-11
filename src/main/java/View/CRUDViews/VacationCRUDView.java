package View.CRUDViews;

import MainPackage.Enum_CRUD;
import javafx.event.ActionEvent;
import javafx.scene.control.*;
import javafx.scene.input.InputMethodEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class VacationCRUDView extends ACRUDView {

    public TextField price;
    public TextField destination;
    public Button cancel_btn;
    public Label priceInfo_lbl;
    public DatePicker departureDate;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        price.setText("500");
        destination.setText("Spain");
        departureDate.setValue(LocalDate.now().plusDays(1));
        departureDate.setDayCellFactory(create_datePicker-> new DateCell(){
            public void updateItem(LocalDate date, boolean empty) {
                super.updateItem(date, empty);
                LocalDate today = LocalDate.now();
//                LocalDate today18 = today.minusYears(18);

                setDisable(empty || date.compareTo(today) < 0 );
            }
        });
    }


    @Override
    public void notifyController(Enum_CRUD arg) {
        System.out.println("VacationCRUDView: " + arg);
        setChanged();
        notifyObservers(arg);

    }


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
