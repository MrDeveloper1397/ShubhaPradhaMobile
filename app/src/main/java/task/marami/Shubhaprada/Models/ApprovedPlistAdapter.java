package task.marami.Shubhaprada.Models;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import task.marami.Shubhaprada.MemberApprovedList;
import task.marami.Shubhaprada.R;


public class ApprovedPlistAdapter extends RecyclerView.Adapter<ApprovedPlistAdapter.MyViewHolder> {
    ArrayList<PlistApprovalCount> pac = new ArrayList<>();
    MemberApprovedList context;
    int pos;

    public ApprovedPlistAdapter(ArrayList<PlistApprovalCount> pac, MemberApprovedList context) {
        this.pac = pac;
        this.context = context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.approved_project_item, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, final int position) {
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
        if (pos == position) {
            holder.cardview1.setBackgroundResource(R.color.colorPrimary);
            holder.Ptitle.setTextColor(Color.YELLOW);
            holder.Count.setTextColor(Color.YELLOW);
            holder.tvApproved.setTextColor(Color.YELLOW);
        } else {
            holder.cardview1.setBackgroundResource(R.color.colorGrey);
            holder.Ptitle.setTextColor(Color.BLACK);
            holder.Count.setTextColor(Color.BLACK);
            holder.tvApproved.setTextColor(Color.BLACK);
        }
    }


    @Override
    public int getItemCount() {
        return pac.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView Ptitle, Count, tvApproved;
        CardView cardview1;

        public MyViewHolder(View itemView) {
            super(itemView);
            Ptitle = (TextView) itemView.findViewById(R.id.txt_approved_title1);
            Count = (TextView) itemView.findViewById(R.id.txt_approved_count);
            tvApproved = (TextView) itemView.findViewById(R.id.tvapproved);
            cardview1 = (CardView) itemView.findViewById(R.id.card_approved_item);
        }
    }
}
