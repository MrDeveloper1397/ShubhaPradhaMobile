package task.marami.Shubhaprada.Models;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import task.marami.Shubhaprada.Fragments.VacantList;
import task.marami.Shubhaprada.R;


public class VacantListHeaderRecyAdp extends RecyclerView.Adapter<VacantListHeaderRecyAdp.MyViewHolder> {

    VacantList context;
    ArrayList<PlotMatrixHeader> header;
    ArrayList<PlotMatrixHeader> newheader=new ArrayList<>();
    int privousindex=0;
    ArrayList<String> sector=new ArrayList<>();
    String Venture;


    public VacantListHeaderRecyAdp(VacantList context, ArrayList<PlotMatrixHeader> header, String Venture) {
        this.context = context;
        this.header = header;
        this.Venture=Venture;
        onFilterData(Venture);
    }
    public void onFilterData(String Venture)
    {
        int plotcount=0;
        double extent=0.0;
        newheader.add((PlotMatrixHeader) header.get(0));
        for(int i=0;i<header.size();i++) {
            if(header.get(i).getVentureCd().contains(Venture)) {
                if (header.get(i).getVentureCd().equals(Venture)) {
                    if (!sector.contains(header.get(i).getSector())) {
                        plotcount = 0;
                        extent = 0.0;
                        sector.add(header.get(i).getSector());
                        String sector = header.get(i).getSector();
                        for (int k = 0; k < header.size(); k++) {
                            if (Venture.equals(header.get(k).getVentureCd()) && sector.equals(header.get(k).getSector())) {
                                plotcount = Integer.parseInt(header.get(k).getTotal());
                                extent = Double.parseDouble(header.get(k).getExtend());
                            }
                        }
                        newheader.add(new PlotMatrixHeader(Venture, String.valueOf(plotcount), String.valueOf(extent), "", sector,""));
                    }
                }
            }
        }
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.plotmatrixvitem, parent, false);
        return new VacantListHeaderRecyAdp.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, final int position) {
        if(position==0)
        {
            holder.vname.setText(newheader.get(position).getVentureCd());
            holder.total.setText(newheader.get(position).getTotal());
            holder.extend.setText(newheader.get(position).getExtend());
            holder.sector.setText(newheader.get(position).getSector());
            holder.vname.setTextColor(Color.RED);
            holder.total.setTextColor(Color.RED);
            holder.extend.setTextColor(Color.RED);
            holder.sector.setTextColor(Color.RED);
        }
        else
        {
            holder.vname.setText(newheader.get(position).getVentureCd());
            holder.total.setText(newheader.get(position).getTotal());
            holder.extend.setText(newheader.get(position).getExtend());
            holder.sector.setText(newheader.get(position).getSector());
            holder.vname.setTextColor(Color.BLACK);
            holder.total.setTextColor(Color.BLACK);
            holder.extend.setTextColor(Color.BLACK);
            holder.sector.setTextColor(Color.BLACK);

        }
        holder.vname.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                privousindex=position;
                notifyDataSetChanged();
                context.itemselect(newheader.get(position).getVentureCd(),newheader.get(position).getSector());
            }
        });
        holder.total.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                privousindex=position;
                notifyDataSetChanged();
                context.itemselect(newheader.get(position).getVentureCd(),newheader.get(position).getSector());
            }
        });
        holder.extend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                privousindex=position;
                notifyDataSetChanged();
                context.itemselect(newheader.get(position).getVentureCd(),newheader.get(position).getSector());
            }
        });
        holder.sector.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                privousindex=position;
                notifyDataSetChanged();
                context.itemselect(newheader.get(position).getVentureCd(),newheader.get(position).getSector());
            }
        });

        if(privousindex==position&&position>0)
        {
            holder.plot_header.setBackgroundColor(Color.parseColor("#7c0000"));
            holder.vname.setTextColor(Color.parseColor("#FFFFFF"));
            holder.sector.setTextColor(Color.parseColor("#FFFFFF"));
            holder.extend.setTextColor(Color.parseColor("#FFFFFF"));
            holder.total.setTextColor(Color.parseColor("#FFFFFF"));
        }
        else
        {
            holder.plot_header.setBackgroundColor(Color.parseColor("#FFFFFF"));
        }
    }


    @Override
    public int getItemCount() {
        return newheader.size();
    }
    public class MyViewHolder extends RecyclerView.ViewHolder
    {
        TextView vname,total,extend,sector;
        LinearLayout plot_header;
        public MyViewHolder(View itemView) {
            super(itemView);
            vname=(TextView) itemView.findViewById(R.id.txt_plotmatrix_vname);
            total=(TextView) itemView.findViewById(R.id.txt_plotmatrix_total);
            extend=(TextView) itemView.findViewById(R.id.txt_plotmatrix_extend);
            sector=(TextView) itemView.findViewById(R.id.txt_plotmatrix_vsector);
            plot_header=(LinearLayout) itemView.findViewById(R.id.plot_header_liner);
        }
    }
}