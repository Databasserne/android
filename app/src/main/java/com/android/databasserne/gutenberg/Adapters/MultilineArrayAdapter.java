package com.android.databasserne.gutenberg.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.android.databasserne.gutenberg.R;
import com.android.databasserne.gutenberg.SingleResult;

import java.util.ArrayList;

/**
 * Created by kasper on 5/17/17.
 */

public class MultilineArrayAdapter extends BaseAdapter {

    private static ArrayList<SingleResult> Results;
    private LayoutInflater inflater;

    public MultilineArrayAdapter(Context context, ArrayList<SingleResult> arr){
        Results = arr;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return Results.size();
    }

    @Override
    public Object getItem(int i) {
        return Results.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder holder;
        if(view == null){
            view = inflater.inflate(R.layout.multiline_list_single, null);
            holder = new ViewHolder();
            holder.first = (TextView) view.findViewById(R.id.single_first);
            holder.second = (TextView) view.findViewById(R.id.single_second);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }
        holder.first.setText(Results.get(i).getFirst());
        holder.second.setText(Results.get(i).getSecond());

        return view;
    }

    private static class ViewHolder{
        TextView first;
        TextView second;
    }
}
