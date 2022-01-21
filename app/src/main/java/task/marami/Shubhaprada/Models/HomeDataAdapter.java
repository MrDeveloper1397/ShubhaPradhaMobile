package task.marami.Shubhaprada.Models;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class HomeDataAdapter {
    ArrayList<HomeData> homedata=new ArrayList<>();
    JSONArray response;
    JSONObject obj;

    public HomeDataAdapter(JSONArray response) {
        this.response = response;
        obj=null;
        for (int i=0;i<response.length();i++)
        {
            try {
                obj=response.getJSONObject(i);
                HomeData hd=new HomeData(obj.getString("Title"),obj.getString("Context"),
                        obj.getString("projectTitle"),obj.getString("UpcomingProject"),obj.getString("PjctId"),
                        obj.getString("LINK"),obj.getString("SECTOR"),obj.getString("EnqyLink"),
                        obj.getString("allo_count"),obj.getString("avl_count"),obj.getString("mort_count"),
                        obj.getString("regs_count"),obj.getString("rese_count"),obj.getString("totcount"),obj.getString("facing"));
                homedata.add(hd);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    public ArrayList<HomeData> getHomedata() {
        return homedata;
    }
}
