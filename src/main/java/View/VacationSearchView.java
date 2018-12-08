package View;

import Controllers.ControllerLogin;
import Controllers.VacationSearchController;
import Model.Vacation.Vacation;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;


import java.util.List;
import java.util.Observable;

public class VacationSearchView extends Observable{

    @FXML
    private TextField destinationField;

    @FXML
    public TableView<Vacation> vacations_tableview;

    @FXML
    public TableColumn<Vacation,String> destinationColumn;

    @FXML
    public TableColumn<Vacation,String> originColumn;

    @FXML
    public TableColumn<Vacation,String> priceColumn;

    private ObservableList<Vacation> masterData = FXCollections.observableArrayList();

    private Vacation pickedVacation;

    //public static final String BTN_ADD = "add_vacation";



    @FXML
    public void initialize() {

        // set the table for double click
        vacations_tableview.setRowFactory(tv -> {
            TableRow<Vacation> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2 && (! row.isEmpty()) ) {
                    Vacation rowData = row.getItem();
                    System.out.println("Vacation picked: " + rowData.getVacationKey());
                    pickedVacation = rowData;
                    this.setChanged();
                    this.notifyObservers(VacationSearchController.VACATION_PICKED);
                }
            });
            return row ;
        });

        destinationColumn.setCellValueFactory(cellData -> cellData.getValue().destinationProperty());
        originColumn.setCellValueFactory(cellData -> cellData.getValue().originProperty());
        priceColumn.setCellValueFactory(cellData -> new SimpleStringProperty("" +cellData.getValue().getPrice()));


        // 1. Wrap the ObservableList in a FilteredList (initially display all data).
        FilteredList<Vacation> filteredData = new FilteredList<>(masterData, p -> true);
        filteredData.setPredicate(Vacation::isVisible);

        // 2. Set the filter Predicate whenever the filter changes.
        destinationField.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(person -> {
                // If filter text is empty, display all persons.
                if (newValue == null || newValue.isEmpty()) {
                    return person.isVisible();
                }
                // Compare first name and last name of every person with filter text.
                String lowerCaseFilter = newValue.toLowerCase();

                if (person.getDestination().toLowerCase().contains(lowerCaseFilter)) {
                    return true; // Filter matches first name.
                }
                return false; // Does not match.
            });
        });

        // 3. Wrap the FilteredList in a SortedList.
        SortedList<Vacation> sortedData = new SortedList<>(filteredData);

        // 4. Bind the SortedList comparator to the TableView comparator.
        sortedData.comparatorProperty().bind(vacations_tableview.comparatorProperty());

        // 5. Add sorted (and filtered) data to the table.
        vacations_tableview.setItems(sortedData);

/*        vacations_tableview.getSelectionModel().selectedItemProperty().addListener((ObservableValue<? extends Vacation> ov, Vacation old_val,
                                                                                    Vacation new_val) -> {
            System.out.println(new_val.getDestination());

        });*/

    }

    public Vacation getPickedVacation() {
        return pickedVacation;
    }


    public void addVacationOnAction(){
        setChanged();
        notifyObservers(VacationSearchController.BTN_ADD);
    }


    public void setVacations_listview(List<Vacation> vacationList) {
        Vacation[] vacations = new Vacation[vacationList.size()];
        for (int i = 0 ; i < vacations.length ; i++){
            vacations[i] = vacationList.get(i);
        }
        masterData.clear();
        masterData.addAll(vacations);
    }
}


