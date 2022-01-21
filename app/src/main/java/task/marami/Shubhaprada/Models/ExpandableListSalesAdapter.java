package task.marami.Shubhaprada.Models;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import task.marami.Shubhaprada.R;


public class ExpandableListSalesAdapter extends BaseExpandableListAdapter implements View.OnClickListener {

    private Context context;
    private List<String> _listDataHeader;
    private ArrayList<CadresCountAssociates> cca;
    private HashMap<String, ArrayList<BookingDetailsModel>> _listDataChild;

    public ExpandableListSalesAdapter(Context context, List<String> expandableListTitle, HashMap<String, ArrayList<BookingDetailsModel>> expandableListDetail, ArrayList<CadresCountAssociates> cclist) {
        this.context = context;
        this.cca = cclist;
        this._listDataHeader = expandableListTitle;
        this._listDataChild = expandableListDetail;
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return this._listDataChild.get(String.valueOf(
                this._listDataHeader.get(groupPosition))).size();
    }

    @Override
    public Object getChild(int groupPosition, int childPosititon) {
        return this._listDataChild.get(String.valueOf(
                this._listDataHeader.get(groupPosition))).get(
                childPosititon);
    }

    @Override
    public int getGroupCount() {
        return this._listDataHeader.size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return this._listDataHeader.get(groupPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater layoutInflater = (LayoutInflater) this.context.
                    getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.list_item_title, null);
        }
        CadresCountAssociates cc = cca.get(groupPosition);
        Log.d("group", String.valueOf(cc));
        ConstraintLayout layout = convertView.findViewById(R.id.llheaderitems);
        TextView tv_ItemTitle = convertView.findViewById(R.id.tv_ItemTitle);
        TextView tv_count = convertView.findViewById(R.id.cadre_count);

        String cadreName = cc.getCadreName();
        String cadrecount = cc.getCountNum();
        tv_ItemTitle.setText(cadreName);
        tv_count.setText(cadrecount);
        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        BookingDetailsModel treesales = (BookingDetailsModel) getChild(groupPosition, childPosition);
        if (convertView == null) {
            LayoutInflater layoutInflater = (LayoutInflater) this.context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.list_dialog_sales, null);
        }
        TextView agentid = (TextView) convertView.findViewById(R.id.agentcd);
        TextView associateName = (TextView) convertView.findViewById(R.id.name);
        TextView name = (TextView) convertView.findViewById(R.id.associatename);
        TextView PlotNo = (TextView) convertView.findViewById(R.id.plotno);
        TextView PlotArea = (TextView) convertView.findViewById(R.id.plotarea);
        TextView MemberId = (TextView) convertView.findViewById(R.id.memberid);
        agentid.setText(treesales.getAgentCd());
        associateName.setText(treesales.getName());
        name.setText(treesales.getAssociateName());
        PlotNo.setText(treesales.getPlotNo()+"(" + treesales.getSector()+")");
        PlotArea.setText(treesales.getPlotArea());
        MemberId.setText(treesales.getMemberId());
        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }

    @Override
    public void onClick(View view) {

    }
}
