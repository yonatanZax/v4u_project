package View;

import javafx.event.ActionEvent;
import javafx.scene.control.*;

public class CreateVacationView {
    public Label errorMessage_d_date;
    public Label errorMessage_r_date;
    public Label errorMessage_d_time;
    public Label errorMessage_r_time;
    public TextField price;
    public TextField flightNumber;
    public TextField airline;
    public TextField destination;
    public TextField numberOfTickets;
    public ChoiceBox vacation_type_choice;
    public ChoiceBox accommodation_type_choice;
    public TextField depart_time;
    public TextField return_time;
    public DatePicker return_datePicker;
    public DatePicker depart_datePicker;
    public TextArea freeText;

    public void handleCancelButtonAction(ActionEvent actionEvent) {
    }

    public void handleSubmitButtonAction(ActionEvent actionEvent) {
    }
}
