package task.marami.Shubhaprada.Models;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class ReservationDataAdapter {
    JSONArray response;
    ArrayList<ReservationModel> reservation_data=new ArrayList<>();
    JSONObject obj;

    public ReservationDataAdapter(JSONArray response) {
        this.response = response;
        for(int i=0;i<response.length();i++)
        {
            try {
                obj=response.getJSONObject(i);
                ReservationModel remod=new ReservationModel(obj.getString("VentureCd"),
                        obj.getString("VentureName"),obj.getString("Sector"));
                reservation_data.add(remod);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    public ArrayList<ReservationModel> getReservation_data() {
        return reservation_data;
    }
}
