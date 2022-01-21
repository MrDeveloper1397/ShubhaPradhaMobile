package task.marami.Shubhaprada.Presenters;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import task.marami.Shubhaprada.Interfaces.IVacantList;
import task.marami.Shubhaprada.Models.PlotMatrixheaderAdapter;
import task.marami.Shubhaprada.Models.VacantListDataAdp;
import task.marami.Shubhaprada.Models.VenturesListAdp;
import task.marami.Shubhaprada.Utils.Contents;
import task.marami.Shubhaprada.Utils.MySingleton;
import task.marami.Shubhaprada.Utils.VolleyErrorHandler;

public class VacantListPresenter implements IVacantList.VacantListPresenter {
    Context context;
    IVacantList.VacantListView vacantListView;

    public VacantListPresenter(Context context, IVacantList.VacantListView vacantListView) {
        this.context = context;
        this.vacantListView = vacantListView;
    }

    @Override
    public void onVacantList(String venture, String Sector) {
        String url= Contents.BASE_URL+"GetVacantList.php?ventureid="+venture+"&sector="+Sector;
        ServerConnection(url);
    }

    @Override
    public void onLoad() {
        String Url= Contents.BASE_URL+"AvailablePlotsVenture";
        ConnectToServer(Url,"load");
    }

    void ServerConnection(String url)
    {
        vacantListView.onStartProgress();
        JsonArrayRequest arrayRequest=new JsonArrayRequest(Request.Method.POST, url, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                vacantListView.onStopProgress();
                if(response.isNull(0))
                {
                    vacantListView.onError("Available Plots Dosen't Exist For Selected Venture");
                }
                else
                {
                    VacantListDataAdp ld=new VacantListDataAdp(response);
                    vacantListView.onSuccess(ld.getProjectsData());
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                vacantListView.onStopProgress();
                vacantListView.onError(VolleyErrorHandler.onVolleyError(error));
            }
        });
        MySingleton.getsInstance(context).getRequestQueue().add(arrayRequest);
    }
    public void ConnectToServer(String Url, final String callback)
    {
        vacantListView.onStartProgress();
        JsonObjectRequest jsonArrayRequest=new JsonObjectRequest(Request.Method.GET, Url,null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        vacantListView.onStopProgress();
                            if(callback.equals("load")) {
                                PlotMatrixheaderAdapter PMHA = null;
                                VenturesListAdp avdp = null;
                                try {

                                    PMHA = new PlotMatrixheaderAdapter(response.getJSONArray("Available Plots"));
                                    avdp = new VenturesListAdp(response.getJSONArray("Ventures"));
                                    vacantListView.onLoadSuccess(PMHA.getHeaders(),avdp.getHeaders());

                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }

                            }
                            else {

                                /*PlotMFacingDataAdapter adapter=new PlotMFacingDataAdapter(response);
                                vacantListView.onLoadFacingSuccess(adapter.getFacingData());*/
                            }
                        }

                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                vacantListView.onStopProgress();
                vacantListView.onError(VolleyErrorHandler.onVolleyError(error));
            }
        });
        MySingleton.getsInstance(context).getRequestQueue().add(jsonArrayRequest);
    }
}
