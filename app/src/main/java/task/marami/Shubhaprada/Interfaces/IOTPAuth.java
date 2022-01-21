package task.marami.Shubhaprada.Interfaces;

public interface IOTPAuth {
    interface OTPAuthPresenter{
        void validation(String userOtp, String fromServer);
        void reSendOtp(String Pin, String apikey);
    }
    interface OTPAuthView{
        void onStartProgress();
        void onStopProgress();
        void onGoHome();
        void onError(String message);
        void onResendSuccess();
    }
}
