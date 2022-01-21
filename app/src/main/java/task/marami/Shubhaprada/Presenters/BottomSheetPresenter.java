package task.marami.Shubhaprada.Presenters;

import android.content.Context;
import android.widget.ArrayAdapter;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import task.marami.Shubhaprada.Interfaces.IBottomSheetForm;
import task.marami.Shubhaprada.Models.ProjectsData;
import task.marami.Shubhaprada.Utils.Contents;
import task.marami.Shubhaprada.Utils.MySingleton;
import task.marami.Shubhaprada.Utils.StoragePrefer;
import task.marami.Shubhaprada.Utils.VolleyErrorHandler;


public class BottomSheetPresenter implements IBottomSheetForm.BottomSheetPresenter {
    Context btf;
    IBottomSheetForm.BottomSheetView BSV;
    int pos;
    StoragePrefer sc;

    public BottomSheetPresenter(Context btf, IBottomSheetForm.BottomSheetView BSV) {
        this.btf = btf;
        this.BSV = BSV;
    }

    @Override
    public void onLoad(ProjectsData pos) {
        ProjectsData pd = pos;
        String Sector=pd.getSector();
        String phase[]=Sector.split(",");
        ArrayAdapter<String> adapter=new ArrayAdapter<String>( btf,android.R.layout.simple_expandable_list_item_1, phase);
        BSV.onSectorRend(adapter);
    }

    @Override
    public void onSendEnquery(String plotno,String sector,String name) {
        ProjectsData pd = Contents.utiprojectsData;
        sc=new StoragePrefer(btf);
        String Url=  pd.getEnqueryLink()+"VentureId=" + pd.getProjectId() + "&SectorId=" + sector + "&MobileNo=" + sc.getSprString(Contents.PREF_KEY_CURR_MOBILE) + "&PlotNo=" + plotno + "&Name=" + name;
        ServerConnection(Url);
    }
    private void ServerConnection(String Url)
    {
        BSV.onStartProg();
        //String url=Url+"VentureId=" + Sector + "&SectorId=" + selphase + "&MobileNo=" + Phoneno + "&PlotNo=" + ploatno + "&Name=" + uname;
        StringRequest stringRequest=new StringRequest(Request.Method.POST, Url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        BSV.onstopProg();
                        if(response.equals("fail"))
                        {
                           BSV.onError(Contents.SERVER_RETRY);
                        }
                        else
                        {
                            BSV.onSuccess(response);
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                BSV.onstopProg();
                BSV.onError(VolleyErrorHandler.onVolleyError(error));
            }
        });
        MySingleton.getsInstance(btf).getRequestQueue().add(stringRequest);
    }
}
