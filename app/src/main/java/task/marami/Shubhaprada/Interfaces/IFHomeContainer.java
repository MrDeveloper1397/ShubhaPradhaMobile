package task.marami.Shubhaprada.Interfaces;

import org.json.JSONObject;

import java.util.ArrayList;

import task.marami.Shubhaprada.Models.HomeData;

public interface IFHomeContainer {
    interface FHomeContainerPresenter{
        void onHomeData();
        void onImageLoad(String url);
    }
    interface FHomeContainerView{
        void onStartProgress();
        void onStopProgress();
        void onSuccess(ArrayList<HomeData> homeData);
        void onSuccessContent(JSONObject obj);
        void onError(String message);
    }
}
