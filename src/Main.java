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
            String str1 = "20/10/2022";
            String str2 = "20/10/2023";
            DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
            Date date1 = df.parse(str1);
            Date date2 = df.parse(str2);
            String dayreturn = "";
            long valuedate1 = date1.getTime();
            long valuedate2 = date2.getTime();
            long value = Math.abs(valuedate1 - valuedate2);
            long valueday = value / (24 * 60 * 60 * 1000);
            System.out.println(valueday + "ngay");


            System.out.println(dayreturn);
        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }


}