package task.marami.Shubhaprada.Models;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import task.marami.Shubhaprada.R;
import task.marami.Shubhaprada.Utils.Contents;
import task.marami.Shubhaprada.Utils.DownloadImageTask;


public class LayoutsListAdapter extends BaseAdapter {
    ArrayList<ProjectsData> projectsData;
    Context context;

    public LayoutsListAdapter(ArrayList<ProjectsData> projectsData,Context context) {
        this.projectsData = projectsData;
        this.context = context;
    }

    @Override
    public int getCount() {
        return projectsData.size();
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
       View view=LayoutInflater.from(context).
               inflate(R.layout.itemlayout, null);
        ImageView mimageview = (ImageView) view.findViewById(R.id.img_layout_image);
        TextView venture=(TextView) view.findViewById(R.id.txt_layout_title);
        TextView totcount=(TextView) view.findViewById(R.id.txt_layout_totcount);
        TextView AvlCount= (TextView) view.findViewById(R.id.txt_layout_avlcount);

        venture.setText(projectsData.get(position).getTitle());
        totcount.setText("Total : "+projectsData.get(position).getTotalcount());
        AvlCount.setText("Available : "+projectsData.get(position).getAvailable());
        String imgname =projectsData.get(position).getImageLink();

        String imgurl = Contents.imageurl + imgname;
        new DownloadImageTask((mimageview))
                .execute(imgurl);
        if(position%2==0)
        {
            view.setBackgroundColor(Color.parseColor("#ECEFF1"));
        }
        else
        {
            view.setBackgroundColor(Color.parseColor("#ffffff"));
        }

        return view;
    }
}
