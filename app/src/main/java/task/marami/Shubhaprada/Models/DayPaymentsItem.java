package task.marami.Shubhaprada.Models;

public class DayPaymentsItem {
    String user_type,acc_type, amount;

    public DayPaymentsItem(String user_type,String acc_type, String amount) {
        this.user_type = user_type;
        this.acc_type = acc_type;
        this.amount = amount;
    }

    public String getUser_type() {
        return user_type;
    }

    public void setUser_type(String user_type) {
        this.user_type = user_type;
    }

    public String getAcc_type() {
        return acc_type;
    }

    public void setAcc_type(String acc_type) {
        this.acc_type = acc_type;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }
}
