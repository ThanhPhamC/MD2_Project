import myproject.bussiness.mess.Message;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Scanner;

import static myproject.bussiness.mess.CheckValidate.strValidate;

public class Main {
    public static void main(String[] args) {
       Scanner sc=new Scanner(System.in);
        ArrayList<String> arr=new ArrayList<>();
        arr.add("Pham chi thanh");
        arr.add("Nguyen viet anh");
        arr.add("Nguyen minh thoa");
        System.out.printf("%s",arr);
    }


}