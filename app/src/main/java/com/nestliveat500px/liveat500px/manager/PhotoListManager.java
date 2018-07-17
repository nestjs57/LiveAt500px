package com.nestliveat500px.liveat500px.manager;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import com.google.gson.Gson;
import com.inthecheesefactory.thecheeselibrary.manager.Contextor;
import com.nestliveat500px.liveat500px.dao.PhotoItemCollectionDao;

/**
 * Created by nuuneoi on 11/16/2014.
 */
public class PhotoListManager {

    private static PhotoListManager instance;
    private PhotoItemCollectionDao dao;

    public static PhotoListManager getInstance() {
        if (instance == null)
            instance = new PhotoListManager();
        return instance;
    }

    private Context mContext;

    public PhotoListManager() {

        mContext = Contextor.getInstance().getContext();
        //Load Data From persitance Storage
        loadCache();
    }

    public PhotoItemCollectionDao getDao() {
        return dao;
    }

    public void setDao(PhotoItemCollectionDao dao) {

        this.dao = dao;
        saveCache();
    }

    public Bundle onSaveInsatanceState() {
        Bundle bundle = new Bundle();
        bundle.putParcelable("dao", dao);
        return bundle;
    }

    public void onRestoreInstanceState(Bundle saveInsatanceState) {
//        dao = saveInsatanceState.getParcelable("dao");
    }

    public void saveCache() {

        PhotoItemCollectionDao cacheDao = new PhotoItemCollectionDao();
        if (dao != null && dao.getData() != null) {
            cacheDao.setData(dao.getData().subList(0, Math.min(20, dao.getData().size())));
        }
        String json = new Gson().toJson(cacheDao);

        SharedPreferences prefs = mContext.getSharedPreferences("photos",
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        //Add / Edit /Delete
        editor.putString("json", json);
        editor.apply();
    }

    public void loadCache() {
        SharedPreferences srefs = mContext.getSharedPreferences("photos",
                Context.MODE_PRIVATE);
        String json = srefs.getString("json", null);
        if (json == null)
            return;
        dao = new Gson().fromJson(json, PhotoItemCollectionDao.class);
    }
}
