package task.marami.Shubhaprada.Interfaces;

import java.util.ArrayList;

import task.marami.Shubhaprada.Models.ApprPassbookList;
import task.marami.Shubhaprada.Models.PlistApprovalCount;

public interface IApprovalList {
    interface ApprovalListPresenter{
        void onLoadPLClist();
        void onPassBookList(int pos);
    }
    interface ApprovalListView{
        void onStartProg();
        void onStopProg();
        void onLoadSuccess(ArrayList<PlistApprovalCount> pac);
        void onPassbookSuccess(ArrayList<ApprPassbookList> apbl);
        void onError(String message);
    }
}
