package db;

import java.sql.Date;

public class User {
    private String userName;
    private String password;
    private String firstName;
    private String lastName;
    private String city;
    private int birthDate;

    public User() {
        this.userName = null;
        this.password = null;
        this.firstName = null;
        this.lastName = null;
        this.city = null;
        this.birthDate = 2018;
    }

    public User(String userName, String password, String firstName, String lastName, String city, int date){
        this.userName = userName;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.city = city;
        this.birthDate = date;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setBirthDate(int birthDate) {
        this.birthDate = birthDate;
    }

    public String getUserName() {
        return userName;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getPassword() {
        return password;
    }

    public String getCity() {
        return city;
    }

    public int getBirthDate() {
        return birthDate;
    }

    @Override
    public String toString() {
        String ans = "";
        ans += "textField: " + userName + "\n";
        ans += "password: " + password + "\n";
        ans += "firstName: " + firstName + "\n";
        ans += "lastName: " + lastName + "\n";
        ans += "city: " + city + "\n";
        ans += "birthDate: " + birthDate + "\n";
        return ans;

    }
}

