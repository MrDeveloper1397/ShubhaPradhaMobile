package task.marami.Shubhaprada;

import android.graphics.Color;
import com.google.android.material.snackbar.Snackbar;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.cardview.widget.CardView;
import android.view.View;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;

import task.marami.Shubhaprada.Interfaces.IPaymentsTransation;
import task.marami.Shubhaprada.Models.PaymentTransationData;
import task.marami.Shubhaprada.Models.PaymentsTransationListAdp;
import task.marami.Shubhaprada.Presenters.PaymentsTransationPresenter;
import task.marami.Shubhaprada.Utils.Contents;
import task.marami.Shubhaprada.Utils.SingletonAlertDialog;

public class PaymentsTransation extends AppCompatActivity
        implements IPaymentsTransation.PaymentsTransationView,View.OnClickListener{
    TextView txtTitleIdicator,txtBankTitle,txtBankTotal,txtCashTitle,txtCashTotal,txtAdjustmentTitle,txtAdjustmentTotal;
    CardView card_bank,card_cash,card_adjustment;
    ListView paymentsList;
    ProgressBar progressBar;
    PaymentsTransationPresenter presenter;
    String ventureCd,usertype,Total,acc_type,date;
    ArrayList<PaymentTransationData> transationData=new ArrayList<>();
    int bank_count=0,cash_count=0,adjustment_count=0;
    PaymentsTransationListAdp adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payments_transation);
        init();
        presenter = new PaymentsTransationPresenter(this,this);
        presenter.onLoad(ventureCd, usertype, date);
    }
    void init()
    {
        txtTitleIdicator = (TextView) findViewById(R.id.txt_payment_indicator);
        txtBankTitle = (TextView) findViewById(R.id.txt_payment_bank_title);
        txtBankTotal = (TextView) findViewById(R.id.txt_payment_bank_total);
        txtCashTitle = (TextView) findViewById(R.id.txt_payment_cash_title);
        txtCashTotal = (TextView) findViewById(R.id.txt_payment_cash_total);
        txtAdjustmentTitle = (TextView) findViewById(R.id.txt_payment_adjustment_title);
        txtAdjustmentTotal = (TextView) findViewById(R.id.txt_payment_adjustment_total);
        card_bank = (CardView) findViewById(R.id.card_payment_bank);
        card_cash = (CardView) findViewById(R.id.card_payment_cash);
        card_adjustment = (CardView) findViewById(R.id.card_payment_adjustment);
        paymentsList = (ListView) findViewById(R.id.list_payment_transation);
        progressBar = (ProgressBar) findViewById(R.id.prog_payments);

        ventureCd = getIntent().getStringExtra("ventureName");
        usertype = getIntent().getStringExtra("user_type");
        Total = getIntent().getStringExtra("total");
        acc_type = getIntent().getStringExtra("type");
        date = getIntent().getStringExtra("date");

        txtTitleIdicator.setText(ventureCd+"->"+usertype);

        card_cash.setOnClickListener(this);
        card_bank.setOnClickListener(this);
        card_adjustment.setOnClickListener(this);

    }

    @Override
    public void onStartProg() {
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void onStopProg() {
        progressBar.setVisibility(View.GONE);
    }

    @Override
    public void onLoadSuccess(ArrayList<PaymentTransationData> transationData) {
        this.transationData=transationData;
        Contents.utitransationData=transationData;
        double cash=0.0,bank=0.0,adjust=0.0;
        for (PaymentTransationData pd:transationData)
        {
            if(pd.getAccCode().equals("1000")) {
                cash_count++;
                cash += Double.parseDouble(pd.getAmount());
            }
            else if(pd.getAccCode().equals("10000")) {
                adjustment_count++;
                adjust += Double.parseDouble(pd.getAmount());
            }
            else {
                bank_count++;
                bank += Double.parseDouble(pd.getAmount());
            }
        }
        txtBankTotal.setText(String.valueOf(bank));
        txtCashTotal.setText(String.valueOf(cash));
        txtAdjustmentTotal.setText(String.valueOf(adjust));
        if(cash_count>0)
        {
            adapter=new PaymentsTransationListAdp(transationData,this,"cash");
            onSelectCard("cash");
        }
        else if(bank_count>0)
        {
            adapter=new PaymentsTransationListAdp(transationData,this,"bank");
            onSelectCard("bank");
        }
        else if(adjustment_count>0)
        {
            adapter=new PaymentsTransationListAdp(transationData,this,"adjustment");
            onSelectCard("adjustment");
        }
        paymentsList.setAdapter(adapter);
    }

    @Override
    public void onError(String message) {
        SingletonAlertDialog.alertDialogShow(PaymentsTransation.this,message);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.card_payment_bank:
                if(bank_count>0)
                {
                    onSelectCard("bank");
                    PaymentsTransationListAdp adapter = new PaymentsTransationListAdp(Contents.utitransationData, this, "bank");
                    paymentsList.setAdapter(adapter);
                }
                else {
                    Snackbar.make(v,"No Transactions Found" , Snackbar.LENGTH_LONG).show();
                }
                break;
            case R.id.card_payment_cash:
                if(cash_count>0)
                {
                    onSelectCard("cash");
                    PaymentsTransationListAdp adapter1 = new PaymentsTransationListAdp(Contents.utitransationData, this, "cash");
                    paymentsList.setAdapter(adapter1);
                }
                else {
                    Snackbar.make(v,"No Transactions Found" , Snackbar.LENGTH_LONG).show();
                }
                break;
            case R.id.card_payment_adjustment:
                if(adjustment_count>0)
                {
                    onSelectCard("adjustment");
                    PaymentsTransationListAdp adapter2 = new PaymentsTransationListAdp(Contents.utitransationData, this, "adjustment");
                    paymentsList.setAdapter(adapter2);
                }
                else {
                    Snackbar.make(v,"No Transactions Found" , Snackbar.LENGTH_LONG).show();
                }
                break;
        }
    }
    void onSelectCard(String cardname)
    {
        if(cardname=="cash")
        {
            card_bank.setCardBackgroundColor(Color.parseColor("#FA6C19"));
            txtBankTitle.setTextColor(Color.parseColor("#FFFFFF"));
            txtBankTotal.setTextColor(Color.parseColor("#FFFFFF"));
            card_cash.setCardBackgroundColor(Color.parseColor("#ffffff"));
            txtCashTitle.setTextColor(Color.parseColor("#FA6C19"));
            txtCashTotal.setTextColor(Color.parseColor("#FA6C19"));
            card_adjustment.setCardBackgroundColor(Color.parseColor("#FA6C19"));
            txtAdjustmentTitle.setTextColor(Color.parseColor("#FFFFFF"));
            txtAdjustmentTotal.setTextColor(Color.parseColor("#FFFFFF"));

        }
        else if(cardname=="bank")
        {
            card_bank.setCardBackgroundColor(Color.parseColor("#FFFFFF"));
            txtBankTitle.setTextColor(Color.parseColor("#FA6C19"));
            txtBankTotal.setTextColor(Color.parseColor("#FA6C19"));
            card_cash.setCardBackgroundColor(Color.parseColor("#FA6C19"));
            txtCashTitle.setTextColor(Color.parseColor("#FFFFFF"));
            txtCashTotal.setTextColor(Color.parseColor("#FFFFFF"));
            card_adjustment.setCardBackgroundColor(Color.parseColor("#FA6C19"));
            txtAdjustmentTitle.setTextColor(Color.parseColor("#FFFFFF"));
            txtAdjustmentTotal.setTextColor(Color.parseColor("#FFFFFF"));
        }
        else
        {
            card_bank.setCardBackgroundColor(Color.parseColor("#FA6C19"));
            txtBankTitle.setTextColor(Color.parseColor("#FFFFFF"));
            txtBankTotal.setTextColor(Color.parseColor("#FFFFFF"));
            card_cash.setCardBackgroundColor(Color.parseColor("#FA6C19"));
            txtCashTitle.setTextColor(Color.parseColor("#FFFFFF"));
            txtCashTotal.setTextColor(Color.parseColor("#FFFFFF"));
            card_adjustment.setCardBackgroundColor(Color.parseColor("#FFFFFF"));
            txtAdjustmentTitle.setTextColor(Color.parseColor("#FA6C19"));
            txtAdjustmentTotal.setTextColor(Color.parseColor("#FA6C19"));
        }
    }
}
