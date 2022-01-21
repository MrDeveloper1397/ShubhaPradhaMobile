package task.marami.Shubhaprada.Models;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class PlotMatrixheaderAdapter {
    JSONArray response;
    JSONObject obj;
    ArrayList<PlotMatrixHeader> headers=new ArrayList<>();

    public PlotMatrixheaderAdapter(JSONArray response) {
        this.response = response;
        for(int i=0;i<response.length();i++)
        {
            try {
                obj=response.getJSONObject(i);
                PlotMatrixHeader ph=new PlotMatrixHeader(obj.getString("VentureCd"),obj.getString("Total"),
                        obj.getString("Extend"),obj.getString("Status"),obj.getString("SectorCd"),
                        obj.getString("VentureName"));
                headers.add(ph);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    public ArrayList<PlotMatrixHeader> getHeaders() {
        return headers;
    }
}
