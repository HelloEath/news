package com.glut.news.entity;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * Created by yy on 2018/2/1.
 */
public class ZhiHuNews implements Parcelable {
    private int type;
    private int id;
    private String ga_prefix;
    private String title;
    private List<String> images;
    private boolean isRead = false;
    private String date;

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

    public List<String> getImages() {
        return images;
    }

    public void setImages(List<String> images) {
        this.images = images;
    }

    public boolean isRead() {
        return isRead;
    }

    public void setRead(boolean read) {
        isRead = read;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public static Creator<ZhiHuNews> getCREATOR() {
        return CREATOR;
    }

    protected ZhiHuNews(Parcel in) {
        type = in.readInt();
        id = in.readInt();
        ga_prefix = in.readString();
        title = in.readString();
        images = in.createStringArrayList();
        isRead = in.readByte() != 0;
        date = in.readString();
    }

    public static final Creator<ZhiHuNews> CREATOR = new Creator<ZhiHuNews>() {
        @Override
        public ZhiHuNews createFromParcel(Parcel in) {
            return new ZhiHuNews(in);
        }

        @Override
        public ZhiHuNews[] newArray(int size) {
            return new ZhiHuNews[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(type);
        dest.writeInt(id);
        dest.writeString(ga_prefix);
        dest.writeString(title);
        dest.writeStringList(images);
        dest.writeByte((byte) (isRead ? 1 : 0));
        dest.writeString(date);
    }
}
