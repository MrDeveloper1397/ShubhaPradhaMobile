package task.marami.Shubhaprada.Models;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import task.marami.Shubhaprada.R;


public class ReservationListAdapter extends BaseAdapter {
    ArrayList<ReservationModel> reservation_models;

    public ReservationListAdapter(ArrayList<ReservationModel> reservation_models) {
        this.reservation_models = reservation_models;
    }

    @Override
    public int getCount() {
        return reservation_models.size();
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
        View view= LayoutInflater.from(parent.getContext()).
                inflate(R.layout.listitem, null );
        TextView txttitle=(TextView) view.findViewById(R.id.txt_reser_item_vname);
        txttitle.setText(reservation_models.get(position).getVenture_name());
        return view;
    }
}
