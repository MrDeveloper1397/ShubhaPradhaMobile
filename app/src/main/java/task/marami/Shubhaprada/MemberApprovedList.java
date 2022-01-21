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

import task.marami.Shubhaprada.Interfaces.IMember_Approved_List;
import task.marami.Shubhaprada.Models.ApprPassbookList;
import task.marami.Shubhaprada.Models.ApprovedPlistAdapter;
import task.marami.Shubhaprada.Models.PassBookListAdapter;
import task.marami.Shubhaprada.Models.PlistApprovalCount;
import task.marami.Shubhaprada.Presenters.Member_Approved_Presenter;
import task.marami.Shubhaprada.Utils.ConnectionDirectory;
import task.marami.Shubhaprada.Utils.Contents;
import task.marami.Shubhaprada.Utils.SingletonAlertDialog;

public class MemberApprovedList extends AppCompatActivity
        implements IMember_Approved_List.Member_Approved_View{
    RecyclerView recy_plist;
    ListView list_pblist;
    ProgressBar progressBar;
    Member_Approved_Presenter presenter;
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
        setContentView(R.layout.activity_member_approved_list);
        init();
        view = getWindow().getDecorView();
        presenter = new Member_Approved_Presenter(this,this);
        if(ConnectionDirectory.isConnected(this))
            presenter.onLoadPLClist();
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
                Intent apprscr=new Intent(MemberApprovedList.this,ApprovalScreen.class);
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
    void init()
    {
        recy_plist = (RecyclerView) findViewById(R.id.recy_approved_plist);
        list_pblist = (ListView) findViewById(R.id.list_approved_pblist);
        progressBar = (ProgressBar) findViewById(R.id.prog_mem_approved_list);
        feb_search = (FloatingActionButton) findViewById(R.id.feb_appr_search);
        edt_search = (EditText) findViewById(R.id.edt_appr_serch);
        crd_edt_ser = (CardView) findViewById(R.id.card_edt_ser);
        feb_search.setVisibility(View.GONE);
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
                = new LinearLayoutManager(MemberApprovedList.this, LinearLayoutManager.HORIZONTAL, false);
        recy_plist.setLayoutManager(horizontalLayoutManagaer);
        ApprovedPlistAdapter pacla=new ApprovedPlistAdapter(pca, this);
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
        SingletonAlertDialog.alertDialogShow(MemberApprovedList.this,message);
    }
    public void plistItemSel(int position)
    {
        plistpos=position;
        if(ConnectionDirectory.isConnected(this))
            presenter.onPassBookList(position);
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
        PBLA=new PassBookListAdapter(Contents.utiPassbookList);
        list_pblist.setAdapter(PBLA);
        edt_search.getText().clear();
        super.onRestart();
    }
    @Override
    public void onBackPressed() {

        if(isSerchVisible)
        {
            crd_edt_ser.setVisibility(View.GONE);
            feb_search.setVisibility(View.VISIBLE);
            edt_search.getText().clear();
            isSerchVisible=false;
        }
        else
        {
            super.onBackPressed();
        }
    }
}
