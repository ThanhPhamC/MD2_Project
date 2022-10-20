package myproject.bussiness.impl;

import myproject.bussiness.design.ILibrary;
import myproject.bussiness.entity.Catalog;
import myproject.bussiness.mess.CheckValidate;
import myproject.bussiness.mess.Message;
import myproject.data.DataUrl;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class CatalogImpl implements ILibrary<Catalog, String> {
    @Override
    public boolean create(Catalog catalog) {
        List<Catalog> catalogList = readFormFile();
        if (catalogList == null) {
            catalogList = new ArrayList<>();
        }
        catalogList.add(catalog);
        boolean result = writeToFile(catalogList);
        return result;
    }

    @Override
    public boolean update(Catalog catalog) {
        List<Catalog> catalogList = readFormFile();
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
    public boolean delete(String name) {
        List<Catalog> catalogList = readFormFile();
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
        } else return false;
    }

    @Override
    public Catalog findbyName(String name) {
        List<Catalog> catalogList = readFormFile();
        for (Catalog catalog : catalogList) {
            if (catalog.getCatalogName().equals(name)) {
                return catalog;
            }
        }
        return null;
    }

    @Override
    public boolean writeToFile(List<Catalog> litst) {
        File file = null;
        FileOutputStream fos = null;
        ObjectOutputStream oos = null;
        boolean check = true;
        try {
            file = new File(DataUrl.Url_Catalog);
            fos = new FileOutputStream(file);
            oos = new ObjectOutputStream(fos);
        } catch (Exception ex) {
            check = false;
            ex.printStackTrace();
        } finally {
            try {
                if (fos != null) {
                    fos.close();
                }
                if (fos != null) {
                    oos.close();
                }
            } catch (Exception ex2) {
                ex2.printStackTrace();
            }
        }
        return check;
    }

    @Override
    public List<Catalog> readFormFile() {
        List<Catalog> catalogList = new ArrayList<>();
        File file = null;
        FileInputStream fis = null;
        ObjectInputStream ois = null;
        try {
            file = new File(DataUrl.Url_Catalog);
            if (file.exists()) {
                fis = new FileInputStream(file);
                ois = new ObjectInputStream(fis);
                catalogList = (List<Catalog>) ois.readObject();
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            try {
                if (fis != null) {
                    fis.close();
                }
                if (ois != null) {
                    ois.close();
                }
            } catch (Exception ex1) {
                ex1.printStackTrace();
            }
        }
        return catalogList;
    }

    @Override
    public Catalog inputData(Scanner sc) {
        List<Catalog> catalogList = readFormFile();
        if (catalogList == null) {
            catalogList = new ArrayList<>();
        }
        Catalog catalog = new Catalog();
        catalog.setCatalogId(catalogList.size() + 1);
        do {
            System.out.println(Message.INPUTNAME);
            catalog.setCatalogName(CheckValidate.nameValidate(sc));
            boolean check = false;
            for (Catalog cat : catalogList) {
                if (cat.getCatalogName().equals(catalog.getCatalogName())) {
                    check = true;
                    break;
                }
            }
            if (check) {
                System.out.println(Message.NAMEERROR1);
            } else {
                break;
            }
        } while (true);
        System.out.println(Message.INPUTSTATUS);
        System.out.println(Message.INPUTCHOICESTATUS);
        int choice = CheckValidate.choiceNumber(sc, 1, 2);
        if (choice == 1) {
            catalog.setCatalogStatus(true);
        } else {
            catalog.setCatalogStatus(false);
        }
        return catalog;
    }
    @Override
    public void displayData() {
        List<Catalog> catalogList = readFormFile();
        String status= "Hoạt động";
        if (catalogList==null){
            System.out.println(Message.ERRORNULL);
        }else {
            for (Catalog catalog:catalogList) {
                if (!catalog.isCatalogStatus()){
                    status="Không hoạt động.";
                }
                System.out.printf("%d%s%s",catalog.getCatalogId(),catalog.getCatalogName(),status);
            }
        }
    }
}
