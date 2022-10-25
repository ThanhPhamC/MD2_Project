import com.sun.xml.internal.ws.api.model.wsdl.WSDLOutput;
import myproject.bussiness.mess.Message;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Scanner;

import static myproject.bussiness.mess.CheckValidate.strValidate;
import static myproject.data.ConstandDesign.SHOWHEARD;

public class Main {
    public static void main(String[] args) {
        int num;
        try {
            String str1="20/10/2022";
            String str2="23/10/2022";
            DateFormat df= new SimpleDateFormat("dd/MM/yyyy");
            Date date1 =df.parse(str1);
            Date date2=df.parse(str2);
            num= date1.compareTo(date2);
            System.out.println(num);
        }catch (Exception ex){
            ex.printStackTrace();
        }

    }



}