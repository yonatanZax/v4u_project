package Model.Vacation;

public class Vacation {

    private String vacationKey;
    // Todo - add more fields ( also getters and setters )


    public Vacation(){
        this.vacationKey = null;
    }

    public Vacation(String vKey){
        this.vacationKey = vKey;
    }

    public String getVacationKey() {
        return vacationKey;
    }

    public void setVacationKey(String vacationKey) {
        this.vacationKey = vacationKey;
    }
}
