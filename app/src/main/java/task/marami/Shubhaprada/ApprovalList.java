package task.marami.Shubhaprada;

import android.content.Intent;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;

import java.util.ArrayList;

import task.marami.Shubhaprada.Interfaces.IApprovalList;
import task.marami.Shubhaprada.Models.ApprPassbookList;
import task.marami.Shubhaprada.Models.PassBookListAdapter;
import task.marami.Shubhaprada.Models.PlistApprovalCount;
import task.marami.Shubhaprada.Models.PlistApprovalCountListAdapter;
import task.marami.Shubhaprada.Presenters.ApprovalListPresenter;
import task.marami.Shubhaprada.Utils.ConnectionDirectory;
import task.marami.Shubhaprada.Utils.Contents;
import task.marami.Shubhaprada.Utils.SingletonAlertDialog;

public class ApprovalList extends AppCompatActivity implements IApprovalList.ApprovalListView{

    RecyclerView recy_plist;
    ListView list_pblist;
    ProgressBar progressBar;
    ApprovalListPresenter approvalListPresenter;
    FloatingActionButton feb_search;
    EditText edt_search;
    CardView crd_edt_ser;
    static int plistpos,passbookpos;
    PassBookListAdapter PBLA;
    boolean isSerchVisible=false;
    View view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_approval_list);
        recy_plist = (RecyclerView) findViewById(R.id.recy_approval_plist);
        list_pblist = (ListView) findViewById(R.id.list_approval_pblist);
        progressBar = (ProgressBar) findViewById(R.id.prog_apptoval_list);
        feb_search = (FloatingActionButton) findViewById(R.id.feb_appr_search);
        edt_search = (EditText) findViewById(R.id.edt_appr_serch);
        crd_edt_ser = (CardView) findViewById(R.id.card_edt_ser);
        feb_search.setVisibility(View.GONE);
        view=getWindow().getDecorView();
        approvalListPresenter = new ApprovalListPresenter(this,this);
        if(ConnectionDirectory.isConnected(this))
            approvalListPresenter.onLoadPLClist();
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

        list_pblist.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                isSerchVisible=false;
                crd_edt_ser.setVisibility(View.GONE);
                feb_search.setVisibility(View.VISIBLE);
                // passbookpos=position;
                for (int i=0;i< Contents.utiPassbookList.size();i++)
                {
                    if(String.valueOf(id).equals(Contents.utiPassbookList.get(i).getMember_id()))
                    {
                        passbookpos=i;
                        break;
                    }
                }
                Intent apprscr=new Intent(ApprovalList.this,ApprovalScreen.class);
                apprscr.putExtra("plistpos", plistpos);
                apprscr.putExtra("passbookpos", passbookpos);
                startActivity(apprscr);
            }
        });
        feb_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isSerchVisible=true;
                feb_search.setVisibility(View.GONE);
                crd_edt_ser.setVisibility(View.VISIBLE);
            }
        });
        edt_search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                PBLA.getFilter().filter(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
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
    public void onLoadSuccess(ArrayList<PlistApprovalCount> pca) {
        Contents.utiplistdata=pca;
        LinearLayoutManager horizontalLayoutManagaer
                = new LinearLayoutManager(ApprovalList.this, LinearLayoutManager.HORIZONTAL, false);
        recy_plist.setLayoutManager(horizontalLayoutManagaer);
        PlistApprovalCountListAdapter pacla=new PlistApprovalCountListAdapter(pca,this);
        recy_plist.setAdapter(pacla);
    }

    @Override
    public void onPassbookSuccess(ArrayList<ApprPassbookList> apbl) {
        feb_search.setVisibility(View.VISIBLE);
        Contents.utiPassbookList=apbl;
        PBLA=new PassBookListAdapter(apbl);
        list_pblist.setAdapter(PBLA);
    }

    @Override
    public void onError(String message) {
        SingletonAlertDialog.alertDialogShow(ApprovalList.this,message);
    }
    public void plistItemSel(int position)
    {
        plistpos=position;
        if(ConnectionDirectory.isConnected(this))
            approvalListPresenter.onPassBookList(position);
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
    protected void onRestart() {
        isSerchVisible=false;
        edt_search.getText().clear();
        PBLA=new PassBookListAdapter(Contents.utiPassbookList);
        list_pblist.setAdapter(PBLA);
        if(Contents.utiPassbookList.get(passbookpos).getPlot_appr().equals("Y") &&
                Contents.utiPassbookList.get(passbookpos).getCost_appr().equals("Y") &&
                Contents.utiPassbookList.get(passbookpos).getDiscou_appr().equals("Y") &&
                Contents.utiPassbookList.get(passbookpos).getComm_appr().equals("Y") )
        {
            int count =  Integer.parseInt(Contents.utiplistdata.get(plistpos).getAppr_count());
            String coun=String.valueOf(count-1) ;
            Contents.utiplistdata.get(plistpos).setAppr_count(coun);
            PlistApprovalCountListAdapter pacla=new PlistApprovalCountListAdapter(Contents.utiplistdata,this);
            recy_plist.setAdapter(pacla);
            Contents.utiPassbookList.remove(passbookpos);
            PassBookListAdapter PBLA=new PassBookListAdapter(Contents.utiPassbookList);
            list_pblist.setAdapter(PBLA);
        }
        super.onRestart();
    }
    @Override
    public void onBackPressed() {

        if(isSerchVisible)
        {
            crd_edt_ser.setVisibility(View.GONE);
            feb_search.setVisibility(View.VISIBLE);
            isSerchVisible=false;
            edt_search.getText().clear();
        }
        else
        {
            super.onBackPressed();
        }
    }
}
