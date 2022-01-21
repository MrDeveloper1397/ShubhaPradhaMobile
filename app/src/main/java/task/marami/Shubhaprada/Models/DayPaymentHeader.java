package task.marami.Shubhaprada.Models;

public class DayPaymentHeader {
    String Venture,Total;

    public DayPaymentHeader(String venture, String total) {
        Venture = venture;
        Total = total;
    }

    public String getVenture() {
        return Venture;
    }

    public void setVenture(String venture) {
        Venture = venture;
    }

    public String getTotal() {
        return Total;
    }

    public void setTotal(String total) {
        Total = total;
    }

}
