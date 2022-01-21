package task.marami.Shubhaprada.Models;

public class CollectionChild {
    String acc_code,acount_type,amount;

    public CollectionChild(String acc_code,String acount_type, String amount) {
        this.acc_code=acc_code;
        this.acount_type = acount_type;
        this.amount = amount;
    }

    public String getAcount_type() {
        return acount_type;
    }

    public void setAcount_type(String acount_type) {
        this.acount_type = acount_type;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getAcc_code() {
        return acc_code;
    }

    public void setAcc_code(String acc_code) {
        this.acc_code = acc_code;
    }
}
