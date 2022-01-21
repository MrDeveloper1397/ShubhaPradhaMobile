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

public class ExpandableListViewAdapter extends BaseExpandableListAdapter implements View.OnClickListener {

    private Context context;
    private List<String> _listDataHeader;
    private List<String> names;
    ArrayList<CadresCountAssociates> cca;
    private HashMap<String, ArrayList<AssociateTreeTeamWise>> _listDataChild;
    public ExpandableListViewAdapter(Context context, List<String> expandableListTitle, HashMap<String, ArrayList<AssociateTreeTeamWise>> expandableListDetail, ArrayList<CadresCountAssociates> cca) {
        this.context = context;
        this.cca = cca;
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
    public View getGroupView(final int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
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
//        final String expandedListText = (String) getChild(groupPosition, childPosition);
        AssociateTreeTeamWise treeTeamWise = (AssociateTreeTeamWise) getChild(groupPosition, childPosition);
        if (convertView == null) {
            LayoutInflater layoutInflater = (LayoutInflater) this.context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.list_dialog_tree, null);
        }
        TextView passbookNo = (TextView) convertView.findViewById(R.id.pbno);
        TextView applicent = (TextView) convertView.findViewById(R.id.applicantName);
        TextView cadre = (TextView) convertView.findViewById(R.id.cadre);
        TextView pan = (TextView) convertView.findViewById(R.id.panno);
        TextView mble = (TextView) convertView.findViewById(R.id.mobile);
        passbookNo.setText(treeTeamWise.getAgentCd());
        applicent.setText(treeTeamWise.getAgentName());
        cadre.setText(treeTeamWise.getAgentCadre());
        pan.setText(treeTeamWise.getPanNo());
        mble.setText(treeTeamWise.getMobile());
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
