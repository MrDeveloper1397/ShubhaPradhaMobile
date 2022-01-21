package task.marami.Shubhaprada.Presenters;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;

import org.json.JSONArray;

import task.marami.Shubhaprada.Interfaces.IFListLayout;
import task.marami.Shubhaprada.Models.LayoutsDataAdapter;
import task.marami.Shubhaprada.Utils.Contents;
import task.marami.Shubhaprada.Utils.MySingleton;
import task.marami.Shubhaprada.Utils.VolleyErrorHandler;

public class NavigationLayoutPresenter
        implements IFListLayout.NavigationLayoutPresenter {
    Context context;
    IFListLayout.ListLayoutView listLayoutView;

    public NavigationLayoutPresenter(Context context, IFListLayout.ListLayoutView listLayoutView) {
        this.context = context;
        this.listLayoutView = listLayoutView;
    }

    @Override
    public void onLayoutsData() {
        String url= Contents.BASE_URL+"getProjects_data?CompanyId="+Contents.company_id;
        ServerConnection(url);
    }
    void ServerConnection( String url)
    {
        listLayoutView.onStartProgress();
        JsonArrayRequest arrayRequest=new JsonArrayRequest(Request.Method.POST, url, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                listLayoutView.onStopProgress();
                if(response.isNull(0))
                {
                    listLayoutView.onError("Projects data Dosen't Exist");
                }
                else
                {
                    LayoutsDataAdapter ld=new LayoutsDataAdapter(response);
                    listLayoutView.onSuccess(ld.getProjectsData());
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                listLayoutView.onStopProgress();
                listLayoutView.onError(VolleyErrorHandler.onVolleyError(error));
            }
        });
        MySingleton.getsInstance(context).getRequestQueue().add(arrayRequest);
    }
}
