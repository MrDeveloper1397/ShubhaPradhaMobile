package task.marami.Shubhaprada.Presenters;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;

import org.json.JSONArray;

import task.marami.Shubhaprada.Interfaces.IVentures;
import task.marami.Shubhaprada.Models.VenturesDataAdapter;
import task.marami.Shubhaprada.Utils.Contents;
import task.marami.Shubhaprada.Utils.MySingleton;
import task.marami.Shubhaprada.Utils.VolleyErrorHandler;


public class VenturesListPresenter implements IVentures.VentureListPresenter {
    Context context;
    IVentures.VentureListView ventureListView;

    public VenturesListPresenter(Context context, IVentures.VentureListView ventureListView) {
        this.context = context;
        this.ventureListView = ventureListView;
    }
    @Override
    public void onLoad() {
        String url = Contents.BASE_URL+"getVentures.php";
        ServerConnection(url);
    }
    void ServerConnection(String url)
    {
        ventureListView.onStartProgress();
        JsonArrayRequest arrayRequest=new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                ventureListView.onStopProgress();
                if(response.isNull(0))
                {
                    ventureListView.onError("Ventures data Dosen't Exist");
                }
                else
                {
                    VenturesDataAdapter vd =new VenturesDataAdapter(response);
                    ventureListView.onSuccess(vd.getVenturesData());
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                ventureListView.onStopProgress();
                ventureListView.onError(VolleyErrorHandler.onVolleyError(error));
            }
        });
        MySingleton.getsInstance(context).getRequestQueue().add(arrayRequest);
    }
}
