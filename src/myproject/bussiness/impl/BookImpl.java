package myproject.bussiness.impl;


import myproject.bussiness.design.ILibrary;
import myproject.bussiness.entity.Book;
import myproject.bussiness.entity.Catalog;
import myproject.bussiness.entity.LibraryBookCard;
import myproject.bussiness.mess.CheckValidate;
import myproject.bussiness.mess.Message;

import myproject.data.WriteAndReadData;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import static myproject.bussiness.mess.CheckValidate.*;
import static myproject.bussiness.mess.Message.*;
import static myproject.data.ConstantRegexAndUrl.*;

public class BookImpl implements ILibrary<Book, String> {
    public static WriteAndReadData writeAndReadData = new WriteAndReadData();

    @Override
    public boolean create(Book book) {                              //. 1 creat book
        List<Book> bookList = readFromFile();
        if (bookList == null) {
            bookList = new ArrayList<>();
        }
        bookList.add(book);
        boolean result = writeToFile(bookList);
        return result;
    }

    @Override
    public boolean update(Book book) {                                       // 2. update book
        Scanner sc = new Scanner(System.in);
        List<Book> bookList = readFromFile();
        if (bookList == null) {
            return false;
        } else {
            boolean check = false;
            for (int i = 0; i < bookList.size(); i++) {
                if (bookList.get(i).getBookId().equals(book.getBookId())) {

                    boolean checkname = false;
                    for (Book book1 : bookList) {
                        if (book1.getBookName().equals(book.getBookName()) && !book1.getBookId().equals(book.getBookId())) {
                            checkname = true;
                            break;
                        }
                    }
                    if (checkname) {
                        System.out.println("Tên đã tồn tại với 1 Id khác.\n" +
                                "1. Sử dụng tên ban đầu của sách.     2.Nhập 1 tên khác.");
                        int choice = choiceNumber(sc, 1, 2);
                        switch (choice) {
                            case 1:
                                book.setBookName(bookList.get(i).getBookName());
                                break;
                            case 2:
                                System.out.println(INPUTNAME);
                                book.setBookName(checkbookName(sc));
                                break;
                        }
                    }
                    bookList.set(i, book);
                    check = true;
                    break;
                }
            }
            LibraryBookCardImpl lbCardImpl = new LibraryBookCardImpl();                        // update lai the muon sach.
            List<LibraryBookCard> libraryBookCardList = lbCardImpl.readFromFile();
            for (LibraryBookCard lbCard : libraryBookCardList) {
                for (int i = 0; i < lbCard.getBookArrayList().size(); i++) {
                    if (lbCard.getBookArrayList().get(i).getBookId().equals(book.getBookId())) {
                        lbCard.getBookArrayList().set(i, book);
                    }
                }
            }
            lbCardImpl.writeToFile(libraryBookCardList);
            boolean result = writeToFile(bookList);
            if (check && result) {
                return true;
            } else {
                return false;
            }
        }
    }

    @Override
    public boolean delete(String id) {                                // 3. delete book by id (update status)
        List<Book> bookList = readFromFile();
        if (bookList == null) {
            bookList = new ArrayList<>();
        }
        boolean check = false;
        for (Book book : bookList) {
            if (book.getBookId().equals(id)) {
                book.setBookStatus(STATUS3);
                check = true;
                break;
            }
        }
        boolean result = writeToFile(bookList);
        if (result && check) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public boolean writeToFile(List<Book> list) {               // 4. write to file
        return writeAndReadData.writeToFile(list, URL_BOOK);
    }

    @Override
    public List<Book> readFromFile() {                          // 5. read form file
        List<Book> bookList = writeAndReadData.readFormFile(URL_BOOK);
        if (bookList == null) {
            bookList = new ArrayList<>();
        }
        return bookList;
    }

    @Override
    public List<Book> findbyName(String name) {                     // 6. find book by name
        List<Book> bookList = readFromFile();
        List<Book> bookListByName = new ArrayList<>();
        for (Book book : bookList) {
            if (book.getBookName().contains(name)) {
                bookListByName.add(book);
            }
        }
        return bookListByName;
    }

    @Override
    public Book inputData(Scanner sc) {                             //7. inputdata
        List<Book> bookList = readFromFile();
        if (bookList == null) {
            bookList = new ArrayList<>();
        }
        Book book = new Book();
        do {
            System.out.println(INPUTIDBOOK);
            book.setBookId(strValidate(sc, REGEXBOOKID));
            boolean check = false;
            for (Book book1 : bookList) {
                if (book1.getBookId().equals(book.getBookId())) {
                    check = true;
                    break;
                }
            }
            if (check) {
                System.out.println(IDERROR1);
            } else {
                break;
            }
        } while (true);
        System.out.println(INPUTNAME);
        book.setBookName(checkbookName(sc));
        System.out.println(BOOKQUANTITY);
        String number = strValidate(sc, REGEXQUANTITY);
        book.setBookquantity(Integer.parseInt(number));
        System.out.println(CATALOGFORBOOK);
        book.setCatalog(catalogForbook(sc));
        book.setListAuthor((ArrayList<String>) addAuthor(sc));
        book.setBookStates(bookStates(sc));
        return book;
    }
    @Override
    public void displayData() {
        List<Book> bookList = readFromFile();
        if (bookList == null) {
            System.out.println(ERRORNULL);
        } else {
            for (Book book : bookList) {
                System.out.printf("                      |     1.MÃ SÁCH: %-6S            2.TÊN SÁCH: %-25S         3. DANH MỤC: %-25S              |\n"+
                                  "                      |     4.SỐ LƯỢNG: %-4d             5.TÌNH TRẠNG: %-13s                   6. TRẠNG THÁI: %-16s                     |\n"+
                                  "                      |     7.TÁC GIẢ: %-100s                |", book.getBookId(), book.getBookName(), book.getCatalog().getCatalogName(), book.getBookquantity(), book.getBookStates(), book.getBookStatus(),book.getListAuthor());
                System.out.println("\n                      +------------------------------------------------------------------------------------------------------------------------------------+");
            }


        }

    }
}
