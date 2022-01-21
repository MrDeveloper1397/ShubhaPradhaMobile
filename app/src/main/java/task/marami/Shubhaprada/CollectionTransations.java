package task.marami.Shubhaprada;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;

import task.marami.Shubhaprada.Interfaces.ICollectionTransations;
import task.marami.Shubhaprada.Models.CollectionTransactionListAdp;
import task.marami.Shubhaprada.Models.CollectionTransationData;
import task.marami.Shubhaprada.Presenters.CollectionTransationPresenter;
import task.marami.Shubhaprada.Utils.SingletonAlertDialog;

public class CollectionTransations extends AppCompatActivity implements ICollectionTransations.CollectionTransationView{
    TextView total,indicator;
    ListView transation_list;
    ProgressBar progressBar;
    CollectionTransationPresenter presenter;
    String VentureCd,VentureName,AccType,AccCode,date;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_collection_transations);
        init();
        presenter = new CollectionTransationPresenter(this,this);
        presenter.onLoad(VentureCd, AccCode, date);
    }
    void init()
    {
        total = (TextView) findViewById(R.id.txt_transation_total);
        indicator = (TextView) findViewById(R.id.txt_transation_indicator);
        progressBar = (ProgressBar) findViewById(R.id.prog_transation);
        transation_list = (ListView) findViewById(R.id.list_transation);
        VentureCd = getIntent().getStringExtra("ventureCd");
        VentureName = getIntent().getStringExtra("ventureName");
        AccType = getIntent().getStringExtra("accType");
        AccCode = getIntent().getStringExtra("accCode");
        total.setText(getIntent().getStringExtra("total"));
        date=getIntent().getStringExtra("date");
        indicator.setText(VentureName+"->"+AccType);
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
    public void onLoadSuccess(ArrayList<CollectionTransationData> transationData) {
        CollectionTransactionListAdp listAdp=new CollectionTransactionListAdp(transationData,this,AccType);
        transation_list.setAdapter(listAdp);
    }

    @Override
    public void onError(String message) {
        SingletonAlertDialog.alertDialogShow(CollectionTransations.this,message);
    }
}
