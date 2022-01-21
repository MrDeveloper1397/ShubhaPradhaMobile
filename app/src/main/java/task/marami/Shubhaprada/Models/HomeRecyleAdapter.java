package task.marami.Shubhaprada.Models;

import android.annotation.SuppressLint;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import java.util.ArrayList;
import task.marami.Shubhaprada.Fragments.HomeContent;
import task.marami.Shubhaprada.R;
import task.marami.Shubhaprada.Utils.Contents;
import task.marami.Shubhaprada.Utils.DownloadImageTask;

public class HomeRecyleAdapter extends RecyclerView.Adapter<HomeRecyleAdapter.MyViewHolder>
{
    ArrayList<HomeData> homeData;
    ImageView img_curproj;
    TextView title;
    HomeContent context;

    public HomeRecyleAdapter(ArrayList<HomeData> homeData,HomeContent context) {
        this.homeData = homeData;
        this.context=context;
    }

    @NonNull
    @Override
    public HomeRecyleAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View ItemView= LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_layout,parent,false);
        return new MyViewHolder(ItemView);
    }

    @Override
    public void onBindViewHolder(@NonNull final HomeRecyleAdapter.MyViewHolder holder, @SuppressLint("RecyclerView") final int position) {
        new DownloadImageTask(img_curproj).execute(Contents.imageurl+homeData.get(position).getImageurl());
        Log.d("jsbvgf",homeData.get(position).getProject_title());
        title.setText(homeData.get(position).getProject_title());
        img_curproj.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(homeData.get(position).getLink().equals("null"))
                {
                    context.onError("Digital Layout Not Available this Venture");
                }
                else
                {
                    HomeData ha=homeData.get(position);
                    context.onGoTOHomeWeb(ha);
                }
            }
        });
    }
    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }
    @Override
    public int getItemCount() {
        return homeData.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public MyViewHolder(View itemView) {
            super(itemView);
            img_curproj = (ImageView) itemView.findViewById(R.id.img_home_curproj);
            title = (TextView) itemView.findViewById(R.id.txt_home_title);
        }
    }
}
