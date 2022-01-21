package task.marami.Shubhaprada.Models;

public class VenturesModel {
    String venturecd,venturename,plot,unitcd;

    public VenturesModel(String venturecd, String venturename, String plot, String unitcd) {
        this.venturecd = venturecd;
        this.venturename = venturename;
        this.plot = plot;
        this.unitcd = unitcd;
    }

    public String getVenturecd() {
        return venturecd;
    }

    public void setVenturecd(String venturecd) {
        this.venturecd = venturecd;
    }

    public String getVenturename() {
        return venturename;
    }

    public void setVenturename(String venturename) {
        this.venturename = venturename;
    }

    public String getPlot() {
        return plot;
    }

    public void setPlot(String plot) {
        this.plot = plot;
    }

    public String getUnitcd() {
        return unitcd;
    }

    public void setUnitcd(String unitcd) {
        this.unitcd = unitcd;
    }
}
