import myproject.bussiness.mess.Message;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Scanner;

import static myproject.bussiness.mess.CheckValidate.strValidate;

public class Main {
    public static void main(String[] args) {
       Scanner sc=new Scanner(System.in);
       String str="^[a-zA-Z]{2,}(?: [a-zA-Z]+){0,4}$";
        System.out.println("\n+----------------------------------------------------------------------------------------------------------------------------------------------------------+\n" +
                "|                                                                                                                                                          |\n" +
                "|                                                                        TIỆM SÁCH SỐ 1                                                                    |\n" +
                "+----------------------------------------------------------------------------------------------------------------------------------------------------------+\n" +
                "|     QUẢN LÝ DANH MỤC        |       QUẢN LÝ SÁCH           |     QUẢN LÝ NGƯỜI ĐỌC         |             QUẢN LÝ PHIẾU ĐỌC      |             THOÁT      |\n" +
                "+----------------------------------------------------------------------------------------------------------------------------------------------------------+\n" +
                "|                                                            |            +-----------------------+                                                        |\n" +
                "|                                                            |            |    1.Danh sách.       |                                                        |\n" +
                "|                                                            |            +-----------------------+                                                        |\n" +
                "|                                                            |    2.Thêm mới.                |                                                             |\n" +
                "|                                                            |    3.Cập nhập.                |                                                             |\n" +
                "|                                                            |    4.Xoá(đổi trạng thái).     |                                                             |\n" +
                "|                                                            |    5.Tìm theo tên.            |                                                             |\n" +
                "|                                                            |    6.Thoát.                   |                                                             |\n" +
                "+----------------------------------------------------------------------------------------------------------------------------------------------------------+\n" +
                "|      *************************************    DANH SÁCH NGƯỜI DÙNG CỦA THƯ VIỆN     *****************************************    |                      \n" +
                "+----------------------------------------------------------------------------------------------------------------------------------+\n" +
                "|     1.MÃ NGƯỜI DÙNG: 10000      2.TÊN ĐẦY ĐỦ: PHẠM CHÍ THANH THANH        3.SỐ ĐIỆN THOẠI: 08264119932                           |\n" +
                "|     4.ĐỊA CHỈ NHÀ: HA NOI - NAM TU LIEM - 114 ME TRI THUONG THUONG        5.EMAIL: THANHPHAMHUMG@GMAIL.COM                       |\n" +
                "|     6.NGÀY MUA THẺ: 12/02/2022      7.NGÀY HẾT HẠN THẺ: 12/08/2022        8.TRẠNG THÁI: KHÔNG HOẠT ĐỘNG                          |\n" +
                "+----------------------------------------------------------------------------------------------------------------------------------+\n");
        String str1=sc.nextLine();
        System.out.println(str1.matches(str));
    }


}