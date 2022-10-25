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
        String str = "";
        if (count != 0) {
            str = "(" + count + ")";
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

                    bookImpl.displayData();
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
                        System.out.printf("|   1.MÃ THẺ: %-5d    2.TÊN THẺ: %-18s      3.NGƯỜI MƯỢN: %-20s      4.TRẠNG THÁI: %-10s |\n" +
                                        "|   5.NGÀY MƯỢN: %-10s                               6.NGÀY TRẢ: %-10s            7.NGÀY TRẢ THỰC TẾ: %-10s |\n",
                                lbCard.getLibraryBookCardId(), lbCard.getLibraryBookCardName(), lbCard.getUser().getUserName(), lbCard.getLibraryBookCardStatus(), borrowDay, returnDay, actualReturnDate);
                        String strListBook = "|   8.DANH SÁCH SÁCH MƯỢN: ";
                        for (Book book : lbCard.getBookArrayList()) {
                            strListBook += book.getBookName() + ". ";
                        }
                        System.out.printf("%-103s                    |", strListBook);
                        System.out.println("\n+--------------------------------------------------------------------------------------------------------------------------+");

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
                    List<User> userList = userImpl.readFromFile();

                    System.out.println("Bạn có muốn thay mới số điện thoại không?\n" +
                            "1. Có      2. Không ");
                    int choice1 = choiceNumber(sc, 1, 2);
                    if (choice1 == 1) {
                        do {
                            System.out.println(PHONENUMBER);
                            user.setPhonenumber(Integer.parseInt(strValidate(sc, REGEXPHONE)));
                             boolean check2 =checkphonenumber(user.getPhonenumber());
                            if (check2){
                                System.out.println(PHONEERR);
                            }else{
                                break;
                            }
                        }while (true);
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
                            user.setUserEmail(strValidate(sc,REGEXEMAIL));
                            boolean check2 =checkEmail(user.getUserEmail());
                            if (check2){
                                System.out.println(EMAILERR);
                            }else{
                                break;
                            }
                        }while (true);
                    }

                for (int i = 0; i < userList.size(); i++) {
                    if (userList.get(i).getUserId()==user.getUserId()){
                        userList.set(i,user);
                        break;
                    }
                }
                boolean check= userImpl.writeToFile(userList);
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
                    System.out.println(" Ơ KÌA TRẢ SÁCH ĐI.\n" +
                            "TRẢ SÁCH ĐI Ơ KÌA.\n" +
                            "ĐI TRẢ SÁCH Ơ KÌA.");
                    break;
                case 6:
                    checkout = false;
                    break;
            }
        } while (checkout);

    }

}
