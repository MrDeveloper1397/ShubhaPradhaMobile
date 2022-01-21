package task.marami.Shubhaprada.Fragments;


import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.material.snackbar.Snackbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.text.Layout;
import android.text.method.ScrollingMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import task.marami.Shubhaprada.Interfaces.IFHomeContainer;
import task.marami.Shubhaprada.Models.HomeData;
import task.marami.Shubhaprada.Models.HomeRecyleAdapter;
import task.marami.Shubhaprada.Models.ProjectsData;
import task.marami.Shubhaprada.Presenters.FHomeContainerPresenter;
import task.marami.Shubhaprada.R;
import task.marami.Shubhaprada.Utils.ConnectionDirectory;
import task.marami.Shubhaprada.Utils.Contents;
import task.marami.Shubhaprada.Utils.SingletonAlertDialog;
import task.marami.Shubhaprada.WebActivity;


public class HomeContent extends Fragment implements
        IFHomeContainer.FHomeContainerView{

    FHomeContainerPresenter fHomeContainerPresenter;
    ProgressBar progressBar;
    TextView aboutcontent;
    RecyclerView ongoprog;
    View Container;

    public static HomeContent newInstance(){
        HomeContent homeContent=new HomeContent();
        return  homeContent;
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Container=inflater.inflate(R.layout.content_main, container,false);
        progressBar = (ProgressBar) Container.findViewById(R.id.prog_home1);
        aboutcontent = (TextView) Container.findViewById(R.id.txt_home_context);
        ongoprog = (RecyclerView) Container.findViewById(R.id.recy_home_prog);

        fHomeContainerPresenter=new FHomeContainerPresenter(getContext(),this);
       if(ConnectionDirectory.isConnected(getContext()))
        fHomeContainerPresenter.onHomeData();
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

        return Container;
    }

    @Override
    public void onStartProgress() {
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void onStopProgress() {
        progressBar.setVisibility(View.GONE);
    }

  @SuppressLint("WrongConstant")
  @Override
    public void onSuccess(ArrayList<HomeData> homeData) {
        if(homeData.size() > 0) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                aboutcontent.setJustificationMode(Layout.JUSTIFICATION_MODE_INTER_WORD);
            }
            aboutcontent.setText(homeData.get(0).getContext());
            aboutcontent.setMovementMethod(new ScrollingMovementMethod());
            final LinearLayoutManager horizontalLayoutManagaer
                    = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
            ongoprog.setLayoutManager(horizontalLayoutManagaer);
            HomeRecyleAdapter adapter = new HomeRecyleAdapter(homeData, this);
            ongoprog.setAdapter(adapter);
        }
    }

    @Override
    public void onSuccessContent(JSONObject obj) {
        try {
            aboutcontent.setText(obj.getString("Context"));
            aboutcontent.setMovementMethod(new ScrollingMovementMethod());
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void onError(String message) {
        SingletonAlertDialog.alertDialogShow(getContext(),message);
    }

    public void onGoTOHomeWeb(HomeData data)
    {
        ProjectsData pd=new ProjectsData(data.getProject_id(),data.getProject_title(),data.getLink(),
                data.getSectors(),"",data.getEnq_url(),"","",data.getTotcount(),data.getAvl_count(),data.getAllo_count(),data.getMort_count(),
                data.getRegs_count(),data.getRese_count(),data.getFacing());
        Intent in=new Intent(getActivity(), WebActivity.class);
        Contents.utiprojectsData=pd;
        startActivity(in);
    }
}
