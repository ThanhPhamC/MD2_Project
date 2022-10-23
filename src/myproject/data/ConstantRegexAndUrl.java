package myproject.data;

public class ConstantRegexAndUrl {
    public static final String URL_CATALOG="/Users/phamthanh/Desktop/JaVa/MD2_Project/src/myproject/data/Catalog.txt";
    public static final String URL_BOOK ="/Users/phamthanh/Desktop/JaVa/MD2_Project/src/myproject/data/Book.txt";
    public static final String URL_LBCARD ="/Users/phamthanh/Desktop/JaVa/MD2_Project/src/myproject/data/LibraryBookCard.txt";
    public static final String URL_USER ="/Users/phamthanh/Desktop/JaVa/MD2_Project/src/myproject/data/User.txt";

    public static final String REGEXFULLNAME ="^[a-zA-Z]{2,}(?: [a-zA-Z]+){0,4}$";
    public static final String REGEXNAME ="\\w{6,50}$";
    public static final String REGEXQUANTITY ="^[^0]\\d*";
    public static final String REGEXBOOKID ="^[B]\\w{4}";
    public static final String REGEXPHONE ="^(0|\\+84)(\\s|\\.)?((3[2-9])|(5[689])|(7[06-9])|(8[1-689])|(9[0-46-9]))(\\d)(\\s|\\.)?(\\d{3})(\\s|\\.)?(\\d{3})$";
    public static final String REGEXEMAIL ="^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$";
    public static final String REGEXDATE ="^([0-9]{2})/([1-9]|10|11|12)/([0-9]{4})$";
    public static final String REGEXPASS ="^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{8,}$";

}
