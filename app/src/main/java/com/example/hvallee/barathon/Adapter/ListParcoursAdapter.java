package com.example.hvallee.barathon.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.hvallee.barathon.Model.Bar;
import com.example.hvallee.barathon.Model.Parcours;
import com.example.hvallee.barathon.R;

import java.util.List;

/**
 * Created by yoannlt on 20/01/2016.
 */
public class ListParcoursAdapter extends BaseAdapter {

    private final String LOG_TAG = ListParcoursAdapter.class.getSimpleName();

    private List<Parcours> mListParcours;
    private Context mContext;
    private Long id;

    public ListParcoursAdapter(List<Parcours> mListParcours, Context mContext) {
        this.mListParcours = mListParcours;
        this.mContext = mContext;
    }

    @Override
    public int getCount() {
        return mListParcours.size();
    }

    @Override
    public Object getItem(int position) {
        return mListParcours.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        MyViewHolder myViewHolder = new MyViewHolder();

        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View row = inflater.inflate(R.layout.parcours_adapter_item_view, null);

        myViewHolder.ParcoursName = (TextView)row.findViewById(R.id.parcoursName);
        myViewHolder.ParcoursName.setText(mListParcours.get(position).getName());
        myViewHolder.ParcoursDescription = (TextView)row.findViewById(R.id.parcoursDescription);
        myViewHolder.ParcoursDescription.setText(mListParcours.get(position).getDescription());

        setId(mListParcours.get(position).getId());

        return row;
    }

    private static class MyViewHolder {
        TextView ParcoursName;
        TextView ParcoursDescription;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    public void clear(){
        mListParcours.clear();
    }

    public void setmListParcours(List<Parcours> list){
        mListParcours = list;
    }
}
