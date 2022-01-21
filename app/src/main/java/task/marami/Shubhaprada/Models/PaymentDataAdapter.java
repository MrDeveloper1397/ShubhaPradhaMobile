package task.marami.Shubhaprada.Models;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class PaymentDataAdapter {
    ArrayList<DayPaymentHeader> header=new ArrayList<>();
    ArrayList<DayPaymentsItem> item=new ArrayList<>();
    HashMap<String,ArrayList<DayPaymentsItem>> has_header=new HashMap<String, ArrayList<DayPaymentsItem>>();
    JSONArray response;
    JSONObject obj;
    ArrayList<String> venturelist=new ArrayList<>();
    ArrayList<String> ventureacc=new ArrayList<>();

    public PaymentDataAdapter(JSONArray response) {
        this.response = response;
        String venture;
        for(int i=0;i<response.length();i++)
        {
            try {
                obj = response.getJSONObject(i);
                venture=obj.getString("VENTURECD");
                item=new ArrayList<>();
                double total=0.0;
                if (!venturelist.contains(venture))
                {
                    venturelist.add(venture);
                    for(int j=0;j<response.length();j++)
                    {
                        JSONObject obj1=response.getJSONObject(j);
                        String venacc=venture+obj1.getString("user_type");
                        if(!ventureacc.contains(venacc)) {
                            long usesum=0;
                            ventureacc.add(venacc);
                            for(int m=0;m<response.length();m++)
                            {
                                JSONObject obj2=response.getJSONObject(m);
                                if(venacc.equals(obj2.getString("VENTURECD")+obj2.getString("user_type")))
                                {
                                    usesum+=Double.parseDouble(obj2.getString("Total"));
                                }
                            }
                            total+=usesum;
                            DayPaymentsItem DPI = new DayPaymentsItem(obj1.getString("user_type"), obj1.getString("acc_type"), String.valueOf(usesum));
                            item.add(DPI);
                        }
                    }
                    has_header.put(venture, item);
                    DayPaymentHeader DPH=new DayPaymentHeader(venture,String.valueOf(total));
                    header.add(DPH);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    public ArrayList<DayPaymentHeader> getHeader() {
        return header;
    }

    public ArrayList<DayPaymentsItem> getItem() {
        return item;
    }

    public HashMap<String, ArrayList<DayPaymentsItem>> getHas_header() {
        return has_header;
    }
}
