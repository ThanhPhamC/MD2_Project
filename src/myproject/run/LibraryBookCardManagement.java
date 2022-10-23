package myproject.run;

import myproject.bussiness.entity.Catalog;
import myproject.bussiness.entity.LibraryBookCard;
import myproject.bussiness.impl.LibraryBookCardImpl;

import java.util.List;
import java.util.Scanner;

import static myproject.bussiness.mess.CheckValidate.*;
import static myproject.bussiness.mess.Message.*;
import static myproject.data.ConstandDesign.*;
import static myproject.data.ConstantRegexAndUrl.REGEXQUANTITY;

public class LibraryBookCardManagement {

public static LibraryBookCardImpl lbCardImpl=new LibraryBookCardImpl();
    public static void displayLBCard(Scanner sc){
        boolean checkout = true;
        do {
            System.out.println(SHOWHEARDCARD);

            int choice = choiceNumber(sc, 1, 6);
            switch (choice) {
                case 1:
                    showListCard( sc);
                    break;
                case 2:
                    addNewCard( sc);
                    break;
                case 3:
                    editCard( sc);
                    break;
                case 4:
                    deleteCard( sc);
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
    public static void showListCard(Scanner sc){                            //1. show list card
        System.out.println(SHOWLISTCARD);
        lbCardImpl.displayData();
        System.out.println("\n\n");
    }
    public static void addNewCard(Scanner sc){                          // 2. add new card
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
    public static void editCard(Scanner sc){                        //                  3. edit card
        List<LibraryBookCard> libraryBookCardList=lbCardImpl.readFromFile();
        LibraryBookCard lbCard = new LibraryBookCard();
        System.out.println(EDITCARD);
        System.out.println("Nhập vào mã của mục muốn cập nhập");
        lbCard.setLibraryBookCardId(Integer.parseInt(strValidate(sc, REGEXQUANTITY)));
        System.out.println(ADDBOOKTOCARD);
        lbCard.setBookArrayList(bookListCard(sc));
        System.out.println(DAYRETURNBOOK);
        lbCard.setReturnDate(dateValidate(sc));
        boolean result=lbCardImpl.update(lbCard);
        soutMess(result);
    }
    public static void deleteCard(Scanner sc){                      //4.delete card(update status)
        System.out.println(DELETECARD);
        System.out.println("Nhập vào mã thẻ muốn xoá: ");
        String cardId=strValidate(sc,REGEXQUANTITY);
        boolean check= lbCardImpl.delete(cardId);
        soutMess(check);
    }
    public static void searchByName(Scanner sc){

        System.out.println(SEARCHCARD);
    }


}
