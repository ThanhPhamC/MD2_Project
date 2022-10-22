package myproject.bussiness.impl;


import myproject.bussiness.design.ILibraryBookCard;
import myproject.bussiness.entity.Book;
import myproject.bussiness.entity.Catalog;
import myproject.bussiness.entity.LibraryBookCard;
import myproject.bussiness.mess.Message;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class LibraryBookCardImpl implements ILibraryBookCard<LibraryBookCard, String> {
    @Override
    public boolean create(LibraryBookCard libraryBookCard) {

        List<LibraryBookCard> libraryBookCardList = readFromFile();
        if (libraryBookCardList == null) {
            libraryBookCardList = new ArrayList<>();
        }
        libraryBookCardList.add(libraryBookCard);
        boolean result = writeToFile(libraryBookCardList);
        return result;
    }
    @Override
    public boolean update(LibraryBookCard libraryBookCard) {
        List<LibraryBookCard> libraryBookCardList = readFromFile();
        if (libraryBookCardList == null) {
            return false;
        } else {
            boolean check = false;
            for (int i = 0; i < libraryBookCardList.size(); i++) {
                if (libraryBookCardList.get(i).getLibraryBookCardId() == libraryBookCard.getLibraryBookCardId()) {
                    libraryBookCardList.set(i, libraryBookCard);
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
    public boolean delete(String name) {
        List<LibraryBookCard> libraryBookCardList = readFromFile();
        boolean check = false;
        for (LibraryBookCard lbcard : libraryBookCardList) {
            if (lbcard.getLibraryBookCardName().equals(name)) {
                lbcard.setLibraryBookCardStatus(Message.LBCARDSTATUS3);
                check = true;
                break;
            }
        }
        boolean result = writeToFile(libraryBookCardList);
        if (result && check) {
            return true;
        } else {
            return false;
        }
    }
    @Override
    public boolean writeToFile(List<LibraryBookCard> list) {
        return false;
    }

    @Override
    public List<LibraryBookCard> readFromFile() {
        return null;
    }

    @Override
    public List<LibraryBookCard> findbyName(String name) {
        return null;
    }

    @Override
    public LibraryBookCard inputData(Scanner sc) {
        return null;
    }

    @Override
    public void displayData() {

    }

    @Override
    public LibraryBookCard searchByUserId(int Id) {
        return null;
    }
}
