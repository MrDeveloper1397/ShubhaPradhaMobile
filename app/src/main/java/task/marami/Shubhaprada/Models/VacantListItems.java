package task.marami.Shubhaprada.Models;

public class VacantListItems {
    String plotno,plotarea,facing;

    public VacantListItems(String plotno, String plotarea, String facing) {
        this.plotno = plotno;
        this.plotarea = plotarea;
        this.facing = facing;
    }

    public String getPlotno() {
        return plotno;
    }

    public void setPlotno(String plotno) {
        this.plotno = plotno;
    }

    public String getPlotarea() {
        return plotarea;
    }

    public void setPlotarea(String plotarea) {
        this.plotarea = plotarea;
    }

    public String getFacing() {
        return facing;
    }

    public void setFacing(String facing) {
        this.facing = facing;
    }
}
