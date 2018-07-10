package com.nestliveat500px.liveat500px.adapter;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.nestliveat500px.liveat500px.view.PhotoListItem;

public class PhotoListAdapter extends BaseAdapter {
    @Override
    public int getCount() {
        return 100;
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        if (getItemViewType(i) % 2 == 0) {
            PhotoListItem item;
            if (view != null) {
                item = (PhotoListItem) view;
            } else {
                item = new PhotoListItem(viewGroup.getContext());
            }
            return item;
        } else {
            TextView item;

            if (view != null) {
                item = (TextView) view;
            } else {
                item = new TextView(viewGroup.getContext());
            }
            item.setText("Position : " + i);
            return item;
        }

    }

    @Override
    public int getViewTypeCount() {
        return 2;
    }

    @Override
    public int getItemViewType(int position) {
        return position % 2 == 0 ? 0 : 1;
    }
}
