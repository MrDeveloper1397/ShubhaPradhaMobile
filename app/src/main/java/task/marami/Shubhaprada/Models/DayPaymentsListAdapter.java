package task.marami.Shubhaprada.Models;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Typeface;
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


public class DayPaymentsListAdapter extends BaseExpandableListAdapter {
    private Context _context;
    private ArrayList<DayPaymentHeader> _listDataHeader; // header titles
    // child data in format of header title, child title
    private HashMap<String, ArrayList<DayPaymentsItem>> _listDataChild;
    @SuppressLint("NewApi")
    Format format = NumberFormat.getCurrencyInstance(new Locale("en", "in"));


    public DayPaymentsListAdapter(Context _context, ArrayList<DayPaymentHeader> _listDataHeader, HashMap<String, ArrayList<DayPaymentsItem>> _listDataChild) {
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

        return this._listDataChild.get(this._listDataHeader.get(groupPosition).getVenture()).size();
    }

    @Override
    public DayPaymentHeader getGroup(int groupPosition) {
        return this._listDataHeader.get(groupPosition);
    }

    @Override
    public DayPaymentsItem getChild(int groupPosition, int childPosition) {
        return this._listDataChild.get(this._listDataHeader.get(groupPosition).getVenture()).
                get(childPosition);
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
        return true;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        DayPaymentHeader headerTitle =  getGroup(groupPosition);
        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) this._context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.collection_header, null);
        }

        TextView lblListHeader = (TextView) convertView
                .findViewById(R.id.txt_collection_title);
        TextView lblListcou = (TextView) convertView
                .findViewById(R.id.collection_amount);
        lblListHeader.setTypeface(null, Typeface.BOLD);
        lblListcou.setText(format.format(new BigDecimal(headerTitle.getTotal())));
        lblListHeader.setText(headerTitle.getVenture());

        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        final DayPaymentsItem childText =  getChild(groupPosition, childPosition);

        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) this._context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.payment_child, null);
        }
        TextView child_header=(TextView) convertView.findViewById(R.id.txt_payment_child_header);


        child_header.setText(childText.getUser_type());
        TextView txtListChildvname = (TextView) convertView
                .findViewById(R.id.agent_total);

        txtListChildvname.setText(childText.getAmount());

        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }
}
