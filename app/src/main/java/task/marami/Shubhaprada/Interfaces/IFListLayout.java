package task.marami.Shubhaprada.Interfaces;

import java.util.ArrayList;

import task.marami.Shubhaprada.Models.ProjectsData;


public interface IFListLayout {
     interface ListLayoutPresenter{
         void onLayoutsData();
     }
     interface ListLayoutView{
         void onStartProgress();
         void onStopProgress();
         void onSuccess(ArrayList<ProjectsData> projectsData);
         void onError(String message);
     }
    interface NavigationLayoutPresenter{
        void onLayoutsData();
    }
}
