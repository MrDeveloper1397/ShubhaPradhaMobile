package task.marami.Shubhaprada.Interfaces;

import org.json.JSONObject;

public interface IAgentProfile {
    interface presenter {
        void onAgentProfileLoad();
    }
    interface view {
        void onStartProg();
        void onStopProg();
        void OnError(String msg);
        void onSuccess(JSONObject response);
    }
}
