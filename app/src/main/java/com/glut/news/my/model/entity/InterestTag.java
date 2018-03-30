package com.glut.news.my.model.entity;

/**
 * Created by yy on 2018/3/27.
 */

public class InterestTag {
    private int id;
    private int UserId;
    private String interestTag;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return UserId;
    }

    public void setUserId(int userId) {
        UserId = userId;
    }

    public String getInterestTag() {
        return interestTag;
    }

    public void setInterestTag(String interestTag) {
        this.interestTag = interestTag;
    }

    @Override
    public String toString() {
        return "InterestTag{" +
                "id=" + id +
                ", UserId=" + UserId +
                ", interestTag='" + interestTag + '\'' +
                '}';
    }
}
