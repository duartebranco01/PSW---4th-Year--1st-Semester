package Logic;

public class Payment {
    public static boolean isCardValid = false;

    public static boolean validateCard(String cnumber, String cname){
        String new_cnumber = cnumber.replaceAll("(?<=\\d) +(?=\\d)", "");
        if(new_cnumber.length() == 16){
            if(!(cname == null || cname.isBlank()|| cname.isEmpty() )) isCardValid = true;
            else isCardValid = false;
        }else isCardValid = false;
        return isCardValid;
    }
}
