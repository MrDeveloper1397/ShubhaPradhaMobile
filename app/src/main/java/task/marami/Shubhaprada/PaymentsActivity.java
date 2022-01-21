package task.marami.Shubhaprada;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.icu.text.NumberFormat;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.ExpandableListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.math.BigDecimal;
import java.text.Format;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;

import task.marami.Shubhaprada.Interfaces.IDayPayments;
import task.marami.Shubhaprada.Models.DayPaymentHeader;
import task.marami.Shubhaprada.Models.DayPaymentsItem;
import task.marami.Shubhaprada.Models.DayPaymentsListAdapter;
import task.marami.Shubhaprada.Presenters.DayPaymentPresenter;
import task.marami.Shubhaprada.Utils.ConnectionDirectory;
import task.marami.Shubhaprada.Utils.Contents;

public class PaymentsActivity extends AppCompatActivity implements IDayPayments.DayPaymentsView {
    TextView TotalAmount;
    ExpandableListView payments;
    DayPaymentPresenter presenter;
    ProgressBar progressBar;
    FloatingActionButton feb_btn;
    String date="now";
    private DatePickerDialog.OnDateSetListener mDateSetListener;
    @SuppressLint("NewApi")
    Format format = NumberFormat.getCurrencyInstance(new Locale("en", "in"));

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payments);
        payments=(ExpandableListView) findViewById(R.id.list_payments);
        TotalAmount=(TextView) findViewById(R.id.txt_total_payments);
        progressBar = (ProgressBar) findViewById(R.id.prog_payments);
        feb_btn = (FloatingActionButton) findViewById(R.id.feb_payments_calender);
        TotalAmount.setText(format.format(new BigDecimal(getIntent().getStringExtra("TotalPayments"))));
        presenter=new DayPaymentPresenter(this,this);

        if(ConnectionDirectory.isConnected(this))
        {
            presenter.onLoad(date);
        }
        else
        {
            Snackbar.make(getCurrentFocus(), Contents.No_Internet, Snackbar.LENGTH_LONG)
                    .setAction("Settings", new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            startActivityForResult(new Intent(android.provider.Settings.ACTION_SETTINGS), 0);
                        }
                    }).show();
        }
        feb_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog dialog = new DatePickerDialog(
                        PaymentsActivity.this,
                        android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                        mDateSetListener,
                        year,month,day);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.getDatePicker().setMaxDate(new Date().getTime());
                dialog.show();
            }
        });
        mDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                month = month + 1;
                date = day + "-"+ month +"-"+ year;
                // mDisplayDate.setText(day + "-"+ month +"-"+ year);
                checkDataconnection(date);
                TotalAmount.setText("0.0");
                payments.setVisibility(View.GONE);
            }
        };
    }

    void checkDataconnection(String date)
    {
        if(ConnectionDirectory.isConnected(this))
        {
              presenter.onLoad(date);
        }
        else
        {
            Snackbar.make(getCurrentFocus(), Contents.No_Internet, Snackbar.LENGTH_LONG)
                    .setAction("Settings", new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            startActivityForResult(new Intent(android.provider.Settings.ACTION_SETTINGS), 0);
                        }
                    }).show();
        }
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
    public void onLoadSuccess(final HashMap<String, ArrayList<DayPaymentsItem>> daypaymentchild, final ArrayList<DayPaymentHeader> daypaymenthead) {
       payments.setVisibility(View.VISIBLE);
        Double tot=0.0;
        for (int k=0;k<daypaymenthead.size();k++)
       {
           tot+=Double.parseDouble(daypaymenthead.get(k).getTotal());
       }
       TotalAmount.setText(format.format(new BigDecimal(String.valueOf(tot))));
        DayPaymentsListAdapter adapter=new DayPaymentsListAdapter(this,daypaymenthead,daypaymentchild);
        payments.setAdapter(adapter);
        payments.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
                if(daypaymentchild.get(daypaymenthead.get(groupPosition).getVenture()).get(childPosition).getAmount().equals("0.0"))
                {
                    Snackbar.make(v, "No Transactions Found", Snackbar.LENGTH_LONG).show();
                }
                else {
                    Intent paymenttran = new Intent(PaymentsActivity.this, PaymentsTransation.class);
                    paymenttran.putExtra("ventureName", daypaymenthead.get(groupPosition).getVenture());
                    paymenttran.putExtra("user_type", daypaymentchild.get(daypaymenthead.get(groupPosition).getVenture()).get(childPosition).getUser_type());
                    paymenttran.putExtra("total", daypaymentchild.get(daypaymenthead.get(groupPosition).getVenture()).get(childPosition).getAmount());
                    paymenttran.putExtra("type", daypaymentchild.get(daypaymenthead.get(groupPosition).getVenture()).get(childPosition).getAcc_type());
                    paymenttran.putExtra("date", date);
                    startActivity(paymenttran);
                }
                return true;
            }
        });
    }
}
