package task.marami.Shubhaprada.Fragments;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import com.google.android.material.snackbar.Snackbar;
import androidx.appcompat.app.AlertDialog;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;

import task.marami.Shubhaprada.Interfaces.IFPlotMatrixBottomSheet;
import task.marami.Shubhaprada.Models.ApprPassbookList;
import task.marami.Shubhaprada.Models.PlotMPlotData;
import task.marami.Shubhaprada.Models.PlotMPlotListAdapter;
import task.marami.Shubhaprada.Models.ReservationModel;
import task.marami.Shubhaprada.PlotMatrix;
import task.marami.Shubhaprada.Presenters.FPlotMatrixBSheetPresenter;
import task.marami.Shubhaprada.R;
import task.marami.Shubhaprada.Utils.SingletonAlertDialog;

@SuppressLint("ValidFragment")
public class PlotMatrixBottomSheet extends BottomSheetDialogFragment
        implements IFPlotMatrixBottomSheet.FPlotMatrixBSheetView{
    GridView gridView;
    FPlotMatrixBSheetPresenter presenter;
    String venture,status,facing,sector,extent;
    ProgressBar progressBar;
    PlotMPlotListAdapter PMPLA;
    PlotMatrix context;
    String action="";

    public PlotMatrixBottomSheet(PlotMatrix context) {
        this.context = context;
    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=LayoutInflater.from(getContext()).inflate(R.layout.plotmatrixbottomsheet, null );
        gridView=(GridView) view.findViewById(R.id.grid_plotm_bottom);
        TextView btm_vname = (TextView) view.findViewById(R.id.txt_plotm_bottom_vname);
        TextView btm_facing = (TextView) view.findViewById(R.id.txt_plotm_bottom_facing);
        TextView btm_status = (TextView) view.findViewById(R.id.txt_plotm_bottom_status);
        TextView btm_sector = (TextView) view.findViewById(R.id.txt_plotm_bottom_sector);
        progressBar=(ProgressBar) view.findViewById(R.id.prog_bottom_prog);

        presenter=new FPlotMatrixBSheetPresenter(getContext(),this);
        venture=getArguments().getString("venture");
        status=getArguments().getString("status");
        facing=getArguments().getString("facing");
        sector=getArguments().getString("sector");

        btm_vname.setText(venture);
        btm_facing.setText(facing);
        btm_sector.setText(sector);
        switch (status)
        {
            case "N":btm_status.setText("Available");
                break;
            case "Y":btm_status.setText("Alloted");
                break;
            case "M":btm_status.setText("Mortgage");
                break;
            case "P":btm_status.setText("Blocked");
                break;
            case "G":btm_status.setText("Registered");
                break;
            case "R":btm_status.setText("Reserved");
                break;
        }
        presenter.onLoad(venture, status, facing,sector);

        return view;
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
    public void onLoadSuccess(final ArrayList<PlotMPlotData> plotData) {
        PMPLA=new PlotMPlotListAdapter(plotData,status);
        gridView.setAdapter(PMPLA);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
                if(status.equals("N"))
                {
                    AlertDialog.Builder builder;
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        builder = new AlertDialog.Builder(getContext(), android.R.style.Theme_Material_Dialog_Alert);
                    } else {
                        builder = new AlertDialog.Builder(getContext());
                    }
                    extent = plotData.get(position).getPlotarea();
                    builder.setTitle("Reserve Plot")
                            .setMessage("Are you sure you want to Reserve this Plot No : "+plotData.get(position).getPlotno()+"?")
                            .setPositiveButton("Reserve", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getContext(), android.R.style.Theme_Material_Dialog_Alert);
                                    alertDialogBuilder.setMessage("Are you sure, You want to Reserve the plot");
                                            alertDialogBuilder.setPositiveButton("yes",
                                                    new DialogInterface.OnClickListener() {
                                                        @Override
                                                        public void onClick(DialogInterface arg0, int arg1) {
                                                            ReservationModel rm=new ReservationModel(venture,"",sector);
                                                            rm.setPlotno(plotData.get(position).getPlotno());
                                                            action ="reserve";
                                                            rm.setStatus("R");
                                                            presenter.onChangePlotStatus(rm);
                                                        }
                                                    });

                                    alertDialogBuilder.setNegativeButton("No",new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {

                                        }
                                    });

                                    AlertDialog alertDialog = alertDialogBuilder.create();
                                    alertDialog.show();

                                }

                            })
                            .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {

                                }
                            })
                            .setIcon(android.R.drawable.ic_dialog_alert)
                            .show();
                }
                else if(status.equals("Y")) {
                    ReservationModel rm = new ReservationModel(venture, "", sector);
                    rm.setPlotno(plotData.get(position).getPlotno());
                    rm.setStatus("Y");
                    presenter.onApplicentDetails(rm);
                }
                else if(status.equals("R")){
                    AlertDialog.Builder builder;
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        builder = new AlertDialog.Builder(getContext(), android.R.style.Theme_Material_Dialog_Alert);
                    } else {
                        builder = new AlertDialog.Builder(getContext());
                    }
                    extent = plotData.get(position).getPlotarea();
                    builder.setTitle("Release Plot")
                            .setMessage("Are you sure you want to Release this Plot No : "+plotData.get(position).getPlotno()+"?")
                            .setPositiveButton("Release", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {

                                    final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getContext(), android.R.style.Theme_Material_Dialog_Alert);
                                    alertDialogBuilder.setMessage("Are you sure, You want to Release the plot");
                                    alertDialogBuilder.setPositiveButton("yes",
                                            new DialogInterface.OnClickListener() {
                                                @Override
                                                public void onClick(DialogInterface arg0, int arg1) {
                                                    ReservationModel  rm=new ReservationModel(venture,"",sector);
                                                    action ="unreserve";
                                                    rm.setPlotno(plotData.get(position).getPlotno());
                                                    rm.setStatus("N");
                                                    presenter.onChangePlotStatus(rm);
                                                }
                                            });

                                    alertDialogBuilder.setNegativeButton("No",new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {

                                        }
                                    });

                                    AlertDialog alertDialog = alertDialogBuilder.create();
                                    alertDialog.show();

                                }
                            })
                            .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {

                                }
                            })
                            .setIcon(android.R.drawable.ic_dialog_alert)
                            .show();
                }

            }
        });
    }
    public View initPopUpView(ApprPassbookList data)
    {
      View view=LayoutInflater.from(getContext()).inflate(R.layout.applicentnameitem, null );
        TextView PassbookNo=(TextView) view.findViewById(R.id.Passbookno);
        TextView Applicent=(TextView) view.findViewById(R.id.ApplicentName);
        TextView Appdoj=(TextView) view.findViewById(R.id.Applicentjoin);
        PassbookNo.setText(data.getMember_id());
        Applicent.setText(data.getMember_name());
        Appdoj.setText(data.getDate_of_join());
      return view;
    }

    @Override
    public void onError(String message) {
        SingletonAlertDialog.alertDialogShow(getContext(),message);
    }

    @Override
    public void onChangeSuccess(String message) {
        Snackbar mSnackBar = Snackbar.make(getView(), message, Snackbar.LENGTH_SHORT);
        View view = mSnackBar.getView();
        CoordinatorLayout.LayoutParams params =(CoordinatorLayout.LayoutParams)view.getLayoutParams();
        params.gravity = Gravity.TOP;
        view.setLayoutParams(params);
        view.setBackgroundColor(Color.RED);
        TextView mainTextView = (TextView) (view).findViewById(com.google.android.material.R.id.snackbar_text);
        mainTextView.setTextColor(Color.WHITE);
        mSnackBar.show();
        if(action.equals("reserve")) {
            context.calfromBootomSheet(facing, "reserve", sector, venture, extent);
        }
        else {
            context.calfromBootomSheet(facing, "unreserve", sector, venture, extent);
        }
        this.dismiss();
    }

    @Override
    public void onApplicentSuccess(ApprPassbookList data) {
        View view1=initPopUpView(data);
        AlertDialog.Builder builder;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            builder = new AlertDialog.Builder(getContext(), android.R.style.Theme_Material_Light_Dialog_Alert);
        } else {
            builder = new AlertDialog.Builder(getContext());
        }
        builder.setTitle("Member Details")
                .setView(view1)
                .setNegativeButton("Ok", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })
                .show();
    }

}

