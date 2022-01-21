package task.marami.Shubhaprada.Interfaces;

import java.util.ArrayList;

import task.marami.Shubhaprada.Models.PlotMatrixHeader;
import task.marami.Shubhaprada.Models.VacantListItems;
import task.marami.Shubhaprada.Models.VentureNamesList;


public interface IVacantList {
    interface VacantListPresenter{
        void onVacantList(String venture, String Sector);
        void onLoad();
    }
    interface VacantListView{
        void onStartProgress();
        void onStopProgress();
        void onSuccess(ArrayList<VacantListItems> projectsData);
        void onLoadSuccess(ArrayList<PlotMatrixHeader> header, ArrayList<VentureNamesList> ventureNamesLists);
        void onError(String message);
    }

}
