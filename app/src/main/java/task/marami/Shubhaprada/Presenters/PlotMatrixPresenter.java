package task.marami.Shubhaprada.Presenters;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;

import org.json.JSONArray;

import task.marami.Shubhaprada.Interfaces.IPlotMatrix;
import task.marami.Shubhaprada.Models.PlotMFacingDataAdapter;
import task.marami.Shubhaprada.Models.PlotMatrixheaderAdapter;
import task.marami.Shubhaprada.Utils.Contents;
import task.marami.Shubhaprada.Utils.MySingleton;
import task.marami.Shubhaprada.Utils.VolleyErrorHandler;

public class PlotMatrixPresenter implements IPlotMatrix.PlotMatrixPresenter {
    Context context;
    IPlotMatrix.PlotMatrixView plotMatrixView;

    public PlotMatrixPresenter(Context context, IPlotMatrix.PlotMatrixView plotMatrixView) {
        this.context = context;
        this.plotMatrixView = plotMatrixView;
    }

    @Override
    public void onLoad() {
        String Url= Contents.BASE_URL+"PlotMatrixHeader";
        ConnectToServer(Url,"load");
    }

    @Override
    public void onLoadFacing(String venture, String Status,String Sector) {
        String Url=Contents.BASE_URL+"PlotMatrixFacingData?Venture="+venture+"&Status="+Status+"&Sector="+Sector;
        ConnectToServer(Url,"facing");
    }

    public void ConnectToServer(String Url, final String callback)
    {
        plotMatrixView.onStartProg();
        JsonArrayRequest jsonArrayRequest=new JsonArrayRequest(Request.Method.POST, Url,null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        plotMatrixView.onStopProg();
                        if (response.isNull(0)) {
                            plotMatrixView.onError("NO Data Available");
                        } else {
                            if(callback.equals("load")) {
                                PlotMatrixheaderAdapter PMHA = new PlotMatrixheaderAdapter(response);
                                plotMatrixView.onLoadSuccess(PMHA.getHeaders());
                            }
                            else {

                                PlotMFacingDataAdapter adapter=new PlotMFacingDataAdapter(response);
                                plotMatrixView.onLoadFacingSuccess(adapter.getFacingData());
                            }
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                plotMatrixView.onStopProg();
                plotMatrixView.onError(VolleyErrorHandler.onVolleyError(error));
            }
        });
        MySingleton.getsInstance(context).getRequestQueue().add(jsonArrayRequest);
    }
}
