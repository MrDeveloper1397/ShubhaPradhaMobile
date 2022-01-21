package task.marami.Shubhaprada.Models;

public class CadresCountAssociates {
    String countNum,cadreName;

    public CadresCountAssociates(String countNum, String cadreName) {
        this.countNum = countNum;
        this.cadreName = cadreName;
    }

    public String getCountNum() {
        return countNum;
    }

    public void setCountNum(String countNum) {
        this.countNum = countNum;
    }

    public String getCadreName() {
        return cadreName;
    }

    public void setCadreName(String cadreName) {
        this.cadreName = cadreName;
    }
}
