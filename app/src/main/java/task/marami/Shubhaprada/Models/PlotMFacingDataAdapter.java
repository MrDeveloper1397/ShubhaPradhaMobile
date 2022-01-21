package task.marami.Shubhaprada.Models;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class PlotMFacingDataAdapter {
    ArrayList<PlotMatrixFacingData> facingData=new ArrayList<>();
    JSONArray response;
    JSONObject obj;

    public PlotMFacingDataAdapter(JSONArray response) {
        this.response = response;
       for (int i=0;i<response.length();i++)
       {
           try {
               obj=response.getJSONObject(i);
               PlotMatrixFacingData PMFD=new PlotMatrixFacingData(obj.getString("total"),
                       obj.getString("Extend"),obj.getString("FACING"));
               facingData.add(PMFD);

           } catch (JSONException e) {
               e.printStackTrace();
           }
       }
    }

    public ArrayList<PlotMatrixFacingData> getFacingData() {
        return facingData;
    }
}
