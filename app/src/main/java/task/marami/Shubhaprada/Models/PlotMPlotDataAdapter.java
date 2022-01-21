package task.marami.Shubhaprada.Models;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class PlotMPlotDataAdapter {
    ArrayList<PlotMPlotData> facingData=new ArrayList<>();
    JSONArray response;
    JSONObject obj;

    public PlotMPlotDataAdapter(JSONArray response) {
        this.response = response;
        for (int i=0;i<response.length();i++)
        {
            try {
                obj=response.getJSONObject(i);
                PlotMPlotData PMFD=new PlotMPlotData(obj.getString("PlotNo"),
                        obj.getString("PLOTAREA"));
                facingData.add(PMFD);

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    public ArrayList<PlotMPlotData> getFacingData() {
        return facingData;
    }
}
