package com.nestliveat500px.liveat500px.manager.http;


import com.nestliveat500px.liveat500px.dao.PhotoItemCollectionDao;

import retrofit2.Call;
import retrofit2.http.POST;

public interface ApiService {

    @POST("list")
    Call<PhotoItemCollectionDao> loadPhotoList();

}
