package task.marami.Shubhaprada.Interfaces;

import java.util.ArrayList;
import java.util.HashMap;

import task.marami.Shubhaprada.Models.BookingDetailsModel;
import task.marami.Shubhaprada.Models.CadresCountAssociates;


public interface IBookingSalesReport {
    interface presenter {
        void onLoad(String venture);
        void onSalesSearch(String venture, String id);
    }
    interface view {
        void onStartProg();
        void onStopProg();
        void OnError(String msg);
        void onSuccess(String usertype, ArrayList<String> cca, HashMap<String, ArrayList<BookingDetailsModel>> childs, ArrayList<CadresCountAssociates> ccarraylist);
    }
}
