package myproject.bussiness.impl;


import myproject.bussiness.design.IUser;
import myproject.bussiness.entity.User;
import myproject.data.WriteAndReadData;

import static myproject.bussiness.mess.CheckValidate.*;
import static myproject.bussiness.mess.Message.*;
import static myproject.data.ConstantRegexAndUrl.*;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

public class UserImpl implements IUser<User, String> {
    public static WriteAndReadData writeAndReadData = new WriteAndReadData();

    @Override
    public boolean create(User user) {                                                  //1. creat

        List<User> userList = readFromFile();
        if (userList == null) {
            userList = new ArrayList<>();
        }
        userList.add(user);
        boolean result = writeToFile(userList);
        return result;
    }

    @Override
    public boolean update(User user) {                                               // 2.update
        List<User> userList = readFromFile();
        if (userList == null) {
            return false;
        } else {
            boolean check = false;
            for (int i = 0; i < userList.size(); i++) {
                if (userList.get(i).getUserId() == user.getUserId()) {
                    userList.set(i, user);
                    check = true;
                    break;
                }
            }
            boolean result = writeToFile(userList);
            if (check && result) {
                return true;
            } else {
                return false;
            }
        }
    }

    @Override
    public boolean delete(String name) {                                            // 3. delete(update status)
        List<User> userList = readFromFile();
        boolean check = false;
        for (User user : userList) {
            if (user.getUserName().equals(name)) {
                user.setUserStatus(!user.isUserStatus());
                check = true;
                break;
            }
        }
        boolean result = writeToFile(userList);
        if (result && check) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public boolean writeToFile(List<User> list) {                                           //4.write to file
        return writeAndReadData.writeToFile(list, URL_USER);
    }

    @Override
    public List<User> readFromFile() {
        List<User> userList = writeAndReadData.readFormFile(URL_USER);
        if (userList == null) {
            userList = new ArrayList<>();
        }
        return userList;
    }

    @Override
    public List<User> findbyName(String name) {                                              // 5. read form file
        List<User> userList = readFromFile();
        List<User> userListByName = new ArrayList<>();
        for (User user : userList) {
            if (user.getUserName().contains(name)) {
                userListByName.add(user);
            }
        }
        return userListByName;
    }

    @Override
    public User inputData(Scanner sc) {
        List<User> userList = readFromFile();
        if (userList == null) {
            userList = new ArrayList<>();
        }
        User user = new User();
        user.setUserId(userList.size() + 1);
        System.out.print(INPUTNAME);
        user.setUserName(strValidate(sc, REGEXFULLNAME));
        do {
            System.out.print(USERNAMELOGIN);
            user.setUserLogin(strValidate(sc, REGEXNAME));
            boolean check = false;
            for (User user1 : userList) {
                if (user1.getUserLogin().equals(user.getUserLogin())) {
                    check = true;
                    break;
                }
            }
            if (check) {
                System.out.println(NAMEERROR1);
            } else break;
        } while (true);
        do {
            System.out.print(USERPASS);
            user.setUserPass(strValidate(sc, REGEXPASS));
            System.out.print("* Nhập lại mật khẩu: ");
            user.setComfirmUserPass(strValidate(sc, REGEXPASS));
            if (user.getUserPass().matches(user.getComfirmUserPass())) {
                break;
            } else {
                System.out.println("Không trùng khớp, nhập lại.");
            }
        } while (true);
        System.out.print(PHONENUMBER);
        String phonenumber = strValidate(sc, REGEXPHONE);
        user.setPhonenumber(Integer.parseInt(phonenumber));
        System.out.print(EMAIL);
        user.setUserEmail(strValidate(sc, REGEXEMAIL));
        ///thieu dia chi nua :(((((
        System.out.print(ADDRESS);
        user.setUserAdress(sc.nextLine());
        System.out.print(LBCARDSTART);
        user.setLibraryCardStartDay(dateValidate(sc));
        Date date = user.getLibraryCardStartDay();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        System.out.print(LBCARDEND);
        int choice1 = choiceNumber(sc, 1, 3);
        switch (choice1) {
            case 1:
                calendar.roll(Calendar.MONTH, 3);
                user.setLibraryCardEndDay(calendar.getTime());
                break;
            case 2:
                calendar.roll(Calendar.MONTH, 6);
                user.setLibraryCardEndDay(calendar.getTime());
                break;
            case 3:
                calendar.roll(Calendar.MONTH, 12);
                user.setLibraryCardEndDay(calendar.getTime());
                break;
        }
        if (userList.size() == 0) {
            user.setPermission(true);
        } else {
            user.setPermission(false);
        }
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
        return user;
    }

    @Override
    public void displayData() {
        LibraryBookCardImpl lbCardImpl=new LibraryBookCardImpl();
        List<User> userList = lbCardImpl.userStatus();
        if (userList == null) {
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

                System.out.printf("%d - %s - %d - %s - %s - %s - %s - %s", user.getUserId(), user.getUserName(), user.getPhonenumber(), user.getUserEmail(),
                        user.getUserAdress(), startDay, endDay, status);
            }
        }


    }

    @Override
    public User checkLogin(String name, String pass) {
        List<User> listUser = readFromFile();
        for (User user : listUser) {
            if (user.getUserLogin().equals(name) && user.getUserPass().equals(pass)) {
                return user;
            }
        }
        return null;
    }
}
