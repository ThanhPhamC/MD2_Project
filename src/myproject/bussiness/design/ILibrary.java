package myproject.bussiness.design;

import java.util.List;
import java.util.Scanner;

public interface ILibrary<T,E> {
    boolean create(T t);
    boolean update(T t);
    boolean delete(E name);
    T findbyName (E name);
    boolean writeToFile (List<T> litst);
    List<T> readFormFile ();
    T inputData (Scanner sc);
    void displayData();
}
