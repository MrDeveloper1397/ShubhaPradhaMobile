package task.marami.Shubhaprada.Fragments;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.material.snackbar.Snackbar;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import task.marami.Shubhaprada.Interfaces.IFPlotApproval;
import task.marami.Shubhaprada.Models.PlotCostApproval;
import task.marami.Shubhaprada.Presenters.FPlotApprovalPresenter;
import task.marami.Shubhaprada.R;
import task.marami.Shubhaprada.Utils.ConnectionDirectory;
import task.marami.Shubhaprada.Utils.Contents;
import task.marami.Shubhaprada.Utils.SingletonAlertDialog;


public class PlotApproval extends Fragment implements
        IFPlotApproval.FPlotApprovalView,View.OnClickListener {

    TextView HVentureid,HSectorId,HPlotNo,HPbno,HName,HeaderTitplot,caltype,ApplicentDoj;
    TextView plotPreminum,plotCatagory,PlotArea,Facing,Spl_Premium;
    EditText edt_remarks;
    ProgressBar progressBar;
    Button b1;
    String unittype,name,plot_type;
    int plistpos,pblistpos;
    FPlotApprovalPresenter fPlotApprovalPresenter;
    LinearLayout approved_sus,remaks,bot;

    public PlotApproval()
    {

    }

    public static PlotApproval newInstance()
    {
        PlotApproval plotApproval=new PlotApproval();
        return plotApproval;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
       View PlotApproval=inflater.inflate(R.layout.fragment_plot_approval, container, false);
       plotPreminum=(TextView) PlotApproval.findViewById(R.id.txt_plot_preminum);
       plotCatagory=(TextView) PlotApproval.findViewById(R.id.txt_plot_catagory);
       PlotArea=(TextView) PlotApproval.findViewById(R.id.PlotArea);
       Facing=(TextView) PlotApproval.findViewById(R.id.txt_plot_facing);
       Spl_Premium=(TextView) PlotApproval.findViewById(R.id.txt_spl_Premium);
       edt_remarks=(EditText)PlotApproval.findViewById(R.id.edt_plot_remarks);
       HVentureid = (TextView) PlotApproval.findViewById(R.id.Headervenid);
       HSectorId = (TextView) PlotApproval.findViewById(R.id.Headersecid);
       HPlotNo = (TextView) PlotApproval.findViewById(R.id.Headerplot);
       HPbno = (TextView) PlotApproval.findViewById(R.id.Headerpassbook);
       HName = (TextView) PlotApproval.findViewById(R.id.Headername);
       ApplicentDoj=(TextView) PlotApproval.findViewById(R.id.ApplicentDoj);
       HeaderTitplot=(TextView) PlotApproval.findViewById(R.id.HeaderTitplot);
       caltype=(TextView) PlotApproval.findViewById(R.id.caltype);
       progressBar=(ProgressBar) PlotApproval.findViewById(R.id.prog_plot_approval);
       b1=(Button) PlotApproval.findViewById(R.id.btnPlot);

        approved_sus = (LinearLayout) PlotApproval.findViewById(R.id.liner_approved_success);
        remaks = (LinearLayout) PlotApproval.findViewById(R.id.layout_remarks);
        bot = (LinearLayout) PlotApproval.findViewById(R.id.layout_plot_bot);

       b1.setOnClickListener(this);

       plistpos=getArguments().getInt("plistpos");
       pblistpos=getArguments().getInt("passbookpos");
       unittype= Contents.utiplistdata.get(plistpos).getUnit_cd();
       name=Contents.utiPassbookList.get(pblistpos).getMember_name();
       plot_type=Contents.utiplistdata.get(plistpos).getPlot_type();
        if(Contents.utiPassbookList.get(pblistpos).getPlot_appr().equals("Y"))
        {
            approved_sus.setVisibility(View.VISIBLE);
            remaks.setVisibility(View.GONE);
            bot.setVisibility(View.GONE);
        }

       fPlotApprovalPresenter = new FPlotApprovalPresenter(getContext(),this);
       if(ConnectionDirectory.isConnected(getContext()))
       fPlotApprovalPresenter.onLoad(plistpos,pblistpos);
       else
       {
           Snackbar.make(container, Contents.No_Internet, Snackbar.LENGTH_LONG)
                   .setAction("Settings", new View.OnClickListener() {
                       @Override
                       public void onClick(View view) {
                           startActivityForResult(new Intent(android.provider.Settings.ACTION_SETTINGS), 0);
                       }
                   }).show();
       }

       return PlotApproval;
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
    public void onLoadSuccess() {

        PlotCostApproval pcost= Contents.utiPlotCostData.get(0);
        HeaderTitplot.setText(plot_type+"No");
        HVentureid.setText(Contents.utiplistdata.get(plistpos).getVen_cd());
        HPbno.setText(Contents.utiPassbookList.get(pblistpos).getMember_id());
        ApplicentDoj.setText(Contents.utiPassbookList.get(pblistpos).getDate_of_join());
        HSectorId.setText(pcost.getSector_cd());
        if (!pcost.getFacing().equals("null")) {
            plotCatagory.setText(": "+pcost.getPcateg());
        }else {
            plotCatagory.setText(": " + "");
        }
        if(unittype.equals("sq.yards")){
            caltype.setText("("+"sq.yds"+")");
        }
        HPlotNo.setText(pcost.getPlot_no());
        PlotArea.setText(pcost.getPlot_area());
        if (!pcost.getFacing().equals("null")) {
            Facing.setText(": " + pcost.getFacing());
        }else{
            Facing.setText(": " + "");
        }
        HName.setText(name);

        if (pcost.getPremium().equals("null")) {
            plotPreminum.setText(": " + "0.0");
        } else if (pcost.getRate_per_sqr().equals("Amount")) {
            plotPreminum.setText(": " + pcost.getPremium()+unittype);
        } else {
            plotPreminum.setText(": " + pcost.getPremium()+unittype);
        }

        if (pcost.getSp_premium().equals("null")) {
            Spl_Premium.setText(": "+"0.0" );
        } else if (pcost.getRate_per_sqr().equals("Amount")) {
            Spl_Premium.setText(": " + pcost.getSp_premium()+unittype);
        } else {
            Spl_Premium.setText(": " + pcost.getSp_premium()+unittype);
        }
        CostApproval.pcost=pcost;
    }

    @Override
    public void onChangeApprovalSuccess() {
        Contents.utiPassbookList.get(pblistpos).setPlot_appr("Y");
        b1.setBackgroundColor(Color.parseColor("#ff669900"));
        b1.setText("Approved");
        b1.setEnabled(false);
    }

    @Override
    public void onError(String message) {
        SingletonAlertDialog.alertDialogShow(getContext(),message);
    }

    @Override
    public void onClick(View v) {
        if(ConnectionDirectory.isConnected(getContext())) {
            String remarks = edt_remarks.getText().toString();
            fPlotApprovalPresenter.onChangeApproval(plistpos, pblistpos, remarks, "Plot");
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
}
