package task.marami.Shubhaprada.Interfaces;

import java.util.ArrayList;

public interface ILogin {
    interface LoginPresenter{
        void onLogin(String userid, String password);
        void onLoadUserIDs(String mobile);
    }
    interface LoginView{
      void onStartProg();
      void onStopProg();
      void onUserSuccessList(ArrayList<String> response);
      void onSuccess(String response);
      void onError(String msg);
    }
}
