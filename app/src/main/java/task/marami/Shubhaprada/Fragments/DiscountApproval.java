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
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import task.marami.Shubhaprada.Models.PlotCostApproval;
import task.marami.Shubhaprada.R;
import task.marami.Shubhaprada.Utils.ConnectionDirectory;
import task.marami.Shubhaprada.Utils.Contents;
import task.marami.Shubhaprada.Utils.MySingleton;
import task.marami.Shubhaprada.Utils.SingletonAlertDialog;
import task.marami.Shubhaprada.Utils.StoragePrefer;
import task.marami.Shubhaprada.Utils.VolleyErrorHandler;

public class DiscountApproval extends Fragment {
    TextView HVentureid,HSectorId,HPlotNo,HPbno,HName,HeaderTitplot,PlotArea,caltype,ApplicentDoj,Comdis,Dis;
    ProgressBar ApprovalProg;
    EditText ed;
    Button b3;
    int plistpos,pblistpos;
    String unittype,name,plot_type;
    LinearLayout approved_sus,remaks,bot;

    public DiscountApproval() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable final ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View DiscountView=inflater.inflate(R.layout.fragment_discount_approval, container, false);
        Comdis=(TextView) DiscountView.findViewById(R.id.CompanyDiscount);
        Dis=(TextView) DiscountView.findViewById(R.id.Discount);
        b3=(Button) DiscountView.findViewById(R.id.btnDiscount);
        HVentureid = (TextView) DiscountView.findViewById(R.id.Headervenid);
        HSectorId = (TextView) DiscountView.findViewById(R.id.Headersecid);
        HPlotNo = (TextView) DiscountView.findViewById(R.id.Headerplot);
        HPbno = (TextView) DiscountView.findViewById(R.id.Headerpassbook);
        HName = (TextView) DiscountView.findViewById(R.id.Headername);
        ed=(EditText) DiscountView.findViewById(R.id.edtText);
        PlotArea = (TextView) DiscountView.findViewById(R.id.PlotArea);
        ApplicentDoj=(TextView) DiscountView.findViewById(R.id.ApplicentDoj);

        approved_sus = (LinearLayout) DiscountView.findViewById(R.id.liner_approved_discount_success);
        remaks = (LinearLayout) DiscountView.findViewById(R.id.layout_remarks);
        bot = (LinearLayout) DiscountView.findViewById(R.id.layout_discount_bot);


        caltype=(TextView) DiscountView.findViewById(R.id.caltype);
        HeaderTitplot=(TextView) DiscountView.findViewById(R.id.HeaderTitplot);

        ApprovalProg=(ProgressBar) DiscountView.findViewById(R.id.ApprovalProg);
        plistpos=getArguments().getInt("plistpos");
        pblistpos=getArguments().getInt("passbookpos");
        unittype= Contents.utiplistdata.get(plistpos).getUnit_cd();
        name=Contents.utiPassbookList.get(pblistpos).getMember_name();
        plot_type=Contents.utiplistdata.get(plistpos).getPlot_type();
        onRender();
        if(Contents.utiPassbookList.get(pblistpos).getDiscou_appr().equals("Y"))
        {
            approved_sus.setVisibility(View.VISIBLE);
            remaks.setVisibility(View.GONE);
            bot.setVisibility(View.GONE);
        }
        b3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(ConnectionDirectory.isConnected(getContext())) {
                    String remarks = ed.getText().toString();
                    ApprovalProg.setVisibility(View.VISIBLE);
                    connectServer(plistpos, pblistpos, remarks, "Discount");
                }
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
            }
        });
        return  DiscountView;
    }
    public void onRender()
    {
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
        PlotArea.setText(pcost.getPlot_area());
        try {
            Comdis.setText(": " + Double.parseDouble(pcost.getCamp_discount()));
            Dis.setText(": " + Double.parseDouble(pcost.getDiscount()));
        }
        catch(NumberFormatException e)
        {
            Toast.makeText(getContext(), e.toString(),Toast.LENGTH_SHORT).show();
        }
    }
    public void connectServer(int plist, int pblist, final String remarks, String Aname)
    {
        StoragePrefer sc =new StoragePrefer(getActivity());
        String passbook=Contents.utiPassbookList.get(pblistpos).getMember_id();
        String venturtecd=Contents.utiplistdata.get(plistpos).getVen_cd();
        String Url =  Contents.BASE_URL+"UpdateApprovalData.php/?PassbookNo=" + passbook + "&VentureId=" + venturtecd + "&Remarks=" + remarks + "&Activity=" + Aname+"&ApiKey="+sc.getSprString(Contents.PREF_KEY_API_TOKEN);
        StringRequest request = new StringRequest(Request.Method.POST, Url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if(response.equals("Success"))
                        {
                            ApprovalProg.setVisibility(View.GONE);
                            Contents.utiPassbookList.get(pblistpos).setDiscou_appr("Y");
                            Toast.makeText(getActivity(), response, Toast.LENGTH_SHORT).show();
                            b3.setBackgroundColor(Color.parseColor("#ff669900"));
                            b3.setText("Approved");
                            b3.setEnabled(false);
                        }
                        else
                        {
                           onError("Please Try Again After Some Time");
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                ApprovalProg.setVisibility(View.GONE);
                onError(VolleyErrorHandler.onVolleyError(error));
            }
        });
        MySingleton.getsInstance(getContext()).getRequestQueue().add(request);
    }
    public void onError(String message) {
        SingletonAlertDialog.alertDialogShow(getContext(),message);
    }
}
