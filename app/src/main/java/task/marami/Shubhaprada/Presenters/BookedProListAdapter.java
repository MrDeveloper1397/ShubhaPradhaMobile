package task.marami.Shubhaprada.Presenters;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import task.marami.Shubhaprada.BookedPlots;
import task.marami.Shubhaprada.Models.PlistApprovalCount;
import task.marami.Shubhaprada.R;

public class BookedProListAdapter extends RecyclerView.Adapter<BookedProListAdapter.MyViewHolder> {
    ArrayList<PlistApprovalCount> pac=new ArrayList<>();
    BookedPlots context;
    int pos;

    public BookedProListAdapter(ArrayList<PlistApprovalCount> pac,BookedPlots context) {
        this.pac = pac;
        this.context=context;
    }

    @NonNull
    @Override
    public BookedProListAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.booked_project_item, parent, false);
        return new BookedProListAdapter.MyViewHolder(view);
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
            holder.tvBooked.setTextColor(Color.YELLOW);
        } else {
            holder.cardview1.setBackgroundResource(R.color.colorGrey);
            holder.Ptitle.setTextColor(Color.BLACK);
            holder.Count.setTextColor(Color.BLACK);
            holder.tvBooked.setTextColor(Color.BLACK);
        }
    }
    @Override
    public int getItemCount() {
        return pac.size();
    }
    public class MyViewHolder extends RecyclerView.ViewHolder
    {
        TextView Ptitle,Count,tvBooked;
        CardView cardview1;
        public MyViewHolder(View itemView) {
            super(itemView);
            Ptitle=(TextView) itemView.findViewById(R.id.txt_booked_title1);
            Count=(TextView) itemView.findViewById(R.id.txt_booked_count);
            tvBooked = itemView.findViewById(R.id.tvBooked);
            cardview1=(CardView) itemView.findViewById(R.id.card_booked_item);
        }
    }
}
