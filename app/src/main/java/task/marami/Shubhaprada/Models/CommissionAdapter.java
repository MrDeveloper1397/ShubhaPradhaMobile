package task.marami.Shubhaprada.Models;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import task.marami.Shubhaprada.R;
import task.marami.Shubhaprada.Utils.Contents;

public class CommissionAdapter extends BaseAdapter {
    ArrayList<CommissionData> ComData=new ArrayList<>();
    TextView CommId,ComName,ComCadre,ComLevel,ComCommi,TotalPay,CommisType,ComDisco;
    Double payble=0.0;
    public CommissionAdapter(ArrayList<CommissionData> comData) {
        this.ComData = comData;
    }

    @Override
    public int getCount() {
        return ComData.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View CommissionView= LayoutInflater.from(parent.getContext()).inflate(R.layout.commissionitem, null);
        CommId=(TextView) CommissionView.findViewById(R.id.CommId);
        ComName=(TextView) CommissionView.findViewById(R.id.ComName);
        ComCadre=(TextView) CommissionView.findViewById(R.id.ComCadre);
        ComLevel=(TextView) CommissionView.findViewById(R.id.ComLevel);
        ComCommi=(TextView) CommissionView.findViewById(R.id.ComCommi);
        TotalPay=(TextView) CommissionView.findViewById(R.id.Totalpay);
        CommisType=(TextView) CommissionView.findViewById(R.id.CommisType);
        ComDisco=(TextView) CommissionView.findViewById(R.id.ComDisco);

        if(ComData.get(position).getCommCalc().equals("A"))
        {
            CommisType.setText("Comm[@sq]");
        }
        else
        {
           // String comper="";
            CommisType.setText("Comm[%]");

            /*for(int i=0;i<ComData.get(position).getCommiss().length();i++)
            {
                if(ComData.get(position).getCommiss().charAt(i)!='%')
                {
                    comper+=ComData.get(position).getCommiss().charAt(i);
                }
            }
            payble=(Double.parseDouble(comper)/100)* Contents.TotalPlotcost;
            TotalPay.setText(payble.toString());*/
        }
        if(ComData.get(position).getComDiscount().equals(""))
        {
            ComDisco.setText("0.0");
            Contents.Totalcommission+=payble;
        }
        else
        {
            try {
                payble-=Double.parseDouble(ComData.get(position).getComDiscount());
                Contents.Totalcommission+=payble;
                ComDisco.setText("" + Double.parseDouble(ComData.get(position).getComDiscount()));
            }catch(NumberFormatException ex)
            {

            }
        }

        CommId.setText(ComData.get(position).getId());
        ComName.setText(ComData.get(position).getName());
        ComCadre.setText(ComData.get(position).getCadre());
        ComCommi.setText(ComData.get(position).getCommiss());
        ComLevel.setText(ComData.get(position).getLevel());
        TotalPay.setText(ComData.get(position).getTotalPayble());

        return CommissionView;
    }
}
