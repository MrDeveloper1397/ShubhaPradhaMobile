package task.marami.Shubhaprada.Presenters;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;

import task.marami.Shubhaprada.Interfaces.IFCommissionApproval;
import task.marami.Shubhaprada.Models.CommissionDataAdapter;
import task.marami.Shubhaprada.Utils.Contents;
import task.marami.Shubhaprada.Utils.MySingleton;
import task.marami.Shubhaprada.Utils.StoragePrefer;
import task.marami.Shubhaprada.Utils.VolleyErrorHandler;

public class FCommissionApprovalPresenter implements IFCommissionApproval.FCommissinApprovalPresenter {
    Context context;
    IFCommissionApproval.FCommissionApprovalView fCommissionApprovalView;

    public FCommissionApprovalPresenter(Context context, IFCommissionApproval.FCommissionApprovalView fCommissionApprovalView) {
        this.context = context;
        this.fCommissionApprovalView = fCommissionApprovalView;
    }

    @Override
    public void onLoad(int plist, int pblist) {
        String passbook= Contents.utiPassbookList.get(pblist).getMember_id();
        String venturtecd=Contents.utiplistdata.get(plist).getVen_cd();
        String CommCalc=Contents.utiplistdata.get(plist).getComm_cal();
        String Url= Contents.BASE_URL+"getCommissiondata.php/?VentureId="+venturtecd+"&PassbookNo="+passbook+"&CommCalc="+CommCalc;
        ServerConnection(Url,CommCalc);
    }

    @Override
    public void onChangeApproval(int plistpos,int pblistpos,String remarks,String param) {
        String passbook=Contents.utiPassbookList.get(pblistpos).getMember_id();
        String venturtecd=Contents.utiplistdata.get(plistpos).getVen_cd();
        StoragePrefer sc=new StoragePrefer(context);
        String Url=Contents.BASE_URL+"UpdateApprovalData.php/?PassbookNo=" + passbook + "&VentureId=" + venturtecd + "&Remarks=" + remarks + "&Activity=" + param+"&ApiKey="+sc.getSprString(Contents.PREF_KEY_API_TOKEN);
        ChangeApprovalStatus(Url);
    }
    void ServerConnection(String Url, final String CommCalc)
    {
        fCommissionApprovalView.onStartProg();

        JsonArrayRequest arrayRequest1=new JsonArrayRequest(Request.Method.POST, Url, null,
                new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                fCommissionApprovalView.onStopProg();
                if(response.length()==0)
                {
                    fCommissionApprovalView.onOffice();
                }
                else
                {
                    CommissionDataAdapter CDA=new CommissionDataAdapter(response,CommCalc);
                    fCommissionApprovalView.onLoadSuccess(CDA.getCommissionData());
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                fCommissionApprovalView.onError(VolleyErrorHandler.onVolleyError(error));
            }
        });
        MySingleton.getsInstance(context).getRequestQueue().add(arrayRequest1);
    }
    public void ChangeApprovalStatus(String Url)
    {
        StringRequest request = new StringRequest(Request.Method.POST, Url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if(response.equals("Success"))
                        {
                            fCommissionApprovalView.onChangeApprovalSuccess();
                        }
                        else
                        {
                            fCommissionApprovalView.onError(Contents.SERVER_RETRY);
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                fCommissionApprovalView.onError(VolleyErrorHandler.onVolleyError(error));
            }
        });
        MySingleton.getsInstance(context).getRequestQueue().add(request);
    }
}
