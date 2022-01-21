package task.marami.Shubhaprada.Presenters;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import task.marami.Shubhaprada.Interfaces.IDayPayments;
import task.marami.Shubhaprada.Models.PaymentDataAdapter;
import task.marami.Shubhaprada.Utils.Contents;
import task.marami.Shubhaprada.Utils.MySingleton;
import task.marami.Shubhaprada.Utils.StoragePrefer;
import task.marami.Shubhaprada.Utils.VolleyErrorHandler;

public class DayPaymentPresenter implements IDayPayments.DayPaymentsPresenter {
    Context context;
    IDayPayments.DayPaymentsView view;

    public DayPaymentPresenter(Context context, IDayPayments.DayPaymentsView view) {
        this.context = context;
        this.view = view;
    }

    @Override
    public void onLoad(String date) {
        StoragePrefer sc=new StoragePrefer(context);
        String Url= Contents.BASE_URL+"GetPaymentsData?ApiKey="+sc.getSprString(Contents.PREF_KEY_API_TOKEN)+"&date="+date;
        ServerConnection(Url);
    }
    public void ServerConnection(String Url)
    {
        view.onStartProg();
        JsonArrayRequest jsonArrayRequest=new JsonArrayRequest(Request.Method.POST, Url,null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                      view.onStopProg();
                      if(!response.isNull(0)) {
                          try {
                              JSONObject jsonObject = response.getJSONObject(0);
                              if (jsonObject.optString("USERTYPE").equals("R")) {
                                  //adminMenuView.hideCollectionList();
                                  // adminMenuView.hidePayments();
                              } else {
                                  PaymentDataAdapter PA = new PaymentDataAdapter(response);
                                  view.onLoadSuccess(PA.getHas_header(), PA.getHeader());
                              }
                          } catch (JSONException e) {
                              e.printStackTrace();
                          }
                      }
                      else
                      {
                          //Toast.makeText(context.getApplicationContext(), "Payments 0.0", Toast.LENGTH_LONG).show();
                      }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                   view.onStopProg();
                VolleyErrorHandler.onVolleyError(error);
            }
        });
        MySingleton.getsInstance(context).getRequestQueue().add(jsonArrayRequest);
    }
}
