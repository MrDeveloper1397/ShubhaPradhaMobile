package task.marami.Shubhaprada.Presenters;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

import task.marami.Shubhaprada.Interfaces.IDayCollection;
import task.marami.Shubhaprada.Models.CollectionChild;
import task.marami.Shubhaprada.Models.CollectionHeaderData;
import task.marami.Shubhaprada.Models.CollectionItemData;
import task.marami.Shubhaprada.Models.DayCollectionByDateAdapter;
import task.marami.Shubhaprada.Utils.Contents;
import task.marami.Shubhaprada.Utils.MySingleton;
import task.marami.Shubhaprada.Utils.StoragePrefer;
import task.marami.Shubhaprada.Utils.VolleyErrorHandler;


public class DayCollectionPresenter implements IDayCollection.DayCollectionPresenter {
    Context context;
    IDayCollection.DayCollectionView view;
    ArrayList<CollectionItemData> collectionItemData = new ArrayList<>();
    ArrayList<CollectionHeaderData> collectionHeaderData = new ArrayList<>();
    ArrayList<String> ventureList=new ArrayList<>();

    HashMap<String, ArrayList<CollectionChild>> collectionChild = new HashMap<String, ArrayList<CollectionChild>>();

    String venture, ventureCd;

    public DayCollectionPresenter(Context context, IDayCollection.DayCollectionView view) {
        this.context = context;
        this.view = view;
    }

    @Override
    public void onLoad() {
        String venture;
        collectionItemData=new ArrayList<>();
        collectionHeaderData=new ArrayList<>();
        ventureList=new ArrayList<>();
        collectionItemData = Contents.uticolitemdata;
        if (collectionItemData.size() != 0) {
            collectionItemData = Contents.uticolitemdata;
            for(int i=0;i<collectionItemData.size();i++)
            {
                ArrayList<CollectionChild> data = new ArrayList<CollectionChild>();
                double tot = 0.0;
                if(!ventureList.contains(collectionItemData.get(i).getVenture_cd()))
                {
                    venture=collectionItemData.get(i).getVenture_cd();
                    ventureList.add(venture);
                    for(int j=0;j<collectionItemData.size();j++)
                    {
                        if(venture.equals(collectionItemData.get(j).getVenture_cd()))
                        {
                            if (collectionItemData.get(j).getType_acount().equals("1000")) {
                                CollectionChild cc = new CollectionChild(collectionItemData.get(j).getType_acount(), "Cash", collectionItemData.get(j).getVenture_collection());
                                tot += Double.parseDouble(collectionItemData.get(j).getVenture_collection());
                                data.add(cc);
                            } else if (collectionItemData.get(j).getType_acount().equals("10000")) {
                                CollectionChild cc = new CollectionChild(collectionItemData.get(j).getType_acount(), "Adjustment", collectionItemData.get(j).getVenture_collection());
                                tot += Double.parseDouble(collectionItemData.get(j).getVenture_collection());
                                data.add(cc);
                            } else {
                                CollectionChild cc = new CollectionChild(collectionItemData.get(j).getType_acount(), "Bank", collectionItemData.get(j).getVenture_collection());
                                tot += Double.parseDouble(collectionItemData.get(j).getVenture_collection());
                                data.add(cc);
                            }
                        }
                    }
                    CollectionHeaderData ch = new CollectionHeaderData(venture, collectionItemData.get(i).getVenture_name(), String.valueOf(tot));
                    collectionHeaderData.add(ch);
                    collectionChild.put(collectionItemData.get(i).getVenture_name(), data);
                    view.onLoadSuccess(collectionChild, collectionHeaderData);
                }
            }
            }

            else {
                view.onError("Day Collection 0.0");
            }
     }

    @Override
    public void getCollectionByday(String Date) {
        StoragePrefer sc=new StoragePrefer(context);
        String Url=Contents.BASE_URL+"getDaycollectionByDate?ApiKey="+sc.getSprString(Contents.PREF_KEY_API_TOKEN)+"&date="+Date;
        ServerConnection(Url);
    }
    public void ServerConnection(String Url)
    {
        JsonArrayRequest jsonArrayRequest=new JsonArrayRequest(Request.Method.POST, Url,null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        view.onStopProg();
                        if(!response.isNull(0)) {
                            try {
                                JSONObject jsonObject = response.getJSONObject(0);
                                DayCollectionByDateAdapter ad = new DayCollectionByDateAdapter(response);
                                view.onDayCollectionSuccess(ad.getCID());
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                        else
                        {
                            view.onError("Collection 0.0");
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
