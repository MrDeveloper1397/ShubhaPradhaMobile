package task.marami.Shubhaprada.Presenters;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import org.json.JSONArray;
import org.json.JSONException;

import task.marami.Shubhaprada.Interfaces.IFHomeContainer;
import task.marami.Shubhaprada.Models.HomeDataAdapter;
import task.marami.Shubhaprada.Utils.Contents;
import task.marami.Shubhaprada.Utils.MySingleton;
import task.marami.Shubhaprada.Utils.VolleyErrorHandler;

public class FHomeContainerPresenter implements IFHomeContainer.FHomeContainerPresenter {
    Context context;
    IFHomeContainer.FHomeContainerView fHomeContainerView;

    public FHomeContainerPresenter(Context context, IFHomeContainer.FHomeContainerView fHomeContainerView) {
        this.context = context;
        this.fHomeContainerView = fHomeContainerView;
    }

    @Override
    public void onHomeData() {
        String Url= Contents.BASE_URL+"getHomeScr_data?CompanyId="+Contents.company_id;
        ServerConnection(Url);
    }

    @Override
    public void onImageLoad(String url) {

    }

    void ServerConnection(String url)
    {
        fHomeContainerView.onStartProgress();
        JsonArrayRequest arrayRequest=new JsonArrayRequest(Request.Method.POST, url, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                fHomeContainerView.onStopProgress();
                if(response.isNull(0))
                {
                    fHomeContainerView.onError("No Data Exist");
                }
                else
                {
                    HomeDataAdapter adapter=new HomeDataAdapter(response);
                    try {
                        if(response.getJSONObject(0).has("UpcomingProject")){
                            fHomeContainerView.onSuccess(adapter.getHomedata());
                        }else{
                            fHomeContainerView.onSuccessContent(response.getJSONObject(0));
                            fHomeContainerView.onError("No OnGoing Projects Exist");
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                fHomeContainerView.onStopProgress();
                fHomeContainerView.onError(VolleyErrorHandler.onVolleyError(error));
            }
        });
        MySingleton.getsInstance(context).getRequestQueue().add(arrayRequest);
    }
}
