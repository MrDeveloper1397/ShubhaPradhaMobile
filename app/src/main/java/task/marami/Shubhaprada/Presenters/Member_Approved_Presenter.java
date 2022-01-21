package task.marami.Shubhaprada.Presenters;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import org.json.JSONArray;

import task.marami.Shubhaprada.Interfaces.IMember_Approved_List;
import task.marami.Shubhaprada.Models.ApprPassbookDataAdapter;
import task.marami.Shubhaprada.Models.PlistApprovalCountDataAdapter;
import task.marami.Shubhaprada.Utils.Contents;
import task.marami.Shubhaprada.Utils.MySingleton;
import task.marami.Shubhaprada.Utils.VolleyErrorHandler;

public class Member_Approved_Presenter implements IMember_Approved_List.Member_Approved_Presenter {
   Context context;
   IMember_Approved_List.Member_Approved_View view;

    public Member_Approved_Presenter(Context context, IMember_Approved_List.Member_Approved_View view) {
        this.context = context;
        this.view = view;
    }

    @Override
    public void onLoadPLClist() {
        String Url= Contents.BASE_URL+"ApprovedProjectsList";
        ServerConnection(Url,"PLIST");
    }

    @Override
    public void onPassBookList(int pos) {
        String Url=Contents.BASE_URL+"getApprovedData?VentureCd="+Contents.utiplistdata.get(pos).getVen_cd();
        ServerConnection(Url, "PBLIST");
    }
    void ServerConnection(String Url, final String callback)
    {
        view.onStartProg();

        JsonArrayRequest jsonArrayRequest=new JsonArrayRequest(Request.Method.POST, Url,null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        view.onStopProg();
                        if (response.isNull(0)) {
                            view.onError("NO Approved Members Data Exist");
                        } else {
                            if(callback.equals("PLIST"))
                            {
                                PlistApprovalCountDataAdapter pacda=new PlistApprovalCountDataAdapter(response);
                                view.onLoadSuccess(pacda.getPlacs());
                            }
                            else
                            {
                                ApprPassbookDataAdapter apbda=new ApprPassbookDataAdapter(response);
                                view.onPassbookSuccess(apbda.getAPBLS());
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
}
