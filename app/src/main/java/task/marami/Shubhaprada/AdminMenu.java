package task.marami.Shubhaprada;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.icu.text.NumberFormat;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.cardview.widget.CardView;

import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.math.BigDecimal;
import java.text.Format;
import java.util.ArrayList;
import java.util.Locale;

import task.marami.Shubhaprada.Interfaces.IAdminMenu;
import task.marami.Shubhaprada.Models.CollectionItemData;
import task.marami.Shubhaprada.Presenters.AdminMenuPresenter;
import task.marami.Shubhaprada.Utils.ConnectionDirectory;
import task.marami.Shubhaprada.Utils.Contents;
import task.marami.Shubhaprada.Utils.SingletonAlertDialog;
import task.marami.Shubhaprada.Utils.StoragePrefer;

public class AdminMenu extends AppCompatActivity
        implements IAdminMenu.AdminMenuView{

    CardView Approvalbtn,PinChange,card_plotmatrix,card_collection,card_payments,card_approved,card_booking;
    ProgressBar progressBar;
    AdminMenuPresenter AMP;
    StoragePrefer sc;
    TextView txt_collection,txt_payments,txt_bookings;
    Double tot=0.0;
    String paymenttot;
    FloatingActionButton refresh;
    View view;
    @SuppressLint("NewApi")
    Format format = NumberFormat.getCurrencyInstance(new Locale("en", "in"));

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_menu);
        view=getWindow().getDecorView();
        Approvalbtn=(CardView)findViewById(R.id.Approvalbtn);
        PinChange=(CardView) findViewById(R.id.PinChange);
        card_plotmatrix=(CardView) findViewById(R.id.card_Plot_matrix);
        progressBar=(ProgressBar) findViewById(R.id.prog_admin_menu);
        card_collection = (CardView) findViewById(R.id.card_day_collection);
        txt_collection=(TextView) findViewById(R.id.txt_adminmenu_collection);
        card_payments = (CardView) findViewById(R.id.card_day_payments);
        txt_payments = (TextView) findViewById(R.id.txt_adminmenu_payments);
        card_approved = (CardView) findViewById(R.id.card_approved);
        txt_bookings = (TextView) findViewById(R.id.txt_booking_count);
        card_booking = (CardView) findViewById(R.id.card_bookings);
        refresh = (FloatingActionButton) findViewById(R.id.feb_refresh);

        sc=new StoragePrefer(this);
        AMP = new AdminMenuPresenter(this, this);
        //check network Status
        if (ConnectionDirectory.isConnected(this)) {
            AMP.onLoad();
        } else {
            //navigate to settings screen
            Snackbar.make(getCurrentFocus(), Contents.No_Internet, Snackbar.LENGTH_LONG)
                    .setAction("Settings", new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            startActivityForResult(new Intent(android.provider.Settings.ACTION_SETTINGS), 0);
                        }
                    }).show();
        }

        //navigate to Approval screen
        Approvalbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(ConnectionDirectory.isConnected(AdminMenu.this)) {
                    Intent Apprllist = new Intent(AdminMenu.this, ApprovalList.class);
                    startActivity(Apprllist);
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
            }
        });
        //navigate to PinChange Screen
        PinChange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent Change_admin_pin=new Intent(AdminMenu.this,ChangeAdminPin.class);
                startActivity(Change_admin_pin);
            }
        });
        //navigate to PlotMatrix Screen
        card_plotmatrix.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent plot_matrix=new Intent(AdminMenu.this,PlotMatrix.class);
                startActivity(plot_matrix);
            }
        });
        //navigate to DayCollection Screen
        card_collection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent day_collection=new Intent(AdminMenu.this,DayCollection.class);
                day_collection.putExtra("TotalCollection", tot.toString());
                startActivity(day_collection);
            }
        });
        //navigate to DayPayments Screen
        card_payments.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent day_payments=new Intent(AdminMenu.this,PaymentsActivity.class);
                day_payments.putExtra("TotalPayments", paymenttot);
                startActivity(day_payments);
            }
        });

        card_approved.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(ConnectionDirectory.isConnected(AdminMenu.this)) {
                    Intent Approvedlist = new Intent(AdminMenu.this,MemberApprovedList.class);
                    startActivity(Approvedlist);
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
            }
        });
        card_booking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(ConnectionDirectory.isConnected(AdminMenu.this)) {
                    Intent Approvedlist = new Intent(AdminMenu.this,BookedPlots.class);
                    startActivity(Approvedlist);
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
            }
        });
        refresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ConnectionDirectory.isConnected(getApplicationContext())) {
                    AMP.onLoad();
                } else {
                    //navigate to settings screen
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
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
        Intent Mainac=new Intent(this,HomeActivity.class);
        startActivity(Mainac);
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
    public void onLoadSuccess(ArrayList<CollectionItemData> response, String paymentTotal, String booking) {
        Contents.uticolitemdata=response;
        tot=0.0;
        for (int i=0;i<response.size();i++)
        {
            tot+=Double.parseDouble(response.get(i).getVenture_collection());
        }
        txt_collection.setText(format.format(new BigDecimal(tot)));
        if(paymentTotal.trim().equals("null"))
        {
            paymenttot="0.0";
            txt_payments.setText(format.format(new BigDecimal(paymenttot)));
        }
        else{
            paymenttot=paymentTotal;
            txt_payments.setText(format.format(new BigDecimal(paymentTotal)));
        }
        if(!booking.trim().equals("null"))
        {
            txt_bookings.setText(booking);
        }
    }

    @Override
    public void onError(String message) {
        SingletonAlertDialog.alertDialogShow(AdminMenu.this,message);
    }

    @Override
    public void hideCollectionList() {
        card_collection.setEnabled(false);
    }
    @Override
    public void hidePayments() {
        card_payments.setEnabled(false);
    }
}
