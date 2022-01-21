package task.marami.Shubhaprada.Models;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class PlistApprovalCountDataAdapter {
    JSONArray response;
    ArrayList<PlistApprovalCount> placs=new ArrayList<>();
    JSONObject obj;

    public PlistApprovalCountDataAdapter(JSONArray response) {
        this.response = response;
        for(int i=0;i<response.length();i++)
        {
            try {
                obj=response.getJSONObject(i);
                PlistApprovalCount plac=new PlistApprovalCount(obj.getString("count"),
                obj.getString("VentureCd"),obj.getString("VentureName"),obj.getString("UnitCd"),
                        obj.getString("Plot"),obj.getString("CommCalc"));
                placs.add(plac);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    public ArrayList<PlistApprovalCount> getPlacs() {
        return placs;
    }
}
