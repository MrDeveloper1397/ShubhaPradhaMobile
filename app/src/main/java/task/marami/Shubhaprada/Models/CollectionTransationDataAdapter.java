package task.marami.Shubhaprada.Models;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class CollectionTransationDataAdapter {
    JSONArray response;
    ArrayList<CollectionTransationData> transationData=new ArrayList<>();
    JSONObject obj;

    public CollectionTransationDataAdapter(JSONArray response) {
        this.response = response;
        for(int i=0;i<response.length();i++)
        {
            try {
                obj=response.getJSONObject(i);
                transationData.add(new CollectionTransationData(obj.getString("BookType"),
                        obj.getString("MemberReceiptsId"),obj.getString("PassBook"),
                        obj.getString("ApplName"),obj.getString("Discount"),obj.getString("RecAmount"),
                        obj.getString("BankName"),obj.getString("CheqDDno"),obj.getString("cheqdate"),
                        obj.getString("receiptDate"),obj.getString("PostingDate")));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    public ArrayList<CollectionTransationData> getTransationData() {
        return transationData;
    }
}
