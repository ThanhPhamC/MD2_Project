package myproject.bussiness.impl;


import myproject.bussiness.design.IUser;
import myproject.bussiness.entity.User;
import myproject.data.WriteAndReadData;

import static myproject.bussiness.mess.CheckValidate.*;
import static myproject.bussiness.mess.Message.*;
import static myproject.data.ConstantRegexAndUrl.*;

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
        System.out.println(INPUTNAME);
        user.setUserName(strValidate(sc, REGEXFULLNAME));
        do {
            System.out.println("Tên đăng nhập");
            user.setUserLogin(strValidate(sc, REGEXNAME));
            boolean check = false;
            for (User user1 : userList) {
                if (user1.getUserLogin().equals(user.getUserName())) {
                    check = true;
                    break;
                }
            }
            if (check) {
                System.out.println(NAMEERROR1);
            } else break;
        } while (true);
        do {
            System.out.println("Nhập mật khẩu: ");
            user.setUserPass(strValidate(sc, REGEXPASS));
            System.out.println("Nhập lại mật khẩu");
            user.setComfirmUserPass(strValidate(sc,REGEXPASS));
            if (user.getUserPass().matches(user.getComfirmUserPass())){
                break;
            }else {
                System.out.println("Không trùng khớp, nhập lại.");
            }
        } while (true);
        System.out.println(PHONENUMBER);
        String phonenumber = strValidate(sc, REGEXPHONE);
        user.setPhonenumber(Integer.parseInt(phonenumber));
        System.out.println(EMAIL);
        user.setUserEmail(strValidate(sc, REGEXEMAIL));
        ///thieu dia chi nua :(((((
        System.out.println(ADDRESS);
        System.out.println(LBCARDSTART);
        user.setLibraryCardStartDay(dateValidate(sc));
        System.out.println(LBCARDEND);
        do {
            user.setLibraryCardEndDay(dateValidate(sc));
            int check = user.getLibraryCardStartDay().compareTo(user.getLibraryCardEndDay());
            if (check < 0) {
                break;
            } else {
                System.out.println(ERRORDATE);
            }
        } while (true);
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
        List<User> userList = readFromFile();
        if (userList == null) {
            System.out.println(ERRORNULL);
        } else {
            String status = STATUS1;
            for (User user : userList) {
                if (!user.isUserStatus()) {
                    status = STATUS3;
                }
                System.out.printf("%d - %s - %d - %s - %s - %s - %s - %s", user.getUserId(), user.getUserName(), user.getPhonenumber(), user.getUserEmail(),
                        user.getUserAdress(), user.getLibraryCardStartDay().toString(), user.getLibraryCardEndDay().toString(), status);
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
