package task.marami.Shubhaprada.Models;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.regex.Pattern;

import task.marami.Shubhaprada.R;


public class PassBookListAdapter extends BaseAdapter implements Filterable {
    ArrayList<ApprPassbookList> apbls;
    private ArrayList<ApprPassbookList> mDisplayedValues;

    public PassBookListAdapter(ArrayList<ApprPassbookList> apbls) {
        this.apbls = apbls;
        this.mDisplayedValues=apbls;
    }

    @Override
    public int getCount() {
        return mDisplayedValues.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return Integer.parseInt(mDisplayedValues.get(position).getMember_id());
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View NameItem= LayoutInflater.from(parent.getContext()).inflate(R.layout.applicentnameitem,null);
        TextView PassbookNo=(TextView) NameItem.findViewById(R.id.Passbookno);
        TextView Applicent=(TextView) NameItem.findViewById(R.id.ApplicentName);
        TextView Appdoj=(TextView) NameItem.findViewById(R.id.Applicentjoin);
        PassbookNo.setText(" "+mDisplayedValues.get(position).getMember_id());
        Applicent.setText(mDisplayedValues.get(position).getMember_name());
        Appdoj.setText(mDisplayedValues.get(position).getDate_of_join());
        return NameItem;
    }
    @Override
    public void notifyDataSetChanged() {
        super.notifyDataSetChanged();
    }

    @Override
    public Filter getFilter() {
        Filter filter = new Filter() {
            String isType;

            @SuppressWarnings("unchecked")
            @Override
            protected void publishResults(CharSequence constraint,FilterResults results) {

                mDisplayedValues = (ArrayList<ApprPassbookList>) results.values; // has the filtered values
                notifyDataSetChanged();  // notifies the data with new filtered values
            }

            @Override
            protected FilterResults performFiltering(CharSequence constraint) {

                FilterResults results = new FilterResults();        // Holds the results of a filtering operation in values
                ArrayList<ApprPassbookList> FilteredArrList = new ArrayList<ApprPassbookList>();

                if (apbls == null) {
                    apbls = new ArrayList<ApprPassbookList>(mDisplayedValues); // saves the original data in mOriginalValues
                }

                /********
                 *
                 *  If constraint(CharSequence that is received) is null returns the mOriginalValues(Original) values
                 *  else does the Filtering and returns FilteredArrList(Filtered)
                 *
                 ********/
                if (constraint == null || constraint.length() == 0) {

                    // set the Original result to return
                    results.count = apbls.size();
                    results.values = apbls;
                } else {
                    constraint = constraint.toString().toLowerCase();
                    if(Pattern.compile( "[0-9]" ).matcher( constraint ).find())
                    {
                        isType="passbook";
                    }
                    else
                    {
                        isType="name";
                    }
                    if(isType.equals("passbook")) {
                        for (int i = 0; i < apbls.size(); i++) {
                            String data = apbls.get(i).getMember_id();
                            if (data.toLowerCase().contains(constraint.toString())) {
                                FilteredArrList.add(new ApprPassbookList(apbls.get(i).getMember_id(), apbls.get(i).getMember_name(),
                                        apbls.get(i).getDate_of_join(), apbls.get(i).getPlot_appr(), apbls.get(i).getPlot_appr(), apbls.get(i).getDiscou_appr(), apbls.get(i).getComm_appr()));
                            }
                        }
                    }
                    else
                    {
                        for (int i = 0; i < apbls.size(); i++) {
                            String data = apbls.get(i).getMember_name();
                            if (data.toLowerCase().startsWith(constraint.toString())) {
                                FilteredArrList.add(new ApprPassbookList(apbls.get(i).getMember_id(), apbls.get(i).getMember_name(),
                                        apbls.get(i).getDate_of_join(), apbls.get(i).getPlot_appr(), apbls.get(i).getPlot_appr(), apbls.get(i).getDiscou_appr(), apbls.get(i).getComm_appr()));
                            }
                        }
                    }
                    // set the Filtered result to return
                    results.count = FilteredArrList.size();
                    results.values = FilteredArrList;
                }
                return results;
            }
        };
        return filter;
    }
}
