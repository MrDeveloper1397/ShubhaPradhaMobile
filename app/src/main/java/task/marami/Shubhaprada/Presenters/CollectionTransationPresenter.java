package task.marami.Shubhaprada.Presenters;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;

import org.json.JSONArray;

import task.marami.Shubhaprada.Interfaces.ICollectionTransations;
import task.marami.Shubhaprada.Models.CollectionTransationDataAdapter;
import task.marami.Shubhaprada.Utils.Contents;
import task.marami.Shubhaprada.Utils.MySingleton;
import task.marami.Shubhaprada.Utils.VolleyErrorHandler;

public class CollectionTransationPresenter implements
        ICollectionTransations.CollectionTransationPresenter {
    Context context;
    ICollectionTransations.CollectionTransationView view;

    public CollectionTransationPresenter(Context context, ICollectionTransations.CollectionTransationView view) {
        this.context = context;
        this.view = view;
    }

    @Override
    public void onLoad(String venturecd, String acctype, String date) {
        String Url= Contents.BASE_URL+"getCollectionTransations?venture="+venturecd+"&acctype="+acctype+"&date="+date;
        ServerConnection(Url);
    }
    void ServerConnection(String url)
    {
        view.onStartProg();
        JsonArrayRequest arrayRequest=new JsonArrayRequest(Request.Method.POST, url, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
              view.onStopProg();
                if(response.isNull(0))
                {
                    view.onError("No Transactions Exist This Date");
                }
                else
                {
                    CollectionTransationDataAdapter ld=new CollectionTransationDataAdapter(response);
                    view.onLoadSuccess(ld.getTransationData());
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                view.onStopProg();
                view.onError(VolleyErrorHandler.onVolleyError(error));
            }
        });
        MySingleton.getsInstance(context).getRequestQueue().add(arrayRequest);
    }
}
