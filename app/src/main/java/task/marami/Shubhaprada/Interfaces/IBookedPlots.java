package task.marami.Shubhaprada.Interfaces;

import java.util.ArrayList;

import task.marami.Shubhaprada.Models.ApprPassbookList;
import task.marami.Shubhaprada.Models.PlistApprovalCount;

public interface IBookedPlots {
    interface IBookedPlotPresenter{
        void onLoadProjectlist(String Date);
        void onbookedPassBookList(int pos, String date);
    }
    interface IBookedPlotView{
        void onStartProg();
        void onStopProg();
        void onLoadSuccess(ArrayList<PlistApprovalCount> pac);
        void onPassbookSuccess(ArrayList<ApprPassbookList> apbl);
        void onError(String message);
    }
}
