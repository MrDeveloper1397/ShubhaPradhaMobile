package task.marami.Shubhaprada.Presenters;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import task.marami.Shubhaprada.Interfaces.IPinChange;
import task.marami.Shubhaprada.Utils.Contents;
import task.marami.Shubhaprada.Utils.MySingleton;
import task.marami.Shubhaprada.Utils.StoragePrefer;
import task.marami.Shubhaprada.Utils.VolleyErrorHandler;


public class PinChangePresenter implements IPinChange.PinChangePresenter {
    Context context;
    IPinChange.PinChangeView pinChangeView;
    private static int count;
    private static int recount;

    public PinChangePresenter(Context context, IPinChange.PinChangeView pinChangeView) {
        this.context = context;
        this.pinChangeView = pinChangeView;
        count=0;
        recount=0;
    }

    @Override
    public void onStoreNewPin(String pin) {
        StoragePrefer sc=new StoragePrefer(context);
        String Url= Contents.BASE_URL+"Change_Admin_Pin?Id="+sc.getSprString(Contents.PREF_KEY_API_TOKEN)+"&Pin=" + pin;
        ServerConnection(Url);
    }

    @Override
    public void onDataEnter(String val) {
        switch (count)
        {
            case 0:
                pinChangeView.onEditOne(val);
                count++;
                break;
            case 1:
                pinChangeView.onEditTwo(val);
                count++;
                break;
            case 2:
                pinChangeView.onEditThree(val);
                count++;
                break;
            case 3:
                pinChangeView.onEditFour(val);
                count++;
                break;
            default:reDataEnter(val);
            break;
        }
    }

    @Override
    public void onClearData() {
      if(recount==0) {
          switch (count) {
              case 4:
                  pinChangeView.onclearFour();
                  count--;
                  break;
              case 3:
                  pinChangeView.onclearThree();
                  count--;
                  break;
              case 2:
                  pinChangeView.onclearTwo();
                  count--;
                  break;
              case 1:
                  pinChangeView.onclearOne();
                  count--;
                  break;
              default:
                  break;
          }
      }
      else
      {
          switch (recount) {
              case 4:
                  pinChangeView.onclearreFour();
                  recount--;
                  break;
              case 3:
                  pinChangeView.onclearreThree();
                  recount--;
                  break;
              case 2:
                  pinChangeView.onclearreTwo();
                  recount--;
                  break;
              case 1:
                  pinChangeView.onclearreOne();
                  recount--;
                  break;
              default:
                  break;
          }
      }
    }

    @Override
    public void reDataEnter(String val) {
        switch (recount)
        {
            case 0:
                pinChangeView.onEditreOne(val);
                recount++;
                break;
            case 1:
                pinChangeView.onEditreTwo(val);
                recount++;
                break;
            case 2:
                pinChangeView.onEditreThree(val);
                recount++;
                break;
            case 3:
                pinChangeView.onEditreFour(val);
                recount++;
                break;
            default:
                break;
        }
    }

    @Override
    public void reclear() {
        pinChangeView.onclearreFour();
        pinChangeView.onclearreThree();
        pinChangeView.onclearreTwo();
        pinChangeView.onclearreOne();
        recount=0;
    }

    void ServerConnection(String Url)
    {
        pinChangeView.onStartProg();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        pinChangeView.onStopProg();
                        if (response.trim().equals("fail")) {
                            pinChangeView.onError(Contents.SERVER_RETRY);
                        } else {
                            pinChangeView.onSuccess();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                pinChangeView.onStopProg();
                pinChangeView.onError(VolleyErrorHandler.onVolleyError(error));
            }
        });
        MySingleton.getsInstance(context).getRequestQueue().add(stringRequest);
    }
}
