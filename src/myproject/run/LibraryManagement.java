package myproject.run;

import myproject.bussiness.entity.User;
import myproject.bussiness.impl.UserImpl;

import java.util.Scanner;
import static myproject.bussiness.mess.CheckValidate.*;
import static myproject.bussiness.mess.Message.*;
import static myproject.data.ConstantRegexAndUrl.*;
public class LibraryManagement {
    public static UserImpl userImpl=new UserImpl();
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        do {
            System.out.println("\n\n\n\t\t+-------------------------------------------------------------------------------------------------------------------------------------------+\n" +
                    "\t\t\t|                                                                                                                                           |\n" +
                    "\t\t\t|                                                              TIỆM SÁCH SỐ 1                                                               |\n" +
                    "\t\t\t+-------------------------------------------------------------------------------------------------------------------------------------------+\n" +
                    "\t\t\t|               1. Đăng nhập                 |                   2. Đăng ký                   |                 3. Thoát                    |\n" +
                    "\t\t\t+-------------------------------------------------------------------------------------------------------------------------------------------+\n");
            System.out.print(YOURCHOICE);
            int choice =choiceNumber(sc,1,3);
            switch (choice) {
                case 1:
                    login(sc);
                    break;
                case 2:
                    register(sc);
                    break;
                case 3:
                    System.exit(0);
            }
        } while (true);
    }
    public static void login(Scanner sc) {
        do {
            System.out.print(USERNAMELOGIN);
            String userName = sc.nextLine();
            System.out.print(USERPASS);
            String passWord = sc.nextLine();
            User user = userImpl.checkLogin(userName, passWord);
            if (user != null) {
                if (user.isPermission()) {
                    Catalogmanagement.dispaAdminyManagement(sc);
                } else {

                }
                break;
            } else {
                System.out.println("\n\n\n\t\t+-------------------------------------------------------------------------------------------------------------------------------------------+\n" +
                        "\t\t\t|                                                                                                                                           |\n" +
                        "\t\t\t|                                                             Đăng nhập thất bại!!!                                                         |\n" +
                        "\t\t\t+-------------------------------------------------------------------------------------------------------------------------------------------+\n" +
                        "\t\t\t|              1. Đăng nhập lại              |                   2. Đăng ký mới               |                 3. Thoát                    |\n" +
                        "\t\t\t+-------------------------------------------------------------------------------------------------------------------------------------------+\n");
                System.out.print("Lựa chọn của bạn là: ");
                int choice =choiceNumber(sc,1,3) ;
                if (choice == 2) {
                    register(sc);
                } else if (choice == 3) {
                    break;
                }
            }
        } while (true);
    }
    public static void register(Scanner sc) {
        User user = new User();
        user = userImpl.inputData(sc);
        boolean result = userImpl.create(user);
        if (result) {
            System.out.println("Đăng kí thanh công");
            login(sc);
        } else {
            System.out.println("Đã xảy ra lỗi trong quá trình đăng kí.");
            register(sc);
        }
    }

}
