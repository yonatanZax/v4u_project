package View;

import Model.Vacation.Vacation;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class VacationView {

    @FXML
    Button buy_btn;

    private Vacation vacation;

    @FXML
    public void initialize() {
        buy_btn.setOnAction(event -> {
            System.out.println("Buy was clicked");
        });
    }

    public void setVacation(Vacation vacation) {

    }
}
