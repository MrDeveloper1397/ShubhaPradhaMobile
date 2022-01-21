package task.marami.Shubhaprada.Models;

public class ReservationModel {
    String venture_cd,venture_name,sector,plotno,status;

    public ReservationModel(String venture_cd, String venture_name, String sector) {
        this.venture_cd = venture_cd;
        this.venture_name = venture_name;
        this.sector = sector;
    }

    public String getVenture_cd() {
        return venture_cd;
    }

    public void setVenture_cd(String venture_cd) {
        this.venture_cd = venture_cd;
    }

    public String getVenture_name() {
        return venture_name;
    }

    public void setVenture_name(String venture_name) {
        this.venture_name = venture_name;
    }

    public String getSector() {
        return sector;
    }

    public void setSector(String sector) {
        this.sector = sector;
    }

    public String getPlotno() {
        return plotno;
    }

    public void setPlotno(String plotno) {
        this.plotno = plotno;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
