package myproject.bussiness.mess;

import javafx.scene.input.DataFormat;

import java.text.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;


import static myproject.bussiness.mess.Message.*;
import static myproject.data.ConstantRegexAndUrl.*;
public class    CheckValidate {
    public static String strValidate(Scanner sc, String str){                              // check input all
        String strOutput;
        do {
            strOutput= sc.nextLine();
            if (strOutput.trim().matches(str)){
                break;
            }else {
                System.out.println(FORMATERROR);
            }
        }while (true);
        return strOutput;
    }
    public static int choiceNumber(Scanner sc,int a, int b){                        // choice int number
        int number;
        do {
            System.out.println(YOURCHOICE);
            try {
                number=Integer.parseInt(sc.nextLine());
                if (number>=a&& number<=b){
                    break;
                }else {
                    System.out.println(ERRCHOICENUMBER+a+"-"+b+" :");
                }
            }catch (Exception e){
                System.out.println(FORMATERROR);
            }
        }while (true);
        return number;
    }
    public static Date dateValidate(Scanner sc ) {
        String intputDate=strValidate(sc,REGEXDATE);
        DateFormat dateFormat=new SimpleDateFormat("dd/mm/yyyy");
        Date date=new Date();
        try {
            date=dateFormat.parse(intputDate);
            return date;
        }catch (Exception e){
            e.printStackTrace();
        }
        return date;
    }









}
