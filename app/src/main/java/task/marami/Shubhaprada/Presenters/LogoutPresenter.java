package task.marami.Shubhaprada.Presenters;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONException;
import org.json.JSONObject;

import task.marami.Shubhaprada.Interfaces.ILogout;
import task.marami.Shubhaprada.Utils.Contents;
import task.marami.Shubhaprada.Utils.MySingleton;
import task.marami.Shubhaprada.Utils.VolleyErrorHandler;

public class LogoutPresenter implements ILogout.presenter {
    Context context;
    ILogout.view view;

    public LogoutPresenter(Context context, ILogout.view view) {
        this.context = context;
        this.view = view;
    }


    @Override
    public void onLogout(String reportID) {
        String Url= Contents.BASE_URL+"logout?reportID="+reportID;
        ServerConnection(Url);
    }
    void ServerConnection(String Url)
    {
        view.onStartProg();
        JsonObjectRequest arrayRequest=new JsonObjectRequest(Request.Method.GET, Url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                view.onStopProg();
                try {
                    JSONObject object = response;
                    if (object.getString("status").equals("success")) {
                        view.onSuccess();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
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
