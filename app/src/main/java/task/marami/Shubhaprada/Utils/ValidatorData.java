package task.marami.Shubhaprada.Utils;

public class ValidatorData {
    public static boolean nameValidator(String _name)
    {
        if(_name.length()>=4)
        {
            return true;
        }
        else
        {
            return false;
        }
    }
    public static boolean numberValidator(String _number)
    {
        if(_number.length()==10)
        {
            if(  _number.charAt(0)=='9' ||_number.charAt(0)=='8' || _number.charAt(0)=='7' || _number.charAt(0)=='6')
            {
               return true;
            }
            else
            {
                return false;
            }
        }
        else
        {
            return false;
        }
    }
}
