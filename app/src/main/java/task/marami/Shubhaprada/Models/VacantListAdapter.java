package task.marami.Shubhaprada.Models;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import task.marami.Shubhaprada.Fragments.VacantList;
import task.marami.Shubhaprada.R;


public class VacantListAdapter extends RecyclerView.Adapter<VacantListAdapter.MyViewHolder> {
    ArrayList<VacantListItems> projectsData;
    VacantList context;

    public VacantListAdapter(ArrayList<VacantListItems> projectsData, VacantList context) {
        this.projectsData = projectsData;
        this.context = context;
    }
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.vacant_list_item, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, @SuppressLint("RecyclerView") final int position) {
        holder.plotnum.setText(projectsData.get(position).getPlotno());
        holder.plotArea.setText(projectsData.get(position).getPlotarea());
        holder.Facing.setText(projectsData.get(position).getFacing());
    }

    @Override
    public int getItemCount() {
        return projectsData.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView plotnum,plotArea,Facing;
        public MyViewHolder(View itemView) {
            super(itemView);
            plotnum = itemView.findViewById(R.id.tvplotno);
            plotArea= itemView.findViewById(R.id.tvplotarea);
            Facing= itemView.findViewById(R.id.tvfacing);

        }
    }
}
