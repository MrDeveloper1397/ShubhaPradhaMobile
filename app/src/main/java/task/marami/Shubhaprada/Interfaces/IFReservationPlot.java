package task.marami.Shubhaprada.Interfaces;

import java.util.ArrayList;

import task.marami.Shubhaprada.Models.ReservationModel;


public interface IFReservationPlot {
    interface ReservationPlotPresenter{
        void onLoad();
        void onCheckPlotStatus(ReservationModel RM);
        void onChangePlotStatus(ReservationModel RM);
    }
    interface ReservationPlotView{
        void onStartProg();
        void onStopProg();
        void onLoadSuccess(ArrayList<ReservationModel> reservation_models);
        void onChangeSuccess(String response);
        void onError(String message);
        void onAvailable();
        void onReserved();
        void onMortgage();
        void onSold();
        void onShowlock();
        void onShowReseve();
        void onShowRelise();
        void onhidelock();
        void onhideReseve();
        void onhideRelise();
    }
}
