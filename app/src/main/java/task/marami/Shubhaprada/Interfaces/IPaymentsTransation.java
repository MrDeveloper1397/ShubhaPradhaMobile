package task.marami.Shubhaprada.Interfaces;

import java.util.ArrayList;

import task.marami.Shubhaprada.Models.PaymentTransationData;

public interface IPaymentsTransation {
    interface PaymentsTransationPresenter{
        void onLoad(String VentureCd, String UserType, String Date);
    }
    interface PaymentsTransationView{
        void onStartProg();
        void onStopProg();
        void onLoadSuccess(ArrayList<PaymentTransationData> transationData);
        void onError(String message);
    }
}
