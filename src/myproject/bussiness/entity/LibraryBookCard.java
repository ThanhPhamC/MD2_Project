package myproject.bussiness.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class LibraryBookCard implements Serializable {
    private int libraryBookCardId;
    private String libraryBookCardName;
    private User user;
    private Date borrowDate;
    private Date returnDate;
    private Date actualReturnDate;
    private ArrayList<Book> bookArrayList=new ArrayList<>();
    private String libraryBookCardStatus;

    public LibraryBookCard() {
    }

    public LibraryBookCard(int libraryBookCardId, String libraryBookCardName, User user, Date borrowDate,
                           Date returnDate, Date actualReturnDate, ArrayList<Book> bookArrayList, String libraryBookCardStatus) {
        this.libraryBookCardId = libraryBookCardId;
        this.libraryBookCardName = libraryBookCardName;
        this.user = user;
        this.borrowDate = borrowDate;
        this.returnDate = returnDate;
        this.actualReturnDate = actualReturnDate;
        this.bookArrayList = bookArrayList;
        this.libraryBookCardStatus = libraryBookCardStatus;
    }
}
