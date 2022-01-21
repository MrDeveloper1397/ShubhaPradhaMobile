package task.marami.Shubhaprada.Models;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class DayCollectionByDateAdapter {
    ArrayList<CollectionItemData> CID=new ArrayList<>();
    JSONArray response;
    JSONObject obj;

    public DayCollectionByDateAdapter(JSONArray response) {
        this.response = response;
        try {
            for(int i=0;i<response.length();i++)
            {
                    obj = response.getJSONObject(i);
                    CollectionItemData cid = new CollectionItemData(obj.getString("venturecd"), obj.getString("VentureName"), obj.getString("Acnumb"), obj.getString("RecAmount"));
                    CID.add(cid);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public ArrayList<CollectionItemData> getCID() {
        return CID;
    }
}
