package Model.User;

import javafx.beans.property.StringProperty;

import java.sql.Date;

public class User {
    private String userName;
    private String password;
    private String firstName;
    private String lastName;
    private String city;
    private int birthDate;
    private String contactInfo;

    public User() {
        this.userName = null;
        this.password = null;
        this.firstName = null;
        this.lastName = null;
        this.city = null;
        this.birthDate = 2018;
        this.contactInfo = null;
    }

    public User(String userName, String password, String firstName, String lastName, String city, int date, String contactInfo){
        this.userName = userName;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.city = city;
        this.birthDate = date;
        this.contactInfo = contactInfo;
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

    public String getContactInfo() {
        return contactInfo;
    }

    public void setContactInfo(String contactInfo) {
        this.contactInfo = contactInfo;
    }


    private String changeDateString(int date) {
        String birthDate = String.valueOf(date);
        String year = birthDate.substring(0, 4);
        String month = birthDate.substring(4, 6);
        String day = birthDate.substring(6, 8);
        return day + "/" + month + "/" + year;
    }

    @Override
    public String toString() {
        String ans = "";
        ans += "User Name: " + userName + "\n";
        ans += "Password: " + password + "\n";
        ans += "First Name: " + firstName + "\n";
        ans += "Last Name: " + lastName + "\n";
        ans += "City: " + city + "\n";
        ans += "Birth Date: " + changeDateString(birthDate) + "\n";
        return ans;

    }
}

