package task.marami.Shubhaprada.Interfaces;

import android.webkit.WebView;

import task.marami.Shubhaprada.Models.ProjectsData;

public interface IWebVenture {
    interface WebVenturePresenter{
        void onLoadVenture(ProjectsData projectsData, WebView webv);
    }
    interface WebVentureView{
        void onStartProg();
        void onStopProg();
        void onWebError();
        void onLinkError();
    }
}
