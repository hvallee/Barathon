package com.example.hvallee.barathon.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.hvallee.barathon.Model.Bar;
import com.example.hvallee.barathon.R;

import java.util.List;

/**
 * Created by yoannlt on 27/11/2015.
 */
public class ListBarAdapter extends BaseAdapter {


    private final String LOG_TAG = ListBarAdapter.class.getSimpleName();

    private List<Bar> mListBars;
    private Context mContext;
    private Long id;

    public ListBarAdapter(List<Bar> bars, Context context) {
        mListBars = bars;
        mContext = context;
    }

    @Override
    public int getCount() {
        return mListBars.size();
    }

    @Override
    public Object getItem(int position) {
        return mListBars.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        MyViewHolder myViewHolder = new MyViewHolder();

        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View row = inflater.inflate(R.layout.bar_adapter_item_view, null);

        myViewHolder.barName = (TextView)row.findViewById(R.id.bar_name);
        myViewHolder.barAddress = (TextView)row.findViewById(R.id.bar_address);

        myViewHolder.barName.setText(mListBars.get(position).getName());
        myViewHolder.barAddress.setText(mListBars.get(position).getAddress());

        setId(mListBars.get(position).getId());

        return null;
    }

    private static class MyViewHolder {
        TextView barName;
        TextView barAddress;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
