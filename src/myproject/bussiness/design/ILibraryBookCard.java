package myproject.bussiness.design;

import myproject.bussiness.entity.LibraryBookCard;

import java.util.List;

public interface ILibraryBookCard<T, E> extends ILibrary<T, E> {
    List<LibraryBookCard> searchByUserId(int id);
}
