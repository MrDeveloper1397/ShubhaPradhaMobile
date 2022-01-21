package task.marami.Shubhaprada.Interfaces;

public interface IPinChange {
    interface PinChangePresenter{
        void onStoreNewPin(String pin);
        void onDataEnter(String val);
        void onClearData();
        void reDataEnter(String val);
        void reclear();
    }
    interface PinChangeView{
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
        void onEditreOne(String val);
        void onclearreOne();
        void onEditreTwo(String val);
        void onclearreTwo();
        void onEditreThree(String val);
        void onclearreThree();
        void onEditreFour(String val);
        void onclearreFour();
    }
}
