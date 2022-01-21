package task.marami.Shubhaprada.Fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ProgressBar;
import android.widget.Spinner;

import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;

import task.marami.Shubhaprada.Interfaces.IVacantList;
import task.marami.Shubhaprada.Models.PlotMatrixHeader;
import task.marami.Shubhaprada.Models.VacantListAdapter;
import task.marami.Shubhaprada.Models.VacantListHeaderRecyAdp;
import task.marami.Shubhaprada.Models.VacantListItems;
import task.marami.Shubhaprada.Models.VentureNamesList;
import task.marami.Shubhaprada.Presenters.VacantListPresenter;
import task.marami.Shubhaprada.R;
import task.marami.Shubhaprada.Utils.ConnectionDirectory;
import task.marami.Shubhaprada.Utils.Contents;
import task.marami.Shubhaprada.Utils.SingletonAlertDialog;

public class VacantList extends Fragment implements IVacantList.VacantListView {
    ProgressBar progressBar;
    RecyclerView vacantList;
    VacantListPresenter presenter;
    ArrayList<PlotMatrixHeader> newheader = new ArrayList<>();
    ArrayList<VacantListItems> newheaderList = new ArrayList<>();
    RecyclerView rec_headers, rec_vacant;
    private VacantListAdapter vla;
    String ventureID;
    Spinner spVentures;
    private ArrayList<VentureNamesList> arr_ventureslist = new ArrayList<>();

    public static VacantList newInstance() {
        VacantList vacantList = new VacantList();
        return vacantList;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_vacant_list, container, false);
        progressBar = view.findViewById(R.id.prog_vlist);
        vacantList = view.findViewById(R.id.llVacantList);
        rec_vacant = view.findViewById(R.id.recy_vacant);
      /*
        rec_headers = view.findViewById(R.id.recy_header);*/
        spVentures = view.findViewById(R.id.spVacantVentures);
        presenter = new VacantListPresenter(getContext(), this);
        if (ConnectionDirectory.isConnected(getContext())) {
            presenter.onLoad();
        } else {
            Snackbar.make(container, Contents.No_Internet, Snackbar.LENGTH_LONG)
                    .setAction("Settings", new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            startActivityForResult(new Intent(android.provider.Settings.ACTION_SETTINGS), 0);
                        }
                    }).show();
        }
        spVentures.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                ventureID = arr_ventureslist.get(position).getVenturecd();
                onVentureSelect(ventureID);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
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
    public void onSuccess(ArrayList<VacantListItems> projectsData) {
        VacantListItems items = new VacantListItems("PlotNo", "Plot Area", "Facing");
        newheaderList.add(items);
        LinearLayoutManager verticalmanager
                = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        vacantList.setLayoutManager(verticalmanager);
        vla = new VacantListAdapter(projectsData, this);
        vacantList.setAdapter(vla);
    }

    @Override
    public void onLoadSuccess(ArrayList<PlotMatrixHeader> header, ArrayList<VentureNamesList> ventureNamesLists) {
       /* JSONArray jsonArray = ventures;
        List<String> VentureList = new ArrayList<String>();
        for (int i = 0; i < jsonArray.length(); i++) {
            try {
                VentureList.add(jsonArray.getString(i));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }*/
        this.arr_ventureslist = ventureNamesLists;
        List<String> VentureList = new ArrayList<String>();
        List<String> VentureNameList = new ArrayList<String>();
        for (int i = 0; i < ventureNamesLists.size(); i++) {
            VentureList.add(ventureNamesLists.get(i).getVenturecd());
            VentureNameList.add(ventureNamesLists.get(i).getVenShortName());
            ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, VentureNameList);
            dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spVentures.setAdapter(dataAdapter);
        }
        Contents.utiplotmatrixheader = header;
        PlotMatrixHeader pa0 = new PlotMatrixHeader("Venture", "Avl.Plots", "Extent", "", "Sector","");
        newheader.add(pa0);
        for (int i = 0; i < header.size(); i++) {
            String VentureId = header.get(i).getVentureCd();
            if (VentureList.contains(VentureId)) {
                for (int j = 0; j < header.size(); j++) {
                    if (VentureId.equals(header.get(j).getVentureCd())) {
                        if (header.get(j).getStatus().equals("header")) {
                            PlotMatrixHeader pa = new PlotMatrixHeader(VentureId, header.get(j).getTotal(), header.get(j).getExtend(), "", header.get(j).getSector(),header.get(j).getVentureName());
                            newheader.add(pa);
                        }
                    }
                }
            }

        }
        /*LinearLayoutManager horizontalLayoutManagaer
                = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        rec_headers.setLayoutManager(horizontalLayoutManagaer);
        VacantListVentureTitles pacla = new VacantListVentureTitles(this, VentureList);
        rec_headers.setAdapter(pacla);*/
    }

   public void onVentureSelect(String vcd) {
        vacantList.setAdapter(null);
        LinearLayoutManager horizontalLayoutManagaer
                = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        rec_vacant.setLayoutManager(horizontalLayoutManagaer);
        VacantListHeaderRecyAdp pacla = new VacantListHeaderRecyAdp(this, newheader, vcd);
        rec_vacant.setAdapter(pacla);
        pacla.notifyDataSetChanged();
    }

    public void itemselect(String vcd, String Ser) {
        presenter.onVacantList(vcd, Ser);
    }

    @Override
    public void onError(String message) {
        SingletonAlertDialog.alertDialogShow(getContext(),message);
    }
}