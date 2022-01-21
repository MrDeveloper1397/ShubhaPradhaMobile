package task.marami.Shubhaprada.Presenters;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import org.json.JSONArray;
import task.marami.Shubhaprada.Interfaces.IPaymentsTransation;
import task.marami.Shubhaprada.Models.PaymentsTransactionDataAdp;
import task.marami.Shubhaprada.Utils.Contents;
import task.marami.Shubhaprada.Utils.MySingleton;
import task.marami.Shubhaprada.Utils.VolleyErrorHandler;

public class PaymentsTransationPresenter
        implements IPaymentsTransation.PaymentsTransationPresenter {
    Context context;
    IPaymentsTransation.PaymentsTransationView view;

    public PaymentsTransationPresenter(Context context, IPaymentsTransation.PaymentsTransationView view) {
        this.context = context;
        this.view = view;
    }

    @Override
    public void onLoad(String VentureCd, String UserType, String Date) {
        String Url= Contents.BASE_URL+"getPaymentTransations?date="+Date+"&VentureCd="+VentureCd+"&AccType="+UserType;
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
                            if(response.isNull(0))
                            {
                                view.onError("No Transactions Found");
                            }
                            else
                           {
                               PaymentsTransactionDataAdp adpter=new PaymentsTransactionDataAdp(response);
                               view.onLoadSuccess(adpter.getTransationData());
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
