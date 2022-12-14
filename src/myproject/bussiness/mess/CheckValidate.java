package myproject.bussiness.mess;

import javafx.scene.input.DataFormat;
import jdk.nashorn.internal.ir.LiteralNode;
import myproject.bussiness.entity.Book;
import myproject.bussiness.entity.Catalog;
import myproject.bussiness.entity.User;
import myproject.bussiness.impl.BookImpl;
import myproject.bussiness.impl.CatalogImpl;
import myproject.bussiness.impl.LibraryBookCardImpl;
import myproject.bussiness.impl.UserImpl;
import myproject.data.WriteAndReadData;
import myproject.run.BookManagement;
import myproject.run.Catalogmanagement;

import java.text.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;


import static myproject.bussiness.mess.Message.*;
import static myproject.data.ConstantRegexAndUrl.*;

public class CheckValidate {
    public static String strValidate(Scanner sc, String str) {                              // check input all
        String strOutput;
        do {
            strOutput = sc.nextLine();
            if (strOutput.trim().matches(str)) {
                break;
            } else {
                System.out.println(FORMATERROR);
            }
        } while (true);
        return strOutput;
    }

    public static int choiceNumber(Scanner sc, int a, int b) {                        // choice int number
        int number;
        do {
            System.out.print(YOURCHOICE);
            try {
                number = Integer.parseInt(sc.nextLine());
                if (number >= a && number <= b) {
                    break;
                } else {
                    System.out.print(ERRCHOICENUMBER + a + "-" + b + " :");
                }
            } catch (Exception e) {
                System.out.print(FORMATERROR);
            }
        } while (true);
        return number;
    }

    public static Date dateValidate(Scanner sc) {                                      // validate date
        String intputDate = strValidate(sc, REGEXDATE);
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        Date date = new Date();
        try {
            date = dateFormat.parse(intputDate);
            return date;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return date;
    }

    public static String checkCatalogName(Scanner sc) {                             // check catalog name
        CatalogImpl catalogImpl = new CatalogImpl();
        List<Catalog> catalogList = catalogImpl.readFromFile();
        String strName;
        do {
            strName = strValidate(sc, REGEXFULLNAME);
            boolean check = false;
            for (Catalog catalog : catalogList) {
                if (catalog.getCatalogName().equals(strName)) {
                    check = true;
                    break;
                }
            }
            if (check) {
                System.out.println(NAMEERROR1);
            } else {
                break;
            }
        } while (true);
        return strName;
    }

    public static String checkbookName(Scanner sc) {                                    // check book name
        BookImpl bookImpl = new BookImpl();
        List<Book> bookList = bookImpl.readFromFile();
        String strName;
        do {
            strName = strValidate(sc, REGEXFULLNAME);
            boolean check = false;
            for (Book book : bookList) {
                if (book.getBookName().equals(strName)) {
                    check = true;
                    break;
                }
            }
            if (check) {
                System.out.println(NAMEERROR1);
            } else {
                break;
            }
        } while (true);

        return strName;
    }

    public static boolean choiceBooleanStatus(Scanner sc) {                             // check choice status
        boolean status = true;
        System.out.println("1." + STATUS1 + "       2." + STATUS3);
        int choice = choiceNumber(sc, 1, 2);
        if (choice == 2) {
            status = false;
        }
        return status;
    }

    public static ArrayList<Book> bookListCard(Scanner sc) {                    // check list book add card
        BookImpl bookImpl = new BookImpl();
        List<Book> bookListReturn = new ArrayList<>();
        do {
            List<Book> bookList = bookImpl.readFromFile();
            System.out.println("+----------------------------------------------+\n" +
                    "|    M?? s??ch.        T??n s??ch.                 |");
            for (Book book : bookList) {
                if (book.getBookStatus().equals(STATUS1)) {
                    System.out.printf("|  %-10s    %-30s|\n", book.getBookId(), book.getBookName());
                }
            }
            System.out.println("+----------------------------------------------+");
            System.out.print("Nh???p Id s??ch mu???n th??m: ");
            String choiceId = strValidate(sc, REGEXBOOKID);
            for (Book book : bookList) {
                if (book.getBookId().equals(choiceId)) {
                        boolean check = false;
                        for (Book book1 : bookListReturn) {
                            if (book1.getBookId().equals(choiceId)) {
                                check = true;
                                break;
                            }
                        }
                        if (check) {
                            System.out.println("S??ch ???? c?? trong th??? m?????n, h??y th??m s??ch kh??c.");
                            break;
                        } else {
                            bookListReturn.add(book);
                            book.setBookquantity(book.getBookquantity() - 1);
                            bookImpl.writeToFile(bookList);
                            break;
                        }

                }
            }
            System.out.println("b???n c?? mu???n th??m s??ch kh??c kh??ng?");
            System.out.println(" 1. c??      2. kh??ng");
            int choice = choiceNumber(sc, 1, 2);
            if (choice != 1) {
                break;
            }
        } while (true);

        return (ArrayList<Book>) bookListReturn;
    }

    public static User userForCard(Scanner sc) {                                     // choice user for card book
        LibraryBookCardImpl lbCardImpl = new LibraryBookCardImpl();
        User user = new User();
        UserImpl userImpl = new UserImpl();
        List<User> userList = lbCardImpl.userStatus();
        do {
            System.out.println("+----------------------------------------------+\n"+
                               "|   M?? ng?????i ?????c      T??n ng?????i ?????c            |");
            for (User user1 : userList) {
                if (user1.isUserStatus()) {
                    System.out.printf("|    %-5d          %-25s  |\n", user1.getUserId(), user1.getUserName());
                }
            }
            System.out.println("+----------------------------------------------+");
            System.out.println(YOURCHOICE);
            int choice = Integer.parseInt(strValidate(sc, REGEXQUANTITY));
            boolean check = false;
            for (User use : userList) {
                if (use.getUserId() == choice) {
                    user = use;
                    check = true;
                    break;
                }
            }
            if (check) {
                break;
            } else {
                System.out.println("Vui l??ng ch???n theo danh s??ch hi???n th???.");
            }
        } while (true);
        return user;
    }

    public static void soutMess(boolean boo) {
        if (boo) {
            System.out.println("Th??nh c??ng");
        } else {
            System.out.println("Kh??ng th??nh c??ng!!!");
        }
    }

    public static List<String> addAuthor(Scanner sc) {                              // add author for book
        List<String> authorList = new ArrayList<>();
        do {
            System.out.println("Nh???p v??o t??n t??c gi???");
            String authorName = strValidate(sc, REGEXFULLNAME);
            boolean check = true;
            for (String author : authorList) {
                if (author.equals(authorName)) {
                    check = false;
                    break;
                }
            }
            if (check) {
                authorList.add(authorName);
            } else {
                System.out.println(NAMEERROR1);
            }
            System.out.println("B???n c?? mu???n th??m t??c gi??? kh??c kh??ng?\n" +
                    "1. c??    2. kh??ng");
            System.out.println("");
            int choice = choiceNumber(sc, 1, 2);
            if (choice != 1) {
                break;
            }
        } while (true);
        return authorList;
    }

    public static String bookStates(Scanner sc) {                                    // book states
        String states;
        System.out.println("T??nh tr???ng c???a s??ch:\n" +
                "1.M???i    2.B??nh th?????ng    3.C??");
        int choice2 = choiceNumber(sc, 1, 3);
        if (choice2 == 1) {
            states = "M???i";
        } else if (choice2 == 2) {
            states = "B??nh th?????ng";
        } else {
            states = "C??";
        }
        return states;
    }

    public static Catalog catalogForbook(Scanner sc) {                  // catalog for book
        CatalogImpl catalogImpl = new CatalogImpl();
        Catalog catalogReturn = new Catalog();
        List<Catalog> catalogList = catalogImpl.readFromFile();
        boolean check = false;
        String strCatalog= "";
        String str1= "";
        for (Catalog catalog : catalogList) {
            strCatalog+="|  "+ catalog.getCatalogId()+ ". "+catalog.getCatalogName()+"  ";
        }
        for (int i=0; i<strCatalog.length()+5;i++){
            str1+="-";
        }
        System.out.println("+"+str1+"+");
        System.out.println(strCatalog+"      |");
        System.out.println("+"+str1+"+");
        int choice = choiceNumber(sc, 1, catalogList.size());
        for (Catalog catalog : catalogList) {
            if (catalog.getCatalogId() == choice) {
                catalogReturn = catalog;
                break;
            }
        }
        return catalogReturn;

    }

    public static boolean checkphonenumber( int phone) {
        UserImpl userImpl = new UserImpl();
        List<User> userList = userImpl.readFromFile();
        boolean check= false;
        for (User us : userList) {
            if (us.getPhonenumber()==phone){
                check=true;
                break;
            }
        }
        return check;
    }
    public static boolean checkEmail(String email){

        UserImpl userImpl = new UserImpl();
        List<User> userList = userImpl.readFromFile();
        boolean check= false;
        for (User us : userList) {
            if (us.getUserEmail().equals(email)){
                check=true;
                break;
            }
        }
        return check;
    }


}
