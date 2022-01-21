package task.marami.Shubhaprada.Interfaces;

public interface IAdminPin {
    interface AdminPinPresenter{
        void onAuthentication(String pin);
        void onDataEnter(String val);
        void onClearData();
    }
    interface AdminPinView{
        void onStartProg();
        void onStopProg();
        void onEditOne(String val);
        void onclearOne();
        void onEditTwo(String val);
        void onclearTwo();
        void onEditThree(String val);
        void onclearThree();
        void onEditFour(String val);
        void onclearFour();
        void onSuccess();
        void onError(String message);
        void onFiledPin();
    }
}
