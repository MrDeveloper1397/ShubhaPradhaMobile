package task.marami.Shubhaprada.Models;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class PlotCostDataAdapter {
    JSONArray response;
    ArrayList<PlotCostApproval> plotcostdata=new ArrayList<>();
    JSONObject obj;

    public PlotCostDataAdapter(JSONArray response) {
        this.response = response;
        for(int i=0;i<response.length();i++)
        {
            try {
                obj=response.getJSONObject(i);
                PlotCostApproval pca=new PlotCostApproval(obj.getString("Memberid"),
                        obj.getString("SectorCd"),obj.getString("PlotNo"),
                        obj.getString("PlotArea"),obj.getString("PCATEG"),
                        obj.getString("FACING"),obj.getString("RatePerSq"),
                        obj.getString("AdminFee"),obj.getString("TotalCost"),
                        obj.getString("DevCharges"),obj.getString("costPremium"),
                        obj.getString("BSP4"),obj.getString("bsp6"),obj.getString("Others"),
                        obj.getString("CarpusFund"),obj.getString("CompDiscount"),
                        obj.getString("Discount"),obj.getString("Rate_Calc"),
                        obj.getString("spPremium"),obj.getString("Premium"),obj.getString("PaidAmount"));
                        plotcostdata.add(pca);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    public ArrayList<PlotCostApproval> getPlotcostdata() {
        return plotcostdata;
    }
}
