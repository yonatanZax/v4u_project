package View;


import Controllers.VacationSearchController;
import Model.Vacation.Vacation;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;

import javafx.fxml.FXML;
import javafx.scene.control.*;


import java.time.LocalDate;
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

    @FXML
    public TableColumn<Vacation,String> sellerColumn;

    @FXML
    public TableColumn<Vacation,String> departureDateColumn;

    private ObservableList<Vacation> masterData = FXCollections.observableArrayList();

    private Vacation pickedVacation;


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
        sellerColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getSellerKey()));
        departureDateColumn.setCellValueFactory(cellData -> new SimpleStringProperty("" + cellData.getValue().getDepartureDate()));


        // 1. Wrap the ObservableList in a FilteredList (initially display all data).
        FilteredList<Vacation> filteredData = new FilteredList<>(masterData, p -> true);
        filteredData.setPredicate(this::predicateForTable);


        // 2. Set the filter Predicate whenever the filter changes.
        destinationField.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(vacation -> {

                // Compare first name and last name of every person with filter text.
                String lowerCaseFilter = newValue.toLowerCase();

                if (vacation.getDestination().toLowerCase().contains(lowerCaseFilter)) {
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

    }

    private boolean predicateForTable(Vacation vacation){
        int departureDateInt = vacation.getDepartureDate();
        int todayDateInt = convertDateStringToInt(LocalDate.now().toString());
        boolean booleanVal =  departureDateInt > todayDateInt;
//        System.out.println("departureDateInt: " + departureDateInt + " , todayDateInt: " + todayDateInt + " , booleanVal: " + booleanVal);
        return booleanVal;
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

    private int convertDateStringToInt(String str) {
        if(str != null && !str.equals("")){
            String[] tempArr = str.split("-");
            String temp = tempArr[0] + tempArr[1] + tempArr[2];
            return Integer.valueOf(temp);
        }
        return 0;
    }
}


