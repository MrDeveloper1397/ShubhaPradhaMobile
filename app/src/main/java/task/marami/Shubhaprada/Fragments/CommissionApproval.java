package task.marami.Shubhaprada.Fragments;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.icu.text.NumberFormat;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.material.snackbar.Snackbar;
import androidx.fragment.app.Fragment;
import androidx.cardview.widget.CardView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import java.math.BigDecimal;
import java.text.Format;
import java.util.ArrayList;
import java.util.Locale;

import task.marami.Shubhaprada.Interfaces.IFCommissionApproval;
import task.marami.Shubhaprada.Models.CommissionAdapter;
import task.marami.Shubhaprada.Models.CommissionData;
import task.marami.Shubhaprada.Models.PlotCostApproval;
import task.marami.Shubhaprada.Presenters.FCommissionApprovalPresenter;
import task.marami.Shubhaprada.R;
import task.marami.Shubhaprada.Utils.ConnectionDirectory;
import task.marami.Shubhaprada.Utils.Contents;
import task.marami.Shubhaprada.Utils.SingletonAlertDialog;


public class CommissionApproval extends Fragment implements
        IFCommissionApproval.FCommissionApprovalView,View.OnClickListener {
    TextView HVentureid,HSectorId,HPlotNo,HPbno,HName,HeaderTitplot,PlotArea,caltype,nodata,ApplicentDoj,TotAmount;
    String unittype,name,plot_type;
    int plistpos,pblistpos;
    ListView listView;
    EditText remark;
    Button b1;
    CardView card2;
    LinearLayout bot;
    Double totsum=0.0;
    FCommissionApprovalPresenter fCommissionApprovalPresenter;

    @SuppressLint("NewApi")
    Format format = NumberFormat.getCurrencyInstance(new Locale("en", "in"));


    public CommissionApproval() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View CommissionView=inflater.inflate(R.layout.fragment_commission_approval, container, false);
        HVentureid = (TextView) CommissionView.findViewById(R.id.Headervenid);
        HSectorId = (TextView) CommissionView.findViewById(R.id.Headersecid);
        HPlotNo = (TextView) CommissionView.findViewById(R.id.Headerplot);
        HPbno = (TextView) CommissionView.findViewById(R.id.Headerpassbook);
        HName = (TextView) CommissionView.findViewById(R.id.Headername);
        PlotArea = (TextView) CommissionView.findViewById(R.id.PlotArea);
        caltype=(TextView) CommissionView.findViewById(R.id.caltype);
        HeaderTitplot=(TextView) CommissionView.findViewById(R.id.HeaderTitplot);
        remark=(EditText) CommissionView.findViewById(R.id.Remark);
        b1=(Button) CommissionView.findViewById(R.id.Commibtn);
        ApplicentDoj=(TextView) CommissionView.findViewById(R.id.ApplicentDoj);
        TotAmount=(TextView) CommissionView.findViewById(R.id.ComTotal);
        nodata=(TextView) CommissionView.findViewById(R.id.nodatamsg);
        bot=(LinearLayout) CommissionView.findViewById(R.id.liner_approved_com_success);
        card2=(CardView) CommissionView.findViewById(R.id.card2);
        final LinearLayout l1=(LinearLayout) CommissionView.findViewById(R.id.fotter);
        b1.setOnClickListener(this);
        listView = (ListView) CommissionView.findViewById(R.id.Commissionlist);
        plistpos=getArguments().getInt("plistpos");
        pblistpos=getArguments().getInt("passbookpos");
        unittype= Contents.utiplistdata.get(plistpos).getUnit_cd();
        name=Contents.utiPassbookList.get(pblistpos).getMember_name();
        plot_type=Contents.utiplistdata.get(plistpos).getPlot_type();
        fCommissionApprovalPresenter=new FCommissionApprovalPresenter(getContext(),this);
        if(ConnectionDirectory.isConnected(getContext()))
        fCommissionApprovalPresenter.onLoad(plistpos, pblistpos);
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
        if(Contents.utiPassbookList.get(pblistpos).getComm_appr().equals("Y"))
        {
            b1.setVisibility(View.GONE);
            remark.setVisibility(View.GONE);
            bot.setVisibility(View.VISIBLE);
        }

        return CommissionView;
    }

    @Override
    public void onStartProg() {

    }

    @Override
    public void onStopProg() {

    }

    @Override
    public void onLoadSuccess(ArrayList<CommissionData> commissionData) {
        totsum=0.0;
        PlotCostApproval pcost= Contents.utiPlotCostData.get(0);
        HeaderTitplot.setText(plot_type+"No");
        HVentureid.setText(Contents.utiplistdata.get(plistpos).getVen_cd());
        HPbno.setText(Contents.utiPassbookList.get(pblistpos).getMember_id());
        ApplicentDoj.setText(Contents.utiPassbookList.get(pblistpos).getDate_of_join());
        HSectorId.setText(pcost.getSector_cd());
        if(unittype.equals("sq.yards")){
            caltype.setText("("+"sq.yds"+")");
        }
        HPlotNo.setText(pcost.getPlot_no());
        PlotArea.setText(pcost.getPlot_area());
        HName.setText(name);
        CommissionAdapter CA=new CommissionAdapter(commissionData);
        listView.setAdapter(CA);
        for(int i=0;i<commissionData.size();i++)
        {
            totsum+=Double.parseDouble(commissionData.get(i).getGrossPayable());
        }
       TotAmount.setText(format.format(new BigDecimal(totsum)).toString());
    }

    @Override
    public void onChangeApprovalSuccess() {
        Contents.utiPassbookList.get(pblistpos).setComm_appr("Y");
        b1.setBackgroundColor(Color.parseColor("#ff669900"));
        b1.setText("Approved");
        b1.setEnabled(false);
    }

    @Override
    public void onError(String message) {
        SingletonAlertDialog.alertDialogShow(getContext(),message);
    }
    public void onOffice()
    {
        PlotCostApproval pcost= Contents.utiPlotCostData.get(0);
        HeaderTitplot.setText(plot_type+"No");
        HVentureid.setText(Contents.utiplistdata.get(plistpos).getVen_cd());
        HPbno.setText(Contents.utiPassbookList.get(pblistpos).getMember_id());
        ApplicentDoj.setText(Contents.utiPassbookList.get(pblistpos).getDate_of_join());
        HSectorId.setText(pcost.getSector_cd());
        caltype.setText("("+unittype+")");
        HPlotNo.setText(pcost.getPlot_no());
        PlotArea.setText(pcost.getPlot_area());
        HName.setText(name);
        nodata.setVisibility(View.VISIBLE);
        card2.setVisibility(View.GONE);
    }

    @Override
    public void onClick(View v) {
        if(ConnectionDirectory.isConnected(getContext())) {
            String remarks = remark.getText().toString();
            fCommissionApprovalPresenter.onChangeApproval(plistpos, pblistpos, remarks, "Commission");
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
