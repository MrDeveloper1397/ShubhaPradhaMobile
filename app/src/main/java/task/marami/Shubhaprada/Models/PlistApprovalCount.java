package task.marami.Shubhaprada.Models;

public class PlistApprovalCount {
    String appr_count,ven_cd,ven_name,unit_cd,plot_type,comm_cal;

    public PlistApprovalCount(String appr_count, String ven_cd, String ven_name, String unit_cd, String plot_type, String comm_cal) {
        this.appr_count = appr_count;
        this.ven_cd = ven_cd;
        this.ven_name = ven_name;
        this.unit_cd = unit_cd;
        this.plot_type = plot_type;
        this.comm_cal = comm_cal;
    }

    public String getAppr_count() {
        return appr_count;
    }

    public void setAppr_count(String appr_count) {
        this.appr_count = appr_count;
    }

    public String getVen_cd() {
        return ven_cd;
    }

    public void setVen_cd(String ven_cd) {
        this.ven_cd = ven_cd;
    }

    public String getVen_name() {
        return ven_name;
    }

    public void setVen_name(String ven_name) {
        this.ven_name = ven_name;
    }

    public String getUnit_cd() {
        return unit_cd;
    }

    public void setUnit_cd(String unit_cd) {
        this.unit_cd = unit_cd;
    }

    public String getPlot_type() {
        return plot_type;
    }

    public void setPlot_type(String plot_type) {
        this.plot_type = plot_type;
    }

    public String getComm_cal() {
        return comm_cal;
    }

    public void setComm_cal(String comm_cal) {
        this.comm_cal = comm_cal;
    }
}
