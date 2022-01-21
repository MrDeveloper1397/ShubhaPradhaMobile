package task.marami.Shubhaprada.Interfaces;

import java.util.ArrayList;

import task.marami.Shubhaprada.Models.CollectionItemData;

public interface IAdminMenu {
    interface AdminMenuPresenter{
        void onLoad();
    }
    interface AdminMenuView{
        void onStartProg();
        void onStopProg();
        void onLoadSuccess(ArrayList<CollectionItemData> response, String paymentcol, String booking);
        void onError(String message);
        void hideCollectionList();
        void hidePayments();
    }
}
