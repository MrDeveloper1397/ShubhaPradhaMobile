package task.marami.Shubhaprada.Models;

import android.annotation.SuppressLint;
import android.content.Context;
import android.icu.text.NumberFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import java.math.BigDecimal;
import java.text.Format;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;

import task.marami.Shubhaprada.R;

public class ExpandableListAdapter extends BaseExpandableListAdapter {

    private Context _context;
    private ArrayList<CollectionHeaderData> _listDataHeader; // header titles
    // child data in format of header title, child title
    private HashMap<String, ArrayList<CollectionChild>> _listDataChild;
    @SuppressLint("NewApi")
    Format format = NumberFormat.getCurrencyInstance(new Locale("en", "in"));


    public ExpandableListAdapter(Context _context, ArrayList<CollectionHeaderData> _listDataHeader, HashMap<String, ArrayList<CollectionChild>> _listDataChild) {
        this._context = _context;
        this._listDataHeader = _listDataHeader;
        this._listDataChild = _listDataChild;
    }


    @Override
    public int getGroupCount() {
        return this._listDataHeader.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return this._listDataChild.get(this._listDataHeader.get(groupPosition).getHeader_title()).size();
    }

    @Override
    public CollectionHeaderData getGroup(int groupPosition) {
        return this._listDataHeader.get(groupPosition);
    }

    @Override
    public CollectionChild getChild(int groupPosition, int childPosition) {
        return this._listDataChild.get(this._listDataHeader.get(groupPosition).getHeader_title())
                .get(childPosition);
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
        TextView lblListcou = (TextView) convertView
                .findViewById(R.id.collection_amount);
        //lblListHeader.setTypeface(null, Typeface.BOLD);
        lblListcou.setText(format.format(new BigDecimal(headerTitle.getTotal_amount())));
        lblListHeader.setText(headerTitle.getHeader_title());

        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        final CollectionChild childText =  getChild(groupPosition, childPosition);

        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) this._context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.collection_item, null);
        }

        TextView txtListChildvname = (TextView) convertView
                .findViewById(R.id.txt_collection_v_name);
        TextView txtListChildcol = (TextView) convertView
                .findViewById(R.id.txt_venture_collection);

        txtListChildvname.setText(childText.getAcount_type());
        txtListChildcol.setText(format.format(new BigDecimal(childText.getAmount())));
        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }
}
