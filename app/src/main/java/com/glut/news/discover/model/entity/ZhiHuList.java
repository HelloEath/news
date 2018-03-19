package com.glut.news.discover.model.entity;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * Created by yy on 2018/2/1.
 */
public class ZhiHuList implements Parcelable {

    public String date;
    public List<ZhiHuNewsModel> stories;


    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public List<ZhiHuNewsModel> getStories() {
        return stories;
    }

    public void setStories(List<ZhiHuNewsModel> stories) {
        this.stories = stories;
    }


    public static Creator<ZhiHuList> getCREATOR() {
        return CREATOR;
    }

    protected ZhiHuList(Parcel in) {
        date = in.readString();
        stories = in.createTypedArrayList(ZhiHuNewsModel.CREATOR);

    }

    public static final Creator<ZhiHuList> CREATOR = new Creator<ZhiHuList>() {
        @Override
        public ZhiHuList createFromParcel(Parcel in) {
            return new ZhiHuList(in);
        }

        @Override
        public ZhiHuList[] newArray(int size) {
            return new ZhiHuList[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(date);
        dest.writeTypedList(stories);

    }
}
