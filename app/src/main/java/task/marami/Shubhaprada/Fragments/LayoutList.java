package task.marami.Shubhaprada.Fragments;

import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.material.snackbar.Snackbar;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;

import java.util.ArrayList;

import task.marami.Shubhaprada.Interfaces.IFListLayout;
import task.marami.Shubhaprada.Models.LayoutsListAdapter;
import task.marami.Shubhaprada.Models.ProjectsData;
import task.marami.Shubhaprada.Presenters.ListLayoutPresenter;
import task.marami.Shubhaprada.R;
import task.marami.Shubhaprada.Utils.ConnectionDirectory;
import task.marami.Shubhaprada.Utils.Contents;
import task.marami.Shubhaprada.Utils.SingletonAlertDialog;
import task.marami.Shubhaprada.WebActivity;


public class LayoutList extends Fragment implements IFListLayout.ListLayoutView{
    ProgressBar progressBar;
    ListView list_layouts;
    ListLayoutPresenter presenter;

    public static LayoutList newInstance()
    {
        LayoutList layoutList=new LayoutList();
        return layoutList;
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
        View view=inflater.inflate(R.layout.fragment_layout_list, container, false);
        progressBar = (ProgressBar) view.findViewById(R.id.prog_list);
        list_layouts = (ListView) view.findViewById(R.id.list_layout);

        presenter=new ListLayoutPresenter(getContext(),this);
        if(ConnectionDirectory.isConnected(getContext()))
        presenter.onLayoutsData();
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
        return view;
    }

    @Override
    public void onStartProgress() {
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void onStopProgress() {
        progressBar.setVisibility(View.GONE);
    }

    @Override
    public void onSuccess(final ArrayList<ProjectsData> projectsData) {
        LayoutsListAdapter lla=new LayoutsListAdapter(projectsData,getContext());
        list_layouts.setAdapter(lla);
        list_layouts.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent webact=new Intent(getActivity(), WebActivity.class);
                Contents.utiProjData=projectsData;
                Contents.utiprojectsData=projectsData.get(position);
                startActivity(webact);
            }
        });
    }

    @Override
    public void onError(String message) {
        SingletonAlertDialog.alertDialogShow(getContext(),message);
    }

}
