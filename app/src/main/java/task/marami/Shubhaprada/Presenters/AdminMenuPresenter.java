package task.marami.Shubhaprada.Presenters;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import task.marami.Shubhaprada.Interfaces.IAdminMenu;
import task.marami.Shubhaprada.Models.CollectionItemDataAdapter;
import task.marami.Shubhaprada.Utils.Contents;
import task.marami.Shubhaprada.Utils.MySingleton;
import task.marami.Shubhaprada.Utils.StoragePrefer;
import task.marami.Shubhaprada.Utils.VolleyErrorHandler;


public class  AdminMenuPresenter implements IAdminMenu.AdminMenuPresenter {
    Context context;
    IAdminMenu.AdminMenuView adminMenuView;

    public AdminMenuPresenter(Context context, IAdminMenu.AdminMenuView adminMenuView) {
        this.context = context;
        this.adminMenuView = adminMenuView;
    }

    @Override
    public void onLoad() {
        StoragePrefer sc=new StoragePrefer(context);
        String Url= Contents.BASE_URL+"GetCollectionData?ApiKey="+sc.getSprString(Contents.PREF_KEY_API_TOKEN);
        ServerConnection(Url);
    }
    public void ServerConnection(String Url)
    {
        adminMenuView.onStartProg();
        JsonArrayRequest jsonArrayRequest=new JsonArrayRequest(Request.Method.POST, Url,null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        adminMenuView.onStopProg();
                        try {
                            JSONObject jsonObject=response.getJSONObject(0);
                            if(jsonObject.optString("USERTYPE").equals("R"))
                            {
                                adminMenuView.hideCollectionList();
                                adminMenuView.hidePayments();
                            }
                            else
                            {
                                CollectionItemDataAdapter ad=new CollectionItemDataAdapter(response);
                                adminMenuView.onLoadSuccess(ad.getCID(),ad.getPaymentcol(),ad.getBookings());
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                adminMenuView.onStopProg();
                adminMenuView.onError(VolleyErrorHandler.onVolleyError(error));
            }
        });
        MySingleton.getsInstance(context).getRequestQueue().add(jsonArrayRequest);
    }
}
