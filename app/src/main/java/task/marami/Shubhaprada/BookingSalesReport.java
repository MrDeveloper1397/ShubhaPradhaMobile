package task.marami.Shubhaprada;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import task.marami.Shubhaprada.Interfaces.IBookingSalesReport;
import task.marami.Shubhaprada.Models.BookingDetailsModel;
import task.marami.Shubhaprada.Models.CadresCountAssociates;
import task.marami.Shubhaprada.Models.ExpandableListSalesAdapter;
import task.marami.Shubhaprada.Presenters.BookingSalesPresenter;
import task.marami.Shubhaprada.Utils.ConnectionDirectory;
import task.marami.Shubhaprada.Utils.Contents;
import task.marami.Shubhaprada.Utils.SingletonAlertDialog;
import task.marami.Shubhaprada.Utils.StoragePrefer;

public class BookingSalesReport extends AppCompatActivity implements IBookingSalesReport.view {
    private View view;
    String venturecd, venturename;
    TextView tvVenture;
    BookingSalesPresenter presenter;
    ArrayList<BookingDetailsModel> ass = new ArrayList<>();
    EditText etSalesID;
    Button btnloadSales;
    ProgressBar progressBar;
    List<String> cadrelist = new ArrayList<>();
    private ExpandableListView expandableList;
    ExpandableListSalesAdapter expandableListAdapter;
    ArrayList<CadresCountAssociates> cadresarraylist = new ArrayList<CadresCountAssociates>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking_sales_report);
        expandableList = findViewById(R.id.expandableListView);
        etSalesID = findViewById(R.id.associateSalesID);
        btnloadSales = findViewById(R.id.btnloadSales);
        progressBar = findViewById(R.id.sales_level_prog);
        tvVenture = findViewById(R.id.tvventurename);

       /* getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);*/

        view = getWindow().getDecorView();
        StoragePrefer sp = new StoragePrefer(this);
        venturecd = getIntent().getStringExtra("ventureCd");
        venturename = getIntent().getStringExtra("ventureName");
        if (venturename != null) {
            tvVenture.setText("Venture:" + venturename);
        }
        presenter = new BookingSalesPresenter(getApplicationContext(), this);
        if (sp.getSprString(Contents.PTEF_KEY_USER_TYPE).equals("user")) {
            etSalesID.setVisibility(View.GONE);
            btnloadSales.setVisibility(View.GONE);

            if (ConnectionDirectory.isConnected(getApplicationContext())) {
                presenter.onLoad(venturecd);
            } else {
                Snackbar.make(getCurrentFocus(), Contents.No_Internet, Snackbar.LENGTH_LONG)
                        .setAction("Settings", new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                startActivityForResult(new Intent(android.provider.Settings.ACTION_SETTINGS), 0);
                            }
                        }).show();
            }

        } else {
            etSalesID.setVisibility(View.VISIBLE);
            btnloadSales.setVisibility(View.VISIBLE);
            btnloadSales.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (ConnectionDirectory.isConnected(getApplicationContext())) {
                        if ((etSalesID.getText().toString().trim().length()) == 0) {
                            OnError("Please Enter ID");
                            ass.clear();
                            cadresarraylist.clear();
                            expandableList.setAdapter((ExpandableListSalesAdapter) null);
                        } else {
                            ass.clear();
                            cadresarraylist.clear();
                            presenter.onSalesSearch(venturecd, etSalesID.getText().toString().trim());
                        }
                    } else {
                        Snackbar.make(getCurrentFocus(), Contents.No_Internet, Snackbar.LENGTH_LONG)
                                .setAction("Settings", new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        startActivityForResult(new Intent(android.provider.Settings.ACTION_SETTINGS), 0);
                                    }
                                }).show();
                    }
                }
            });
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
    public void OnError(String msg) {
        SingletonAlertDialog.alertDialogShow(BookingSalesReport.this, msg);
    }

    @Override
    public void onSuccess(String usertype, ArrayList<String> cca, HashMap<String, ArrayList<BookingDetailsModel>> childs, ArrayList<CadresCountAssociates> ccarraylist) {
        if (usertype.equals("user")) {
            cadrelist.clear();
            expandableListAdapter = new ExpandableListSalesAdapter(BookingSalesReport.this, cca, childs, ccarraylist);
            //expandableList.setIndicatorBounds(0, 20);
            expandableList.setAdapter(expandableListAdapter);
            expandableListAdapter.notifyDataSetChanged();
        } else {
            cadrelist.clear();
            this.cadresarraylist = ccarraylist;
            expandableListAdapter = new ExpandableListSalesAdapter(BookingSalesReport.this, cca, childs, cadresarraylist);
            // expandableList.setIndicatorBounds(0, 20);
            expandableList.setAdapter(expandableListAdapter);
            expandableListAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        this.finish();
    }

    /*@Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }*/
}