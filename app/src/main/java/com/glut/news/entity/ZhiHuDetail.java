package com.glut.news.entity;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * Created by yy on 2018/2/1.
 */
public class ZhiHuDetail implements Parcelable{
    private String body;
    private String image_source;
    private String title;
    private String image;
    private String share_url;
    private String ga_prefix;
    private int type;
    private int id;
    private List<String> js;
    private List<String> css;

    protected ZhiHuDetail(Parcel in) {
        body = in.readString();
        image_source = in.readString();
        title = in.readString();
        image = in.readString();
        share_url = in.readString();
        ga_prefix = in.readString();
        type = in.readInt();
        id = in.readInt();
        js = in.createStringArrayList();
        css = in.createStringArrayList();
    }

    public static final Creator<ZhiHuDetail> CREATOR = new Creator<ZhiHuDetail>() {
        @Override
        public ZhiHuDetail createFromParcel(Parcel in) {
            return new ZhiHuDetail(in);
        }

        @Override
        public ZhiHuDetail[] newArray(int size) {
            return new ZhiHuDetail[size];
        }
    };

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getImage_source() {
        return image_source;
    }

    public void setImage_source(String image_source) {
        this.image_source = image_source;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getShare_url() {
        return share_url;
    }

    public void setShare_url(String share_url) {
        this.share_url = share_url;
    }

    public String getGa_prefix() {
        return ga_prefix;
    }

    public void setGa_prefix(String ga_prefix) {
        this.ga_prefix = ga_prefix;
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

    public List<String> getJs() {
        return js;
    }

    public void setJs(List<String> js) {
        this.js = js;
    }

    public List<String> getCss() {
        return css;
    }

    public void setCss(List<String> css) {
        this.css = css;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(body);
        dest.writeString(image_source);
        dest.writeString(title);
        dest.writeString(image);
        dest.writeString(share_url);
        dest.writeString(ga_prefix);
        dest.writeInt(type);
        dest.writeInt(id);
        dest.writeStringList(js);
        dest.writeStringList(css);
    }
}
