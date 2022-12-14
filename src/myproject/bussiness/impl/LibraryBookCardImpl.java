package myproject.bussiness.impl;


import myproject.bussiness.design.ILibraryBookCard;
import myproject.bussiness.entity.Book;
import myproject.bussiness.entity.LibraryBookCard;
import myproject.bussiness.entity.User;
import myproject.data.WriteAndReadData;
import myproject.run.LibraryManagement;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

import static myproject.bussiness.mess.CheckValidate.*;
import static myproject.bussiness.mess.Message.*;
import static myproject.data.ConstantRegexAndUrl.*;

public class LibraryBookCardImpl implements ILibraryBookCard<LibraryBookCard, String>, Comparator<LibraryBookCard> {
    public static WriteAndReadData writeAndReadData = new WriteAndReadData();

    @Override
    public boolean create(LibraryBookCard libraryBookCard) {                    //1. creat

        List<LibraryBookCard> libraryBookCardList = readFromFile();
        if (libraryBookCardList == null) {
            libraryBookCardList = new ArrayList<>();
        }
        libraryBookCardList.add(libraryBookCard);
        boolean result = writeToFile(libraryBookCardList);
        return result;
    }

    @Override
    public boolean update(LibraryBookCard libraryBookCard) {                //      2.update
        List<LibraryBookCard> libraryBookCardList = readFromFile();
        if (libraryBookCardList == null) {
            return false;
        } else {
            boolean check = false;
            for (int i = 0; i < libraryBookCardList.size(); i++) {
                if (libraryBookCardList.get(i).getLibraryBookCardId() == libraryBookCard.getLibraryBookCardId()) {
                    libraryBookCardList.get(i).setBookArrayList(libraryBookCard.getBookArrayList());
                    libraryBookCardList.get(i).setReturnDate(libraryBookCard.getReturnDate());
                    check = true;
                    break;
                }
            }
            boolean result = writeToFile(libraryBookCardList);
            if (check && result) {
                return true;
            } else {
                return false;
            }
        }
    }
    @Override
    public boolean delete(String id) {                            //                  3. deletebyId(update status)
        int lBCard = Integer.parseInt(id);
        List<LibraryBookCard> libraryBookCardList = readFromFile();
        BookImpl bookImpl = new BookImpl();
        List<Book> bookList = bookImpl.readFromFile();
        boolean check = false;
        for (LibraryBookCard lbcard : libraryBookCardList) {            // xu li cap nhap so luong sach khi tra the
            if (lbcard.getLibraryBookCardId() == lBCard) {
                for (Book book : lbcard.getBookArrayList()) {
                    for (int i = 0; i < bookList.size(); i++) {
                        if (book.getBookId().equals(bookList.get(i).getBookId())) {
                            bookList.get(i).setBookquantity(bookList.get(i).getBookquantity() + 1);
                            bookImpl.writeToFile(bookList);
                        }
                    }
                }
                Date date = new Date();
                lbcard.setActualReturnDate(date);
                check = true;
                break;
            }
        }
//        for (LibraryBookCard lbcard : libraryBookCardList) {
//            if (lbcard.getLibraryBookCardName() == null) {
//                for (Book lbBook : lbcard.getBookArrayList()) {
//                    for (Book book : bookList) {
//                        if (lbBook.getBookId().equals(book.getBookId()) && book.getBookquantity() == 1) {
//                        }
//                    }
//                }
//            }
//        }

        boolean result = writeToFile(libraryBookCardList);

        if (result && check) {
            return true;
        } else {
            return false;
        }
    }
    @Override
    public boolean writeToFile(List<LibraryBookCard> list) {                    //4. write to file
        return writeAndReadData.writeToFile(list, URL_LBCARD);
    }
    @Override
    public List<LibraryBookCard> readFromFile() {                               //5. read form file
        List<LibraryBookCard> libraryBookCardList = writeAndReadData.readFormFile(URL_LBCARD);
        if (libraryBookCardList == null) {
            libraryBookCardList = new ArrayList<>();
        }
        return libraryBookCardList;
    }

    @Override
    public List<LibraryBookCard> findbyName(String name) {                       //6. find by name
        List<LibraryBookCard> libraryBookCardList = updateStatus();
        if (libraryBookCardList == null) {
            libraryBookCardList = new ArrayList<>();
        }
        List<LibraryBookCard> lbCardByName = new ArrayList<>();
        for (LibraryBookCard lbCard : libraryBookCardList) {
            if (lbCard.getLibraryBookCardName().contains(name)) {
                lbCardByName.add(lbCard);
            }
        }
        return lbCardByName;
    }
    @Override
    public LibraryBookCard inputData(Scanner sc) {                                 //7. input data
        List<LibraryBookCard> libraryBookCardList = readFromFile();
        if (libraryBookCardList == null) {
            libraryBookCardList = new ArrayList<>();
        }
        LibraryBookCard lbCard = new LibraryBookCard();
        lbCard.setLibraryBookCardId(libraryBookCardList.size() + 1);
        DateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");
        Date date = new Date();
        String strName = df.format(date) + "-" + (int) ((Math.random()) * 1000);
        lbCard.setLibraryBookCardName(strName);
        lbCard.setBorrowDate(date);
        do {
            System.out.println(DAYRETURNBOOK);
            lbCard.setReturnDate(dateValidate(sc));
            if (lbCard.getReturnDate().compareTo(lbCard.getBorrowDate()) < 0) {
                System.out.println("ng??y tr??? ph???i l???n h??n ng??y m?????n!");
            } else {
                break;
            }
        } while (true);
        System.out.println(ADDBOOKTOCARD);
        lbCard.setBookArrayList(bookListCard(sc));
        System.out.println("Ch???n ng?????i ?????c mu???n t???o phi???u.");
        lbCard.setUser(userForCard(sc));
        return lbCard;
    }
    @Override
    public void displayData() {                                                     //8 display data
        Scanner sc = new Scanner(System.in);
        List<LibraryBookCard> libraryBookCardList = updateStatus();
        Collections.sort(libraryBookCardList, new Comparator<LibraryBookCard>() {
            @Override
            public int compare(LibraryBookCard o1, LibraryBookCard o2) {
                return o1.getReturnDate().compareTo(o2.getReturnDate());
            }
        });
        for (LibraryBookCard lbCard : libraryBookCardList) {
            String actualReturnDate = "Ch??a tr???.";
            DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
            if (lbCard.getActualReturnDate() != null) {
                actualReturnDate = df.format(lbCard.getActualReturnDate());
            }
            String borrowDay = df.format(lbCard.getBorrowDate());
            String returnDay = df.format(lbCard.getReturnDate());
            System.out.printf("|   1.M?? TH???: %-5d    2.T??N TH???: %-18s      3.NG?????I M?????N: %-20s      4.TR???NG TH??I: %-10s |\n" +
                            "|   5.NG??Y M?????N: %-10s                               6.NG??Y H???N TR???: %-10s        7.NG??Y TR??? TH???C T???: %-10s |\n",
                    lbCard.getLibraryBookCardId(), lbCard.getLibraryBookCardName(), lbCard.getUser().getUserName(), lbCard.getLibraryBookCardStatus(), borrowDay, returnDay, actualReturnDate);
            String strListBook = "|   8.DANH S??CH S??CH M?????N: ";
            for (Book book : lbCard.getBookArrayList()) {
                strListBook += book.getBookName() + ". ";
            }
            System.out.printf("%-103s                    |", strListBook);
            System.out.println("\n+--------------------------------------------------------------------------------------------------------------------------+");

        }
    }

    public List<LibraryBookCard> updateStatus() {                      //9 update status for all card.
        Date currentDay = new Date();
        List<LibraryBookCard> libraryBookCardList = readFromFile();
        for (LibraryBookCard lbCard : libraryBookCardList) {
            if (lbCard.getActualReturnDate() != null) {
                lbCard.setLibraryBookCardStatus(LBCARDSTATUS3);
            } else {
                int checkStatus = lbCard.getReturnDate().compareTo(currentDay);
                if (checkStatus >= 0) {
                    lbCard.setLibraryBookCardStatus(LBCARDSTATUS1);
                } else {
                    lbCard.setLibraryBookCardStatus(LBCARDSTATUS2);
                }
            }
        }
        return libraryBookCardList;
    }


    @Override
    public List<LibraryBookCard> searchByUserId(int id) {                          //9. search by user id
        List<LibraryBookCard> libraryBookCardList = updateStatus();
        if (libraryBookCardList == null) {
            libraryBookCardList = new ArrayList<>();
        }
        List<LibraryBookCard> lbCardById = new ArrayList<>();
        for (LibraryBookCard lbCard : libraryBookCardList) {
            if (lbCard.getUser().getUserId() == id) {
                lbCardById.add(lbCard);
            }
        }
        return lbCardById;
    }

    public List<User> userStatus() {                                // update user stt
        UserImpl userImpl = new UserImpl();
        List<User> userList = userImpl.readFromFile();
        List<LibraryBookCard> libraryBookCardList = updateStatus();
        for (User user : userList) {
            int count = 0;
            for (LibraryBookCard lbCard : libraryBookCardList) {
                if (user.getUserName().equals(lbCard.getUser().getUserName()) && lbCard.getLibraryBookCardStatus().equals(LBCARDSTATUS2)) {
                    count++;
                }
            }
            if (count >= 3) {
                user.setUserStatus(false);
            }
        }
        return userList;
    }

    public LibraryBookCard inputDataUser(Scanner sc, User user) {
        LibraryBookCard lbCard = new LibraryBookCard();
        List<LibraryBookCard> libraryBookCardList = readFromFile();
        lbCard.setLibraryBookCardId(libraryBookCardList.size() + 1);
        lbCard.setUser(user);
        System.out.println(ADDBOOKTOCARD);
        lbCard.setBookArrayList(bookListCard(sc));
        BookImpl bookImpl = new BookImpl();
        List<Book> bookList = bookImpl.readFromFile();
        boolean check =false;
        for (Book book : lbCard.getBookArrayList()) {
            for (Book book1 : bookList) {
                if (book.getBookId().equals(book1.getBookId())&&book1.getBookquantity()<0){
                    check=true;
                    break;
                }
            }
        }
        if (!check){
            DateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");
            Date date = new Date();
            String strName = df.format(date) + "-" + (int) ((Math.random()) * 1000);
            lbCard.setLibraryBookCardName(strName);
            lbCard.setBorrowDate(date);
            do {
                System.out.println(DAYRETURNBOOK);
                lbCard.setReturnDate(dateValidate(sc));
                if (lbCard.getReturnDate().compareTo(lbCard.getBorrowDate()) < 0) {
                    System.out.println("ng??y tr??? ph???i l???n h??n ng??y m?????n!");
                } else {
                    break;
                }
            } while (true);
        }else {
            System.out.println("Nh???p s??? ng??y mu???n m?????n (<=30 ng??y)");
        }
        return lbCard;
    }


    @Override
    public int compare(LibraryBookCard o1, LibraryBookCard o2) {
        return o1.getReturnDate().compareTo(o2.getReturnDate());
    }
}
