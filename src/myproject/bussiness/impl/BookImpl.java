package myproject.bussiness.impl;


import myproject.bussiness.design.ILibrary;
import myproject.bussiness.entity.Book;
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
        List<Book> bookList = readFromFile();
        if (bookList == null) {
            return false;
        } else {
            boolean check = false;
            for (int i = 0; i < bookList.size(); i++) {
                if (bookList.get(i).getBookId().equals(book.getBookId()) ) {
                    bookList.set(i, book);
                    check = true;
                    break;
                }
            }
            boolean result = writeToFile(bookList);
            if (check && result) {
                return true;
            } else {
                return false;
            }
        }
    }

    @Override
    public boolean delete(String name) {                                // 3. delete book (update status)
        List<Book> bookList = readFromFile();
        boolean check = false;
        for (Book book : bookList) {
            if (book.getBookName().equals(name)) {
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
        return writeAndReadData.writeToFile(list,URL_BOOK );
    }

    @Override
    public List<Book> readFromFile() {                          // 5. read form file
        List<Book> bookList=writeAndReadData.readFormFile(URL_BOOK );
        if (bookList==null){
            bookList=new ArrayList<>();
        }
        return bookList;
    }

    @Override
    public List<Book> findbyName(String name) {                     // 6. find book by name
        List<Book> bookList = readFromFile();
        List<Book> catalogListByName = new ArrayList<>();
        for (Book book : bookList) {
            if (book.getBookName().contains(name)) {
                catalogListByName.add(book);
            }
        }
        return catalogListByName;
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
        book.setBookName(strValidate(sc,REGEXNAME ));
        System.out.println("Nhập số lượng sách: ");
        String number = strValidate(sc,REGEXBOOKQUANTITY );
        book.setBookquantity(Integer.parseInt(number));
        do {
            System.out.println("Nhập vào tên tác giả");
            String addNameauthor = strValidate(sc,REGEXFULLNAME );
            boolean check = false;
            for (String authorName : book.getListAuthor()) {
                if (authorName.equals(authorName)) {
                    check = true;
                    break;
                }
            }
            if (check) {
                System.out.println(NAMEERROR1);
            } else {
                book.getListAuthor().add(addNameauthor);
            }
            System.out.println("Bạn có muốn thêm tác giả khác không?\n" +
                    "1. có    2. không");
            System.out.println("");
            int choice = choiceNumber(sc, 1, 2);
            if (choice != 1) {
                break;
            }
            System.out.println("Tình trạng của sách:\n" +
                    "1.Mới    2.Bình thường    3.Cũ");
            int choice1 =choiceNumber(sc, 1, 3);
            if (choice1 == 1) {
                book.setBookStates("Mới");
            } else if (choice1 == 2) {
                book.setBookStates("Bình thường");
            } else {
                book.setBookStates("Cũ");
            }
        } while (true);
        System.out.println(INPUTSTATUS+"\n" +
                "1.Hoạt động     2.Hết sách     3.Không hoạt động.");
        int choice2 = choiceNumber(sc, 1, 3);
        if (choice2 == 1) {
            book.setBookStates(STATUS1);
        } else if (choice2 == 2) {
            book.setBookStates(STATUS2);
        } else {
            book.setBookStates(STATUS3);
        }
        return book;
    }
    @Override
    public void displayData() {
        List<Book> bookList = readFromFile();
        for (Book book : bookList) {
            System.out.printf("%s - %s - %d - %s - %s - %s - %s",book.getBookId(),book.getBookName(),book.getBookquantity(),book.getCatalog().getCatalogName(),book.getBookStates(),book.getBookStatus());
        }
    }
}
