package task.marami.Shubhaprada.Models;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class VenturesDataAdapter {
    ArrayList<VenturesModel> ventures =new ArrayList<>();;
    JSONArray response;
    JSONObject jsonObject=null;

    public VenturesDataAdapter(JSONArray response) {
        this.response = response;
        for(int i=0;i<response.length();i++)
        {
            try {
                jsonObject = response.getJSONObject(i);
                VenturesModel v1 = new VenturesModel(jsonObject.getString("VentureCd"),
                        jsonObject.getString("VentureName"),
                        jsonObject.getString("Plot"),
                        jsonObject.getString("UnitCd"));

                ventures.add(v1);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    public void setVenturesData(ArrayList<VenturesModel> ventures) {
        this.ventures = ventures;
    }

    public ArrayList<VenturesModel> getVenturesData() {
        return ventures;
    }
}
