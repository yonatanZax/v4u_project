package Controllers;

import MainPackage.Enum_CRUD;
import Model.Vacation.VacationModel;
import View.CRUDViews.VacationCRUDView;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Observable;
import java.util.Observer;

public class ControllerCreateVacation extends Observable implements Observer {

    private Stage stage;
    private Parent root;
    private FXMLLoader fxmlLoader;
    public VacationCRUDView vacationView;
    public VacationModel vacationModel = new VacationModel();

    public static final String VACATION_ADDED = "vacation_added";

    public void showStage() {
        vacationView.price.setText("500");
        vacationView.destination.setText("Spain");
        stage.show();
    }

    public ControllerCreateVacation() {
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
        vacationView = fxmlLoader.getController();
        vacationView.addObserver(this);
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

        } else if (o.equals(vacationView)) {
            if (arg.equals(Enum_CRUD.CREATE)) {
                if (!checkNumber(vacationView.price.getText())) {
                    vacationView.createVacationSetPriceError(true);
                } else {
                    double price = Double.parseDouble(vacationView.price.getText());

                    String date = vacationView.departureDate.getValue().getYear() + "" + vacationView.departureDate.getValue().getMonthValue() + "" + vacationView.departureDate.getValue().getDayOfMonth();

                    int departureDate = Integer.parseInt(date);
                    vacationModel.insertVacationToTable(vacationView.destination.getText(), price, departureDate);
                    System.out.println("ControllerCreateVacation: update by vacationModel");
                    System.out.println("ControllerCreateVacation: " + vacationModel.getUserName() + " registered vacation to " + vacationView.destination.getText());
                    vacationView.closeWindow();
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

