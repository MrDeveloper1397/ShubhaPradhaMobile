package task.marami.Shubhaprada;

import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import task.marami.Shubhaprada.Interfaces.IWebVenture;
import task.marami.Shubhaprada.Models.ProjectsData;
import task.marami.Shubhaprada.Presenters.WebVenturePresenter;
import task.marami.Shubhaprada.Utils.Contents;

public class WebActivity extends AppCompatActivity implements View.OnClickListener,
        IWebVenture.WebVentureView {
    WebView web_venture;
    ImageView page_error;
    ProgressBar progressBar;
    WebVenturePresenter wvp;
    ProjectsData pd;
    TextView txtAvailable,txtAlloted,txtMortgage,txtRegister,txttotal;
    String Sector,Facing;
    int pos;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web);
        init();

        pd= Contents.utiprojectsData;
        txtAlloted.setText(pd.getAlotted());
        txtAvailable.setText(pd.getAvailable());
        txtMortgage.setText(pd.getMortgage());
        txtRegister.setText(pd.getReserved());
        Facing = pd.getFacing();
        Sector = pd.getSector();
        this.setTitle(pd.getTitle());
        txttotal.setText("Total Plots:"+  pd.getTotalcount());
        wvp=new WebVenturePresenter(this,this);
        wvp.onLoadVenture(pd,web_venture);
    }
    private void init()
    {
        web_venture = (WebView) findViewById(R.id.wev_web_venture);
        progressBar = (ProgressBar) findViewById(R.id.prog_web);
        page_error = (ImageView) findViewById(R.id.web_pageerror);
        txtAvailable = (TextView) findViewById(R.id.availableCount);
        txtAlloted = (TextView) findViewById(R.id.allotted_count);
        txtMortgage = (TextView) findViewById(R.id.mortgage_count);
        txtRegister = (TextView) findViewById(R.id.register_count);
        txttotal = findViewById(R.id.totalCount);
       // findViewById(R.id.fab_web_enq).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
     /*   Bundle bundle = new Bundle();
        Bottom_Sheet_Form BSF=new Bottom_Sheet_Form();
        BSF.show(getSupportFragmentManager(), "BottomSheet");*/
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
    public void onWebError() {
        web_venture.setVisibility(View.GONE);
        page_error.setVisibility(View.VISIBLE);
        findViewById(R.id.fab_web_enq).setVisibility(View.GONE);
    }

    @Override
    public void onLinkError() {
        startActivity(new Intent(this,HomeActivity.class));
        this.finish();
    }
}
