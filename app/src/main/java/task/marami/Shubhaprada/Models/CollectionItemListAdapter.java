package task.marami.Shubhaprada.Models;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import task.marami.Shubhaprada.R;

public class CollectionItemListAdapter extends BaseExpandableListAdapter {
    private Context _context;
    private ArrayList<CollectionHeaderData> _listDataHeader; // header titles
    // child data in format of header title, child title
    ArrayList<CollectionItemData> _listDataChild;

    public CollectionItemListAdapter(Context _context, ArrayList<CollectionHeaderData> _listDataHeader, ArrayList<CollectionItemData> _listDataChild) {
        this._context = _context;
        this._listDataHeader = _listDataHeader;
        this._listDataChild = _listDataChild;
    }

    @Override
    public int getGroupCount() {
        return _listDataHeader.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return _listDataChild.size();
    }

    @Override
    public CollectionHeaderData getGroup(int groupPosition) {
        return this._listDataHeader.get(groupPosition);
    }

    @Override
    public CollectionItemData getChild(int groupPosition, int childPosition) {
        return this._listDataChild.get(childPosition);
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
        CollectionHeaderData headerTitle =  getGroup(groupPosition);
        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) this._context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.collection_header, null);
        }

        TextView lblListHeader = (TextView) convertView
                .findViewById(R.id.txt_collection_title);
        TextView txtAmount = (TextView) convertView
                .findViewById(R.id.collection_amount);
        lblListHeader.setText(headerTitle.getHeader_title());
        txtAmount.setText(headerTitle.getTotal_amount());
        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        CollectionItemData childText =  getChild(groupPosition,childPosition);

        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) this._context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.collection_item, null);
        }

        TextView txtListChild = (TextView) convertView
                .findViewById(R.id.txt_collection_v_name);
        TextView txtlistamount= (TextView) convertView.findViewById(R.id.txt_venture_collection);


        txtListChild.setText(childText.getVenture_name());
        txtlistamount.setText(childText.getVenture_collection());
        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return false;
    }
}
