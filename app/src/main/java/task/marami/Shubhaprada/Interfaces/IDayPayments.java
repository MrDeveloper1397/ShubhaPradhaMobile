package task.marami.Shubhaprada.Interfaces;

import java.util.ArrayList;
import java.util.HashMap;

import task.marami.Shubhaprada.Models.DayPaymentHeader;
import task.marami.Shubhaprada.Models.DayPaymentsItem;


public interface IDayPayments {
    interface DayPaymentsPresenter {
        void onLoad(String date);
    }
    interface DayPaymentsView{
        void onStartProg();
        void onStopProg();
        void onLoadSuccess(HashMap<String, ArrayList<DayPaymentsItem>> collectionChild, ArrayList<DayPaymentHeader> collectionHeaderData);
    }
}
