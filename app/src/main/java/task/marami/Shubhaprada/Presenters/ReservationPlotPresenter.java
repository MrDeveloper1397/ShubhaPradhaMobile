package task.marami.Shubhaprada.Presenters;

import android.content.Context;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;

import task.marami.Shubhaprada.Interfaces.IFReservationPlot;
import task.marami.Shubhaprada.Models.ReservationDataAdapter;
import task.marami.Shubhaprada.Models.ReservationModel;
import task.marami.Shubhaprada.Utils.Contents;
import task.marami.Shubhaprada.Utils.MySingleton;
import task.marami.Shubhaprada.Utils.StoragePrefer;
import task.marami.Shubhaprada.Utils.VolleyErrorHandler;


public class ReservationPlotPresenter implements
        IFReservationPlot.ReservationPlotPresenter {
    Context context;
    IFReservationPlot.ReservationPlotView reservationPlotView;
    String stutes;

    public ReservationPlotPresenter(Context context, IFReservationPlot.ReservationPlotView reservationPlotView) {
        this.context = context;
        this.reservationPlotView = reservationPlotView;
    }

    @Override
    public void onLoad() {
        String Url = Contents.BASE_URL + "Get_Reg_Proj_data";
        ServerConnectionJson(Url);
    }

    @Override
    public void onCheckPlotStatus(ReservationModel RM) {
        StoragePrefer sc = new StoragePrefer(context);
        String Url = Contents.BASE_URL + "plotstatus.php/?VentureId=" + RM.getVenture_cd() + "&SectorId=" + RM.getSector() + "&PlotNo=" + RM.getPlotno() + "&Id=" + sc.getSprString(Contents.PREF_KEY_API_TOKEN);
        ServerConnectionStr(Url, "check");
    }

    @Override
    public void onChangePlotStatus(ReservationModel RM) {
        StoragePrefer sc=new StoragePrefer(context);
        stutes=RM.getStatus();
        String Url=Contents.BASE_URL+"changeStatus?VentureId="+RM.getVenture_cd()+"&SectorId="+RM.getSector()+"&PlotNo="+RM.getPlotno()+"&Status="+RM.getStatus()+"&Id="+sc.getSprString(Contents.PREF_KEY_API_TOKEN);
        ServerConnectionStr(Url, "change");
    }

    public void ServerConnectionJson(String Url) {
        reservationPlotView.onStartProg();
        JsonArrayRequest jsObjRequest = new JsonArrayRequest
                (Request.Method.POST, Url, null,
                        new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        reservationPlotView.onStopProg();
                        if (response.length() == 0) {
                            reservationPlotView.onError("No Projects Availabile");
                        } else {
                            ReservationDataAdapter RDA = new ReservationDataAdapter(response);
                            Log.d("res", response.toString());
                            reservationPlotView.onLoadSuccess(RDA.getReservation_data());
                        }
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        reservationPlotView.onStopProg();
                        reservationPlotView.onError(VolleyErrorHandler.onVolleyError(error));
                    }
                });
        MySingleton.getsInstance(context).getRequestQueue().add(jsObjRequest);
    }

    void ServerConnectionStr(String Url, final String callback) {
        reservationPlotView.onStartProg();
        StringRequest stringRequest=new StringRequest(Request.Method.POST, Url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                //  Toast.makeText(getApplicationContext(),response,Toast.LENGTH_SHORT).show();
                reservationPlotView.onStopProg();
                if(callback.equals("check"))
                {
                    checkPlotResult(response);
                }
                else
                {
                   changePlotResult(response);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                reservationPlotView.onStopProg();
                reservationPlotView.onError(VolleyErrorHandler.onVolleyError(error));
            }
        });
        MySingleton.getsInstance(context).getRequestQueue().add(stringRequest);
    }

    void checkPlotResult(String response)
    {
        if(response.isEmpty())
        {
            reservationPlotView.onError("Please Enter Correct Plot Number");
        }
        else if(response.equals("F")||response.equals(("N")))
        {
          reservationPlotView.onAvailable();
        }
        else if(response.equals("R"))
        {
            reservationPlotView.onReserved();
        }
        else if(response.equals("S")||response.equals("Y"))
        {
            reservationPlotView.onSold();
        }
        else if(response.equals("M"))
        {
            reservationPlotView.onMortgage();
        }
        else
        {
          reservationPlotView.onError("Invalid Data");
        }
    }
    void changePlotResult(String result)
    {
        String serverresult=result;
        if(stutes.equals("N"))
        {
            processop(serverresult);
        }
        else if(stutes.equals("R"))
        {
            Avaprocess(serverresult);
        }
        else
        {
            solprocess(serverresult);
        }
    }
    public void Avaprocess(String res)
    {
        switch (res) {
            case "Success":
                reservationPlotView.onhidelock();
                reservationPlotView.onhideReseve();
                reservationPlotView.onShowRelise();
               reservationPlotView.onChangeSuccess("Plot Is Blocked For You");
                break;
            case "fail":
                reservationPlotView.onError("Server busy Please Try Again After Some Time");
                break;
            case "Sold":
                reservationPlotView.onhidelock();
                reservationPlotView.onhideReseve();
                reservationPlotView.onhideRelise();
                reservationPlotView.onChangeSuccess("Requested Plot Already Sold");
                break;
            case "Same":
                reservationPlotView.onChangeSuccess("Requested Plot Already Blocked");
                break;
        }
    }
    public void processop(String res)
    {
        switch (res) {
            case "Success":
                reservationPlotView.onShowlock();
                reservationPlotView.onShowReseve();
                reservationPlotView.onhideRelise();
                reservationPlotView.onChangeSuccess("This Plot Is Realised");
                break;
            case "fail":
                reservationPlotView.onError("Server busy Please Try Again After Some Time");
                break;
            case "Sold":
                reservationPlotView.onhidelock();
                reservationPlotView.onhideReseve();
                reservationPlotView.onhideRelise();
                reservationPlotView.onChangeSuccess("Requested Plot Already Sold");
                break;
            case "Same":
                reservationPlotView.onChangeSuccess("Requested Plot Available");
                break;
        }
    }
    public void solprocess(String res)
    {
        switch (res) {
            case "Success":
                reservationPlotView.onhidelock();
                reservationPlotView.onhideReseve();
                reservationPlotView.onhideRelise();
                reservationPlotView.onChangeSuccess("This Plot Is Alloted To you");
                break;
            case "fail":
                reservationPlotView.onError("Server busy Please Try Again After Some Time");
                break;
            case "Reserved":
                reservationPlotView.onhidelock();
                reservationPlotView.onhideReseve();
                reservationPlotView.onhideRelise();
                reservationPlotView.onChangeSuccess("Requested Plot Blocked By Some One");
                break;
            case "Same":
                reservationPlotView.onChangeSuccess("Requested Plot Already Sold");
                break;
        }
    }
}