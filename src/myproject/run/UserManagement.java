package myproject.run;

import java.util.Scanner;
import java.util.regex.Pattern;

import static myproject.bussiness.mess.CheckValidate.choiceNumber;
import static myproject.data.ConstandDesign.*;


public class UserManagement {
    public static void displayUser(Scanner sc){
        boolean checkout = true;
        do {
            System.out.println(SHOWHEARDUSER);
            int choice = choiceNumber(sc, 1, 6);
            switch (choice) {
                case 1:
                    showListUser( sc);
                    break;
                case 2:
                    addNewUser( sc);
                    break;
                case 3:
                    editUser( sc);
                    break;
                case 4:
                    deleteUser( sc);
                    break;
                case 5:
                    searchByName( sc);
                    break;
                case 6:
                    checkout = false;
                    break;
            }
        } while (checkout);
    }
    public static void showListUser(Scanner sc){
        System.out.println(SHOWLISTUSER);
    }
    public static void addNewUser(Scanner sc){
        System.out.println(ADDUSER);
    }
    public static void editUser(Scanner sc){
        System.out.println(EDITUSER);
    }
    public static void deleteUser(Scanner sc){
        System.out.println(DELETEUSER);
    }
    public static void searchByName(Scanner sc){
        System.out.println(SEARCHUSER);
    }
}
