package task.marami.Shubhaprada.Presenters;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import task.marami.Shubhaprada.Interfaces.IContactUs;
import task.marami.Shubhaprada.Utils.Contents;
import task.marami.Shubhaprada.Utils.MySingleton;
import task.marami.Shubhaprada.Utils.StoragePrefer;
import task.marami.Shubhaprada.Utils.VolleyErrorHandler;


public class ContactUsPresernter implements IContactUs.Contactpresenter {
    Context context;
    IContactUs.view contactview;

    public ContactUsPresernter(Context context, IContactUs.view contactview) {
        this.context = context;
        this.contactview = contactview;
    }
    @Override
    public void onLoadCompanyAddress() {
        StoragePrefer sc=new StoragePrefer(context);
        String Url= Contents.BASE_URL+"getCompanyAddress.php";
        ServerConnection(Url);
    }
    public void ServerConnection(String Url)
    {
        JsonArrayRequest jsonArrayRequest=new JsonArrayRequest(Request.Method.POST, Url,null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        contactview.onStopProg();
                        if(!response.isNull(0)) {
                            try {
                                JSONObject jsonObject = response.getJSONObject(0);
                                contactview.getCompanyAddress(jsonObject);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                        else
                        {
                            contactview.onError("No Address Found");
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                contactview.onStopProg();
                contactview.onError(VolleyErrorHandler.onVolleyError(error));
            }
        });
        MySingleton.getsInstance(context).getRequestQueue().add(jsonArrayRequest);
    }
}
