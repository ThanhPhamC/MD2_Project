package myproject.bussiness.entity;

import com.sun.org.apache.xpath.internal.objects.XString;

import java.io.Serializable;
import java.util.Date;

public class User implements Serializable {
    private int userId;
    private String userName;
    private int phonenumber;
    private String userEmail;
    private String userAdress;
    private Date libraryCardStartDay;
    private Date libraryCardEndDay;

    public User() {
    }

    public User(int userId, String userName, int phonenumber, String userEmail, String userAdress,
                Date libraryCardStartDay, Date libraryCardEndDay) {
        this.userId = userId;
        this.userName = userName;
        this.phonenumber = phonenumber;
        this.userEmail = userEmail;
        this.userAdress = userAdress;
        this.libraryCardStartDay = libraryCardStartDay;
        this.libraryCardEndDay = libraryCardEndDay;
    }
}
