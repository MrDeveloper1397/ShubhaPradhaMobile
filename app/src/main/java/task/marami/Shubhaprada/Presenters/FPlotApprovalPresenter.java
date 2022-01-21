package task.marami.Shubhaprada.Presenters;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;

import task.marami.Shubhaprada.Interfaces.IFPlotApproval;
import task.marami.Shubhaprada.Models.PlotCostDataAdapter;
import task.marami.Shubhaprada.Utils.Contents;
import task.marami.Shubhaprada.Utils.MySingleton;
import task.marami.Shubhaprada.Utils.StoragePrefer;
import task.marami.Shubhaprada.Utils.VolleyErrorHandler;

public class FPlotApprovalPresenter implements IFPlotApproval.FPlotApprovalPresenter{

    Context context;
    IFPlotApproval.FPlotApprovalView fPlotApprovalView;

    public FPlotApprovalPresenter(Context plotApproval, IFPlotApproval.FPlotApprovalView fPlotApprovalView) {
        this.context = plotApproval;
        this.fPlotApprovalView = fPlotApprovalView;
    }

    @Override
    public void onLoad(int plistpos,int pblistpos) {
        String passbook= Contents.utiPassbookList.get(pblistpos).getMember_id();
        String venturtecd=Contents.utiplistdata.get(plistpos).getVen_cd();
        String Url= Contents.BASE_URL+"PlotcostApprovaldata?PassbookNo="+passbook+"&VentureId="+venturtecd;
        ServerConnection(Url);
    }

    @Override
    public void onChangeApproval(int plistpos,int pblistpos,String remarks,String param) {
        String passbook=Contents.utiPassbookList.get(pblistpos).getMember_id();
        String venturtecd=Contents.utiplistdata.get(plistpos).getVen_cd();
        StoragePrefer sc=new StoragePrefer(context);
        String Url=Contents.BASE_URL+"UpdateApprovalData.php/?PassbookNo=" + passbook + "&VentureId=" + venturtecd + "&Remarks=" + remarks + "&Activity=" + param+"&ApiKey="+sc.getSprString(Contents.PREF_KEY_API_TOKEN);
        ChangeApprovalStatus(Url);
    }
    void ServerConnection(String Url)
    {
        fPlotApprovalView.onStartProg();
        JsonArrayRequest arrayRequest=new JsonArrayRequest(Request.Method.POST, Url,
                null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                fPlotApprovalView.onStopProg();
                if(response.isNull(0))
                {
                   fPlotApprovalView.onError("No Data Exist For This Passbook");
                }
                else
                {
                    PlotCostDataAdapter pcda=new PlotCostDataAdapter(response);
                    Contents.utiPlotCostData=pcda.getPlotcostdata();
                    fPlotApprovalView.onLoadSuccess();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                fPlotApprovalView.onStopProg();
                fPlotApprovalView.onError(VolleyErrorHandler.onVolleyError(error));
            }
        });
        MySingleton.getsInstance(context).getRequestQueue().add(arrayRequest);
    }
    public void ChangeApprovalStatus(String Url)
    {
        fPlotApprovalView.onStartProg();
        StringRequest request = new StringRequest(Request.Method.POST, Url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        fPlotApprovalView.onStopProg();
                        if(response.equals("Success"))
                        {
                            fPlotApprovalView.onChangeApprovalSuccess();
                        }
                        else
                        {
                            fPlotApprovalView.onError("Please Try Again Some Time");
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                fPlotApprovalView.onStopProg();
                fPlotApprovalView.onError(VolleyErrorHandler.onVolleyError(error));
            }
        });
        MySingleton.getsInstance(context).getRequestQueue().add(request);
    }
}
