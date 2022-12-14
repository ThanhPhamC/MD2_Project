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
                    userList.set(i,user);
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
    public boolean delete(String id) {                                            // 3. delete(update status)
        int userId=Integer.parseInt(id);
        List<User> userList = readFromFile();
        boolean check = false;
        for (User user : userList) {
            if (user.getUserId()==userId) {
                user.setUserStatus(false);
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
            System.out.print("* Nh???p l???i m???t kh???u: ");
            user.setComfirmUserPass(strValidate(sc, REGEXPASS));
            if (user.getUserPass().matches(user.getComfirmUserPass())) {
                break;
            } else {
                System.out.println("Kh??ng tr??ng kh???p, nh???p l???i.");
            }
        } while (true);
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
        do {
            System.out.print(EMAIL);
            user.setUserEmail(strValidate(sc, REGEXEMAIL));
            boolean check = checkEmail(user.getUserEmail());
            if (check) {
                System.out.println(EMAILERR);
            } else {
                break;
            }
        } while (true);
        System.out.print(ADDRESS);
        user.setUserAdress(sc.nextLine());
        System.out.print(LBCARDSTART);
        user.setLibraryCardStartDay(dateValidate(sc));
        Date date = user.getLibraryCardStartDay();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        System.out.print(LBCARDEND);
        int choice1 = choiceNumber(sc, 1, 3);
        int month = calendar.get(Calendar.MONTH);
        switch (choice1) {
            case 1:
                calendar.roll(Calendar.MONTH, 3);
                if (month >= 10) {
                    calendar.roll(Calendar.YEAR, 1);
                }
                break;
            case 2:
                calendar.roll(Calendar.MONTH, 6);
                if (month >= 7) {
                    calendar.roll(Calendar.YEAR, 1);
                }
                break;
            case 3:
                calendar.roll(Calendar.YEAR, 1);
                break;
        }
        user.setLibraryCardEndDay(calendar.getTime());

        if (userList.size() == 0) {                                 // set quyen add min
            user.setPermission(true);
        } else {
            user.setPermission(false);
        }
        user.setUserStatus(true);
        return user;
    }
    @Override
    public void displayData() {
        LibraryBookCardImpl lbCardImpl = new LibraryBookCardImpl();
        List<User> userList = lbCardImpl.userStatus();
        if (userList == null) {
            System.out.println(ERRORNULL);
        } else {
            String status ;
            for (User user : userList) {
                if (user.isUserStatus()) {
                    status = STATUS1;
                }else {
                    status = STATUS3;
                }
                DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
                String startDay = df.format(user.getLibraryCardStartDay());
                String endDay = df.format(user.getLibraryCardEndDay());
                System.out.printf(
                        "|     1.M?? NG?????I D??NG: %-5d          2.T??N ?????Y ?????: %-20s    3.S??? ??I???N THO???I: %-11d                             |\n" +
                        "|     4.?????A CH??? NH??: %-47s        5.EMAIL: %-23s                         |\n" +
                        "|     6.NG??Y MUA TH???: %-10s      7.NG??Y H???T H???N TH???: %-10s        8.TR???NG TH??I: %-16s                           |\n" +
                        "+------------------------------------------------------------------------------------------------------------------------------------+\n",
                        user.getUserId(), user.getUserName(), user.getPhonenumber(),user.getUserAdress(),user.getUserEmail(),startDay, endDay, status);

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
