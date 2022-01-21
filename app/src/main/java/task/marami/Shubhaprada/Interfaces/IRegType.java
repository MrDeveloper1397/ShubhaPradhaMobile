package task.marami.Shubhaprada.Interfaces;

import task.marami.Shubhaprada.Models.EmployeeReg;
import task.marami.Shubhaprada.Models.UserData;

public interface IRegType {
    interface RegTypePresenter{
        void onUserReg(String AgentId);
        void onEmpProcess(String id);
        void OtherReg(UserData user);
        void onEmpReg(EmployeeReg emp);
    }
    interface RegTypeView{
        void onstartprog();
        void onStopProg();
        void onUserRegSuccess(String response);
        void onEmpProcessSuccess(EmployeeReg emp,String usertype);
        void onEmpRegSuccess(String response);
        void onError(String message);
    }
}
