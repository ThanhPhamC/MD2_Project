package myproject.run;

import myproject.bussiness.entity.Catalog;

import myproject.bussiness.impl.CatalogImpl;


import java.util.List;
import java.util.Scanner;

import static myproject.bussiness.mess.CheckValidate.*;
import static myproject.bussiness.mess.Message.*;
import static myproject.bussiness.mess.Message.STATUS3;
import static myproject.data.ConstandDesign.*;
import static myproject.data.ConstantRegexAndUrl.*;

public class Catalogmanagement {
    public static CatalogImpl catalogImpl = new CatalogImpl();


    public static void main(String[] args) {

    }

    public static void displayCatalogMenu(Scanner sc) {
        boolean checkout = true;
        do {
            System.out.println(SHOWHEARDCATALOG);

            int choice = choiceNumber(sc, 1, 6);
            switch (choice) {
                case 1:
                    showListCatalog();
                    break;
                case 2:
                    addNewCatalog(sc);
                    break;
                case 3:
                    updateCatalog(sc);
                    break;
                case 4:
                    updateStatus(sc);
                    break;
                case 5:
                    searchByName(sc);
                    break;
                case 6:
                    checkout = false;
                    break;
            }
        } while (checkout);
    }


    public static void showListCatalog() {                                //1. show list catalog
        System.out.println(SHOWLISTCATALOG);
        catalogImpl.displayData();
        System.out.println("\n\n");
    }

    public static void addNewCatalog(Scanner sc) {                         //2. add new catalog
        System.out.println(ADDNEWCATALOG);
        System.out.println("* Nhập số lượng muốn thêm mới");
        int number = Integer.parseInt(strValidate(sc, REGEXQUANTITY));
        for (int i = 0; i < number; i++) {
            Catalog catalog = new Catalog();
            System.out.printf("* Danh mục thứ %d.\n", (i + 1));
            catalog = catalogImpl.inputData(sc);
            boolean check = catalogImpl.create(catalog);
            soutMess(check);
        }
    }

    public static void updateCatalog(Scanner sc) {                  //3  update catalog
        System.out.println(EDITCATALOG);
        List<Catalog> catalogList = catalogImpl.readFromFile();
        System.out.println("Nhập Id muốn cập nhập:");
        int idInput = Integer.parseInt(strValidate(sc, REGEXQUANTITY));
        boolean check = false;
        for (Catalog catalog : catalogList) {
            if (catalog.getCatalogId() == idInput) {
                do {
                System.out.println("Bạn có muốn cập nhập tên không?\n" +
                        "1.Có      2. Không.");
                int choices = choiceNumber(sc, 1, 2);
                if (choices == 1) {
                        System.out.println(INPUTNAME);
                        catalog.setCatalogName(strValidate(sc, REGEXFULLNAME));
                        boolean checkname = false;
                        for (Catalog cat : catalogList) {
                            if (cat.getCatalogName().equals(catalog.getCatalogName())) {
                                checkname = true;
                                break;
                            }
                        }
                        if (checkname) {
                            System.out.println(NAMEERROR1);
                        } else {
                            break;
                        }
                    }else break;
                }while (true);
                System.out.println(INPUTSTATUS);
                boolean catalogStatus=choiceBooleanStatus(sc);
                catalog.setCatalogStatus(catalogStatus);
                check = catalogImpl.update(catalog);
                break;
            }
        }
        soutMess(check);
    }

    public static void updateStatus(Scanner sc) {                               //4. delete catalog(update status)
        System.out.println(DELETECATALOG);
        System.out.println("Nhập vào mã của Catalog muốn cập nhập");
        String idCatalog = strValidate(sc, REGEXQUANTITY);
        boolean check = catalogImpl.delete(idCatalog);
        soutMess(check);
    }

    public static void searchByName(Scanner sc) {                                //5 search by name
        System.out.println(SEARCHBYNAME);
        System.out.println("Nhập vào tên muốn tìm kiếm");
        String catalogName = strValidate(sc, REGEXFULLNAME);
        List<Catalog> catalogList = catalogImpl.findbyName(catalogName);
        if (catalogList == null) {
            System.out.printf("Catalog %s không có trong danh sách. ", catalogName); ///// sao no khong vao day????
        } else {
            String status = STATUS1;
            System.out.println(SEARCHBYNAME +
                    "                                |            ****************************       DANH SÁCH TÌM KIẾM THEO TÊN       ****************************             |\n" +
                    "                                |--------------------------------------------------------------------------------------------------------------------------|\n" +
                    "                                |         1.Mã Danh Mục.     |                2. Tên Danh Mục.                  |        3. Trang Thái Của Danh Mục.       |\n" +
                    "                                +--------------------------------------------------------------------------------------------------------------------------+");
            for (Catalog cat : catalogList) {
                if (!cat.isCatalogStatus()) {
                    status = STATUS3;
                }
                System.out.printf("                                |             %-10d      |                %-30s    |         %-20s            |\n", cat.getCatalogId(), cat.getCatalogName(), status);
                System.out.println("                                +--------------------------------------------------------------------------------------------------------------------------+");

            }
        }
    }
}

