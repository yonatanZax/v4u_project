package MainPackage;

import db.Managers.DBResult;
import db.User;
import db.UserTable_Model;

public class CreateUser_controller {

    private UserTable_Model userTable;
    private User updatedUser = null;
    private static String [] updateUserName = null;


    public CreateUser_controller(){
        userTable = UserTable_Model.getInstance();
    }




    /**
     * This method checks if the parameters are valid and creates a User
     * @param values: An array with parameters to create a new user
     * @param date: The user's birthday
     * @return
     */
    private User createUserIfValuesAreValid(String[] values, int date){
        User newUser = new User();
        for (String val: values) {
            if (val.equals(""))
                return null;
        }
        if (date <= 0)
            return null;

        newUser.setUserName(values[0]);
        newUser.setPassword(values[1]);
        newUser.setFirstName(values[2]);
        newUser.setLastName(values[3]);
        newUser.setCity(values[4]);
        newUser.setBirthDate(date);
        return newUser;
    }



    public String addUserToDB(String[] values,int date){
        String added = "";
        DBResult result = DBResult.NONE;
        // Creates a new user if the values a valid
        User newUser = createUserIfValuesAreValid(values,date);


        if (newUser != null){
            // Try to add the new user to the database
            result = updatedUser==null? userTable.InsertToTable(newUser): userTable.UpdateUser(newUser);

            if (result == DBResult.ADDED || result == DBResult.UPDATED){
                added = "User was "+ (updatedUser==null? "added": "updated") + " successfully";
            }else if (result == DBResult.ALREADY_EXIST) {
                added = "User already exists";
            }else if (result == DBResult.ERROR)
                added = "Error while inserting new user";
        }else
            added = "Please fill all the fields..";

        return added;

    }


    public void setUpdatedUser(){
        updatedUser = userTable.readUsers(updateUserName).get(0);
    }





}
