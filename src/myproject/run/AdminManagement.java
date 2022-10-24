package myproject.run;

import myproject.bussiness.entity.User;
import myproject.bussiness.impl.UserImpl;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.EventListener;
import java.util.List;
import java.util.Scanner;

import static myproject.bussiness.mess.CheckValidate.*;
import static myproject.bussiness.mess.Message.*;
import static myproject.bussiness.mess.Message.ADDRESS;
import static myproject.data.ConstandDesign.*;
import static myproject.data.ConstantRegexAndUrl.*;
import static myproject.data.ConstantRegexAndUrl.REGEXEMAIL;


public class AdminManagement {
    public static UserImpl userImpl = new UserImpl();

    public static void displayUser(Scanner sc) {
        boolean checkout = true;
        do {
            System.out.println(SHOWHEARDUSER);
            int choice = choiceNumber(sc, 1, 6);
            switch (choice) {
                case 1:
                    showListUser(sc);
                    break;
                case 2:
                    addNewUser(sc);
                    break;
                case 3:
                    editUser(sc);
                    break;
                case 4:
                    deleteUser(sc);
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

    public static void showListUser(Scanner sc) {                      // 1. show list user
        System.out.println(SHOWLISTUSER);
        userImpl.displayData();
    }

    public static void addNewUser(Scanner sc) {                      //      2. add new user
        System.out.println(ADDUSER);
        System.out.println("* Nhập số lượng muốn thêm mới");
        int number = Integer.parseInt(strValidate(sc, REGEXQUANTITY));
        for (int i = 0; i < number; i++) {
            User user = new User();
            System.out.printf("* Người thứ %d.\n", (i + 1));
            user = userImpl.inputData(sc);
            boolean check = userImpl.create(user);
            soutMess(check);
        }
    }

    public static void editUser(Scanner sc) {                        // 3. edit user
        System.out.println(EDITUSER);
        List<User> userList = userImpl.readFromFile();
        User user = new User();
        System.out.println("* Nhập mã của người đọc muốn cập nhập.");
        user.setUserId(Integer.parseInt(strValidate(sc, REGEXQUANTITY)));
        do {
            System.out.print(PHONENUMBER);
            user.setPhonenumber(Integer.parseInt(strValidate(sc, REGEXPHONE)));
            boolean check = checkphonenumber(user.getPhonenumber());
            if (check) {
                System.out.println(PHONEERR);
            } else {
                break;
            }
        } while (true);

            System.out.print(EMAIL);
            user.setUserEmail(strValidate(sc, REGEXEMAIL));

        System.out.print(ADDRESS);
        user.setUserAdress(sc.nextLine());
        System.out.println(INPUTSTATUS);
        System.out.println("1." + STATUS1 + "       2." + STATUS3);
        int choice = choiceNumber(sc, 1, 2);
        switch (choice) {
            case 1:
                user.setUserStatus(true);
                break;
            case 2:
                user.setUserStatus(false);
                break;
        }
        boolean check = userImpl.update(user);
        soutMess(check);
    }

    public static void deleteUser(Scanner sc) {                                 // 4.delete(update status)
        System.out.println(DELETEUSER);
        System.out.println("Nhập vào mã của người đọc muốn xoá.");
        String userId = strValidate(sc, REGEXQUANTITY);
        boolean check = userImpl.delete(userId);
        soutMess(check);
    }

    public static void searchByName(Scanner sc) {
        System.out.println(SEARCHUSER);
        System.out.print("Nhập tên sách muốn tìm: ");
        String userName = sc.nextLine();
        System.out.println(SEARCHUSER);
        List<User> userList = userImpl.findbyName(userName);
        if (userList == null) {
            userList = new ArrayList<>();
            System.out.println(ERRORNULL);
        } else {
            String status = STATUS1;
            for (User user : userList) {
                if (!user.isUserStatus()) {
                    status = STATUS3;
                }
                DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
                String startDay = df.format(user.getLibraryCardStartDay());
                String endDay = df.format(user.getLibraryCardEndDay());
                System.out.printf(
                        "|     1.MÃ NGƯỜI DÙNG: %-5d      2.TÊN ĐẦY ĐỦ: %-20s        3.SỐ ĐIỆN THOẠI: %-11d                             |\n" +
                                "|     4.ĐỊA CHỈ NHÀ: %-47s        5.EMAIL: %-23s                         |\n" +
                                "|     6.NGÀY MUA THẺ: %-10s      7.NGÀY HẾT HẠN THẺ: %-10s        8.TRẠNG THÁI: %-15s                           |\n" +
                                "+------------------------------------------------------------------------------------------------------------------------------------+\n",
                        user.getUserId(), user.getUserName(), user.getPhonenumber(), user.getUserAdress(), user.getUserEmail(), startDay, endDay, status);
            }
        }

    }
}
