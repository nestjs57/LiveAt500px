package com.nestliveat500px.liveat500px.dao;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class PhotoItemCollectionDao implements Parcelable{

    public PhotoItemCollectionDao(){
        //Create Emtry Contructor
    }

    @SerializedName("success")
    private Boolean success;
    @SerializedName("data")
    private List<PhotoItemDao> data;

    protected PhotoItemCollectionDao(Parcel in) {
        byte tmpSuccess = in.readByte();
        success = tmpSuccess == 0 ? null : tmpSuccess == 1;
        data = in.createTypedArrayList(PhotoItemDao.CREATOR);
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeByte((byte) (success == null ? 0 : success ? 1 : 2));
        dest.writeTypedList(data);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<PhotoItemCollectionDao> CREATOR = new Creator<PhotoItemCollectionDao>() {
        @Override
        public PhotoItemCollectionDao createFromParcel(Parcel in) {
            return new PhotoItemCollectionDao(in);
        }

        @Override
        public PhotoItemCollectionDao[] newArray(int size) {
            return new PhotoItemCollectionDao[size];
        }
    };

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public List<PhotoItemDao> getData() {
        return data;
    }

    public void setData(List<PhotoItemDao> data) {
        this.data = data;
    }
}
