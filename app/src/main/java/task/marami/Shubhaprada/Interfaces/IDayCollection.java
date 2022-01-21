package task.marami.Shubhaprada.Interfaces;

import java.util.ArrayList;
import java.util.HashMap;

import task.marami.Shubhaprada.Models.CollectionChild;
import task.marami.Shubhaprada.Models.CollectionHeaderData;
import task.marami.Shubhaprada.Models.CollectionItemData;


public interface IDayCollection {
    interface DayCollectionPresenter {
        void onLoad();
        void getCollectionByday(String Date);
    }
    interface DayCollectionView{
        void onLoadSuccess(HashMap<String, ArrayList<CollectionChild>> collectionChild, ArrayList<CollectionHeaderData> collectionHeaderData);
        void onError(String message);
        void onStartprog();
        void onStopProg();
        void onDayCollectionSuccess(ArrayList<CollectionItemData> response);
    }
}
