package task.marami.Shubhaprada.Presenters;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import task.marami.Shubhaprada.Interfaces.IOTPAuth;
import task.marami.Shubhaprada.Utils.Contents;
import task.marami.Shubhaprada.Utils.MySingleton;
import task.marami.Shubhaprada.Utils.StoragePrefer;
import task.marami.Shubhaprada.Utils.VolleyErrorHandler;


public class OTPPresenter implements IOTPAuth.OTPAuthPresenter {
    Context context;
    IOTPAuth.OTPAuthView otpAuthView;
    StoragePrefer sc;

    public OTPPresenter(Context context, IOTPAuth.OTPAuthView otpAuthView) {
        this.context = context;
        this.otpAuthView = otpAuthView;
    }

    @Override
    public void validation(String userOtp, String fromServer) {
        if(userOtp.equals(fromServer))
        {
            sc=new StoragePrefer(context.getApplicationContext());
            sc.sprStoreString(Contents.PTEF_KEY_USER_TYPE, "Employee");
            sc.sprStoreBoolean(Contents.PREF_KEY_AUTH_VALUE, true);
            otpAuthView.onGoHome();
        }
        else
        {
            otpAuthView.onError("Please Enter Correct Pin Number");
        }
    }

    @Override
    public void reSendOtp(String pin,String apikey) {
        String Url= Contents.BASE_URL+"resendotp?Id="+apikey+"&Otp="+pin;

        ServerConnection(context, Url);
    }
    void ServerConnection(final Context context, String Url)
    {
        otpAuthView.onStartProgress();
        RequestQueue queue= Volley.newRequestQueue(context);
        StringRequest stringRequest=new StringRequest(Request.Method.POST, Url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        otpAuthView.onStopProgress();
                          if(response.equals("success"))
                          {
                           otpAuthView.onResendSuccess();
                          }
                          else
                          {
                              otpAuthView.onError(Contents.SERVER_RETRY);
                          }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                otpAuthView.onStopProgress();
                otpAuthView.onError(VolleyErrorHandler.onVolleyError(error));
            }
        });
        MySingleton.getsInstance(context).getRequestQueue().add(stringRequest);
    }
}
