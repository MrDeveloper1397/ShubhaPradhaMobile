package task.marami.Shubhaprada.Models;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import task.marami.Shubhaprada.ApprovalList;
import task.marami.Shubhaprada.R;

public class PlistApprovalCountListAdapter extends RecyclerView.Adapter<PlistApprovalCountListAdapter.MyViewHolder> {
    ArrayList<PlistApprovalCount> pac=new ArrayList<>();
    ApprovalList context;
     int pos;

    public PlistApprovalCountListAdapter(ArrayList<PlistApprovalCount> pac,ApprovalList context) {
        this.pac = pac;
        this.context=context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
       View view= LayoutInflater.from(context).inflate(R.layout.projectitem, parent, false);
        return new MyViewHolder(view);
    }

    @SuppressLint("ResourceType")
    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, final int position) {
        holder.Ptitle.setText(pac.get(position).getVen_name());
        holder.Count.setText(pac.get(position).getAppr_count());

        holder.cardview1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                notifyDataSetChanged();
                pos = position;
                context.plistItemSel(position);
            }
        });
        if(pos==position){
            holder.cardview1.setBackgroundResource(R.color.colorPrimary);
            holder.Ptitle.setTextColor(Color.YELLOW);
            holder.Count.setTextColor(Color.YELLOW);
            holder.tvPending.setTextColor(Color.YELLOW);
        }
        else
        {
            holder.cardview1.setBackgroundResource(R.color.colorGrey);
            holder.Ptitle.setTextColor(Color.BLACK);
            holder.Count.setTextColor(Color.BLACK);
            holder.tvPending.setTextColor(Color.BLACK);
        }
    }

    @Override
    public int getItemCount() {
        return pac.size();
    }
    public class MyViewHolder extends RecyclerView.ViewHolder
    {
        TextView Ptitle,Count,tvPending;
        CardView cardview1;
        public MyViewHolder(View itemView) {
            super(itemView);
            Ptitle=(TextView) itemView.findViewById(R.id.ProjectTitle);
            Count=(TextView) itemView.findViewById(R.id.PenddingCount);
            tvPending=(TextView) itemView.findViewById(R.id.tvPending);
            cardview1=(CardView) itemView.findViewById(R.id.cardview1);
        }
    }
}
