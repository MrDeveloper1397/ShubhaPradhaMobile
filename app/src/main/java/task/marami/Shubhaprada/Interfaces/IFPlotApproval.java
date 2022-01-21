package task.marami.Shubhaprada.Interfaces;

public interface IFPlotApproval {
    interface FPlotApprovalPresenter{
        void onLoad(int plist, int pblist);
        void onChangeApproval(int plist, int pblist, String remarks, String param);
    }
    interface FPlotApprovalView{
        void onStartProg();
        void onStopProg();
        void onLoadSuccess();
        void onChangeApprovalSuccess();
        void onError(String message);
    }
}
