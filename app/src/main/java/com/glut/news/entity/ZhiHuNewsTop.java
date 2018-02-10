package com.glut.news.entity;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by yy on 2018/2/1.
 */
public class ZhiHuNewsTop implements Parcelable{
    public String image;
    public int type;
    public int id;
    public String ga_prefix;
    public String title;

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getGa_prefix() {
        return ga_prefix;
    }

    public void setGa_prefix(String ga_prefix) {
        this.ga_prefix = ga_prefix;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public static Creator<ZhiHuNewsTop> getCREATOR() {
        return CREATOR;
    }

    protected ZhiHuNewsTop(Parcel in) {
        image = in.readString();
        type = in.readInt();
        id = in.readInt();
        ga_prefix = in.readString();
        title = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(image);
        dest.writeInt(type);
        dest.writeInt(id);
        dest.writeString(ga_prefix);
        dest.writeString(title);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<ZhiHuNewsTop> CREATOR = new Creator<ZhiHuNewsTop>() {
        @Override
        public ZhiHuNewsTop createFromParcel(Parcel in) {
            return new ZhiHuNewsTop(in);
        }

        @Override
        public ZhiHuNewsTop[] newArray(int size) {
            return new ZhiHuNewsTop[size];
        }
    };
}
