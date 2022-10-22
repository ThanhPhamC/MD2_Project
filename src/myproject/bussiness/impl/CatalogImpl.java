package myproject.bussiness.impl;


import myproject.bussiness.design.ICatalog;
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
    public boolean update(Catalog catalog) {                    // 3. update catalog
        List<Catalog> catalogList = readFromFile();
        if (catalogList == null) {
            return false;
        } else {
            boolean check = false;
            for (int i = 0; i < catalogList.size(); i++) {
                if (catalogList.get(i).getCatalogId() == catalog.getCatalogId()) {
                    catalogList.set(i, catalog);
                    check = true;
                    break;
                }
            }
            boolean result = writeToFile(catalogList);
            if (check && result) {
                return true;
            } else {
                return false;
            }
        }
    }
    @Override
    public boolean delete(String name) {                          // 4. delete catalog( update status)
        List<Catalog> catalogList = readFromFile();
        boolean check = false;
        for (Catalog catalog : catalogList) {
            if (catalog.getCatalogName().equals(name)) {
                catalog.setCatalogStatus(!catalog.isCatalogStatus());
                check = true;
                break;
            }
        }
        boolean result = writeToFile(catalogList);
        if (result && check) {
            return true;
        } else {
            return false;
        }
    }
    @Override
    public boolean writeToFile(List<Catalog> list) {                             // 5. write file
        return writeAndReadData.writeToFile(list,URL_CATALOG);
    }

    @Override
    public List<Catalog> readFromFile() {                                         // 6. read file
        List<Catalog> catalogList=writeAndReadData.readFormFile(URL_CATALOG);
        if (catalogList==null){
            catalogList=new ArrayList<>();
        }
        return catalogList;
    }

    @Override
    public List<Catalog> findbyName(String name) {                  //7. find catalog by name
        List<Catalog> catalogList = readFromFile();
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
        do {
            System.out.println(INPUTNAME);
            catalog.setCatalogName(strValidate(sc,REGEXNAME ));
            boolean check = false;
            for (Catalog cat : catalogList) {
                if (cat.getCatalogName().equals(catalog.getCatalogName())) {
                    check = true;
                    break;
                }
            }
            if (check) {
                System.out.println(NAMEERROR1);
            } else {
                break;
            }
        } while (true);
        System.out.println(INPUTSTATUS);
        System.out.println("1."+STATUS1+"       2."+STATUS3);
        int choice = choiceNumber(sc, 1, 2);
        switch (choice){
            case 1:catalog.setCatalogStatus(true);break;
            case 2:catalog.setCatalogStatus(false);break;
        }
        return catalog;
    }

    @Override
    public void displayData() {
        List<Catalog> catalogList = sortbyName();
        String status = STATUS1;
        if (catalogList == null) {
            System.out.println(ERRORNULL);
        } else {
            for (Catalog catalog : catalogList) {
                if (!catalog.isCatalogStatus()) {
                    status = STATUS3;
                }
                System.out.printf("%d - %s - %s", catalog.getCatalogId(), catalog.getCatalogName(),status);
            }
        }
    }
    @Override
    public int compare(Catalog o1, Catalog o2) {
        return 0;
    }
}
