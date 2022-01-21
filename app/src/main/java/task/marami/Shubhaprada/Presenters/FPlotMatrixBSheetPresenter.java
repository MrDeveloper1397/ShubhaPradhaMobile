package task.marami.Shubhaprada.Presenters;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import task.marami.Shubhaprada.Interfaces.IFPlotMatrixBottomSheet;
import task.marami.Shubhaprada.Models.ApprPassbookList;
import task.marami.Shubhaprada.Models.PlotMPlotDataAdapter;
import task.marami.Shubhaprada.Models.ReservationModel;
import task.marami.Shubhaprada.Utils.Contents;
import task.marami.Shubhaprada.Utils.MySingleton;
import task.marami.Shubhaprada.Utils.StoragePrefer;
import task.marami.Shubhaprada.Utils.VolleyErrorHandler;


public class FPlotMatrixBSheetPresenter
        implements IFPlotMatrixBottomSheet.FPlotMatrixBSheetPresenter {
    Context context;
    IFPlotMatrixBottomSheet.FPlotMatrixBSheetView view;

    public FPlotMatrixBSheetPresenter(Context context, IFPlotMatrixBottomSheet.FPlotMatrixBSheetView view) {
        this.context = context;
        this.view = view;
    }

    @Override
    public void onLoad(String venture, String status, String facing,String Sector) {
        String Url= Contents.BASE_URL+"PlotMatrixPlotData?Venture="+venture+"&Status="+status+"&Facing="+facing+"&Sector="+Sector;
        ConnectToServer(Url, "load");
    }

    @Override
    public void onChangePlotStatus(ReservationModel RM) {
        StoragePrefer sc=new StoragePrefer(context);
        //stutes=RM.getStatus();
        String Url=Contents.BASE_URL+"changeStatus?VentureId="+RM.getVenture_cd()+"&SectorId="+RM.getSector()+"&PlotNo="+RM.getPlotno()+"&Status="+RM.getStatus()+"&Id="+sc.getSprString(Contents.PREF_KEY_API_TOKEN);
        ServerConnectionStr(Url, "change");
    }

    @Override
    public void onApplicentDetails(ReservationModel RM) {
        String Url=Contents.BASE_URL+"plotmatrix_applicentdata?Venture="+RM.getVenture_cd()+"&Status="+RM.getStatus()+"&PlotNo="+RM.getPlotno()+"&Sector="+RM.getSector();
        ConnectToServer(Url, "applicent");
    }

    public void ConnectToServer(String Url, final String callback)
    {
        view.onStartProg();
        JsonArrayRequest jsonArrayRequest=new JsonArrayRequest(Request.Method.POST, Url,null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        view.onStopProg();
                        if (response.isNull(0)) {
                            view.onError("NO Data Available");
                        } else {
                            if(callback.equals("load")) {
                                PlotMPlotDataAdapter PMHA = new PlotMPlotDataAdapter(response);
                                view.onLoadSuccess(PMHA.getFacingData());
                            }
                            else
                            {
                                try {
                                    JSONObject obj=response.getJSONObject(0);
                                    ApprPassbookList data=new ApprPassbookList(obj.getString("Pbno"),
                                            obj.getString("ApplicantName"),obj.getString("DateJoin"),obj.getString("PlotApproval")
                                    ,obj.getString("PlotCostApproval"),obj.getString("CommissionApproval"),obj.getString("DiscountApproval"));
                                    view.onApplicentSuccess(data);
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                view.onStopProg();
               view.onError(VolleyErrorHandler.onVolleyError(error));
            }
        });
        MySingleton.getsInstance(context).getRequestQueue().add(jsonArrayRequest);
    }
    void ServerConnectionStr(String Url, final String callback) {
        view.onStartProg();
        StringRequest stringRequest=new StringRequest(Request.Method.POST, Url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                //  Toast.makeText(getApplicationContext(),response,Toast.LENGTH_SHORT).show();
                view.onStopProg();
                view.onChangeSuccess(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                view.onStopProg();
                view.onError(VolleyErrorHandler.onVolleyError(error));
            }
        });
        MySingleton.getsInstance(context).getRequestQueue().add(stringRequest);
    }
}
