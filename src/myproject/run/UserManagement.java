package myproject.run;

import myproject.bussiness.entity.Book;
import myproject.bussiness.entity.LibraryBookCard;
import myproject.bussiness.entity.User;
import myproject.bussiness.impl.BookImpl;
import myproject.bussiness.impl.LibraryBookCardImpl;
import myproject.bussiness.impl.UserImpl;
import myproject.bussiness.mess.Message;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

import static myproject.bussiness.mess.CheckValidate.*;
import static myproject.bussiness.mess.Message.*;
import static myproject.data.ConstantRegexAndUrl.*;

public class UserManagement {
    public static LibraryBookCardImpl lbCardImpl = new LibraryBookCardImpl();
    public static UserImpl userImpl = new UserImpl();

    public static BookImpl bookImpl = new BookImpl();

    public static void main(String[] args) {
    }

    public static void displayMenuUser(Scanner sc) {
        List<LibraryBookCard> libraryBookCardList = lbCardImpl.updateStatus();
        LibraryManagement libraryManagement = new LibraryManagement();
        User user = libraryManagement.readFormFile();
        int count = 0;
        for (LibraryBookCard lbCard : libraryBookCardList) {
            if (lbCard.getUser().getUserId() == user.getUserId() && lbCard.getLibraryBookCardStatus().equals(Message.LBCARDSTATUS2)) {
                count++;
            }
        }
        String str ;
        if (count != 0) {
            str = "(" + count + ")";
        }else {
            str="";
        }

        boolean checkout = true;
        do {
            System.out.printf("\n+----------------------------------------------------------------------------------------------------------------------------------------------------------+\n" +
                    "|                                                                                                                                                          |\n" +
                    "|                                       C???A H??NG S??CH HO??NG KIM H??N H???NH ????N TI???P KH??CH H??NG: %-20s                                         |\n" +
                    "+----------------------------------------------------------------------------------------------------------------------------------------------------------+\n" +
                    "|    1.DANH S??CH S??CH     |  2.????NG K?? M?????N S??CH    |   3.L???CH S??? M?????N S??CH   |  4.?????I TH??NG TIN C?? NH??N   |      5.TH??NG B??O%3s       |        6.THO??T    |\n" +
                    "+----------------------------------------------------------------------------------------------------------------------------------------------------------+\n", user.getUserName().toUpperCase(), str);
            int choice = choiceNumber(sc, 1, 6);
            switch (choice) {
                case 1:
                    System.out.printf("\n+----------------------------------------------------------------------------------------------------------------------------------------------------------+\n" +
                            "|                                                                                                                                                          |\n" +
                            "|                                       C???A H??NG S??CH HO??NG KIM H??N H???NH ????N TI???P KH??CH H??NG: %-20s                                         |\n" +
                            "+----------------------------------------------------------------------------------------------------------------------------------------------------------+\n" +
                            "| +------------------+|                         |                         |                            |                           |                       |\n" +
                            "| | 1.DANH S??CH S??CH ||  2.????NG K?? M?????N S??CH    |   3.L???CH S??? M?????N S??CH   |  4.?????I TH??NG TIN C?? NH??N   |      5.TH??NG B??O%3s       |        6.THO??T        |\n" +
                            "| +------------------+|                         |                         |                            |                           |                       |\n" +
                            "+----------------------------------------------------------------------------------------------------------------------------------------------------------+\n", user.getUserName().toUpperCase(), str);
                    BookImpl bookImpl = new BookImpl();
                    List<Book> bookList = bookImpl.readFromFile();
                    if (bookList == null) {
                        System.out.println(ERRORNULL);
                    } else {
                        System.out.print("                      |         M?? S??CH         |        T??N S??CH         |                                   T??C GI???                                      |");
                        System.out.println("\n                      +------------------------------------------------------------------------------------------------------------------------------------+");
                        for (Book book : bookList) {
                            String authorName = "";
                            for (String name : book.getListAuthor()) {
                                authorName+=name+". ";
                            }
                            if (!book.getBookStatus().equals(STATUS3)) {
                                System.out.printf("                      |        %-6S               %-25S     %-60s             |\n", book.getBookId(), book.getBookName(), authorName);
                            }
                        }
                        System.out.println("                      +------------------------------------------------------------------------------------------------------------------------------------+");

                    }
                    System.out.println("\n\n");
                    break;
                case 2:
                    System.out.printf("\n+----------------------------------------------------------------------------------------------------------------------------------------------------------+\n" +
                            "|                                                                                                                                                          |\n" +
                            "|                                       C???A H??NG S??CH HO??NG KIM H??N H???NH ????N TI???P KH??CH H??NG: %-20s                                         |\n" +
                            "+----------------------------------------------------------------------------------------------------------------------------------------------------------+\n" +
                            "|                     |  +--------------------+ |                       |                         |                            |                           |\n" +
                            "|  1.DANH S??CH S??CH   |  | 2.????NG K?? M?????N S??CH| | 3.L???CH S??? M?????N S??CH   | 4.?????I TH??NG TIN C?? NH??N |      5.TH??NG B??O%3s        |        6.THO??T            |\n" +
                            "|                     |  +--------------------+ |                       |                         |                            |                           |\n" +
                            "+----------------------------------------------------------------------------------------------------------------------------------------------------------+\n", user.getUserName().toUpperCase(), str);

                    break;
                case 3:
                    System.out.printf("\n+----------------------------------------------------------------------------------------------------------------------------------------------------------+\n" +
                            "|                                                                                                                                                          |\n" +
                            "|                                       C???A H??NG S??CH HO??NG KIM H??N H???NH ????N TI???P KH??CH H??NG: %-20s                                         |\n" +
                            "+----------------------------------------------------------------------------------------------------------------------------------------------------------+\n" +
                            "|                     |                      |  +---------------------+ |                         |                         |                              |\n" +
                            "|  1.DANH S??CH S??CH   |  2.????NG K?? M?????N S??CH |  | 3.L???CH S??? M?????N S??CH | | 4.?????I TH??NG TIN C?? NH??N |      5.TH??NG B??O%3s     |        6.THO??T               |\n" +
                            "|                     |                      |  +---------------------+ |                         |                         |                              |\n" +
                            "+----------------------------------------------------------------------------------------------------------------------------------------------------------+\n", user.getUserName().toUpperCase(), str);
                    List<LibraryBookCard> lbCardUser = lbCardImpl.searchByUserId(user.getUserId());
                    for (LibraryBookCard lbCard : lbCardUser) {
                        String actualReturnDate = "Ch??a tr???.";
                        DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
                        if (lbCard.getActualReturnDate() != null) {
                            actualReturnDate = df.format(lbCard.getActualReturnDate());
                        }
                        String borrowDay = df.format(lbCard.getBorrowDate());
                        String returnDay = df.format(lbCard.getReturnDate());
                        System.out.printf("|   1.M?? TH???: %-5d                 2.T??N TH???: %-18s      3.TR???NG TH??I: %-10s                             |\n" +
                                        "|   5.NG??Y M?????N: %-10s         6.NG??Y H???N TR???: %-10s            7.NG??Y TR??? TH???C T???: %-10s                    |\n",
                                lbCard.getLibraryBookCardId(), lbCard.getLibraryBookCardName(), lbCard.getLibraryBookCardStatus(), borrowDay, returnDay, actualReturnDate);

                        String strListBook = "|   8.DANH S??CH S??CH M?????N: ";
                        for (Book book : lbCard.getBookArrayList()) {
                            strListBook += book.getBookName() + ". ";
                        }
                        System.out.printf("%-103s                     |", strListBook);
                        System.out.println("\n+---------------------------------------------------------------------------------------------------------------------------+");

                    }
                    break;
                case 4:
                    System.out.printf("\n+----------------------------------------------------------------------------------------------------------------------------------------------------------+\n" +
                            "|                                                                                                                                                          |\n" +
                            "|                                       C???A H??NG S??CH HO??NG KIM H??N H???NH ????N TI???P KH??CH H??NG: %-20s                                         |\n" +
                            "+----------------------------------------------------------------------------------------------------------------------------------------------------------+\n" +
                            "|                     |                      |                        |  +-------------------------+ |                         |                           |                              |\n" +
                            "|  1.DANH S??CH S??CH   |  2.????NG K?? M?????N S??CH |  3.L???CH S??? M?????N S??CH   |  | 4.?????I TH??NG TIN C?? NH??N | |     5.TH??NG B??O%3s      |        6.THO??T            |\n" +
                            "|                     |                      |                        |  +-------------------------+ |                         |                           |                              |\n" +
                            "+----------------------------------------------------------------------------------------------------------------------------------------------------------+\n", user.getUserName().toUpperCase(), str);
                    String status ;
                    if (user.isUserStatus()) {
                        status = STATUS1;
                    }else {
                        status = STATUS3;
                    }
                    DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
                    String startDay = df.format(user.getLibraryCardStartDay());
                    String endDay = df.format(user.getLibraryCardEndDay());
                    System.out.printf(
                            "|     1.M?? NG?????I D??NG: %-5d          2.T??N ?????Y ?????: %-20s    3.S??? ??I???N THO???I: %-11d                       |\n" +
                                    "|     4.?????A CH??? NH??: %-47s        5.EMAIL: %-23s                   |\n" +
                                    "|     6.NG??Y MUA TH???: %-10s      7.NG??Y H???T H???N TH???: %-10s        8.TR???NG TH??I: %-16s                     |\n" +
                                    "+------------------------------------------------------------------------------------------------------------------------------+\n",
                            user.getUserId(), user.getUserName(), user.getPhonenumber(),user.getUserAdress(),user.getUserEmail(),startDay, endDay, status);
                    List<User> userList = userImpl.readFromFile();
                    do {
                        System.out.println("Nh???p m???t kh???u c??:");
                        String oldPass = strValidate(sc, REGEXPASS);
                        System.out.println("Nh???p m???t kh???u m???i:");
                        String newPass =strValidate(sc, REGEXPASS);
                        System.out.println("Nh???p l???i m???t kh???u m???i:");
                        String checkNewPass = strValidate(sc, REGEXPASS);
                        if (user.getUserPass().equals(oldPass)) {
                            if (newPass.equals(checkNewPass)) {
                                user.setUserPass(newPass);
                                System.out.println("C???p nh???p th??nh c??ng");
                            } else {
                                System.out.println("Kh??ng tr??ng kh???p");
                            }
                            break;
                        } else {
                            System.out.println("M???t kh???u c?? kh??ng ????ng");
                        }
                    } while (true);
                    System.out.println("B???n c?? mu???n thay m???i s??? ??i???n tho???i kh??ng?\n" +
                            "1. C??      2. Kh??ng ");
                    int choice1 = choiceNumber(sc, 1, 2);
                    if (choice1 == 1) {
                        do {
                            System.out.println(PHONENUMBER);
                            user.setPhonenumber(Integer.parseInt(strValidate(sc, REGEXPHONE)));
                            boolean check2 = checkphonenumber(user.getPhonenumber());
                            if (check2) {
                                System.out.println(PHONEERR);
                            } else {
                                break;
                            }
                        } while (true);
                    }

                    System.out.println("B???n c?? mu???n thay m???i ?????a ch??? kh??ng?\n" +
                            "1. C??      2. Kh??ng ");
                    int choice2 = choiceNumber(sc, 1, 2);
                    if (choice2 == 1) {
                        System.out.println(Message.ADDRESS);
                        user.setUserAdress(sc.nextLine());
                    }
                    System.out.println("B???n c?? mu???n thay m???i email kh??ng?\n" +
                            "1. C??      2. Kh??ng ");
                    int choi = choiceNumber(sc, 1, 2);
                    if (choi == 1) {
                        do {
                            System.out.println(Message.EMAIL);
                            user.setUserEmail(strValidate(sc, REGEXEMAIL));
                            boolean check2 = checkEmail(user.getUserEmail());
                            if (check2) {
                                System.out.println(EMAILERR);
                            } else {
                                break;
                            }
                        } while (true);
                    }

                    for (int i = 0; i < userList.size(); i++) {
                        if (userList.get(i).getUserId() == user.getUserId()) {
                            userList.set(i, user);
                            break;
                        }
                    }
                    boolean check = userImpl.writeToFile(userList);
                    soutMess(check);
                    break;
                case 5:
                    System.out.printf("\n+----------------------------------------------------------------------------------------------------------------------------------------------------------+\n" +
                            "|                                                                                                                                                          |\n" +
                            "|                                       C???A H??NG S??CH HO??NG KIM H??N H???NH ????N TI???P KH??CH H??NG: %-20s                                         |\n" +
                            "+----------------------------------------------------------------------------------------------------------------------------------------------------------+\n" +
                            "|                     |                      |                        |                           |  +------------------------+ |                          |                           |                              |\n" +
                            "|  1.DANH S??CH S??CH   |  2.????NG K?? M?????N S??CH |  3.L???CH S??? M?????N S??CH   |  4.?????I TH??NG TIN C?? NH??N  |  |    5.TH??NG B??O%3s      | |       6.THO??T            |\n" +
                            "|                     |                      |                        |                           |  +------------------------+ |                          |                           |                              |\n" +
                            "+----------------------------------------------------------------------------------------------------------------------------------------------------------+\n", user.getUserName().toUpperCase(), str);

                    for (LibraryBookCard lbCard : libraryBookCardList) {
                        if (lbCard.getUser().getUserId() == user.getUserId() && lbCard.getLibraryBookCardStatus().equals(Message.LBCARDSTATUS2)) {
                            Date date = new Date();
                            String mess = gapday(lbCard.getReturnDate(), date);
                            String actualReturnDate = "Ch??a tr???.";
                            DateFormat dfm = new SimpleDateFormat("dd/MM/yyyy");
                            if (lbCard.getActualReturnDate() != null) {
                                actualReturnDate = dfm.format(lbCard.getActualReturnDate());
                            }
                            String borrowDay = dfm.format(lbCard.getBorrowDate());
                            String returnDay = dfm.format(lbCard.getReturnDate());
                            System.out.printf("|   1.M?? TH???: %-5d    2.T??N TH???: %-18s      3.NG?????I M?????N: %-20s      4.TR???NG TH??I: %-10s |\n" +
                                            "|   5.NG??Y M?????N: %-10s                               6.NG??Y TR???: %-10s            7.Phi???u ch???m h???n tr??? %-7s . |\n",
                                    lbCard.getLibraryBookCardId(), lbCard.getLibraryBookCardName(), lbCard.getUser().getUserName(), lbCard.getLibraryBookCardStatus(), borrowDay, returnDay, mess.toUpperCase());

                            String strListBook = "|   8.DANH S??CH S??CH M?????N: ";
                            for (Book book : lbCard.getBookArrayList()) {
                                strListBook += book.getBookName() + ". ";
                            }
                            System.out.printf("%-103s                    |", strListBook);
                            System.out.println("\n+--------------------------------------------------------------------------------------------------------------------------+");


                        }
                    }

                    break;
                case 6:
                    checkout = false;
                    break;
            }
        } while (checkout);

    }

    public static String gapday(Date date1, Date date2) {
        String dayreturn;
        long valuedate1 = date1.getTime();
        long valuedate2 = date2.getTime();
        long value = Math.abs(valuedate1 - valuedate2);
        long valueday = value / (24 * 60 * 60 * 1000);
        return dayreturn = valueday + " ng??y";
    }
}
