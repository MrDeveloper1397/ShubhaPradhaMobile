package task.marami.Shubhaprada.Models;

public class PlotMatrixHeader {
    String ventureCd,total,extend,status,sector,ventureName;

    public PlotMatrixHeader(String ventureCd, String total, String extend, String status,String sector,String ventureName) {
        this.ventureCd = ventureCd;
        this.total = total;
        this.extend = extend;
        this.status = status;
        this.sector = sector;
        this.ventureName = ventureName;
    }

    public String getVentureName() {
        return ventureName;
    }

    public String getVentureCd() {
        return ventureCd;
    }

    public void setVentureCd(String ventureCd) {
        this.ventureCd = ventureCd;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public String getExtend() {
        return extend;
    }

    public void setExtend(String extend) {
        this.extend = extend;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getSector() {
        return sector;
    }

    public void setSector(String sector) {
        this.sector = sector;
    }
}
