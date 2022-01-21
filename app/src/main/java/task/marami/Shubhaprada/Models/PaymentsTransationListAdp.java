package task.marami.Shubhaprada.Models;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import task.marami.Shubhaprada.PaymentsTransation;
import task.marami.Shubhaprada.R;

public class PaymentsTransationListAdp extends BaseAdapter {
    ArrayList<PaymentTransationData> transationData=new ArrayList<>();
    ArrayList<PaymentTransationData> transationData1=new ArrayList<>();
    PaymentsTransation context;
    String t_type;
    TextView voucher,tran_date,pb_no,name,chequeno,chequedate,total;

    public PaymentsTransationListAdp(ArrayList<PaymentTransationData> transationData,
                                     PaymentsTransation context,String t_type) {
        this.transationData = transationData;
        this.context = context;
        this.t_type = t_type;
        getnewdatalist();
    }
    void getnewdatalist()
    {
        for(int i=0;i<transationData.size();i++)
        {
            if(t_type.equals("cash"))
            {
                if(transationData.get(i).getAccCode().equals("1000"))
                {
                    transationData1.add(transationData.get(i));
                }
            }
            else if(t_type.equals("adjustment"))
            {
                if(transationData.get(i).getAccCode().equals("10000"))
                {
                    transationData1.add(transationData.get(i));
                }
            }
            else if(t_type.equals("bank"))
            {
                if(Integer.parseInt(transationData.get(i).getAccCode())>299 && Integer.parseInt(transationData.get(i).getAccCode()) <500)
                {
                    transationData1.add(transationData.get(i));
                }
            }
        }

    }
    @Override
    public int getCount() {
        return transationData1.size();
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
        View transaction= LayoutInflater.from(parent.getContext()).inflate(R.layout.payment_transaction_item,null);
        voucher = (TextView) transaction.findViewById(R.id.txt_voucher_no);
        tran_date = (TextView) transaction.findViewById(R.id.txt_tran_date);
        pb_no = (TextView) transaction.findViewById(R.id.txt_tran_pb_no);
        name = (TextView) transaction.findViewById(R.id.txt_tran_pbname);
        chequeno = (TextView) transaction.findViewById(R.id.txt_cheque_no);
        chequedate = (TextView) transaction.findViewById(R.id.txt_cheq_date);
        total = (TextView) transaction.findViewById(R.id.txt_receipt_total);
        LinearLayout layout = (LinearLayout) transaction.findViewById(R.id.layout_tran_chequedet);
        if (t_type.equals("cash")&&transationData1.get(position).getAccCode().equals("1000"))
        {
            voucher.setText(transationData1.get(position).getVouchNo());
            tran_date.setText(transationData1.get(position).getPaymentDate());
            pb_no.setText(transationData1.get(position).getAgentCode());
            name.setText(transationData1.get(position).getAgentName());
            total.setText(transationData1.get(position).getAmount());
            layout.setVisibility(View.GONE);
            return transaction;
        }
        if (t_type.equals("bank"))
        {
            voucher.setText(transationData1.get(position).getVouchNo());
            tran_date.setText(transationData1.get(position).getPaymentDate());
            pb_no.setText(transationData1.get(position).getAgentCode());
            name.setText(transationData1.get(position).getAgentName());
            total.setText(transationData1.get(position).getAmount());
            chequeno.setText(transationData1.get(position).getCheqDDNo());
            chequedate.setText(transationData1.get(position).getCheqDate());
            return transaction;
        }
        if (t_type.equals("adjustment"))
        {
            voucher.setText(transationData1.get(position).getVouchNo());
            tran_date.setText(transationData1.get(position).getPaymentDate());
            pb_no.setText(transationData1.get(position).getAgentCode());
            name.setText(transationData1.get(position).getAgentName());
            total.setText(transationData1.get(position).getAmount());
            layout.setVisibility(View.GONE);
            return transaction;
        }
        return null;
    }
}
