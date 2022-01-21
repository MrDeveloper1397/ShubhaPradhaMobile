package task.marami.Shubhaprada.Interfaces;

public interface ILogout {
    interface  presenter{
        void onLogout(String reportID);
    }
    interface view {
        void onStartProg();
        void onStopProg();
        void OnError(String msg);
        void onSuccess();
    }
}
