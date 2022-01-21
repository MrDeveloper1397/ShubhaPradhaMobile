package task.marami.Shubhaprada.Presenters;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import task.marami.Shubhaprada.Interfaces.IAdminPin;
import task.marami.Shubhaprada.Utils.Contents;
import task.marami.Shubhaprada.Utils.MySingleton;
import task.marami.Shubhaprada.Utils.StoragePrefer;
import task.marami.Shubhaprada.Utils.VolleyErrorHandler;


public class AdminPinPresenter implements IAdminPin.AdminPinPresenter {
    Context context;
    IAdminPin.AdminPinView adminPinView;
    public static int count=0;

    public AdminPinPresenter(Context context, IAdminPin.AdminPinView adminPinView) {
        this.context = context;
        this.adminPinView = adminPinView;
        count=0;
    }

    @Override
    public void onAuthentication(String pin) {
        StoragePrefer sc = new StoragePrefer(context.getApplicationContext());
        String Url= Contents.BASE_URL+"Admin_pin_check?Id="+sc.getSprString(Contents.PREF_KEY_API_TOKEN)+"&Pin="+pin;
        ServerConnection(Url);
    }

    @Override
    public void onDataEnter(String val) {
        switch (count)
        {
            case 0:
                adminPinView.onEditOne(val);
                count++;
                break;
            case 1:
                adminPinView.onEditTwo(val);
                count++;
                break;
            case 2:
                adminPinView.onEditThree(val);
                count++;
                break;
            case 3:
                adminPinView.onEditFour(val);
                count++;
                adminPinView.onFiledPin();
                break;
            default:
               // adminPinView.onError("It will allows four Digits only");
                break;
        }
    }

    @Override
    public void onClearData() {
        switch (count)
        {
            case 4:
                adminPinView.onclearFour();
                count--;
                break;
            case 3:
                adminPinView.onclearThree();
                count--;
                break;
            case 2:
                adminPinView.onclearTwo();
                count--;
                break;
            case 1:
                adminPinView.onclearOne();
                count--;
                break;
            default:

                break;
        }
    }
    void clearpin()
    {
        adminPinView.onclearFour();
        adminPinView.onclearThree();
        adminPinView.onclearTwo();
        adminPinView.onclearOne();
        count=0;
    }
    void ServerConnection(String Url)
    {
        adminPinView.onStartProg();
        StringRequest stringRequest=new StringRequest(Request.Method.POST, Url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        adminPinView.onStopProg();
                        if(response.trim().equals("miss_match"))
                        {
                            clearpin();
                            adminPinView.onError("Invaild Pin");
                        }else if(response.trim().equals("Not_Admin"))
                        {
                            clearpin();
                            adminPinView.onError("You don't Have Admin Rights");
                        }
                        else if(response.trim().equals("Success"))
                        {
                            adminPinView.onSuccess();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                adminPinView.onStopProg();
                adminPinView.onError(VolleyErrorHandler.onVolleyError(error));
            }
        });
        MySingleton.getsInstance(context).getRequestQueue().add(stringRequest);
    }
}
