package com.nestliveat500px.liveat500px.manager;

import android.content.Context;
import android.os.Bundle;

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
    }

    public PhotoItemCollectionDao getDao() {
        return dao;
    }

    public void setDao(PhotoItemCollectionDao dao) {
        this.dao = dao;
    }

    public Bundle onSaveInsatanceState(){
        Bundle bundle = new Bundle();
        bundle.putParcelable("dao",dao);
        return bundle;
    }
    public void onRestoreInstanceState(Bundle saveInsatanceState){
        dao = saveInsatanceState.getParcelable("dao");
    }
}
