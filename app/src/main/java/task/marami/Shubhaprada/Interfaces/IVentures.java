package task.marami.Shubhaprada.Interfaces;


import java.util.ArrayList;

import task.marami.Shubhaprada.Models.VenturesModel;

public interface IVentures {
    interface VentureListPresenter{
        void onLoad();
    }
    interface VentureListView{
        void onStartProgress();
        void onStopProgress();
        void onSuccess(ArrayList<VenturesModel> venturesData);
        void onError(String message);
    }
}
