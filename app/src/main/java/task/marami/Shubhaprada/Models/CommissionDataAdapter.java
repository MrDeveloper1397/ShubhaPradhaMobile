package task.marami.Shubhaprada.Models;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class CommissionDataAdapter {
    ArrayList<CommissionData> commissionData=new ArrayList<>();
    JSONArray response;
    JSONObject obj;

    public CommissionDataAdapter(JSONArray response,String CommCalc) {
        this.response = response;
        for(int i=0;i<response.length();i++)
        {
            try {
                obj=response.getJSONObject(i);
                CommissionData data=new CommissionData(obj.getString("AgentCode"),obj.getString("AgentName"),obj.getString("AgentCadre"),obj.getString("AgentLevel"),obj.getString("Commission"),obj.getString("Total_Amo"),CommCalc,obj.getString("Discount"),obj.getString("GrossPayable"));
                commissionData.add(data);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

    }

    public ArrayList<CommissionData> getCommissionData() {
        return commissionData;
    }
}
