package task.marami.Shubhaprada.Models;

public class PlotMPlotData {
    String plotno,plotarea;

    public PlotMPlotData(String plotno, String plotarea) {
        this.plotno = plotno;
        this.plotarea = plotarea;
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
}
