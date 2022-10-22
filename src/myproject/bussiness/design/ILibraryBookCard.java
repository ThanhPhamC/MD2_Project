package myproject.bussiness.design;

import myproject.bussiness.entity.LibraryBookCard;

public interface ILibraryBookCard<T,E> extends ILibrary<T,E>{
LibraryBookCard searchByUserId(int Id);
}
