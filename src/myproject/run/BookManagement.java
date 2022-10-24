package myproject.run;

import myproject.bussiness.entity.Book;

import myproject.bussiness.impl.BookImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import static myproject.bussiness.mess.CheckValidate.*;
import static myproject.bussiness.mess.Message.*;
import static myproject.data.ConstandDesign.*;
import static myproject.data.ConstantRegexAndUrl.*;

public class BookManagement {
    public static BookImpl bookImpl = new BookImpl();

    public static void main(String[] args) {
    }

    public static void displayBookMenu(Scanner sc) {
        boolean checkout = true;
        do {
            System.out.println(SHOWHEARDBOOK);
            int choice = choiceNumber(sc, 1, 6);
            switch (choice) {
                case 1:
                    showlistBook(sc);
                    break;
                case 2:
                    addNewBook(sc);
                    break;
                case 3:
                    updateBook(sc);
                    break;
                case 4:
                    deleteBook(sc);
                    break;
                case 5:
                    searchBook(sc);
                    break;
                case 6:
                    checkout = false;
                    break;
            }
        } while (checkout);
    }

    public static void showlistBook(Scanner sc) {                    //1. show list book
        System.out.println(SHOWLISTBOOK);
        bookImpl.displayData();
        System.out.println("\n\n");
    }

    public static void addNewBook(Scanner sc) {                  //2. add new book
        System.out.println(ADDNEWTBOOK);
        System.out.println("* Nhập số lượng muốn thêm mới");
        int number = Integer.parseInt(strValidate(sc, REGEXQUANTITY));
        for (int i = 0; i < number; i++) {
            Book book = new Book();
            System.out.printf("* Book thứ %d.\n", (i + 1));
            book = bookImpl.inputData(sc);
            boolean check = bookImpl.create(book);
            soutMess(check);
        }
    }

    public static void updateBook(Scanner sc) {              //3. update book
        List<Book> bookList = bookImpl.readFromFile();
        Book book = new Book();
        System.out.println(EDITTBOOK);
        System.out.println("Nhập vào Id của sách muốn cập nhập");
        book.setBookId(strValidate(sc, REGEXBOOKID));
        System.out.println(INPUTNAME);
        book.setBookName(strValidate(sc,REGEXFULLNAME));
        System.out.println(BOOKQUANTITY);
        String number = strValidate(sc, REGEXQUANTITY);
        book.setBookquantity(Integer.parseInt(number));
        System.out.println(CATALOGFORBOOK);
        book.setCatalog(catalogForbook(sc));
        book.setListAuthor((ArrayList<String>) addAuthor(sc));
        book.setBookStates(bookStates(sc));
        boolean check = bookImpl.update(book);
        soutMess(check);
    }

    public static void deleteBook(Scanner sc) {                        //4. delete book
        System.out.println(DELETEBOOK);
        System.out.println("Nhập vào mã của sách muốn cập nhập");
        String bookId = strValidate(sc, REGEXBOOKID);
        boolean check = bookImpl.delete(bookId);
        soutMess(check);
    }

    public static void searchBook(Scanner sc) {                           // 5. search by name
        System.out.println(SEARCHBYNAMEBOOK);
        System.out.print("Nhập tên sách muốn tìm: ");
        String bookName = sc.nextLine();
        System.out.println(SEARCHBYNAMEBOOK);
        List<Book> bookList = bookImpl.findbyName(bookName);
        if (bookList == null) {
            System.out.printf("Không tồn tại sách %s trong danh sách.", bookName);
        } else {
            System.out.println(SEARCHBYNAMEBOOK +
                    "                            |    *************************************     DANH SÁCH SÁCH TÌM THEO TÊN      *******************************************    |\n" +
                    "                            |------------------------------------------------------------------------------------------------------------------------------|"
            );
            for (Book book : bookList) {
                System.out.printf("                            |     1.MÃ SÁCH: %-6S           2.TÊN SÁCH: %-25S        3. DANH MỤC: %-20S               |\n"+
                                  "                            |     4.SỐ LƯỢNG: %-4d             5.TÌNH TRẠNG: %-13s                   6. TRẠNG THÁI: %-16s               |\n"+
                                  "                            |     7.TÁC GIẢ: %-100s          |\n"+
                                  "                            +------------------------------------------------------------------------------------------------------------------------------+", book.getBookId(), book.getBookName(), book.getCatalog().getCatalogName(), book.getBookquantity(), book.getBookStates(), book.getBookStatus(),book.getListAuthor().toString());


            }
        }


    }

}



