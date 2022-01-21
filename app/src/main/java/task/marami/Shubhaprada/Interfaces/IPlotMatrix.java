package task.marami.Shubhaprada.Interfaces;

import java.util.ArrayList;

import task.marami.Shubhaprada.Models.PlotMatrixFacingData;
import task.marami.Shubhaprada.Models.PlotMatrixHeader;

public interface IPlotMatrix {
    interface PlotMatrixPresenter{
        void onLoad();
        void onLoadFacing(String venture, String Status, String Sector);

    }
    interface PlotMatrixView{
        void onStartProg();
        void onStopProg();
        void onStausVisable();
        void onLoadFacingSuccess(ArrayList<PlotMatrixFacingData> PMFD);
        void onLoadSuccess(ArrayList<PlotMatrixHeader> header);
        void onError(String msg);
    }
}
