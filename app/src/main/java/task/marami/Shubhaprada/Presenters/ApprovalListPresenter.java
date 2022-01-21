package task.marami.Shubhaprada.Presenters;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;

import org.json.JSONArray;

import task.marami.Shubhaprada.ApprovalList;
import task.marami.Shubhaprada.Interfaces.IApprovalList;
import task.marami.Shubhaprada.Models.ApprPassbookDataAdapter;
import task.marami.Shubhaprada.Models.PlistApprovalCountDataAdapter;
import task.marami.Shubhaprada.Utils.Contents;
import task.marami.Shubhaprada.Utils.MySingleton;
import task.marami.Shubhaprada.Utils.VolleyErrorHandler;


public class ApprovalListPresenter implements IApprovalList.ApprovalListPresenter{
    ApprovalList context;
    IApprovalList.ApprovalListView approvalListView;

    public ApprovalListPresenter(ApprovalList context, IApprovalList.ApprovalListView approvalListView) {
        this.context = context;
        this.approvalListView = approvalListView;
    }

    @Override
    public void onLoadPLClist() {
        String Url= Contents.BASE_URL+"ApprovalProjectList.php";
        ServerConnection(Url,"PLIST");
    }

    @Override
    public void onPassBookList(int pos) {
        String Url=Contents.BASE_URL+"getApprovalData.php?VentureCd="+Contents.utiplistdata.get(pos).getVen_cd();
        ServerConnection(Url, "PBLIST");
    }
    void ServerConnection(String Url, final String callback)
    {
        approvalListView.onStartProg();
        JsonArrayRequest jsonArrayRequest=new JsonArrayRequest(Request.Method.POST, Url,null,
        new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                approvalListView.onStopProg();
                if (response.isNull(0)) {
                    approvalListView.onError("No Pending Approvals Exist");
                } else {
                    if(callback.equals("PLIST"))
                    {
                        PlistApprovalCountDataAdapter pacda=new PlistApprovalCountDataAdapter(response);
                        approvalListView.onLoadSuccess(pacda.getPlacs());
                    }
                    else
                    {
                        ApprPassbookDataAdapter apbda=new ApprPassbookDataAdapter(response);
                        approvalListView.onPassbookSuccess(apbda.getAPBLS());
                    }
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                approvalListView.onStopProg();
                approvalListView.onError(VolleyErrorHandler.onVolleyError(error));
            }
        });
        MySingleton.getsInstance(context).getRequestQueue().add(jsonArrayRequest);
    }
}
