package com.glut.news.my.model.entity;

/**
 * Created by yy on 2018/3/9.
 */
public class Star {
    private int Star_Id;
    private int Star_ContentId;
    private int Star_UserId;
    private String  Star_Time;
    private int Star_Type;

    public int getStar_Id() {
        return Star_Id;
    }

    public void setStar_Id(int star_Id) {
        Star_Id = star_Id;
    }

    public int getStar_ContentId() {
        return Star_ContentId;
    }

    public void setStar_ContentId(int star_ContentId) {
        Star_ContentId = star_ContentId;
    }

    public int getStar_UserId() {
        return Star_UserId;
    }

    public void setStar_UserId(int star_UserId) {
        Star_UserId = star_UserId;
    }

    public String getStar_Time() {
        return Star_Time;
    }

    public void setStar_Time(String star_Time) {
        Star_Time = star_Time;
    }

    public int getStar_Type() {
        return Star_Type;
    }

    public void setStar_Type(int star_Type) {
        Star_Type = star_Type;
    }
}
