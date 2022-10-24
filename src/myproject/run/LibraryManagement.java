package myproject.run;

import myproject.bussiness.entity.User;
import myproject.bussiness.impl.UserImpl;
import myproject.data.ConstantRegexAndUrl;
import myproject.data.WriteAndReadData;

import java.io.*;
import java.util.Scanner;

import static myproject.bussiness.mess.CheckValidate.*;
import static myproject.bussiness.mess.Message.*;
import static myproject.data.ConstandDesign.*;

public class LibraryManagement {
    public static UserImpl userImpl = new UserImpl();

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        do {
            System.out.println(SHOWHEARD);
            int choice = choiceNumber(sc, 1, 3);
            switch (choice) {
                case 1:
                    login(sc);
                    break;
                case 2:
                    register(sc);
                    break;
                case 3:
                    System.exit(0);
            }
        } while (true);
    }

    public static void login(Scanner sc) {
        do {
            System.out.println(SHOWHLOGIN);
            System.out.print(USERNAMELOGIN);
            String userName = sc.nextLine();
            System.out.print(USERPASS);
            String passWord = sc.nextLine();
            User user = userImpl.checkLogin(userName, passWord);
            if (user != null) {
                writeToFileLogin(user);
                if (user.isPermission()) {
                    displayAdminMenu(sc);
                } else {
                    UserManagement.displayMenuUser(sc);
                    break;
                }
                break;
            } else {
                System.out.println(SHOWHEARDERROR);
                int choice = choiceNumber(sc, 1, 3);
                if (choice == 2) {
                    register(sc);
                } else if (choice == 3) {
                    break;
                }
            }
        } while (true);
    }

    public static void register(Scanner sc) {
        System.out.println(SHOWHREGISTER);
        User user = userImpl.inputData(sc);
        boolean result = userImpl.create(user);
        soutMess(result);
    }

    public static void displayAdminMenu(Scanner sc) {
        boolean checkout = true;
        do {
            System.out.println(SHOWADMINMENU);
            int choice = choiceNumber(sc, 1, 5);
            switch (choice) {
                case 1:
                    Catalogmanagement.displayCatalogMenu(sc);
                    break;
                case 2:
                    BookManagement.displayBookMenu(sc);
                    break;
                case 3:
                    AdminManagement.displayUser(sc);
                    break;
                case 4:
                    LibraryBookCardManagement.displayLBCard(sc);
                    break;
                case 5:
                    checkout = false;
                    break;
            }
        } while (checkout);
    }

    public static void writeToFileLogin(User user) {
        File file = null;
        FileOutputStream fos = null;
        ObjectOutputStream oos = null;
        try {
            file = new File(ConstantRegexAndUrl.URL_CheckLogin);
            fos = new FileOutputStream(file);
            oos = new ObjectOutputStream(fos);
            oos.writeObject(user);
        } catch (Exception ex1) {
            ex1.printStackTrace();
        } finally {
            try {
                if (fos != null) {
                    fos.close();
                }
                if (oos != null) {
                    oos.close();
                }
            } catch (Exception ex2) {
                ex2.printStackTrace();
            }
        }
    }

    public User readFormFile() {
        User user = new User();
        File file = null;
        FileInputStream fis = null;
        ObjectInputStream ois = null;
        try {
            file=new File(ConstantRegexAndUrl.URL_CheckLogin);
            if (file.exists()){
                fis=new FileInputStream(file);
                ois=new ObjectInputStream(fis);
                user= (User) ois.readObject();
            }
        }catch (Exception ex1){
            ex1.printStackTrace();
        }finally {
            try {
               if (fis!=null){
                   fis.close();
               }if (ois!=null){
                   ois.close();
                }
            }catch (Exception ex2){
               ex2.printStackTrace();
            }
        }
return user;
    }
}
