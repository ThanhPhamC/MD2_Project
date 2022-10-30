package myproject.run;

import myproject.bussiness.entity.Book;

import myproject.bussiness.entity.LibraryBookCard;
import myproject.bussiness.impl.LibraryBookCardImpl;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Scanner;

import static myproject.bussiness.mess.CheckValidate.*;
import static myproject.bussiness.mess.Message.*;
import static myproject.data.ConstandDesign.*;
import static myproject.data.ConstantRegexAndUrl.REGEXNAME;
import static myproject.data.ConstantRegexAndUrl.REGEXQUANTITY;

public class LibraryBookCardManagement {

    public static LibraryBookCardImpl lbCardImpl = new LibraryBookCardImpl();

    public static void displayLBCard(Scanner sc) {
        boolean checkout = true;
        do {
            System.out.println(SHOWHEARDCARD);
            int choice = choiceNumber(sc, 1, 6);
            switch (choice) {
                case 1:
                    showListCard();
                    break;
                case 2:
                    addNewCard(sc);
                    break;
                case 3:
                    editCard(sc);
                    break;
                case 4:
                    deleteCard(sc);
                    break;
                case 5:
                    searchByName(sc);
                    break;
                case 6:
                    checkout = false;
                    break;
            }
        } while (checkout);
    }

    public static void showListCard() {                            //1. show list card
        System.out.println(SHOWLISTCARD);
        lbCardImpl.displayData();
        System.out.println("\n\n");
    }

    public static void addNewCard(Scanner sc) {                          // 2. add new card
        System.out.println(ADDCARD);
        System.out.println("* Nhập số lượng muốn thêm mới");
        int number = Integer.parseInt(strValidate(sc, REGEXQUANTITY));
        for (int i = 0; i < number; i++) {
            LibraryBookCard lbCard = new LibraryBookCard();
            System.out.printf("* Thẻ thư viện thứ %d.\n", (i + 1));
            lbCard = lbCardImpl.inputData(sc);
            boolean check = lbCardImpl.create(lbCard);
            soutMess(check);
        }

    }

    public static void editCard(Scanner sc) {                        //                  3. edit card
        List<LibraryBookCard> libraryBookCardList = lbCardImpl.readFromFile();
        System.out.println(EDITCARD);
        System.out.println("Nhập vào mã của thẻ muốn cập nhập");
        int lbCardId = Integer.parseInt(strValidate(sc, REGEXQUANTITY));
        boolean result = false;
        for (LibraryBookCard lbc : libraryBookCardList) {
            if (lbc.getLibraryBookCardId() == lbCardId) {
                    System.out.println("Xác nhận cập nhập sách cho thẻ.\n" + "1. Có      2. Không.");
                    int choice1 = choiceNumber(sc, 1, 2);
                    if (choice1 == 1) {
                        System.out.println(ADDBOOKTOCARD);
                        lbc.setBookArrayList(bookListCard(sc));
                    }
                System.out.println("Xác nhận cập nhập ngày trả sách cho thẻ.\n" + "1. Có      2. Không.");
                int choice = choiceNumber(sc, 1, 2);
                if (choice == 1) {
                    do {
                        System.out.println(DAYRETURNBOOK);
                        lbc.setReturnDate(dateValidate(sc));
                        if (lbc.getReturnDate().compareTo(lbc.getBorrowDate()) < 0) {
                            System.out.println("Ngày trả phải lớn hơn ngày mượn");
                        } else {
                            break;
                        }
                    } while (true);
                }
                result = lbCardImpl.update(lbc);
                break;
            }
        }
        soutMess(result);
    }

    public static void deleteCard(Scanner sc) {                      //4.delete card(update status)
        System.out.println(DELETECARD);
        System.out.println("Nhập vào mã thẻ muốn xoá: ");
        String cardId = strValidate(sc, REGEXQUANTITY);
        boolean check = lbCardImpl.delete(cardId);
        soutMess(check);
    }

    public static void searchByName(Scanner sc) {
        System.out.println(SEARCHCARD);
        System.out.println(SEARCHNAME);
        String searchName = strValidate(sc, REGEXNAME);
        List<LibraryBookCard> libraryBookCardList = lbCardImpl.findbyName(searchName);
        if (libraryBookCardList == null) {
            System.out.println(ERRORNULL);
        } else {
            System.out.println(SEARCHCARD +
                    "|     *************************************     DANH SÁCH THẺ MƯỢN SÁCH     *****************************************      |\n" +
                    "+--------------------------------------------------------------------------------------------------------------------------+");
            for (LibraryBookCard lbCard : libraryBookCardList) {
                String actualDay = "Chưa trả.";
                DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
                if (lbCard.getActualReturnDate() != null) {
                    actualDay = df.format(lbCard.getActualReturnDate());
                }
                String borrowDay = df.format(lbCard.getBorrowDate());
                String returnDay = df.format(lbCard.getReturnDate());
                System.out.printf("|   1.MÃ THẺ: %-5d    2.TÊN THẺ: %-18s      3.NGƯỜI MƯỢN: %-20s      4.TRẠNG THÁI: %-10s |\n" +
                                "|   5.NGÀY MƯỢN: %-10s                               6.NGÀY TRẢ: %-10s            7.NGÀY TRẢ THỰC TẾ: %-10s |\n"
                        ,
                        lbCard.getLibraryBookCardId(), lbCard.getLibraryBookCardName(), lbCard.getUser().getUserName(), lbCard.getLibraryBookCardStatus(), borrowDay, returnDay, actualDay);
                for (Book book : lbCard.getBookArrayList()) {
                    System.out.printf("|   8.DANH SÁCH SÁCH MƯỢN: %-80s                |\n" +
                            "+--------------------------------------------------------------------------------------------------------------------------+\n", book.getBookName());
                }
            }
        }
    }


}
