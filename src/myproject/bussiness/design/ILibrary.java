package myproject.bussiness.design;

import java.util.List;
import java.util.Scanner;

public interface ILibrary<T,E> {
    boolean create(T t);
    boolean update(T t);
    boolean delete(E name);
    boolean writeToFile(List<T> list);
    List<T> readFromFile();
    List<T> findbyName (E name);
    T inputData (Scanner sc);
    void displayData();

}
