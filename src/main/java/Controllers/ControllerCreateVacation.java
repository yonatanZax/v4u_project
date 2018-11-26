package Controllers;

import MainPackage.Enum_CRUD;
import Model.Vacation.VacationModel;
import View.CRUDViews.VacationCRUDView;

import java.util.Observable;
import java.util.Observer;

public class ControllerCreateVacation implements Observer {


    public VacationCRUDView vacationView = new VacationCRUDView();
    public VacationModel vacationModel = new VacationModel();

    // Todo - implement here

    @Override
    public void update(Observable o, Object arg) {

        if (o.equals(vacationModel)){

        }else if (o.equals(vacationView)){
            if (arg.equals(Enum_CRUD.CREATE)){

            }
        }

    }
}
