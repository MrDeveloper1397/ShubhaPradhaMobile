package task.marami.Shubhaprada.Models;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;
;import task.marami.Shubhaprada.PlotMatrix;
import task.marami.Shubhaprada.R;


public class PlotMatrixVentureTitles extends RecyclerView.Adapter<PlotMatrixVentureTitles.MyViewHolder>{

    PlotMatrix context;
    List<String> VentureTitle;

    public PlotMatrixVentureTitles(PlotMatrix context, List<String> ventureTitle) {
        this.context = context;
        VentureTitle = ventureTitle;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.plotmatrix_venture_id, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, final int position) {
        holder.text_venture.setText(VentureTitle.get(position));
        holder.card_item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               context.onVentureSelect(VentureTitle.get(position));
            }
        });
    }



    @Override
    public int getItemCount() {
        return VentureTitle.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        CardView card_item;
        TextView text_venture;
        public MyViewHolder(View itemView) {
            super(itemView);
            card_item = (CardView) itemView.findViewById(R.id.card_plotv_venid);
            text_venture = (TextView) itemView.findViewById(R.id.txt_plotv_venname);
        }
    }
}
