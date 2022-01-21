package task.marami.Shubhaprada.Interfaces;

import java.util.ArrayList;

import task.marami.Shubhaprada.Models.ApprPassbookList;
import task.marami.Shubhaprada.Models.PlistApprovalCount;


public interface IMember_Approved_List {
    interface Member_Approved_Presenter{
        void onLoadPLClist();
        void onPassBookList(int pos);
    }
    interface Member_Approved_View{
        void onStartProg();
        void onStopProg();
        void onLoadSuccess(ArrayList<PlistApprovalCount> pac);
        void onPassbookSuccess(ArrayList<ApprPassbookList> apbl);
        void onError(String message);
    }
}
