package task.marami.Shubhaprada;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.View;
import android.widget.AdapterView;
import android.widget.DatePicker;
import android.widget.ListView;
import android.widget.ProgressBar;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import task.marami.Shubhaprada.Interfaces.IBookedPlots;
import task.marami.Shubhaprada.Models.ApprPassbookList;
import task.marami.Shubhaprada.Models.PassBookListAdapter;
import task.marami.Shubhaprada.Models.PlistApprovalCount;
import task.marami.Shubhaprada.Presenters.BookedPlotsPresenter;
import task.marami.Shubhaprada.Presenters.BookedProListAdapter;
import task.marami.Shubhaprada.Utils.ConnectionDirectory;
import task.marami.Shubhaprada.Utils.Contents;
import task.marami.Shubhaprada.Utils.SingletonAlertDialog;

public class BookedPlots extends AppCompatActivity implements
        IBookedPlots.IBookedPlotView,View.OnClickListener{

    BookedPlotsPresenter presenter;
    RecyclerView recy_plist;
    ListView list_pblist;
    static int plistpos,passbookpos;
    FloatingActionButton fabutton;
    private DatePickerDialog.OnDateSetListener mDateSetListener;
    String date="";
    ProgressBar progressBar;
    View view;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booked_plots);
        init();
        date="now";
        checkDataconnection(date);
        view=getWindow().getDecorView();
        fabutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog dialog = new DatePickerDialog(
                        BookedPlots.this,
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
            }
        };
        list_pblist.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                passbookpos=position;
                Intent apprscr=new Intent(BookedPlots.this,ApprovalScreen.class);
                apprscr.putExtra("plistpos", plistpos);
                apprscr.putExtra("passbookpos", passbookpos);
                startActivity(apprscr);
            }
        });
    }
    void init()
    {
        //mDisplayDate = (TextView) findViewById(R.id.tvDate);
        recy_plist = (RecyclerView) findViewById(R.id.recy_booked_plist);
        list_pblist = (ListView) findViewById(R.id.list_booked_pblist);
        fabutton = (FloatingActionButton) findViewById(R.id.feb_booked_find);
        progressBar = (ProgressBar) findViewById(R.id.booked_prog);
        presenter=new BookedPlotsPresenter(this,this);
        Calendar cal = Calendar.getInstance();
        //mDisplayDate.setText(cal.get(Calendar.DAY_OF_MONTH)+"-"+(Integer.parseInt(String.valueOf(cal.get(Calendar.MONTH)))+1)+"-"+cal.get(Calendar.YEAR));

        fabutton.setOnClickListener(this);


    }

    void checkDataconnection(String date)
    {
        if(ConnectionDirectory.isConnected(this))
        {
            presenter.onLoadProjectlist(date);
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
    public void onClick(View v) {
        switch(v.getId())
        {
            case R.id.feb_booked_find:

                break;
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
    public void onLoadSuccess(ArrayList<PlistApprovalCount> pac) {
        Contents.utiplistdata=pac;
        LinearLayoutManager horizontalLayoutManagaer
                = new LinearLayoutManager(BookedPlots.this, LinearLayoutManager.HORIZONTAL, false);
        recy_plist.setLayoutManager(horizontalLayoutManagaer);
        BookedProListAdapter pacla=new BookedProListAdapter(pac,this);
        recy_plist.setAdapter(pacla);
    }

    @Override
    public void onPassbookSuccess(ArrayList<ApprPassbookList> apbl) {
        Contents.utiPassbookList=apbl;
        PassBookListAdapter PBLA=new PassBookListAdapter(apbl);
        list_pblist.setAdapter(PBLA);
    }

    @Override
    public void onError(String message) {
        SingletonAlertDialog.alertDialogShow(BookedPlots.this,message);
    }
    public void plistItemSel(int position)
    {
        plistpos=position;
        if(ConnectionDirectory.isConnected(this))
            presenter.onbookedPassBookList(position,date);
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
}
