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
                    "|                                       CỬA HÀNG SÁCH HOÀNG KIM HÂN HẠNH ĐÓN TIẾP KHÁCH HÀNG: %-20s                                         |\n" +
                    "+----------------------------------------------------------------------------------------------------------------------------------------------------------+\n" +
                    "|    1.DANH SÁCH SÁCH     |  2.ĐĂNG KÍ MƯỢN SÁCH    |   3.LỊCH SỬ MƯỢN SÁCH   |  4.ĐỔI THÔNG TIN CÁ NHÂN   |      5.THÔNG BÁO%3s       |        6.THOÁT    |\n" +
                    "+----------------------------------------------------------------------------------------------------------------------------------------------------------+\n", user.getUserName().toUpperCase(), str);
            int choice = choiceNumber(sc, 1, 6);
            switch (choice) {
                case 1:
                    System.out.printf("\n+----------------------------------------------------------------------------------------------------------------------------------------------------------+\n" +
                            "|                                                                                                                                                          |\n" +
                            "|                                       CỬA HÀNG SÁCH HOÀNG KIM HÂN HẠNH ĐÓN TIẾP KHÁCH HÀNG: %-20s                                         |\n" +
                            "+----------------------------------------------------------------------------------------------------------------------------------------------------------+\n" +
                            "| +------------------+|                         |                         |                            |                           |                       |\n" +
                            "| | 1.DANH SÁCH SÁCH ||  2.ĐĂNG KÍ MƯỢN SÁCH    |   3.LỊCH SỬ MƯỢN SÁCH   |  4.ĐỔI THÔNG TIN CÁ NHÂN   |      5.THÔNG BÁO%3s       |        6.THOÁT        |\n" +
                            "| +------------------+|                         |                         |                            |                           |                       |\n" +
                            "+----------------------------------------------------------------------------------------------------------------------------------------------------------+\n", user.getUserName().toUpperCase(), str);
                    BookImpl bookImpl = new BookImpl();
                    List<Book> bookList = bookImpl.readFromFile();
                    if (bookList == null) {
                        System.out.println(ERRORNULL);
                    } else {
                        System.out.print("                      |         MÃ SÁCH         |        TÊN SÁCH         |                                   TÁC GIẢ                                      |");
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
                            "|                                       CỬA HÀNG SÁCH HOÀNG KIM HÂN HẠNH ĐÓN TIẾP KHÁCH HÀNG: %-20s                                         |\n" +
                            "+----------------------------------------------------------------------------------------------------------------------------------------------------------+\n" +
                            "|                     |  +--------------------+ |                       |                         |                            |                           |\n" +
                            "|  1.DANH SÁCH SÁCH   |  | 2.ĐĂNG KÍ MƯỢN SÁCH| | 3.LỊCH SỬ MƯỢN SÁCH   | 4.ĐỔI THÔNG TIN CÁ NHÂN |      5.THÔNG BÁO%3s        |        6.THOÁT            |\n" +
                            "|                     |  +--------------------+ |                       |                         |                            |                           |\n" +
                            "+----------------------------------------------------------------------------------------------------------------------------------------------------------+\n", user.getUserName().toUpperCase(), str);

                    break;
                case 3:
                    System.out.printf("\n+----------------------------------------------------------------------------------------------------------------------------------------------------------+\n" +
                            "|                                                                                                                                                          |\n" +
                            "|                                       CỬA HÀNG SÁCH HOÀNG KIM HÂN HẠNH ĐÓN TIẾP KHÁCH HÀNG: %-20s                                         |\n" +
                            "+----------------------------------------------------------------------------------------------------------------------------------------------------------+\n" +
                            "|                     |                      |  +---------------------+ |                         |                         |                              |\n" +
                            "|  1.DANH SÁCH SÁCH   |  2.ĐĂNG KÍ MƯỢN SÁCH |  | 3.LỊCH SỬ MƯỢN SÁCH | | 4.ĐỔI THÔNG TIN CÁ NHÂN |      5.THÔNG BÁO%3s     |        6.THOÁT               |\n" +
                            "|                     |                      |  +---------------------+ |                         |                         |                              |\n" +
                            "+----------------------------------------------------------------------------------------------------------------------------------------------------------+\n", user.getUserName().toUpperCase(), str);
                    List<LibraryBookCard> lbCardUser = lbCardImpl.searchByUserId(user.getUserId());
                    for (LibraryBookCard lbCard : lbCardUser) {
                        String actualReturnDate = "Chưa trả.";
                        DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
                        if (lbCard.getActualReturnDate() != null) {
                            actualReturnDate = df.format(lbCard.getActualReturnDate());
                        }
                        String borrowDay = df.format(lbCard.getBorrowDate());
                        String returnDay = df.format(lbCard.getReturnDate());
                        System.out.printf("|   1.MÃ THẺ: %-5d                 2.TÊN THẺ: %-18s      3.TRẠNG THÁI: %-10s                             |\n" +
                                        "|   5.NGÀY MƯỢN: %-10s         6.NGÀY HẸN TRẢ: %-10s            7.NGÀY TRẢ THỰC TẾ: %-10s                    |\n",
                                lbCard.getLibraryBookCardId(), lbCard.getLibraryBookCardName(), lbCard.getLibraryBookCardStatus(), borrowDay, returnDay, actualReturnDate);

                        String strListBook = "|   8.DANH SÁCH SÁCH MƯỢN: ";
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
                            "|                                       CỬA HÀNG SÁCH HOÀNG KIM HÂN HẠNH ĐÓN TIẾP KHÁCH HÀNG: %-20s                                         |\n" +
                            "+----------------------------------------------------------------------------------------------------------------------------------------------------------+\n" +
                            "|                     |                      |                        |  +-------------------------+ |                         |                           |                              |\n" +
                            "|  1.DANH SÁCH SÁCH   |  2.ĐĂNG KÍ MƯỢN SÁCH |  3.LỊCH SỬ MƯỢN SÁCH   |  | 4.ĐỔI THÔNG TIN CÁ NHÂN | |     5.THÔNG BÁO%3s      |        6.THOÁT            |\n" +
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
                            "|     1.MÃ NGƯỜI DÙNG: %-5d          2.TÊN ĐẦY ĐỦ: %-20s    3.SỐ ĐIỆN THOẠI: %-11d                       |\n" +
                                    "|     4.ĐỊA CHỈ NHÀ: %-47s        5.EMAIL: %-23s                   |\n" +
                                    "|     6.NGÀY MUA THẺ: %-10s      7.NGÀY HẾT HẠN THẺ: %-10s        8.TRẠNG THÁI: %-16s                     |\n" +
                                    "+------------------------------------------------------------------------------------------------------------------------------+\n",
                            user.getUserId(), user.getUserName(), user.getPhonenumber(),user.getUserAdress(),user.getUserEmail(),startDay, endDay, status);
                    List<User> userList = userImpl.readFromFile();
                    do {
                        System.out.println("Nhập mật khẩu cũ:");
                        String oldPass = strValidate(sc, REGEXPASS);
                        System.out.println("Nhập mật khẩu mới:");
                        String newPass =strValidate(sc, REGEXPASS);
                        System.out.println("Nhập lại mật khẩu mới:");
                        String checkNewPass = strValidate(sc, REGEXPASS);
                        if (user.getUserPass().equals(oldPass)) {
                            if (newPass.equals(checkNewPass)) {
                                user.setUserPass(newPass);
                                System.out.println("Cập nhập thành công");
                            } else {
                                System.out.println("Không trùng khớp");
                            }
                            break;
                        } else {
                            System.out.println("Mật khẩu cũ không đúng");
                        }
                    } while (true);
                    System.out.println("Bạn có muốn thay mới số điện thoại không?\n" +
                            "1. Có      2. Không ");
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

                    System.out.println("Bạn có muốn thay mới địa chỉ không?\n" +
                            "1. Có      2. Không ");
                    int choice2 = choiceNumber(sc, 1, 2);
                    if (choice2 == 1) {
                        System.out.println(Message.ADDRESS);
                        user.setUserAdress(sc.nextLine());
                    }
                    System.out.println("Bạn có muốn thay mới email không?\n" +
                            "1. Có      2. Không ");
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
                            "|                                       CỬA HÀNG SÁCH HOÀNG KIM HÂN HẠNH ĐÓN TIẾP KHÁCH HÀNG: %-20s                                         |\n" +
                            "+----------------------------------------------------------------------------------------------------------------------------------------------------------+\n" +
                            "|                     |                      |                        |                           |  +------------------------+ |                          |                           |                              |\n" +
                            "|  1.DANH SÁCH SÁCH   |  2.ĐĂNG KÍ MƯỢN SÁCH |  3.LỊCH SỬ MƯỢN SÁCH   |  4.ĐỔI THÔNG TIN CÁ NHÂN  |  |    5.THÔNG BÁO%3s      | |       6.THOÁT            |\n" +
                            "|                     |                      |                        |                           |  +------------------------+ |                          |                           |                              |\n" +
                            "+----------------------------------------------------------------------------------------------------------------------------------------------------------+\n", user.getUserName().toUpperCase(), str);

                    for (LibraryBookCard lbCard : libraryBookCardList) {
                        if (lbCard.getUser().getUserId() == user.getUserId() && lbCard.getLibraryBookCardStatus().equals(Message.LBCARDSTATUS2)) {
                            Date date = new Date();
                            String mess = gapday(lbCard.getReturnDate(), date);
                            String actualReturnDate = "Chưa trả.";
                            DateFormat dfm = new SimpleDateFormat("dd/MM/yyyy");
                            if (lbCard.getActualReturnDate() != null) {
                                actualReturnDate = dfm.format(lbCard.getActualReturnDate());
                            }
                            String borrowDay = dfm.format(lbCard.getBorrowDate());
                            String returnDay = dfm.format(lbCard.getReturnDate());
                            System.out.printf("|   1.MÃ THẺ: %-5d    2.TÊN THẺ: %-18s      3.NGƯỜI MƯỢN: %-20s      4.TRẠNG THÁI: %-10s |\n" +
                                            "|   5.NGÀY MƯỢN: %-10s                               6.NGÀY TRẢ: %-10s            7.Phiếu chậm hạn trả %-7s . |\n",
                                    lbCard.getLibraryBookCardId(), lbCard.getLibraryBookCardName(), lbCard.getUser().getUserName(), lbCard.getLibraryBookCardStatus(), borrowDay, returnDay, mess.toUpperCase());

                            String strListBook = "|   8.DANH SÁCH SÁCH MƯỢN: ";
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
        return dayreturn = valueday + " ngày";
    }
}
