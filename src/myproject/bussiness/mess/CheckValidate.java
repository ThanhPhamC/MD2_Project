package myproject.bussiness.mess;

import java.util.Scanner;

public class CheckValidate {
    public static String nameValidate(Scanner sc){
        String nameOutput;
        do {
            String nameCheck= "\\w{6,50}$";
            nameOutput= sc.nextLine();
            if (nameOutput.trim().matches(nameCheck)){
                break;
            }else {
                System.out.println(Message.NAMEERROR);
            }
        }while (true);
        return nameOutput;
    }
    public static int choiceNumber(Scanner sc,int a, int b){
        int number;
        do {
            try {
                number=Integer.parseInt(sc.nextLine());
                if (number>=a&& number<=b){
                    break;
                }else {
                    System.out.println(Message.ERRCHOICENUMBER+a+"-"+b+" :");
                }
            }catch (Exception e){
                System.out.println(Message.FORMATERROR);
            }
        }while (true);
        return number;
    }

}
