package myproject.bussiness.impl;

import myproject.bussiness.design.ILibrary;
import myproject.bussiness.entity.User;

import java.util.List;
import java.util.Scanner;

public class UserImpl implements ILibrary<User,String> {
    @Override
    public boolean create(User user) {
        return false;
    }

    @Override
    public boolean update(User user) {
        return false;
    }

    @Override
    public boolean delete(String name) {
        return false;
    }

    @Override
    public User findbyName(String name) {
        return null;
    }

    @Override
    public boolean writeToFile(List<User> litst) {
        return false;
    }

    @Override
    public List<User> readFormFile() {
        return null;
    }

    @Override
    public User inputData(Scanner sc) {
        return null;
    }

    @Override
    public void displayData() {

    }
}
