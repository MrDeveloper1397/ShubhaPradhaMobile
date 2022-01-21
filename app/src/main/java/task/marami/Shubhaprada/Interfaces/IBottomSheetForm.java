package task.marami.Shubhaprada.Interfaces;

import android.widget.ArrayAdapter;

import task.marami.Shubhaprada.Models.ProjectsData;

public interface IBottomSheetForm {
    interface BottomSheetPresenter{
        void onLoad(ProjectsData pos);
        void onSendEnquery(String plotno, String sector, String name);
    }
    interface BottomSheetView{
        void onStartProg();
        void onstopProg();
        void onSuccess(String message);
        void onError(String message);
        void onSectorRend(ArrayAdapter<String> sctors);
    }
}
