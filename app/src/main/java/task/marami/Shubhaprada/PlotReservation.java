package task.marami.Shubhaprada;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;

import task.marami.Shubhaprada.Interfaces.IFReservationPlot;
import task.marami.Shubhaprada.Models.ReservationListAdapter;
import task.marami.Shubhaprada.Models.ReservationModel;
import task.marami.Shubhaprada.Presenters.ReservationPlotPresenter;
import task.marami.Shubhaprada.Utils.ConnectionDirectory;
import task.marami.Shubhaprada.Utils.Contents;
import task.marami.Shubhaprada.Utils.SingletonAlertDialog;

public class PlotReservation extends AppCompatActivity implements
        IFReservationPlot.ReservationPlotView, View.OnClickListener {
    private ListView list_venture_names;
    private TextView txt_vname;
    private EditText edt_plotno;
    private Spinner spi_Sector;
    private Button btn_checkstatus;
    private CardView card_lockplot,card_reservplot,card_realiseplot,card3;
    private ProgressBar progressBar;
    private ReservationPlotPresenter RPP;
    View view;
    int position;
    ReservationModel selmodule;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plot_reservation);

        list_venture_names = findViewById(R.id.list_reser_ventures);
        txt_vname = findViewById(R.id.txt_reser_vname);
        spi_Sector = findViewById(R.id.spi_reser_sector);
        edt_plotno = findViewById(R.id.edt_reser_plotno);
        btn_checkstatus = findViewById(R.id.btn_reser_checkstatus);
        card_lockplot = findViewById(R.id.card_reser_lockplot);
        card_reservplot = findViewById(R.id.card_reser_soldout);
        card_realiseplot = findViewById(R.id.card_reser_relise);
        card3 = findViewById(R.id.cardView3);
        progressBar = findViewById(R.id.prog_reser);
        btn_checkstatus.setOnClickListener(this);
        card_lockplot.setOnClickListener(this);
        card_realiseplot.setOnClickListener(this);
        card_reservplot.setOnClickListener(this);
        view = getWindow().getDecorView();

       /* getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);*/

        RPP=new ReservationPlotPresenter(this,this);
        if(ConnectionDirectory.isConnected(getApplicationContext()))
            RPP.onLoad();
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
    public void onLoadSuccess(final ArrayList<ReservationModel> reservation_models) {
        Contents.utiReservationData=reservation_models;
        ReservationListAdapter RLA=new ReservationListAdapter(reservation_models);
        list_venture_names.setAdapter(RLA);
        list_venture_names.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                card3.setVisibility(View.VISIBLE);
                edt_plotno.setText("");
                card_lockplot.setVisibility(View.GONE);
                card_reservplot.setVisibility(View.GONE);
                card_realiseplot.setVisibility(View.GONE);
                position=position;
                txt_vname.setText(reservation_models.get(position).getVenture_name());
                String phase[]=reservation_models.get(position).getSector().split(",");
                ArrayAdapter<String> adapter=new ArrayAdapter<String>(getApplicationContext(),android.R.layout.simple_expandable_list_item_1, phase);
                spi_Sector.setAdapter(adapter);
            }
        });
    }
   /* @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId()== android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }*/
    @Override
    public void onChangeSuccess(String response) {
        Snackbar mSnackBar = Snackbar.make(view, response, Snackbar.LENGTH_LONG);
        View view = mSnackBar.getView();
        FrameLayout.LayoutParams params =(FrameLayout.LayoutParams)view.getLayoutParams();
        params.gravity = Gravity.TOP;
        view.setLayoutParams(params);
        view.setBackgroundColor(Color.parseColor("#ff669900"));
        TextView mainTextView = (TextView) (view).findViewById(com.google.android.material.R.id.snackbar_text);
        mainTextView.setTextColor(Color.WHITE);
        mSnackBar.show();
    }

    @Override
    public void onError(String message) {
        SingletonAlertDialog.alertDialogShow(PlotReservation.this,message);
    }

    @Override
    public void onAvailable() {
        card_lockplot.setVisibility(View.VISIBLE);
        card_reservplot.setVisibility(View.GONE);
    }

    @Override
    public void onReserved() {
        card_realiseplot.setVisibility(View.VISIBLE);
    }

    @Override
    public void onMortgage() {
        AlertDialog alertDialog1=new AlertDialog.Builder(this).create();
        alertDialog1.setTitle("Not For Sale");
        alertDialog1.setMessage("The Plot NO:-"+selmodule.getPlotno()+" Not for Sale");
        alertDialog1.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
        alertDialog1.show();
    }

    @Override
    public void onSold() {
        AlertDialog alertDialog = new AlertDialog.Builder(this).create();
        alertDialog.setTitle("Sold Out");
        alertDialog.setMessage("The Plot No:-"+selmodule.getPlotno()+"Already Sold");
        alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
        alertDialog.show();
    }

    @Override
    public void onShowlock() {
        card_lockplot.setVisibility(View.VISIBLE);
    }

    @Override
    public void onShowReseve() {
        card_reservplot.setVisibility(View.GONE);
    }

    @Override
    public void onShowRelise() {
        card_realiseplot.setVisibility(View.VISIBLE);
    }

    @Override
    public void onhidelock() {
        card_lockplot.setVisibility(View.GONE);
    }

    @Override
    public void onhideReseve() {
        card_reservplot.setVisibility(View.GONE);
    }

    @Override
    public void onhideRelise() {
        card_realiseplot.setVisibility(View.GONE);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_reser_checkstatus:
                card_lockplot.setVisibility(View.GONE);
                card_reservplot.setVisibility(View.GONE);
                card_realiseplot.setVisibility(View.GONE);
                String Venture=txt_vname.getText().toString();
                for(int i=0;i<Contents.utiReservationData.size();i++)
                {
                    if(Venture.equals(Contents.utiReservationData.get(i).getVenture_name()))
                    {
                        selmodule=Contents.utiReservationData.get(i);
                    }
                }
                selmodule.setPlotno(edt_plotno.getText().toString());
                selmodule.setSector(spi_Sector.getSelectedItem().toString());
                if(ConnectionDirectory.isConnected(getApplicationContext()))
                    if(edt_plotno.getText().toString().trim().isEmpty()){
                        onError("Please Enter Plot Number");
                    }else {
                        RPP.onCheckPlotStatus(selmodule);
                    }
                else
                {
                    Snackbar.make(v, Contents.No_Internet, Snackbar.LENGTH_LONG)
                            .setAction("Settings", new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    startActivityForResult(new Intent(android.provider.Settings.ACTION_SETTINGS), 0);
                                }
                            }).show();
                }
                break;
            case R.id.card_reser_lockplot:
                showAlertmessage("Do you really want to Block This Plot", "R");
                break;
            case R.id.card_reser_relise:
                showAlertmessage("Do you really want to Release The Plot", "N");
                break;
            case R.id.card_reser_soldout:
                showAlertmessage("Do you really want to Buy The Plot", "Y");
                break;
        }
    }
    public void showAlertmessage(String message, final String value)
    {
        new AlertDialog.Builder(this)
                .setTitle("Alert")
                .setMessage(message)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int whichButton) {
                        selmodule.setStatus(value);
                        RPP.onChangePlotStatus(selmodule);
                    }
                })
                .setNegativeButton(android.R.string.no, null).show();
    }


}