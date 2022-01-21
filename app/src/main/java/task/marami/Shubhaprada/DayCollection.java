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

import task.marami.Shubhaprada.Interfaces.IDayCollection;
import task.marami.Shubhaprada.Models.CollectionChild;
import task.marami.Shubhaprada.Models.CollectionHeaderData;
import task.marami.Shubhaprada.Models.CollectionItemData;
import task.marami.Shubhaprada.Models.ExpandableListAdapter;
import task.marami.Shubhaprada.Presenters.DayCollectionPresenter;
import task.marami.Shubhaprada.Utils.ConnectionDirectory;
import task.marami.Shubhaprada.Utils.Contents;
import task.marami.Shubhaprada.Utils.SingletonAlertDialog;

public class DayCollection extends AppCompatActivity  implements IDayCollection.DayCollectionView{
    ExpandableListView list_collection;
    DayCollectionPresenter presenter;
    TextView collection;
    ProgressBar progressBar;
    FloatingActionButton cal_btn;
    String date="now";
    private DatePickerDialog.OnDateSetListener mDateSetListener;
    @SuppressLint("NewApi")
    Format format = NumberFormat.getCurrencyInstance(new Locale("en", "in"));

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_day_collection);
        list_collection=(ExpandableListView) findViewById(R.id.lst_collection);
        collection=(TextView) findViewById(R.id.txt_day_col_total);
        collection.setText(format.format(new BigDecimal(getIntent().getStringExtra("TotalCollection"))));
        list_collection.setVisibility(View.GONE);
        progressBar = (ProgressBar) findViewById(R.id.prog_coll_day);
        cal_btn = (FloatingActionButton) findViewById(R.id.feb_calender_daycol);
        presenter=new DayCollectionPresenter(this,this);
        presenter.onLoad();

        cal_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog dialog = new DatePickerDialog(
                        DayCollection.this,
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
                collection.setText("0.0");
                list_collection.setVisibility(View.GONE);
            }
        };
    }

    void checkDataconnection(String date)
    {
        if(ConnectionDirectory.isConnected(this))
        {
            presenter.getCollectionByday(date);
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
    public void onLoadSuccess(final HashMap<String,ArrayList<CollectionChild>> collectionChild, final ArrayList<CollectionHeaderData> collectionHeaderData) {
       Double tot=0.0;
        for(int k=0;k<collectionHeaderData.size();k++)
       {
           tot+=Double.parseDouble(collectionHeaderData.get(k).getTotal_amount());
       }
       collection.setText(format.format(new BigDecimal(String.valueOf(tot))));
        list_collection.setVisibility(View.VISIBLE);
        ExpandableListAdapter ELV=new ExpandableListAdapter(this,collectionHeaderData,collectionChild);
        list_collection.setAdapter(ELV);

        list_collection.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
                Intent trasaction=new Intent(DayCollection.this,CollectionTransations.class);
                trasaction.putExtra("ventureCd", collectionHeaderData.get(groupPosition).getHeader_cd());
                trasaction.putExtra("ventureName", collectionHeaderData.get(groupPosition).getHeader_title());
                trasaction.putExtra("accType",  collectionChild.get(collectionHeaderData.get(groupPosition).getHeader_title()).get(childPosition).getAcount_type());
                trasaction.putExtra("accCode",  collectionChild.get(collectionHeaderData.get(groupPosition).getHeader_title()).get(childPosition).getAcc_code());
                trasaction.putExtra("total",collectionChild.get(collectionHeaderData.get(groupPosition).getHeader_title()).get(childPosition).getAmount());
                trasaction.putExtra("date",date);
                startActivity(trasaction);
                return false;
            }
        });
    }

    @Override
    public void onError(String message) {
        SingletonAlertDialog.alertDialogShow(DayCollection.this,message);
    }

    @Override
    public void onStartprog() {
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void onStopProg() {
        progressBar.setVisibility(View.GONE);
    }

    @Override
    public void onDayCollectionSuccess(ArrayList<CollectionItemData> response) {
        Contents.uticolitemdata=response;
        presenter.onLoad();
    }
}
