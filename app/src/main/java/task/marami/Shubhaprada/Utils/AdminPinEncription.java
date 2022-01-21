package task.marami.Shubhaprada.Utils;

public class AdminPinEncription {
    public static String getValue(String pin)
    {
        String pindig="";
        switch (pin)
        {
            case "0":
                pindig="0000";
                break;
            case "1":
                pindig="0001";
                break;
            case "2":
                pindig="0010";
                break;
            case "3":
                pindig="0011";
                break;
            case "4":
                pindig="0100";
                break;
            case "5":
                pindig="0101";
                break;
            case "6":
                pindig="0110";
                break;
            case "7":
                pindig="0111";
                break;
            case "8":
                pindig="1000";
                break;
            case "9":
                pindig="1001";
                break;
        }
        return pindig;
    }
}
