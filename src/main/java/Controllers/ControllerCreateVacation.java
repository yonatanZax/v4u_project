package Controllers;

import View.CreateVacationView;

import java.util.Observable;
import java.util.Observer;

public class ControllerCreateVacation implements Observer {

    private CreateVacationView vacationView;


    private void initCountriesForDestination() {
        String countries = "Andorra,Australia,Bahamas,Bhutan,Canada,Switzerland,Spain,Hong Kong";
        String[] country_list = countries.split(",");
        for (String country : country_list){
            vacationView.destination.getItems().add(country);
        }
    }


    @Override
    public void update(Observable o, Object arg) {

    }
}
