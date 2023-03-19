package Logic;

import GUI.LoginController;
import SQL.UserTable;

public class Account{
    public static String email, pass, username;
    public static boolean isLoggedIn = false;
    public static UserTable user = new UserTable();

    public static boolean LogIn(String email_in, String pass_in){
        if(LoginController.validate_email(email_in)){
            String UserString = user.getUserByEmail(email_in);
            separateUser(UserString);
            if(email_in.equals(email) && pass_in.equals(pass)){
                isLoggedIn = true;
            }else{
                isLoggedIn = false;
            }
        }
        return isLoggedIn;
    }

    public static boolean LogOut(){
        if(isLoggedIn){
            isLoggedIn = false;
            return true;
        }else return false;
    }

    public static boolean createAccount(String email_in, String pass_in){
        if(LoginController.validate_email(email_in)){
            if(user.getUserByEmail(email_in).isEmpty()){
                isLoggedIn = true;
                if(user.addUser(email_in, pass_in)){
                    String UserString = user.getUserByEmail(email_in);
                    separateUser(UserString);
                    return true;
                }
            }
        }
        return false;
    }

    public static boolean deleteAccount(String email_in){
        if(isLoggedIn){
            isLoggedIn = false;
            return user.removeUserByEmail(email_in);
        }
        return false;
    }

    public static boolean changePassword(String email_in, String new_pass){
        if(isLoggedIn){
            return user.updatePassword(email_in, new_pass);
        }
        return false;
    }

    public  static void separateUser(String UserString){
        if(UserString.contains(";")){
            String[] parts = UserString.split(";");
            email = parts[0];
            pass = parts[1];
        }else{
            email = null;
            pass = null;
        }
        if(UserString.contains("@")){
            String[] us = email.split("@"); //separar o username
            username = us[0];
        }else{
            username = null;
        }
    }
}
