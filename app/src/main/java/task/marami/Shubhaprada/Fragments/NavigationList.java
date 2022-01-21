package task.marami.Shubhaprada.Fragments;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.location.LocationManager;
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
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;

import java.util.ArrayList;

import task.marami.Shubhaprada.Interfaces.IFListLayout;
import task.marami.Shubhaprada.LocationView;
import task.marami.Shubhaprada.Models.NavigationListAdapter;
import task.marami.Shubhaprada.Models.ProjectsData;
import task.marami.Shubhaprada.Presenters.NavigationLayoutPresenter;
import task.marami.Shubhaprada.R;
import task.marami.Shubhaprada.Utils.ConnectionDirectory;
import task.marami.Shubhaprada.Utils.Contents;
import task.marami.Shubhaprada.Utils.SingletonAlertDialog;


public class NavigationList extends Fragment implements
        IFListLayout.ListLayoutView{

    ListView list_navi;
    ProgressBar progressBar;
    NavigationLayoutPresenter NLP;
    private static final int Error_Dailog_Request = 9001;
    public static NavigationList newInstance()
    {
        NavigationList navigationList = new NavigationList();
        return navigationList;
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
        View view=inflater.inflate(R.layout.fragment_navigation_list,container ,false );
        list_navi = (ListView) view.findViewById(R.id.list_map_view);
        progressBar = (ProgressBar) view.findViewById(R.id.prog_map_list);

        NLP=new NavigationLayoutPresenter(getContext(),this);
        if(ConnectionDirectory.isConnected(getContext()))
        NLP.onLayoutsData();
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
    public void onSuccess(ArrayList<ProjectsData> projectsData) {
        NavigationListAdapter lla=new NavigationListAdapter(projectsData,getContext());
        list_navi.setAdapter(lla);
        Contents.utiProjData=projectsData;
        list_navi.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(isServiceOK()) {
                    Intent Locationview = new Intent(getActivity(), LocationView.class);
                    Locationview.putExtra("Title", Contents.utiProjData.get(position).getTitle());
                    Locationview.putExtra("Lon", Contents.utiProjData.get(position).getLongitude());
                    Locationview.putExtra("Lat", Contents.utiProjData.get(position).getLatitude());
                    startActivity(Locationview);
                }
                else
                {
                    Toast.makeText(getActivity(),"Please Turn On Gps This required Gps" ,Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    public boolean isServiceOK()
    {

        int Available= GoogleApiAvailability.getInstance().isGooglePlayServicesAvailable(getActivity());
        if(Available== ConnectionResult.SUCCESS)
        {
            final LocationManager manager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE );

            if ( !manager.isProviderEnabled( LocationManager.GPS_PROVIDER ) ) {
                Intent gpsOptionsIntent = new Intent(
                        android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                startActivity(gpsOptionsIntent);
                return false;
            }
            else {
                return true;
            }
        }
        else if(GoogleApiAvailability.getInstance().isUserResolvableError(Available)){
            Dialog d1=GoogleApiAvailability.getInstance().getErrorDialog(getActivity(), Available, Error_Dailog_Request);
            d1.show();
        }else{
            Toast.makeText(getActivity(), "You can't Make Map Request", Toast.LENGTH_SHORT).show();
        }
        return false;
    }
    @Override
    public void onError(String message) {
        SingletonAlertDialog.alertDialogShow(getContext(),message);
    }
}
