package task.marami.Shubhaprada.Models;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class VenturesListAdp {
    JSONArray response;
    JSONObject obj;
    ArrayList<VentureNamesList> headers=new ArrayList<>();

    public ArrayList<VentureNamesList> getHeaders() {
        return headers;
    }

    public VenturesListAdp(JSONArray response) {
        this.response = response;
        for(int i=0;i<response.length();i++)
        {
            try {
                obj=response.getJSONObject(i);
                VentureNamesList ph=new VentureNamesList(obj.getString("VentureCd"),obj.getString("VentureName"));
                headers.add(ph);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
}
