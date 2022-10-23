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
                    System.out.println(ERRCHOICENUMBER + a + "-" + b + " :");
                }
            } catch (Exception e) {
                System.out.println(FORMATERROR);
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
            for (Book book : bookList) {
                if (book.getBookStatus().equals(STATUS1)) {
                    System.out.printf("%s---%s\n", book.getBookId(), book.getBookName());
                }
            }
            System.out.print("Nhập Id sách muốn thêm: ");
            String choiceId = strValidate(sc, REGEXBOOKID);
            for (Book book : bookList) {
                if (book.getBookId().equals(choiceId)) {
                    if (bookListReturn.size() == 0) {
                        bookListReturn.add(book);
                        book.setBookquantity(book.getBookquantity() - 1);
                        bookImpl.writeToFile(bookList);
                        break;
                    } else {
                        boolean check = false;
                        for (Book book1 : bookListReturn) {
                            if (book1.getBookId().equals(choiceId)) {
                                check = true;
                                break;
                            }
                        }
                        if (check) {
                            System.out.println("Sách đã có trong thẻ mượn, hãy thêm sách khác.");
                            break;
                        } else {
                            bookListReturn.add(book);
                            book.setBookquantity(book.getBookquantity() - 1);
                            bookImpl.writeToFile(bookList);
                            break;
                        }
                    }
                }
            }
            System.out.println("bạn có muốn thêm sách khác không?");
            System.out.println(" 1. có      2. không");
            int choice = choiceNumber(sc, 1, 2);
            if (choice != 1) {
                break;
            }
        } while (true);

        return (ArrayList<Book>) bookListReturn;
    }
    public static User userForCard(Scanner sc){
        LibraryBookCardImpl lbCardImpl=new LibraryBookCardImpl();
        User user =new User();
        UserImpl userImpl=new UserImpl();
        List<User> userList=lbCardImpl.userStatus();
        do {
            for (User user1:userList) {
                if (user1.isUserStatus()){
                    System.out.printf("%d---%5s\n",user1.getUserId(),user1.getUserName());
                }
            }
            System.out.println(YOURCHOICE);
            int choice=Integer.parseInt(strValidate(sc,REGEXQUANTITY)) ;
            boolean check= false;
            for (User use:userList) {
                if (use.getUserId()==choice){
                    user=use;
                    check=true;
                    break;
                }
            }if (check){
                break;
            }else {
                System.out.println("Vui lòng chọn theo danh sách hiển thị.");
            }
        }while (true);
        return user;
    }

    public static void soutMess(boolean boo) {
        if (boo) {
            System.out.println("Thành công");
        } else {
            System.out.println("Không thành công!!!");
        }
    }

    public static List<String> addAuthor(Scanner sc) {                              // add author for book
        List<String> authorList = new ArrayList<>();
        do {
            System.out.println("Nhập vào tên tác giả");
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
            System.out.println("Bạn có muốn thêm tác giả khác không?\n" +
                    "1. có    2. không");
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
        System.out.println("Tình trạng của sách:\n" +
                "1.Mới    2.Bình thường    3.Cũ");
        int choice2 = choiceNumber(sc, 1, 3);
        if (choice2 == 1) {
            states = "Mới";
        } else if (choice2 == 2) {
            states = "Bình thường";
        } else {
            states = "Cũ";
        }
        return states;
    }

    public static Catalog catalogForbook(Scanner sc) {                  // catalog for book
        CatalogImpl catalogImpl = new CatalogImpl();
        Catalog catalogReturn = new Catalog();
        List<Catalog> catalogList = catalogImpl.readFromFile();
        boolean check = false;
        for (Catalog catalog : catalogList) {
            System.out.printf("%d.%s\n", catalog.getCatalogId(), catalog.getCatalogName());
        }
        int choice = choiceNumber(sc, 1, catalogList.size());
        for (Catalog catalog : catalogList) {
            if (catalog.getCatalogId() == choice) {
                catalogReturn = catalog;
                break;
            }
        }
        return catalogReturn;

    }


}
