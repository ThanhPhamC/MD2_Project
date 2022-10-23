package myproject.run;

import myproject.bussiness.entity.Book;
import myproject.bussiness.entity.User;
import myproject.bussiness.impl.BookImpl;
import myproject.bussiness.impl.CatalogImpl;
import myproject.bussiness.impl.LibraryBookCardImpl;
import myproject.bussiness.impl.UserImpl;


import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import static myproject.bussiness.mess.CheckValidate.*;
import static myproject.bussiness.mess.Message.*;
import static myproject.data.ConstandDesign.*;

public class LibraryManagement {

    public static UserImpl userImpl = new UserImpl();
    public static BookImpl bookUmpl = new BookImpl();
    public static CatalogImpl catalogOmpl =new CatalogImpl();
    public static LibraryBookCardImpl lbCardImpl= new LibraryBookCardImpl();

    public static void main(String[] args) {
        List<Book> bookList= bookUmpl.readFromFile();




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
                if (user.isPermission()) {
                    displayAdminMenu(sc);
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
        User user = new User();
        user = userImpl.inputData(sc);
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
                    UserManagement.displayUser(sc);
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

}
