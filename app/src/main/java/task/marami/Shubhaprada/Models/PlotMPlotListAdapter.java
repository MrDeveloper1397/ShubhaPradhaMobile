package task.marami.Shubhaprada.Models;

import android.graphics.Color;
import androidx.cardview.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import task.marami.Shubhaprada.R;


public class PlotMPlotListAdapter extends BaseAdapter {
    TextView plotno,plotarea;
    ArrayList<PlotMPlotData> plotMPlotData=new ArrayList<>();
    String status;
    CardView plot_patrixm;

    public PlotMPlotListAdapter(ArrayList<PlotMPlotData> plotMPlotData,String status) {
        this.plotMPlotData = plotMPlotData;
        this.status=status;
    }

    @Override
    public int getCount() {
        return plotMPlotData.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.plotmatrix_plot_item, null);
        plotno=(TextView) view.findViewById(R.id.txt_plotm_bottom_plotno);
        plotarea=(TextView) view.findViewById(R.id.txt_plotm_bottom_parea);
        plot_patrixm = (CardView) view.findViewById(R.id.card_plotm_plot);
        plotno.setText(plotMPlotData.get(position).getPlotno());
        plotarea.setText(plotMPlotData.get(position).getPlotarea());
        switch (status)
        {
            case "N":plot_patrixm.setCardBackgroundColor(Color.parseColor("#1E8BC3"));
                break;
            case "Y":plot_patrixm.setCardBackgroundColor(Color.parseColor("#EF4836"));
                break;
            case "M":plot_patrixm.setCardBackgroundColor(Color.parseColor("#1F3A93"));
                break;
            case "P":plot_patrixm.setCardBackgroundColor(Color.parseColor("#913D88"));
                break;
            case "G":plot_patrixm.setCardBackgroundColor(Color.parseColor("#1BA39C"));
                break;
            case "R":plot_patrixm.setCardBackgroundColor(Color.parseColor("#F39912"));
                break;
        }
        return view;
    }
}
