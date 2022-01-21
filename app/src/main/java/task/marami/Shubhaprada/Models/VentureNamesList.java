package task.marami.Shubhaprada.Models;

public class VentureNamesList {
    String venturecd,venShortName;

    public String getVenturecd() {
        return venturecd;
    }

    public String getVenShortName() {
        return venShortName;
    }

    public VentureNamesList(String venturecd, String venShortName) {
        this.venturecd = venturecd;
        this.venShortName = venShortName;
    }
}
