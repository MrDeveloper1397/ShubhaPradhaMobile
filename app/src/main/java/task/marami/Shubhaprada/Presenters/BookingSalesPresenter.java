package task.marami.Shubhaprada.Presenters;

import android.content.Context;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

import task.marami.Shubhaprada.Interfaces.IBookingSalesReport;
import task.marami.Shubhaprada.Models.BookingDetailsModel;
import task.marami.Shubhaprada.Models.CadresCountAssociates;
import task.marami.Shubhaprada.Utils.Contents;
import task.marami.Shubhaprada.Utils.MySingleton;
import task.marami.Shubhaprada.Utils.StoragePrefer;
import task.marami.Shubhaprada.Utils.VolleyErrorHandler;

public class BookingSalesPresenter implements IBookingSalesReport.presenter {
    IBookingSalesReport.view view;
    Context context;
    private JSONArray childArray;
    ArrayList<BookingDetailsModel> Ats= new ArrayList<>();
    ArrayList<CadresCountAssociates> ccarraylist= new ArrayList<>();
    ArrayList<JSONArray> childs = new ArrayList<>();
    ArrayList<String> cadres = new ArrayList<>();
    HashMap<String, ArrayList<BookingDetailsModel>> _listchild = new HashMap<String, ArrayList<BookingDetailsModel>>();

    public BookingSalesPresenter(Context context, IBookingSalesReport.view view) {
        this.context = context;
        this.view = view;
    }

    @Override
    public void onLoad(String venture) {
        StoragePrefer sp = new StoragePrefer(context);
        String url = Contents.BASE_URL + "getAssociativeSales?reportID="+sp.getSprString(Contents.PTEF_KEY_USERID)+"&UserType=" + sp.getSprString(Contents.PTEF_KEY_USER_TYPE)+"&VentureCode="+venture+"&AgentID="+sp.getSprString(Contents.PTEF_KEY_USERID);
        Log.d("Url:::", url);
        getServerConnection(url,"salestree");
    }

    @Override
    public void onSalesSearch(String venture, String id) {
        StoragePrefer sp = new StoragePrefer(context);
        String url = Contents.BASE_URL + "AssociativeSalesEmp?reportID="+sp.getSprString(Contents.PTEF_KEY_USERID)+"&UserType=" + sp.getSprString(Contents.PTEF_KEY_USER_TYPE)+"&VentureCode="+venture+"&AgentID="+id;
        Log.d("Url:::", url);
        getServerConnection(url,"salesemp");
    }

    public void getServerConnection(String url, final String method) {
        view.onStartProg();
        JsonObjectRequest arrayRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                view.onStopProg();
                ccarraylist.clear();
                try {
                    if(method.equals("salestree")) {
                        if (response.getString("status").equals("success")) {
                            JSONObject treeObject = response.getJSONObject("result");
                            JSONArray arr_assocaiteCadre = treeObject.getJSONArray("CadreSalesCount");
                            for (int i= 0; i < arr_assocaiteCadre.length(); i++) {
                                JSONObject objCount = arr_assocaiteCadre.getJSONObject(i);
                                CadresCountAssociates cca = new CadresCountAssociates(objCount.getString("count"), objCount.getString("Cadre"));
                                ccarraylist.add(cca);
                                cadres.add(objCount.getString("Cadre"));
                                childArray = objCount.getJSONArray("childs");
                                for (int j = 0; j < childArray.length(); j++) {
                                    JSONObject objchilds = childArray.getJSONObject(j);
                                    BookingDetailsModel attw = new BookingDetailsModel(objchilds.getString("AgentCd"),
                                            objchilds.getString("PlotNo"), objchilds.getString("PlotArea"),
                                            objchilds.getString("MemberId"), objchilds.getString("Name"),objchilds.getString("Sector"),objchilds.getString("AssociateName"));
                                    Ats.add(attw);
                                }
                                childs.add(childArray);
                                ArrayList<BookingDetailsModel> listdata = new ArrayList<BookingDetailsModel>();
                                JSONArray jArray = (JSONArray)childArray;
                                if (jArray != null) {
                                    for (int ij=0;ij<jArray.length();ij++){
                                        JSONObject obj1 = jArray.getJSONObject(ij);
                                        //listdata.add(obj1.getString("Name"));
                                        listdata.add(new BookingDetailsModel(obj1.getString("AgentCd"),
                                                obj1.getString("PlotNo"), obj1.getString("PlotArea"),
                                                obj1.getString("MemberId"), obj1.getString("Name"),obj1.getString("Sector"),obj1.getString("AssociateName")));
                                    }
                                }

                                _listchild.put(cca.getCadreName(), listdata);
                                view.onSuccess("user",cadres,_listchild,ccarraylist);
                            }
                        } else if (response.getString("status").equals("fail")) {
                            view.OnError("No Data Exists to this Venture");
                        }
                    }else if(method.equals("salesemp")){
                        if (response.getString("status").equals("success")) {
                            JSONObject treeObject = response.getJSONObject("result");
                            JSONArray arr_assocaiteCadre = treeObject.getJSONArray("CadreSalesCount");
                            for (int i= 0; i < arr_assocaiteCadre.length(); i++) {
                                JSONObject objCount = arr_assocaiteCadre.getJSONObject(i);
                                CadresCountAssociates cca = new CadresCountAssociates(objCount.getString("count"), objCount.getString("Cadre"));
                                ccarraylist.add(cca);
                                cadres.add(objCount.getString("Cadre"));
                                childArray = objCount.getJSONArray("childs");
                                for (int j = 0; j < childArray.length(); j++) {
                                    JSONObject objchilds = childArray.getJSONObject(j);
                                    BookingDetailsModel attw = new BookingDetailsModel(objchilds.getString("AgentCd"),
                                            objchilds.getString("PlotNo"), objchilds.getString("PlotArea"),
                                            objchilds.getString("MemberId"), objchilds.getString("Name"),objchilds.getString("Sector"),objchilds.getString("AssociateName"));
                                    Ats.add(attw);
                                }
                                childs.add(childArray);
                                ArrayList<BookingDetailsModel> listdata = new ArrayList<BookingDetailsModel>();
                                JSONArray jArray = (JSONArray)childArray;
                                if (jArray != null) {
                                    for (int ij=0;ij<jArray.length();ij++){
                                        JSONObject obj1 = jArray.getJSONObject(ij);
                                        listdata.add(new BookingDetailsModel(obj1.getString("AgentCd"),
                                                obj1.getString("PlotNo"), obj1.getString("PlotArea"),
                                                obj1.getString("MemberId"), obj1.getString("Name"),obj1.getString("Sector"),obj1.getString("AssociateName")));
                                    }
                                }

                                _listchild.put(cca.getCadreName(), listdata);
                                view.onSuccess("employee",cadres,_listchild,ccarraylist);
                            }
                        } else if (response.getString("status").equals("fail")) {
                            view.OnError("No Data Exists to this Venture");
                        }
                    }

                }catch (JSONException e) {
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
