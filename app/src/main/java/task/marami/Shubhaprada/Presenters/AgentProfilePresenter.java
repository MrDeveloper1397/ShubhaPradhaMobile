package task.marami.Shubhaprada.Presenters;

import android.content.Context;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONObject;

import task.marami.Shubhaprada.Interfaces.IAgentProfile;
import task.marami.Shubhaprada.Utils.Contents;
import task.marami.Shubhaprada.Utils.MySingleton;
import task.marami.Shubhaprada.Utils.StoragePrefer;
import task.marami.Shubhaprada.Utils.VolleyErrorHandler;

public class AgentProfilePresenter implements IAgentProfile.presenter {
    Context context;
    IAgentProfile.view view;
    public AgentProfilePresenter(Context context, IAgentProfile.view view){
        this.context = context;
        this.view = view;
    }
    @Override
    public void onAgentProfileLoad() {
        StoragePrefer sp = new StoragePrefer(context);
        String url = Contents.BASE_URL+"AgentProfile?CompanyId="+Contents.company_id+"&ApiKey="+sp.getSprString(Contents.PREF_KEY_API_TOKEN)+"&UserType="+sp.getSprString(Contents.PTEF_KEY_USER_TYPE);
        Log.d("Url:::", url);
        getServerConnection(url);
    }
    public void getServerConnection(String url) {
        view.onStartProg();
        JsonObjectRequest arrayRequest=new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                view.onStopProg();
                view.onSuccess(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                view.onStopProg();
                view.OnError(VolleyErrorHandler.onVolleyError(error));
            }
        });
        MySingleton.getsInstance(context).getRequestQueue().add(arrayRequest);
    }
}
