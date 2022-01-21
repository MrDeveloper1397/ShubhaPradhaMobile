package task.marami.Shubhaprada.Models;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class BookedPlotDataAdapter {
    JSONArray response;
    JSONObject obj;
    ArrayList<BookedData> bookedPlotData=new ArrayList<>();

    public BookedPlotDataAdapter(JSONArray response) {
        this.response = response;
        for(int i=0;i<response.length();i++)
        {
            try {
                obj=response.getJSONObject(i);
                BookedData data=new BookedData(obj.getString("VentureCd"),
                        obj.getString("MemberId"),obj.getString("ApplicantName"),
                        obj.getString("Mobile"),obj.getString("AgentName"),
                        obj.getString("SectorCd"),obj.getString("PlotNo"),obj.getString("PlotArea"),obj.getString("RatePerSq"),obj.getString("TotalCost"));
                bookedPlotData.add(data);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    public ArrayList<BookedData> getBookedPlotData() {
        return bookedPlotData;
    }
}
