package task.marami.Shubhaprada.Models;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class CollectionItemDataAdapter {
    ArrayList<CollectionItemData> CID=new ArrayList<>();
    JSONArray response;
    JSONObject obj;
    int j=0;
    double tot=0.0;
    String paymentcol,bookings;
    public CollectionItemDataAdapter(JSONArray response) {
        this.response = response;
        try {
        for(int i=0;i<response.length();i++)
        {
            if(i!=response.length()-1) {
                obj = response.getJSONObject(i);
                CollectionItemData cid = new CollectionItemData(obj.getString("venturecd"), obj.getString("VentureName"), obj.getString("Acnumb"), obj.getString("RecAmount"));
                CID.add(cid);
            }
            else
            {
                obj=response.getJSONObject(i);
                paymentcol=obj.getString("PaymentTotal");
                bookings = obj.getString("bookscount");
            }
        }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public ArrayList<CollectionItemData> getCID() {
        return CID;
    }

    public String getPaymentcol() {
        return paymentcol;
    }

    public String getBookings() {
        return bookings;
    }
}
