package Model.Vacation;

import Model.ACRUDModel;
import db.DBResult;
import db.Tables.VacationTable;

public class VacationModel extends ACRUDModel<Vacation> {

    private VacationTable vacationTable;

    public VacationModel(){ VacationTable.getInstance();}


    @Override
    public void updateTable(Vacation vacation) {
        DBResult result = DBResult.NONE;
        String [] where = {vacationTable.COLUMN_TABLE_KEY};

        // Todo - implement set and values
        String [] set = {vacationTable.COLUMN_TABLE_KEY};
        String [] values = {vacation.getVacationKey()};


        result = vacationTable.updateData(set , values, where);
        setChanged();
        notifyObservers(result);
    }
}
