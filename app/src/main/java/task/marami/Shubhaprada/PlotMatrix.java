package task.marami.Shubhaprada;

import android.content.Intent;
import android.graphics.Color;
import com.google.android.material.snackbar.Snackbar;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import lecho.lib.hellocharts.model.PieChartData;
import lecho.lib.hellocharts.model.SliceValue;
import lecho.lib.hellocharts.view.PieChartView;
import task.marami.Shubhaprada.Fragments.PlotMatrixBottomSheet;
import task.marami.Shubhaprada.Interfaces.IPlotMatrix;
import task.marami.Shubhaprada.Models.PlotMatrixFacingData;
import task.marami.Shubhaprada.Models.PlotMatrixHeader;
import task.marami.Shubhaprada.Models.PlotMatrixHeaderRecyAdp;
import task.marami.Shubhaprada.Models.VentureNamesList;
import task.marami.Shubhaprada.Presenters.PlotMatrixPresenter;
import task.marami.Shubhaprada.Utils.ConnectionDirectory;
import task.marami.Shubhaprada.Utils.Contents;
import task.marami.Shubhaprada.Utils.SingletonAlertDialog;

public class PlotMatrix extends AppCompatActivity implements View.OnClickListener,
        IPlotMatrix.PlotMatrixView{
    RecyclerView recy_header,rect_venture;
    ProgressBar progressBar;
    PlotMatrixPresenter presenter;
    LinearLayout lin_status;
    TextView allo,aval,mort,block,rese,regi,east,west,north,south,northeast,northwest,southeast,southwest,alloExt,avalExt,mortExt,blockExt,reseExt,regiExt;
    CardView card_east,card_west,card_north,card_south,card_northeast,card_northwest,card_southeast,card_southwest;
    TextView east_ext,west_ext,north_ext,south_ext,northeast_ext,northwest_ext,southeast_ext,southwest_ext;
    String Venrure,status,Sector;
    static String VENTURE_ALERT="Select Venture From List";
    static String STATUS_ALERT="Select Status From above Buttons";
    List<SliceValue> pieData = new ArrayList<>();
    ArrayList<PlotMatrixHeader> newheader=new ArrayList<>();
    List<String> VentureList=new ArrayList<>();
    PieChartView pieChartView;
    ArrayList<PlotMatrixFacingData> FacingList=new ArrayList<>();
    LinearLayout llavl,llalot,llmort,llblock,llregi,llrese;
    Spinner spVentures;
    private ArrayList<VentureNamesList> arr_ventureslist = new ArrayList<>();
    ArrayList<VentureNamesList> ventureNamesLists = new ArrayList<>();
    View view;
    String ventureID;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plot_matrix);
        init();
        view = getWindow().getDecorView();
        presenter=new PlotMatrixPresenter(this,this);
        if(ConnectionDirectory.isConnected(this))
        {
            presenter.onLoad();
        }
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
        spVentures.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                ventureID= arr_ventureslist.get(position).getVenturecd();
                onVentureSelect(ventureID);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }
    void init()
    {
        recy_header=(RecyclerView) findViewById(R.id.recy_plotmatrix);
        spVentures = findViewById(R.id.spPlotVentures);
        //rect_venture = (RecyclerView) findViewById(R.id.recy_plotmatrix_venture);
        progressBar=(ProgressBar) findViewById(R.id.prog_plot_matrix);
        lin_status=(LinearLayout) findViewById(R.id.lin_status_count);
        allo=(TextView) findViewById(R.id.txt_plotm_alloted);
        aval=(TextView) findViewById(R.id.txt_plotm_available);
        mort=(TextView) findViewById(R.id.txt_plotm_mortgage);
        block=(TextView) findViewById(R.id.txt_plotm_blocked);
        rese=(TextView) findViewById(R.id.txt_plotm_reserved);
        regi=(TextView) findViewById(R.id.txt_plotm_registered);
        east=(TextView) findViewById(R.id.txt_plotm_east);
        west=(TextView) findViewById(R.id.txt_plotm_west);
        north=(TextView) findViewById(R.id.txt_plotm_north);
        south=(TextView) findViewById(R.id.txt_plotm_south);
        northeast=(TextView) findViewById(R.id.txt_plotm_northeast);
        northwest=(TextView) findViewById(R.id.txt_plotm_northwest);
        southeast=(TextView) findViewById(R.id.txt_plotm_southeast);
        southwest=(TextView) findViewById(R.id.txt_plotm_southwest);
        pieChartView = findViewById(R.id.chart);

        east_ext=(TextView) findViewById(R.id.txt_plotm_east_ext);
        west_ext=(TextView) findViewById(R.id.txt_plotm_west_ext);
        north_ext=(TextView) findViewById(R.id.txt_plotm_north_ext);
        south_ext=(TextView) findViewById(R.id.txt_plotm_south_ext);
        northeast_ext=(TextView) findViewById(R.id.txt_plotm_northeast_ext);
        northwest_ext=(TextView) findViewById(R.id.txt_plotm_northwest_ext);
        southeast_ext=(TextView) findViewById(R.id.txt_plotm_southeast_ext);
        southwest_ext=(TextView) findViewById(R.id.txt_plotm_southwest_ext);

        card_east = (CardView) findViewById(R.id.card_plotm_east);
        card_west = (CardView) findViewById(R.id.card_plotm_west);
        card_north = (CardView) findViewById(R.id.card_plotm_north);
        card_south = (CardView) findViewById(R.id.card_plotm_south);
        card_northeast = (CardView) findViewById(R.id.card_plotm_northeast);
        card_northwest = (CardView) findViewById(R.id.card_plotm_northwest);
        card_southeast = (CardView) findViewById(R.id.card_plotm_southeast);
        card_southwest = (CardView) findViewById(R.id.card_plotm_southwest);

        card_east.setOnClickListener(this);
        card_west.setOnClickListener(this);
        card_north.setOnClickListener(this);
        card_south.setOnClickListener(this);
        card_northeast.setOnClickListener(this);
        card_northwest.setOnClickListener(this);
        card_southeast.setOnClickListener(this);
        card_southwest.setOnClickListener(this);

        alloExt=(TextView) findViewById(R.id.txt_plotm_alloted_extend);
        avalExt=(TextView) findViewById(R.id.txt_plotm_available_extend);
        mortExt=(TextView) findViewById(R.id.txt_plotm_mortgage_extend);
        blockExt=(TextView) findViewById(R.id.txt_plotm_blocked_extend);
        reseExt=(TextView) findViewById(R.id.txt_plotm_reserved_extend);
        regiExt=(TextView) findViewById(R.id.txt_plotm_registered_extend);

        llalot=findViewById(R.id.llalotted);
        llavl=findViewById(R.id.llavailable);
        llmort=findViewById(R.id.llmortgage);
        llblock=findViewById(R.id.llblocked);
        llrese=findViewById(R.id.llreserved);
        llregi=findViewById(R.id.llregistered);

        llalot.setOnClickListener(this);
        llavl.setOnClickListener(this);
        llmort.setOnClickListener(this);
        llblock.setOnClickListener(this);
        llrese.setOnClickListener(this);
        llregi.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.llalotted:
                clearFacing();
                if(Venrure==null)
                    onError(VENTURE_ALERT);
                else {
                    if(allo.getText().equals("0"))
                    {
                        onError("No Plots Available");
                    }
                    else
                    {
                        clearFacing();
                        status = "Y";
                        presenter.onLoadFacing(Venrure, status, Sector);
                        changeColors("#EF4836");
                    }
                }
                break;
            case R.id.llavailable:
                clearFacing();
                if(Venrure==null)
                    onError(VENTURE_ALERT);
                else {
                    if(aval.getText().equals("0"))
                    {
                        onError("No Plots Available");
                    }
                    else
                    {
                        clearFacing();
                        status = "N";
                        presenter.onLoadFacing(Venrure, status, Sector);
                        changeColors("#1E8BC3");
                    }
                }
                break;
            case R.id.llmortgage:
                clearFacing();
                if(Venrure==null)
                    onError(VENTURE_ALERT);
                else {
                    if(mort.getText().equals("0"))
                    {
                        onError("No Plots Available");
                    }
                    else {
                        clearFacing();
                        status = "M";
                        presenter.onLoadFacing(Venrure, status, Sector);
                        changeColors("#1F3A93");
                    }
                }
                break;
            case R.id.llblocked:
                clearFacing();
                if(Venrure==null)
                    onError(VENTURE_ALERT);
                else {
                    if(block.getText().equals("0"))
                    {
                        onError("No Plots Available");
                    }
                    else {
                        clearFacing();
                        status = "P";
                        presenter.onLoadFacing(Venrure, status, Sector);
                        changeColors("#913D88");
                    }
                }
                break;
            case R.id.llregistered:
                clearFacing();
                if(Venrure==null)
                    onError(VENTURE_ALERT);
                else {
                    if(regi.getText().equals("0"))
                    {
                        onError("No Plots Available");
                    }
                    else
                    {
                        clearFacing();
                        status = "G";
                        presenter.onLoadFacing(Venrure, status, Sector);
                        changeColors("#1BA39C");
                    }}
                break;
            case R.id.llreserved:
                clearFacing();
                if(Venrure==null)
                    onError(VENTURE_ALERT);
                else {
                    if (rese.getText().equals("0")) {
                        onError("No Plots Available");
                    } else {
                        clearFacing();
                        status = "R";
                        presenter.onLoadFacing(Venrure, status, Sector);
                        changeColors("#F39912");
                    }
                }
                break;
            case R.id.card_plotm_east:
                if(status==null)
                    onError(STATUS_ALERT);
                else
                if(east.getText().equals("0"))
                    onError("No Plots Available");
                else
                    callBottomSheet("East");
                break;
            case R.id.card_plotm_west:
                if(status==null)
                    onError(STATUS_ALERT);
                else
                if(west.getText().equals("0"))
                    onError("No Plots Available");
                else
                    callBottomSheet("West");
                break;
            case R.id.card_plotm_north:
                if(status==null)
                    onError(STATUS_ALERT);
                else
                if(north.getText().equals("0"))
                    onError("No Plots Available");
                else
                    callBottomSheet("North");
                break;
            case R.id.card_plotm_south:
                if(status==null)
                    onError(STATUS_ALERT);
                else
                if(south.getText().equals("0"))
                    onError("No Plots Available");
                else
                    callBottomSheet("South");
                break;
            case R.id.card_plotm_northeast:
                if(status==null)
                    onError(STATUS_ALERT);
                else
                if(northeast.getText().equals("0"))
                    onError("No Plots Available");
                else
                    callBottomSheet("NorthEast");
                break;
            case R.id.card_plotm_northwest:
                if(status==null)
                    onError(STATUS_ALERT);
                else
                if(northwest.getText().equals("0"))
                    onError("No Plots Available");
                else
                    callBottomSheet("NorthWest");
                break;
            case R.id.card_plotm_southeast:
                if(status==null)
                    onError(STATUS_ALERT);
                else
                if(southeast.getText().equals("0"))
                    onError("No Plots Available");
                else
                    callBottomSheet("SouthEast");
                break;
            case R.id.card_plotm_southwest:
                if(status==null)
                    onError(STATUS_ALERT);
                else
                if(southwest.getText().equals("0"))
                    onError("No Plots Available");
                else
                    callBottomSheet("SouthWest");
                break;
        }
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
    public void onStausVisable() {
        lin_status.setVisibility(View.VISIBLE);
    }

    @Override
    public void onLoadFacingSuccess(ArrayList<PlotMatrixFacingData> PMFD) {
        FacingList = PMFD;
        for(int i=0;i<FacingList.size();i++)
        {
            switch (FacingList.get(i).getFacing())
            {
                case "East":
                    east.setText(FacingList.get(i).getTotal());
                    east_ext.setText(FacingList.get(i).getExtend());
                    break;
                case "West":
                    west.setText(FacingList.get(i).getTotal());
                    west_ext.setText(FacingList.get(i).getExtend());
                    break;
                case "North":
                    north.setText(PMFD.get(i).getTotal());
                    north_ext.setText(PMFD.get(i).getExtend());
                    break;
                case "South":
                    south.setText(FacingList.get(i).getTotal());
                    south_ext.setText(FacingList.get(i).getExtend());
                    break;
                case "NorthEast":
                    northeast.setText(FacingList.get(i).getTotal());
                    northeast_ext.setText(FacingList.get(i).getExtend());
                    break;
                case "NorthWest":
                    northwest.setText(FacingList.get(i).getTotal());
                    northwest_ext.setText(FacingList.get(i).getExtend());
                    break;
                case "SouthEast":
                    southeast.setText(FacingList.get(i).getTotal());
                    southeast_ext.setText(FacingList.get(i).getExtend());
                    break;
                case "SouthWest":
                    southwest.setText(FacingList.get(i).getTotal());
                    southwest_ext.setText(FacingList.get(i).getExtend());
                    break;
            }
        }
    }

    @Override
    public void onLoadSuccess(ArrayList<PlotMatrixHeader> header) {
        Contents.utiplotmatrixheader=header;
        List<String> Ids=new ArrayList<>();
        List<String> VentureNames=new ArrayList<>();
        PlotMatrixHeader pa0=new PlotMatrixHeader("Venture", "Total Plots","Extent","","Sector","");
        newheader.add(pa0);
        for (int i=0;i<header.size();i++)
        {
            String VentureId=header.get(i).getVentureCd();
            String SectorId=header.get(i).getSector();
            String VentureName = header.get(i).getVentureName();
            if(!VentureList.contains(VentureId)) {
                VentureList.add(VentureId);
                ventureNamesLists.add(new VentureNamesList(VentureId, VentureName));
                this.arr_ventureslist = ventureNamesLists;
                VentureNames.add(VentureName);
                ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, VentureNames);
                dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spVentures.setAdapter(dataAdapter);
            }
            //  ventureNamesLists.add(new VentureNamesList(VentureId, Shortname));
            Double extend=0.0;
            int total=0;
            String id=header.get(i).getVentureCd()+header.get(i).getSector()+header.get(i).getVentureName();
            if(!Ids.contains(id))
            {
                Ids.add(id);
                for(int j=0;j<header.size();j++)
                {
                    if(id.equals(header.get(j).getVentureCd()+header.get(j).getSector()+header.get(i).getVentureName())) {
                        total += Integer.parseInt(header.get(j).getTotal());
                        extend += Double.parseDouble(header.get(j).getExtend());
                    }
                }
                PlotMatrixHeader pa=new PlotMatrixHeader(VentureId,String.valueOf(total),String.valueOf(extend),"",SectorId,VentureName);
                newheader.add(pa);
            }
        }

        /*LinearLayoutManager horizontalLayoutManagaer
                = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        rect_venture.setLayoutManager(horizontalLayoutManagaer);
        PlotMatrixVentureTitles pacla=new PlotMatrixVentureTitles(this,VentureList);
        rect_venture.setAdapter(pacla);*/
    }
    public void onVentureSelect(String vcd)
    {
        clearFacing();
        clearStatus();
        pieData.clear();
        LinearLayoutManager horizontalLayoutManagaer
                = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recy_header.setLayoutManager(horizontalLayoutManagaer);
        PlotMatrixHeaderRecyAdp pacla=new PlotMatrixHeaderRecyAdp(this,newheader,vcd);
        recy_header.setAdapter(pacla);
    }
    @Override
    public void onError(String message) {
        SingletonAlertDialog.alertDialogShow(PlotMatrix.this,message);
    }
    public void itemselect(String vcd,String Ser)
    {
        Venrure=vcd;
        Sector=Ser;
        int TotalPlots=0;
        float value=0;
        for(int i=0;i<newheader.size();i++)
        {
            if(newheader.get(i).getVentureCd().equals(vcd)&&newheader.get(i).getSector().equals(Sector))
            {
                TotalPlots=Integer.parseInt(newheader.get(i).getTotal());
            }
        }
        clearFacing();
        clearStatus();
        pieData.clear();
        for (int i=0;i<Contents.utiplotmatrixheader.size();i++)
        {
            PlotMatrixHeader PMH=Contents.utiplotmatrixheader.get(i);
            if(PMH.getVentureCd().equals(vcd)&&PMH.getSector().equals(Sector))
            {
                switch (PMH.getStatus())
                {
                    case "N":
                        aval.setText(PMH.getTotal());
                        avalExt.setText(PMH.getExtend());
                        int num=Integer.parseInt(PMH.getTotal());
                        value= (float)num/TotalPlots;
                        pieData.add(new SliceValue(value*100, Color.parseColor("#1E8BC3")).setLabel(String.format("%.2f", value*100)+" %"));
                        break;
                    case "Y":
                        allo.setText(PMH.getTotal());
                        alloExt.setText(PMH.getExtend());
                        int num1=Integer.parseInt(PMH.getTotal());
                        value= (float)num1/TotalPlots;
                        pieData.add(new SliceValue(value*100, Color.parseColor("#EF4836")).setLabel(String.format("%.2f", value*100)+" %"));
                        break;
                    case "M":
                        mort.setText(PMH.getTotal());
                        mortExt.setText(PMH.getExtend());
                        break;
                    case "P":
                        block.setText(PMH.getTotal());
                        blockExt.setText(PMH.getExtend());
                        break;
                    case "G":
                        regi.setText(PMH.getTotal());
                        regiExt.setText(PMH.getExtend());
                        break;
                    case "R":
                        rese.setText(PMH.getTotal());
                        reseExt.setText(PMH.getExtend());
                        int num2=Integer.parseInt(PMH.getTotal());
                        value= (float)num2/TotalPlots;
                        pieData.add(new SliceValue(value*100, Color.parseColor("#F39912")).setLabel(String.format("%.2f", value*100)+" %"));
                        break;
                }
            }
        }
        PieChartData pieChartData = new PieChartData(pieData);
        pieChartData.setHasLabels(true).setValueLabelTextSize(12);
        pieChartData.setHasCenterCircle(true).setCenterText1("Plots").setCenterText1FontSize(16).setCenterText1Color(Color.parseColor("#0097A7"));
        pieChartView.setPieChartData(pieChartData);
    }
    public void clearFacing()
    {
        east.setText("0");
        north.setText("0");
        west.setText("0");
        south.setText("0");
        northeast.setText("0");
        northwest.setText("0");
        southeast.setText("0");
        southwest.setText("0");

        east_ext.setText("0");
        north_ext.setText("0");
        west_ext.setText("0");
        south_ext.setText("0");
        northeast_ext.setText("0");
        northwest_ext.setText("0");
        southeast_ext.setText("0");
        southwest_ext.setText("0");
    }
    public  void clearStatus()
    {
        allo.setText("0");
        aval.setText("0");
        mort.setText("0");
        block.setText("0");
        regi.setText("0");
        rese.setText("0");
        alloExt.setText("0");
        avalExt.setText("0");
        mortExt.setText("0");
        blockExt.setText("0");
        regiExt.setText("0");
        reseExt.setText("0");
    }
    void callBottomSheet(String Facing)
    {
        Bundle bundle=new Bundle();
        bundle.putString("venture", Venrure);
        bundle.putString("status", status);
        bundle.putString("facing", Facing);
        bundle.putString("sector", Sector);
        PlotMatrixBottomSheet BSF=new PlotMatrixBottomSheet(this);
        BSF.setArguments(bundle);
        BSF.show(getSupportFragmentManager(), "BottomSheet");
    }
    void changeColors(String color)
    {
        card_east.setCardBackgroundColor(Color.parseColor(color));
        card_west.setCardBackgroundColor(Color.parseColor(color));
        card_north.setCardBackgroundColor(Color.parseColor(color));
        card_south.setCardBackgroundColor(Color.parseColor(color));
        card_southeast.setCardBackgroundColor(Color.parseColor(color));
        card_southwest.setCardBackgroundColor(Color.parseColor(color));
        card_northeast.setCardBackgroundColor(Color.parseColor(color));
        card_northwest.setCardBackgroundColor(Color.parseColor(color));
    }
    public void calfromBootomSheet(String facing,String actiontype,String sector,String venture,String extent) {
        if(actiontype.equals("reserve")){
            for(int i = 0;i<FacingList.size();i++){
                if(FacingList.get(i).getFacing().equals(facing)) {
                    FacingList.get(i).setTotal(String.valueOf(Integer.parseInt(FacingList.get(i).getTotal()) - 1 ));
                    FacingList.get(i).setExtend(String.valueOf(Double.parseDouble(FacingList.get(i).getExtend()) - Double.parseDouble(extent)));
                    onLoadFacingSuccess(FacingList);
                }
            }
            for (int i=0;i<Contents.utiplotmatrixheader.size();i++) {
                PlotMatrixHeader PMH=Contents.utiplotmatrixheader.get(i);
                if(PMH.getVentureCd().equals(venture)&&PMH.getSector().equals(sector)) {
                    if(PMH.getStatus().equals("N")){
                        Contents.utiplotmatrixheader.get(i).setTotal(String.valueOf (Integer.parseInt(PMH.getTotal())-1));
                    }
                    if (PMH.getStatus().equals("R")) {
                        Contents.utiplotmatrixheader.get(i).setTotal(String.valueOf(Integer.parseInt(rese.getText().toString()) + 1));
                    }

                }
            }
            if(rese.getText().toString().equals("0")){
                Contents.utiplotmatrixheader.add(new PlotMatrixHeader(venture,"1",extent,"R",sector,""));
            }
            itemselect(venture,sector);
        }else if(actiontype.equals("unreserve")){
            for(int i = 0;i<FacingList.size();i++) {
                if (FacingList.get(i).getFacing().equals(facing)) {
                    FacingList.get(i).setTotal(String.valueOf(Integer.parseInt(FacingList.get(i).getTotal()) + 1));
                    FacingList.get(i).setExtend(String.valueOf(Double.parseDouble(FacingList.get(i).getExtend()) - Double.parseDouble(extent)));
                    onLoadFacingSuccess(FacingList);
                }
            }
            for (int i=0;i<Contents.utiplotmatrixheader.size();i++) {
                PlotMatrixHeader PMH=Contents.utiplotmatrixheader.get(i);
                if(PMH.getVentureCd().equals(venture)&&PMH.getSector().equals(sector)) {
                    if(PMH.getStatus().equals("N")){
                        Contents.utiplotmatrixheader.get(i).setTotal(String.valueOf (Integer.parseInt(PMH.getTotal())+1));
                    }
                    if (PMH.getStatus().equals("R")) {
                        Contents.utiplotmatrixheader.get(i).setTotal(String.valueOf(Integer.parseInt(rese.getText().toString()) - 1));
                    }

                }
            }
            if(rese.getText().toString().equals("0")){
                Contents.utiplotmatrixheader.add(new PlotMatrixHeader(venture,"1",extent,"N",sector,""));
            }
            itemselect(venture,sector);
        }

    }
}
