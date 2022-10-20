package myproject.bussiness.entity;

import java.io.Serializable;
import java.util.ArrayList;

public class Book implements Serializable {
    private int bookId;
    private String bookName;
    private int bookquantity;
    private Catalog catalog;
    private ArrayList<String> listAuthor=new ArrayList<>();
    private String bookStates;
    private boolean bookStatus;

    public Book() {
    }

    public Book(int bookId, String bookName, int bookquantity, Catalog catalog,
                ArrayList<String> listAuthor, String bookStates, boolean bookStatus) {
        this.bookId = bookId;
        this.bookName = bookName;
        this.bookquantity = bookquantity;
        this.catalog = catalog;
        this.listAuthor = listAuthor;
        this.bookStates = bookStates;
        this.bookStatus = bookStatus;
    }

    public int getBookId() {
        return bookId;
    }

    public void setBookId(int bookId) {
        this.bookId = bookId;
    }

    public String getBookName() {
        return bookName;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName;
    }

    public int getBookquantity() {
        return bookquantity;
    }

    public void setBookquantity(int bookquantity) {
        this.bookquantity = bookquantity;
    }

    public Catalog getCatalog() {
        return catalog;
    }

    public void setCatalog(Catalog catalog) {
        this.catalog = catalog;
    }

    public ArrayList<String> getListAuthor() {
        return listAuthor;
    }

    public void setListAuthor(ArrayList<String> listAuthor) {
        this.listAuthor = listAuthor;
    }

    public String getBookStates() {
        return bookStates;
    }

    public void setBookStates(String bookStates) {
        this.bookStates = bookStates;
    }

    public boolean isBookStatus() {
        return bookStatus;
    }

    public void setBookStatus(boolean bookStatus) {
        this.bookStatus = bookStatus;
    }
}
