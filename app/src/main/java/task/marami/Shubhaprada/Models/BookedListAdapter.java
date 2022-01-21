package task.marami.Shubhaprada.Models;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import task.marami.Shubhaprada.BookedPlots;
import task.marami.Shubhaprada.R;


public class BookedListAdapter extends BaseAdapter {
    BookedPlots context;
    ArrayList<BookedData> bookedData=new ArrayList<>();
    TextView venture,sector,plotno,plotarea,plotreate,total,pbno,applicent,mobile,agent;

    public BookedListAdapter(BookedPlots context, ArrayList<BookedData> bookedData) {
        this.context = context;
        this.bookedData = bookedData;
    }

    @Override
    public int getCount() {
        return bookedData.size();
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
        View view= LayoutInflater.from(context).inflate(R.layout.booked_item, null );
        venture = (TextView) view.findViewById(R.id.txt_book_vname);
        sector = (TextView) view.findViewById(R.id.txt_book_sector);
        plotno = (TextView) view.findViewById(R.id.txt_book_plotno);
        plotarea = (TextView) view.findViewById(R.id.txt_book_plotarea);
        plotreate = (TextView) view.findViewById(R.id.txt_book_ratepersqr);
        total = (TextView) view.findViewById(R.id.txt_book_totcost);
        pbno = (TextView) view.findViewById(R.id.txt_book_pbno);
        applicent = (TextView) view.findViewById(R.id.txt_book_appliname);
        mobile = (TextView) view.findViewById(R.id.txt_book_mobile);
        agent = (TextView) view.findViewById(R.id.txt_book_agent);

        BookedData data=bookedData.get(position);
        venture.setText(data.getVenture());
        sector.setText(data.getSector());
        plotno.setText(data.getPlotno());
        plotarea.setText(data.getPlotarea());
        plotreate.setText(data.getRate());
        total.setText(data.getTotal());
        pbno.setText(data.getPassbook());
        applicent.setText(data.getAplicent());
        mobile.setText(data.getMobile());
        agent.setText(data.getAgent());

        return view;
    }
}
