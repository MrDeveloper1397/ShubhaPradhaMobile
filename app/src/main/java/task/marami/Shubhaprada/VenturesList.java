package task.marami.Shubhaprada;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;

import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;

import task.marami.Shubhaprada.Interfaces.IVentures;
import task.marami.Shubhaprada.Models.VenturesListAdapter;
import task.marami.Shubhaprada.Models.VenturesModel;
import task.marami.Shubhaprada.Presenters.VenturesListPresenter;
import task.marami.Shubhaprada.Utils.ConnectionDirectory;
import task.marami.Shubhaprada.Utils.Contents;
import task.marami.Shubhaprada.Utils.SingletonAlertDialog;

public class VenturesList extends AppCompatActivity implements IVentures.VentureListView {
    ListView lvVentures;
    ProgressBar progressBar;
    VenturesListPresenter presenter;
    VenturesListAdapter adapter;
    View view;
    String selectedItembar;
    ArrayList<VenturesModel> arr_ventures;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ventures_list);

       // getSupportActionBar().setDisplayHomeAsUpEnabled(true);
       // getSupportActionBar().setDisplayShowHomeEnabled(true);

        lvVentures = findViewById(R.id.lvVentures);
        progressBar = findViewById(R.id.prog_ventures);
        view = getWindow().getDecorView();
        selectedItembar = getIntent().getStringExtra("item");
        presenter = new VenturesListPresenter(this,this);
        if(ConnectionDirectory.isConnected(this)){
            presenter.onLoad();
        }else{
            Snackbar.make(getCurrentFocus(), Contents.No_Internet, Snackbar.LENGTH_LONG)
                    .setAction("Settings", new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            startActivityForResult(new Intent(android.provider.Settings.ACTION_SETTINGS), 0);
                        }
                    }).show();
        }
        lvVentures.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if(selectedItembar.equals("bookings")) {
                    Intent in = new Intent(getApplicationContext(), BookingSalesReport.class);
                    in.putExtra("ventureCd", arr_ventures.get(i).getVenturecd());
                    in.putExtra("ventureName", arr_ventures.get(i).getVenturename());
                    startActivity(in);
                }else if(selectedItembar.equals("tree")){
                    Intent in = new Intent(getApplicationContext(), AssociateDownline.class);
                    in.putExtra("ventureCd", arr_ventures.get(i).getVenturecd());
                    in.putExtra("ventureName", arr_ventures.get(i).getVenturename());
                    startActivity(in);
                }
            }
        });
    }

    @Override
    public void onStartProgress() {
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void onStopProgress() {
        progressBar.setVisibility(View.GONE);
    }

    @Override
    public void onSuccess(ArrayList<VenturesModel> venturesData) {
        if(venturesData.size() > 0){
            this.arr_ventures = venturesData;
            adapter = new VenturesListAdapter(this,venturesData);
            lvVentures.setAdapter(adapter);
            adapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onError(String message) {
        SingletonAlertDialog.alertDialogShow(VenturesList.this,message);
    }
   /* @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }*/
}