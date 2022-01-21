package task.marami.Shubhaprada;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import task.marami.Shubhaprada.Interfaces.IAssociateTree;
import task.marami.Shubhaprada.Models.AssociateTreeTeamWise;
import task.marami.Shubhaprada.Models.CadresCountAssociates;
import task.marami.Shubhaprada.Models.ExpandableListViewAdapter;
import task.marami.Shubhaprada.Presenters.AssociateTreePresenter;
import task.marami.Shubhaprada.Utils.ConnectionDirectory;
import task.marami.Shubhaprada.Utils.Contents;
import task.marami.Shubhaprada.Utils.SingletonAlertDialog;
import task.marami.Shubhaprada.Utils.StoragePrefer;

public class AssociateDownline extends AppCompatActivity implements IAssociateTree.view, View.OnClickListener {
        AssociateTreePresenter presenter;
        View view;
        ArrayList<AssociateTreeTeamWise> associateTreeTeamWiseArrayList = new ArrayList<>();
        String venture, venturename;
        EditText etAssociateID;
        Button btnload;
        EditText edt_tree_search;
        TextView txtventure;
        ProgressBar tree_level_prog;
        List<String> cadrelist = new ArrayList<>();
        ArrayList<CadresCountAssociates> cadresarraylist = new ArrayList<CadresCountAssociates>();
        private ExpandableListView expandableList;
        ExpandableListViewAdapter expandableListAdapter;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
                super.onCreate(savedInstanceState);
                setContentView(R.layout.activity_associate_downline);
                etAssociateID = findViewById(R.id.associateID);
                btnload = findViewById(R.id.btnloadtree);
                /*getSupportActionBar().setDisplayHomeAsUpEnabled(true);
                getSupportActionBar().setDisplayShowHomeEnabled(true);*/
                StoragePrefer sp = new StoragePrefer(this);
                view = getWindow().getDecorView();
                expandableList = findViewById(R.id.expandableListViewTree);
                edt_tree_search = (EditText) findViewById(R.id.associateID);
                txtventure = findViewById(R.id.tvventure);
                tree_level_prog = findViewById(R.id.tree_level_prog);
                venture = getIntent().getStringExtra("ventureCd");
                venturename = getIntent().getStringExtra("ventureName");
                if (venturename != null) {
                        txtventure.setText("Venture:" + venturename);
                }
                presenter = new AssociateTreePresenter(getApplicationContext(), this);
                if (sp.getSprString(Contents.PTEF_KEY_USER_TYPE).equals("user")) {
                        etAssociateID.setVisibility(View.GONE);
                        btnload.setVisibility(View.GONE);
                        if (ConnectionDirectory.isConnected(getApplicationContext())) {
                                presenter.onLoad(venture);
                        } else {
                                Snackbar.make(getCurrentFocus(), Contents.No_Internet, Snackbar.LENGTH_LONG)
                                        .setAction("Settings", new View.OnClickListener() {
                                                @Override
                                                public void onClick(View view) {
                                                        startActivityForResult(new Intent(android.provider.Settings.ACTION_SETTINGS), 0);
                                                }
                                        }).show();
                        }

                } else {
                        etAssociateID.setVisibility(View.VISIBLE);
                        btnload.setVisibility(View.VISIBLE);
                        btnload.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                        if (ConnectionDirectory.isConnected(getApplicationContext())) {

                                                if ((etAssociateID.getText().toString().trim().length()) == 0) {
                                                        OnError("Please Enter ID");
                                                        associateTreeTeamWiseArrayList.clear();
                                                        cadresarraylist.clear();
                                                        expandableList.setAdapter((ExpandableListViewAdapter) null);
                                                } else {
                                                        associateTreeTeamWiseArrayList.clear();
                                                        cadresarraylist.clear();
                                                        presenter.onAssociateSearch(venture, etAssociateID.getText().toString().trim());
                                                }

                                        } else {
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
        }

        @Override
        public void onStartProg() {
                tree_level_prog.setVisibility(View.VISIBLE);
        }

        @Override
        public void onStopProg() {
                tree_level_prog.setVisibility(View.GONE);
        }

        @Override
        public void OnError(String msg) {
                SingletonAlertDialog.alertDialogShow(AssociateDownline.this, msg);
        }

        @Override
        public void onSuccess(String type, ArrayList<String> cca, HashMap<String, ArrayList<AssociateTreeTeamWise>> arr_child, ArrayList<CadresCountAssociates> cadresCountAssociatesArrayList) {
                if (type.equals("user")) {
                        cadrelist.clear();
                        expandableListAdapter = new ExpandableListViewAdapter(AssociateDownline.this, cca, arr_child, cadresCountAssociatesArrayList);
                        expandableList.setAdapter(expandableListAdapter);
                        expandableListAdapter.notifyDataSetChanged();

                } else {
                        cadrelist.clear();
                        this.cadresarraylist = cadresCountAssociatesArrayList;
                        expandableListAdapter = new ExpandableListViewAdapter(AssociateDownline.this, cca, arr_child, cadresarraylist);
                        expandableList.setAdapter(expandableListAdapter);
                        expandableListAdapter.notifyDataSetChanged();
                }

        }

        @Override
        public void onBackPressed() {
                super.onBackPressed();
        }

        @Override
        public void onClick(View v) {
                int id = v.getId();
                View view1 = null;
                TextView tv = findViewById(id);
                if (null != tv) {
                        for (int i = 0; i < associateTreeTeamWiseArrayList.size(); i++) {
                                if (id - 1 == i) {
                                        view1 = initPopUpView(associateTreeTeamWiseArrayList.get(i));
                                }
                        }

                }
                AlertDialog.Builder builder;
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        builder = new AlertDialog.Builder(this, android.R.style.Theme_Material_Light_Dialog_Alert);
                } else {
                        builder = new AlertDialog.Builder(this);
                }
                builder.setTitle("Associate Details")
                        .setView(view1)
                        .setNegativeButton("Ok", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {

                                }
                        })
                        .show();
        }

        public View initPopUpView(AssociateTreeTeamWise data) {

                View view = LayoutInflater.from(this).inflate(R.layout.applicant_dialog, null);
                TextView PassbookNo = (TextView) view.findViewById(R.id.pbno);
                TextView Applicent = (TextView) view.findViewById(R.id.applicantName);
                TextView cadre = (TextView) view.findViewById(R.id.cadre);
                TextView pan = (TextView) view.findViewById(R.id.panno);
                TextView mble = (TextView) view.findViewById(R.id.mobile);
                PassbookNo.setText(data.getWorkUnder());
                Applicent.setText(data.getAgentName());
                cadre.setText(data.getAgentCadre());
                pan.setText(data.getPanNo());
                mble.setText(data.getMobile());
                return view;
        }
}
/*
@Override
public boolean onOptionsItemSelected(MenuItem item){
        if(item.getItemId()==android.R.id.home){
        finish();
        }
        return super.onOptionsItemSelected(item);
        }
        }*/
