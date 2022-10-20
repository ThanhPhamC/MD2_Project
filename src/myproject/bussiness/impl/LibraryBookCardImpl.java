package myproject.bussiness.impl;

import myproject.bussiness.design.ILibrary;
import myproject.bussiness.entity.LibraryBookCard;

import java.util.List;
import java.util.Scanner;

public class LibraryBookCardImpl implements ILibrary<LibraryBookCard, String> {
    @Override
    public boolean create(LibraryBookCard libraryBookCard) {
        return false;
    }

    @Override
    public boolean update(LibraryBookCard libraryBookCard) {
        return false;
    }

    @Override
    public boolean delete(String name) {
        return false;
    }

    @Override
    public LibraryBookCard findbyName(String name) {
        return null;
    }

    @Override
    public boolean writeToFile(List<LibraryBookCard> litst) {
        return false;
    }

    @Override
    public List<LibraryBookCard> readFormFile() {
        return null;
    }

    @Override
    public LibraryBookCard inputData(Scanner sc) {
        return null;
    }

    @Override
    public void displayData() {

    }
}
