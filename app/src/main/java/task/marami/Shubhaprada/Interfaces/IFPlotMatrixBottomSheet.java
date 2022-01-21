package task.marami.Shubhaprada.Interfaces;

import java.util.ArrayList;

import task.marami.Shubhaprada.Models.ApprPassbookList;
import task.marami.Shubhaprada.Models.PlotMPlotData;
import task.marami.Shubhaprada.Models.ReservationModel;

public interface IFPlotMatrixBottomSheet {
    interface FPlotMatrixBSheetPresenter{
        void onLoad(String venture, String status, String facing, String Sector);
        void onChangePlotStatus(ReservationModel RM);
        void onApplicentDetails(ReservationModel RM);
    }
    interface FPlotMatrixBSheetView{
        void onStartProg();
        void onStopProg();
        void onLoadSuccess(ArrayList<PlotMPlotData> plotData);
        void onError(String message);
        void onChangeSuccess(String response);
        void onApplicentSuccess(ApprPassbookList data);
    }
}
