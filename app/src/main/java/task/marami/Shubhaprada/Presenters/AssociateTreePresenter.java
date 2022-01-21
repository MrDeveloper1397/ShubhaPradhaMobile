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

import task.marami.Shubhaprada.Interfaces.IAssociateTree;
import task.marami.Shubhaprada.Models.AssociateTreeTeamWise;
import task.marami.Shubhaprada.Models.CadresCountAssociates;
import task.marami.Shubhaprada.Utils.Contents;
import task.marami.Shubhaprada.Utils.MySingleton;
import task.marami.Shubhaprada.Utils.StoragePrefer;
import task.marami.Shubhaprada.Utils.VolleyErrorHandler;

public class AssociateTreePresenter implements IAssociateTree.presenter {
    IAssociateTree.view view;
    Context context;
    ArrayList<AssociateTreeTeamWise> Atw= new ArrayList<>();
    ArrayList<CadresCountAssociates> ccarraylist= new ArrayList<>();
    private JSONArray childArray;
    ArrayList<JSONArray> childs = new ArrayList<>();
    ArrayList<String> cadres = new ArrayList<>();
    HashMap<String, ArrayList<AssociateTreeTeamWise>> _listchild = new HashMap<String, ArrayList<AssociateTreeTeamWise>>();


    public AssociateTreePresenter(Context context, IAssociateTree.view view) {
        this.context = context;
        this.view = view;
    }
    @Override
    public void onLoad(String venturecd) {
        StoragePrefer sp = new StoragePrefer(context);
        String url = Contents.BASE_URL + "GetAssocaitiveTree?ApiKey=" + sp.getSprString(Contents.PREF_KEY_API_TOKEN) + "&UserType=" + sp.getSprString(Contents.PTEF_KEY_USER_TYPE)+"&VentureCode="+venturecd+"&reportID="+sp.getSprString(Contents.PTEF_KEY_USERID);
        Log.d("Url:::", url);
        getServerConnection(url,"usertree");
    }

    @Override
    public void onAssociateSearch(String venture, String associateID) {
        StoragePrefer sp = new StoragePrefer(context);
        String url = Contents.BASE_URL + "GetAssocaitiveTreeEmployee.php?AssociateID="+associateID+"&VentureCode="+venture+"&reportID="+sp.getSprString(Contents.PTEF_KEY_USERID);
        Log.d("Url:::", url);
        getServerConnection(url,"useremp");
    }

    @Override
    public void onChildItems(String Cadre, String venture) {
        StoragePrefer sp = new StoragePrefer(context);
        String url = Contents.BASE_URL + "TreeSubList.php?AgentCadre="+Cadre+"&reportID="+sp.getSprString(Contents.PTEF_KEY_USERID)+"&VentureCd="+venture;
        Log.d("Url:::", url);
        getServerConnection1(url,"sublist");
    }

    public void getServerConnection(String url, final String method) {
        view.onStartProg();
        JsonObjectRequest arrayRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                view.onStopProg();
                try {
                    if(method.equals("usertree")) {
                        if (response.getString("status").equals("success")) {
                            JSONObject treeObject = response.getJSONObject("result");
                            JSONArray arr_assocaitetree = treeObject.getJSONArray("AssocaitiveTree");
                            for (int i = 0; i < arr_assocaitetree.length(); i++) {
                                JSONObject obj = arr_assocaitetree.getJSONObject(i);
                                CadresCountAssociates cca = new CadresCountAssociates(obj.getString("Associatescount"), obj.getString("Carde"));
                                ccarraylist.add(cca);
                                cadres.add(obj.getString("Carde"));
                                childArray = obj.getJSONArray("childs");
                                for (int j = 0; j < childArray.length(); j++) {
                                    JSONObject objchilds = childArray.getJSONObject(j);
                                    AssociateTreeTeamWise attw = new AssociateTreeTeamWise(objchilds.getString("AgentCd"),
                                            objchilds.getString("AgentName"), objchilds.getString("AgentCarde"),
                                            objchilds.getString("WorkUnder"), objchilds.getString("PanNo"),
                                            objchilds.getString("Mobile"));
                                    Atw.add(attw);
                                }
                                childs.add(childArray);
                                ArrayList<AssociateTreeTeamWise> listdata = new ArrayList<AssociateTreeTeamWise>();
                                JSONArray jArray = (JSONArray)childArray;
                                if (jArray != null) {
                                    for (int ij=0;ij<jArray.length();ij++){
                                        JSONObject obj1 = jArray.getJSONObject(ij);
                                        listdata.add(new AssociateTreeTeamWise(obj1.getString("AgentCd"),
                                                obj1.getString("AgentName"), obj1.getString("AgentCarde"),
                                                obj1.getString("WorkUnder"), obj1.getString("PanNo"),
                                                obj1.getString("Mobile")));
                                    }
                                }

                                _listchild.put(cca.getCadreName(), listdata);
                                view.onSuccess("user",cadres,_listchild,ccarraylist);
                            }
                        }else{
                            view.OnError("No Associate Tree Available for Selected Venture");
                        }
                    }else if(method.equals("useremp")){
                        if (response.getString("status").equals("success")) {
                            JSONObject treeObject = response.getJSONObject("result");
                            JSONArray arr_assocaitetree = treeObject.getJSONArray("AssocaitiveTree");
                            for (int i = 0; i < arr_assocaitetree.length(); i++) {
                                JSONObject obj = arr_assocaitetree.getJSONObject(i);
                                CadresCountAssociates cca = new CadresCountAssociates(obj.getString("Associatescount"), obj.getString("Carde"));
                                ccarraylist.add(cca);
                                cadres.add(obj.getString("Carde"));
                                childArray = obj.getJSONArray("childs");
                                for (int j = 0; j < childArray.length(); j++) {
                                    JSONObject objchilds = childArray.getJSONObject(j);
                                    AssociateTreeTeamWise attw = new AssociateTreeTeamWise(objchilds.getString("AgentCd"),
                                            objchilds.getString("AgentName"), objchilds.getString("AgentCarde"),
                                            objchilds.getString("WorkUnder"), objchilds.getString("PanNo"),
                                            objchilds.getString("Mobile"));
                                    Atw.add(attw);
                                }
                                childs.add(childArray);
                                ArrayList<AssociateTreeTeamWise> listdata = new ArrayList<AssociateTreeTeamWise>();
                                JSONArray jArray = (JSONArray)childArray;
                                if (jArray != null) {
                                    for (int ij=0;ij<jArray.length();ij++){
                                        JSONObject obj1 = jArray.getJSONObject(ij);
                                        listdata.add(new AssociateTreeTeamWise(obj1.getString("AgentCd"),
                                                obj1.getString("AgentName"), obj1.getString("AgentCarde"),
                                                obj1.getString("WorkUnder"), obj1.getString("PanNo"),
                                                obj1.getString("Mobile")));
                                    }
                                }

                                _listchild.put(cca.getCadreName(), listdata);
                                view.onSuccess("employee",cadres,_listchild,ccarraylist);
                            }
                        }else{
                            view.OnError("No Associate Tree Available for Selected Venture");
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

    public void getServerConnection1(String url, final String method) {
        view.onStartProg();
        JsonObjectRequest arrayRequest1 = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                view.onStopProg();
                try {
                        if (response.getString("status").equals("success")) {
                            Atw.clear();
                            JSONObject treeObject = response.getJSONObject("result");
                            JSONArray arr_assocaitetree = treeObject.getJSONArray("AssocaitiveTree");
                            for (int i = 0; i < arr_assocaitetree.length(); i++) {
                                JSONObject obj = arr_assocaitetree.getJSONObject(i);
                                AssociateTreeTeamWise attw = new AssociateTreeTeamWise(obj.getString("AgentCd"),
                                        obj.getString("AgentName"), obj.getString("AgentCarde"),
                                        obj.getString("WorkUnder"), obj.getString("PanNo"),
                                        obj.getString("Mobile"));
                                Atw.add(attw);
                            }
                        }else{
                            view.OnError("No Associate Tree Available for Selected Venture");
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
        MySingleton.getsInstance(context).getRequestQueue().add(arrayRequest1);
    }
}
