package task.marami.Shubhaprada.Models;

import android.annotation.SuppressLint;
import android.icu.text.NumberFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.math.BigDecimal;
import java.text.Format;
import java.util.ArrayList;
import java.util.Locale;

import task.marami.Shubhaprada.CollectionTransations;
import task.marami.Shubhaprada.R;

public class CollectionTransactionListAdp extends BaseAdapter {
    ArrayList<CollectionTransationData> transationData=new ArrayList<>();
    CollectionTransations context;
    LinearLayout lbank,lbankdetails;
    @SuppressLint("NewApi")
    Format format = NumberFormat.getCurrencyInstance(new Locale("en", "in"));

    TextView receiptId,postDate,pbno,name,amount,booktype,bank,chequeNo,chequeDate;
    String accType;

    public CollectionTransactionListAdp(ArrayList<CollectionTransationData> transationData, CollectionTransations context,String accType) {
        this.transationData = transationData;
        this.context = context;
        this.accType = accType;
    }

    @Override
    public int getCount() {
        return transationData.size();
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
        View transaction= LayoutInflater.from(parent.getContext()).inflate(R.layout.transation_item,null);
        receiptId = (TextView) transaction.findViewById(R.id.txt_recipt_id);
        postDate = (TextView) transaction.findViewById(R.id.txt_tran_date);
        pbno = (TextView) transaction.findViewById(R.id.txt_tran_pb_no);
        name = (TextView) transaction.findViewById(R.id.txt_tran_pbname);
        amount = (TextView) transaction.findViewById(R.id.txt_receipt_total);
        //booktype = (TextView) transaction.findViewById(R.id.txt_book_type);
        bank = (TextView) transaction.findViewById(R.id.txt_tran_bank);
        chequeNo = (TextView) transaction.findViewById(R.id.txt_cheque_no);
        chequeDate = (TextView) transaction.findViewById(R.id.txt_cheq_date);
        lbank = (LinearLayout) transaction.findViewById(R.id.layout_tran_bankname);
        lbankdetails = (LinearLayout) transaction.findViewById(R.id.layout_tran_chequedet);

        receiptId.setText(transationData.get(position).getReceiptId());
        postDate.setText(transationData.get(position).getCreateDate());
        pbno.setText(transationData.get(position).getPdno());
        name.setText(transationData.get(position).getName());
        amount.setText(format.format(new BigDecimal(transationData.get(position).getAmount())));
       // booktype.setText(transationData.get(position).getBookType());
        bank.setText(transationData.get(position).getBank());
        chequeNo.setText(transationData.get(position).getChequeNo());
        chequeDate.setText(transationData.get(position).getChequeDate());
        if (accType.equals("Bank"))
        {
            lbank.setVisibility(View.VISIBLE);
            lbankdetails.setVisibility(View.VISIBLE);
        }

        return transaction;
    }
}
