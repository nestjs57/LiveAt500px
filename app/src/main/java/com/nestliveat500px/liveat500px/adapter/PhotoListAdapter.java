package com.nestliveat500px.liveat500px.adapter;

import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.nestliveat500px.liveat500px.R;
import com.nestliveat500px.liveat500px.dao.PhotoItemDao;
import com.nestliveat500px.liveat500px.manager.PhotoListManager;
import com.nestliveat500px.liveat500px.view.PhotoListItem;

public class PhotoListAdapter extends BaseAdapter {


    private PhotoItemDao dao;
    int lastPosition =  -1;

    @Override
    public int getCount() {
        if (PhotoListManager.getInstance().getDao() == null) {
            return 0;
        }
        if (PhotoListManager.getInstance().getDao().getData() == null) {
            return 0;
        }
        return PhotoListManager.getInstance().getDao().getData().size();

    }

    @Override
    public Object getItem(int i) {

        return PhotoListManager.getInstance().getDao().getData().get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        PhotoListItem item;


        if (view != null) {
            item = (PhotoListItem) view;
        } else {
            item = new PhotoListItem(viewGroup.getContext());
        }

        dao = (PhotoItemDao) getItem(i);
        item.setNameText(dao.getCaption());
        item.setDescriptionText(dao.getUsername() + "\n" + dao.getCamera());
        item.setImgUrl(dao.getImageUrl());

        if (i > lastPosition){
            Animation anim = AnimationUtils.loadAnimation(viewGroup.getContext(),
                    R.anim.up_from_bottom);
            item.startAnimation(anim);
            lastPosition = i;
        }

        return item;
    }

}
