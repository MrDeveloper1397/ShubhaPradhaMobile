package task.marami.Shubhaprada.Models;

public class PlotMatrixFacingData {
    String total,extend,facing;

    public PlotMatrixFacingData(String total, String extend, String facing) {
        this.total = total;
        this.extend = extend;
        this.facing = facing;
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

    public String getFacing() {
        return facing;
    }

    public void setFacing(String facing) {
        this.facing = facing;
    }
}
