package task.marami.Shubhaprada.Models;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import task.marami.Shubhaprada.R;

public class VenturesListAdapter extends BaseAdapter {
    Context context;
    ArrayList<VenturesModel> arv;

    public VenturesListAdapter(Context context, ArrayList<VenturesModel> arv) {
        this.arv = arv;
        this.context = context;
    }

    @Override
    public int getCount() {
        return arv.size();
    }

    @Override
    public Object getItem(int i) {
        return i;
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        View view1 = LayoutInflater.from(context).inflate(R.layout.listitem, null);
        TextView venture = view1.findViewById(R.id.txt_reser_item_vname);
        venture.setText(arv.get(i).getVenturename());
        return view1;
    }
}