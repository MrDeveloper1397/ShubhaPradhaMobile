package task.marami.Shubhaprada.Interfaces;

import java.util.ArrayList;

import task.marami.Shubhaprada.Models.CommissionData;

public interface IFCommissionApproval {

    interface FCommissinApprovalPresenter{
        void onLoad(int plist, int pblist);
        void onChangeApproval(int plist, int pblist, String remarks, String param);
    }

    interface FCommissionApprovalView{
        void onStartProg();
        void onStopProg();
        void onLoadSuccess(ArrayList<CommissionData> commissionData);
        void onChangeApprovalSuccess();
        void onError(String message);
        void onOffice();
    }
}
