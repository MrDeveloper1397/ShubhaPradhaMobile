package task.marami.Shubhaprada.Models;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class PaymentsTransactionDataAdp {
    JSONObject obj;
    ArrayList<PaymentTransationData> transationData=new ArrayList<>();
    JSONArray response;

    public PaymentsTransactionDataAdp(JSONArray response) {
        this.response = response;
        for(int i=0;i<response.length();i++)
        {
            try {
                obj=response.getJSONObject(i);
                transationData.add(new PaymentTransationData(obj.getString("Vcode"),
                        obj.getString("Vouchno"),obj.getString("PaymentDate"),
                        obj.getString("AgentCode"),obj.getString("AgentName"),obj.getString("CheqDDno"),
                        obj.getString("CheqDate"),obj.getString("Amount")));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    public ArrayList<PaymentTransationData> getTransationData() {
        return transationData;
    }
}
