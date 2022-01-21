package task.marami.Shubhaprada.Models;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class ApprPassbookDataAdapter {
    JSONArray response;
    ArrayList<ApprPassbookList> APBLS=new ArrayList<>();
    JSONObject obj;

    public ApprPassbookDataAdapter(JSONArray response) {
        this.response = response;
        for (int i=0;i<response.length();i++)
        {
            try {
                obj=response.getJSONObject(i);
                ApprPassbookList apl=new ApprPassbookList(obj.getString("MemberId"),
                        obj.getString("ApplicantName"),obj.getString("DateJoin"),
                        obj.getString("PlotApproval"),obj.getString("PlotCostApproval"),
                        obj.getString("CommissionApproval"),obj.getString("DiscountApproval"));
                APBLS.add(apl);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    public ArrayList<ApprPassbookList> getAPBLS() {
        return APBLS;
    }
}
