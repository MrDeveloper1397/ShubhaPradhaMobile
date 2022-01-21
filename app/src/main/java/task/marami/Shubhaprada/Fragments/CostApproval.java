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

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.math.BigDecimal;
import java.text.Format;
import java.util.Locale;

import task.marami.Shubhaprada.Interfaces.IFPlotApproval;
import task.marami.Shubhaprada.Models.PlotCostApproval;
import task.marami.Shubhaprada.Presenters.FPlotApprovalPresenter;
import task.marami.Shubhaprada.R;
import task.marami.Shubhaprada.Utils.ConnectionDirectory;
import task.marami.Shubhaprada.Utils.Contents;
import task.marami.Shubhaprada.Utils.SingletonAlertDialog;


public class CostApproval extends Fragment implements IFPlotApproval.FPlotApprovalView,View.OnClickListener {
    TextView Plotarea,Rate_per_sql,AdminFee,DevChargs,Premimum,BSP4,BSP6,Others,CampusFund,TotalCost,PlotCost,PaidAmount,PayableAmount;
    Double s;
    TextView HVentureid,HSectorId,HPlotNo,HPbno,HName,HeaderTitplot,caltype,ApplicentDoj;
    ProgressBar ApprovalProg;
    Button b1;
    EditText ed;
    String unittype,name,plot_type;
    int plistpos,pblistpos;
    LinearLayout approved_sus,remaks,bot;
    @SuppressLint("NewApi")
    Format format = NumberFormat.getCurrencyInstance(new Locale("en", "in"));

    public static PlotCostApproval pcost;
    FPlotApprovalPresenter fPlotApprovalPresenter;

    public CostApproval()
    {

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View CostView=inflater.inflate(R.layout.fragment_cost_approval,  container,false);
        Plotarea=(TextView) CostView.findViewById(R.id.PlotArea);
        Rate_per_sql=(TextView) CostView.findViewById(R.id.txt_cost_ratepersqr);
        PlotCost=(TextView) CostView.findViewById(R.id.txt_cost_plotcost);
        AdminFee=(TextView) CostView.findViewById(R.id.txt_cost_adminfee);
        DevChargs=(TextView) CostView.findViewById(R.id.txt_cost_devcharges);
        Premimum=(TextView) CostView.findViewById(R.id.txt_cost_cpreminum);
        BSP4=(TextView) CostView.findViewById(R.id.txt_cost_bsp4);
        BSP6=(TextView) CostView.findViewById(R.id.txt_cost_bsp6);
        Others= (TextView) CostView.findViewById(R.id.txt_cost_others);
        CampusFund=(TextView) CostView.findViewById(R.id.txt_cost_campusfund);
        TotalCost =(TextView) CostView.findViewById(R.id.txt_cost_totalcost);
        PaidAmount = (TextView) CostView.findViewById(R.id.txt_cost_totalpayed);
        PayableAmount = (TextView) CostView.findViewById(R.id.txt_cost_payble);
        HeaderTitplot=(TextView) CostView.findViewById(R.id.HeaderTitplot);
        ApprovalProg=(ProgressBar) CostView.findViewById(R.id.ApprovalProg);

        caltype=(TextView) CostView.findViewById(R.id.caltype);
        HVentureid = (TextView) CostView.findViewById(R.id.Headervenid);
        HSectorId = (TextView) CostView.findViewById(R.id.Headersecid);
        HPlotNo = (TextView) CostView.findViewById(R.id.Headerplot);
        HPbno = (TextView) CostView.findViewById(R.id.Headerpassbook);
        HName = (TextView) CostView.findViewById(R.id.Headername);
        ApplicentDoj=(TextView) CostView.findViewById(R.id.ApplicentDoj);
        approved_sus = (LinearLayout) CostView.findViewById(R.id.liner_approved_cost_success);
        remaks = (LinearLayout) CostView.findViewById(R.id.layout_remarks);
        bot = (LinearLayout) CostView.findViewById(R.id.layout_cost_bot);

        ed=(EditText) CostView.findViewById(R.id.editText);
        b1=(Button) CostView.findViewById(R.id.btnCost);
        b1.setOnClickListener(this);

        plistpos=getArguments().getInt("plistpos");
        pblistpos=getArguments().getInt("passbookpos");
        unittype= Contents.utiplistdata.get(plistpos).getUnit_cd();
        name=Contents.utiPassbookList.get(pblistpos).getMember_name();
        plot_type=Contents.utiplistdata.get(plistpos).getPlot_type();
        if(Contents.utiPassbookList.get(pblistpos).getCost_appr().equals("Y"))
        {
            approved_sus.setVisibility(View.VISIBLE);
            remaks.setVisibility(View.GONE);
            bot.setVisibility(View.GONE);
        }

        fPlotApprovalPresenter = new FPlotApprovalPresenter(getContext(),this);
        if(ConnectionDirectory.isConnected(getActivity()))
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

        return CostView;
    }

    @Override
    public void onStartProg() {
        ApprovalProg.setVisibility(View.VISIBLE);
    }

    @Override
    public void onStopProg() {
        ApprovalProg.setVisibility(View.GONE);
    }

    @Override
    public void onLoadSuccess() {
        PlotCostApproval pcost = Contents.utiPlotCostData.get(0);
        HeaderTitplot.setText(plot_type + "No");
        HVentureid.setText(Contents.utiplistdata.get(plistpos).getVen_cd());
        HPbno.setText(Contents.utiPassbookList.get(pblistpos).getMember_id());
        ApplicentDoj.setText(Contents.utiPassbookList.get(pblistpos).getDate_of_join());
        HSectorId.setText(pcost.getSector_cd());
        if(unittype.equals("sq.yards")){
            caltype.setText("("+"sq.yds"+")");
        }
        HPlotNo.setText(pcost.getPlot_no());
        HName.setText(name);
        Plotarea.setText(pcost.getPlot_area());
        s = Double.parseDouble(pcost.getTotal_cost());

        try {
            PlotCost.setText(format.format(new BigDecimal(s)).toString());
            s += Double.valueOf(pcost.getAdmin_fee()).doubleValue();
            s += Double.parseDouble(pcost.getDev_charges());
            s += Double.parseDouble(pcost.getCost_premium());
            s += Double.parseDouble(pcost.getBsp4());
            s += Double.parseDouble(pcost.getBsp6());
            s += Double.parseDouble(pcost.getOthers());
            s += Double.parseDouble(pcost.getCampus_fund());
            AdminFee.setText("" + Double.valueOf(pcost.getAdmin_fee()).doubleValue());
            Plotarea.setText(pcost.getPlot_area());
            Rate_per_sql.setText("" + Double.parseDouble(pcost.getRate_per_sqr()));

            DevChargs.setText("" + Double.parseDouble(pcost.getDev_charges()));
            Premimum.setText("" + Double.parseDouble(pcost.getCost_premium()));
            BSP4.setText("" + Double.parseDouble(pcost.getBsp4()));
            BSP6.setText("" + Double.parseDouble(pcost.getBsp6()));
            Others.setText("" + Double.parseDouble(pcost.getOthers()));
            CampusFund.setText("" + Double.parseDouble(pcost.getCampus_fund()));

            //TotalCost.setText(s.toString());
            Contents.TotalPlotcost=s;
            TotalCost.setText(format.format(new BigDecimal(s)).toString());
            PaidAmount.setText(format.format(new BigDecimal(pcost.getMember_rec())).toString());

            Double Payble = s-Double.parseDouble(pcost.getMember_rec());
            if(Payble != 0) {
                PayableAmount.setText(format.format(new BigDecimal(Payble)).toString());
            }
            else {
                PayableAmount.setText("Total Paid");
            }
        } catch (NumberFormatException e) {
            Toast.makeText(getActivity(), e.toString(), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onChangeApprovalSuccess() {
        Contents.utiPassbookList.get(pblistpos).setCost_appr("Y");
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
        if(ConnectionDirectory.isConnected(getActivity())) {
            String remarks = ed.getText().toString();
            fPlotApprovalPresenter.onChangeApproval(plistpos, pblistpos, remarks, "Cost");
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
