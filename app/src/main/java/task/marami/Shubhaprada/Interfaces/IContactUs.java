package task.marami.Shubhaprada.Interfaces;


import org.json.JSONObject;

public interface IContactUs {
    interface Contactpresenter{
        void onLoadCompanyAddress();
    }
    interface  view{
        void onStartprog();
        void onStopProg();
        void onError(String msg);
        void getCompanyAddress(JSONObject response);
    }

}
