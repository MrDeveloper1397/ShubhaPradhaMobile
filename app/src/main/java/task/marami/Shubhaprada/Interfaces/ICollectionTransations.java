package task.marami.Shubhaprada.Interfaces;

import java.util.ArrayList;

import task.marami.Shubhaprada.Models.CollectionTransationData;

public interface ICollectionTransations {
    interface CollectionTransationPresenter{
        void onLoad(String venturecd, String acctype, String date);
    }
    interface CollectionTransationView{
        void onStartProg();
        void onStopProg();
        void onLoadSuccess(ArrayList<CollectionTransationData> transationData);
        void onError(String message);
    }
}
