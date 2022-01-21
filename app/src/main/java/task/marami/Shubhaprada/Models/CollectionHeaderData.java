package task.marami.Shubhaprada.Models;

public class CollectionHeaderData {
    String header_cd,header_title,total_amount;

    public CollectionHeaderData(String header_cd,String header_title, String total_amount) {
        this.header_cd = header_cd;
        this.header_title = header_title;
        this.total_amount = total_amount;
    }

    public String getHeader_title() {
        return header_title;
    }

    public void setHeader_title(String header_title) {
        this.header_title = header_title;
    }

    public String getTotal_amount() {
        return total_amount;
    }

    public void setTotal_amount(String total_amount) {
        this.total_amount = total_amount;
    }

    public String getHeader_cd() {
        return header_cd;
    }

    public void setHeader_cd(String header_cd) {
        this.header_cd = header_cd;
    }
}
