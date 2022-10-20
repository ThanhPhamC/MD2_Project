package myproject.bussiness.impl;

import myproject.bussiness.design.ILibrary;
import myproject.bussiness.entity.Book;

import java.util.List;
import java.util.Scanner;

public class BookImpl implements ILibrary<Book, String> {

    @Override
    public boolean create(Book book) {
        return false;
    }

    @Override
    public boolean update(Book book) {
        return false;
    }

    @Override
    public boolean delete(String name) {
        return false;
    }

    @Override
    public Book findbyName(String name) {
        return null;
    }

    @Override
    public boolean writeToFile(List<Book> litst) {
        return false;
    }

    @Override
    public List<Book> readFormFile() {
        return null;
    }

    @Override
    public Book inputData(Scanner sc) {
        return null;
    }

    @Override
    public void displayData() {

    }
}
