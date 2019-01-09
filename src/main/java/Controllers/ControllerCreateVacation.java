package Controllers;

import MainPackage.Enum_CRUD;
import Model.Vacation.VacationModel;
import View.CRUDViews.CreateVacationView;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Observable;
import java.util.Observer;

public class ControllerCreateVacation extends Observable implements Observer {

    private Stage stage;
    private Parent root;
    private FXMLLoader fxmlLoader;
    public CreateVacationView createVacationView;
    public VacationModel vacationModel;

    public static final String VACATION_ADDED = "vacation_added";

    public void showStage() {
        createVacationView.price.setText("500");
        createVacationView.destination.setText("Spain");
        stage.show();
    }

    public ControllerCreateVacation(VacationModel vacationModel) {
        this.vacationModel = vacationModel;
        stage = new Stage();
        fxmlLoader = new FXMLLoader(getClass().getResource("/createVacation_view.fxml"));
        try {
            root = fxmlLoader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Scene scene = new Scene(root);
        stage.getIcons().add(new Image("/images/create.png"));
        stage.setScene(scene);
        createVacationView = fxmlLoader.getController();
        createVacationView.addObserver(this);
    }

    private boolean checkNumber(String num) {
        try {
            Double.parseDouble(num);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    private void showAlertWithoutHeaderText(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }

    @Override
    public void update(Observable o, Object arg) {

        if (o.equals(vacationModel)) {

        } else if (o.equals(createVacationView)) {
            if (arg.equals(Enum_CRUD.CREATE)) {
                if (!checkNumber(createVacationView.price.getText())) {
                    createVacationView.createVacationSetPriceError(true);
                } else {
                    double price = Double.parseDouble(createVacationView.price.getText());
                    String day = String.valueOf(createVacationView.departureDate.getValue().getDayOfMonth());
                    String month = String.valueOf(createVacationView.departureDate.getValue().getMonthValue());
                    if (day.length() < 2){
                        day = "0" + day;
                    }
                    if (month.length() < 2){
                        month = "0" + month;
                    }
                    String date = createVacationView.departureDate.getValue().getYear() + "" + month + day;

                    int departureDate = Integer.parseInt(date);
                    vacationModel.insertVacationToTable(createVacationView.destination.getText(), price, departureDate, createVacationView.exchange_checkBox.isSelected());
                    System.out.println("ControllerCreateVacation: update by vacationModel");
                    System.out.println("ControllerCreateVacation: " + vacationModel.getUserName() + " registered vacation to " + createVacationView.destination.getText());
                    createVacationView.closeWindow();
                    setChanged();
                    notifyObservers(VACATION_ADDED);
                }
            } else {
                String title = "houston we have a problem";
                String content = "We have some problems right now.. \n Please try again later..";
                showAlertWithoutHeaderText(title, content);
            }
        }
    }
}

