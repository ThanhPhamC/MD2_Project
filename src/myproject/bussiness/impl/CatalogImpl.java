package myproject.bussiness.impl;


import myproject.bussiness.design.ICatalog;
import myproject.bussiness.entity.Book;
import myproject.bussiness.entity.Catalog;

import static myproject.bussiness.mess.CheckValidate.*;

import myproject.data.WriteAndReadData;

import static myproject.bussiness.mess.Message.*;
import static myproject.data.ConstantRegexAndUrl.*;

import java.util.*;

public class CatalogImpl implements ICatalog<Catalog, String>, Comparator<Catalog> {
    public static WriteAndReadData writeAndReadData = new WriteAndReadData();

    @Override
    public List<Catalog> sortbyName() {                                 //1. sort by name
        List<Catalog> catalogList = readFromFile();
        if (catalogList == null) {
            catalogList = new ArrayList<>();
        }
        Collections.sort(catalogList, new Comparator<Catalog>() {
            @Override
            public int compare(Catalog o1, Catalog o2) {
                return o1.getCatalogName().compareTo(o2.getCatalogName());
            }
        });
        return catalogList;
    }

    @Override
    public boolean create(Catalog catalog) {                                // 2. creat catalog
        List<Catalog> catalogList = readFromFile();
        if (catalogList == null) {
            catalogList = new ArrayList<>();
        }
        catalogList.add(catalog);
        boolean result = writeToFile(catalogList);
        return result;
    }


    @Override
    public boolean update(Catalog catalog) {                            // 3. update catalog
        List<Catalog> catalogList = readFromFile();

        for (int i = 0; i < catalogList.size(); i++) {
            if (catalogList.get(i).getCatalogId() == catalog.getCatalogId()) {
                catalogList.set(i,catalog);
                break;
            }
        }
        BookImpl bookImpl = new BookImpl();                                         // doan ghi lai book
        List<Book> bookList = bookImpl.readFromFile();
        for (Book book : bookList) {
            if (book.getCatalog().getCatalogId() == catalog.getCatalogId()) {
                book.setCatalog(catalog);
            }
        }
        bookImpl.writeToFile(bookList);
        boolean result = writeToFile(catalogList);                                // xong book
      if (result){
          return true;
      }else {
          return false;
      }

    }

    @Override
    public boolean delete(String id) {                          // 4. delete catalog( update status)
        int idCatalog= Integer.parseInt(id);
        List<Catalog> catalogList = readFromFile();
        Catalog catalogReturn = new Catalog();
        boolean check = false;
        for (Catalog catalog : catalogList) {
            if (catalog.getCatalogId()==idCatalog) {
                catalog.setCatalogStatus(false);
                catalogReturn = catalog;
                check = true;
                break;
            }
        }
        BookImpl bookImpl = new BookImpl();                                         // doan ghi lai book
        List<Book> bookList = bookImpl.readFromFile();
        for (Book book : bookList) {
            if (book.getCatalog().getCatalogId() == catalogReturn.getCatalogId()) {
                book.setCatalog(catalogReturn);
            }
        }
        bookImpl.writeToFile(bookList);
        boolean result = writeToFile(catalogList);
        if (result && check) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public boolean writeToFile(List<Catalog> list) {                             // 5. write file
        return writeAndReadData.writeToFile(list, URL_CATALOG);
    }

    @Override
    public List<Catalog> readFromFile() {                                         // 6. read file
        List<Catalog> catalogList = writeAndReadData.readFormFile(URL_CATALOG);
        if (catalogList == null) {
            catalogList = new ArrayList<>();
        }
        return catalogList;
    }

    @Override
    public List<Catalog> findbyName(String name) {                  //7. find catalog by name
        List<Catalog> catalogList = readFromFile();
        if (catalogList == null) {
            catalogList = new ArrayList<>();
        }
        List<Catalog> catalogListByName = new ArrayList<>();
        for (Catalog catalog : catalogList) {
            if (catalog.getCatalogName().contains(name)) {
                catalogListByName.add(catalog);
            }
        }
        return catalogListByName;
    }

    @Override
    public Catalog inputData(Scanner sc) {
        List<Catalog> catalogList = readFromFile();
        if (catalogList == null) {
            catalogList = new ArrayList<>();
        }
        Catalog catalog = new Catalog();
        catalog.setCatalogId(catalogList.size() + 1);
        System.out.println(INPUTNAME);
        catalog.setCatalogName(checkCatalogName(sc));
        System.out.println(INPUTSTATUS);
        boolean catalogStatus=choiceBooleanStatus(sc);
        catalog.setCatalogStatus(catalogStatus);
        return catalog;
    }

    @Override
    public void displayData() {                                 // display data
        List<Catalog> catalogList = sortbyName();
        String status;
        if (catalogList == null) {
            System.out.println(ERRORNULL);
        } else {
            for (Catalog catalog : catalogList) {
                if (catalog.isCatalogStatus()) {
                    status = STATUS1;
                } else {
                    status = STATUS3;
                }
                System.out.printf("                                 |            %-10d      |                %-30s    |         %-20s            |\n", catalog.getCatalogId(), catalog.getCatalogName(), status);
                System.out.println("                                 +-------------------------------------------------------------------------------------------------------------------------+");

            }
        }
    }

    @Override
    public int compare(Catalog o1, Catalog o2) {
        return 0;
    }
}
