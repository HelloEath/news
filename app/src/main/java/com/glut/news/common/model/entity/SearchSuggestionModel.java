package com.glut.news.common.model.entity;


import android.annotation.SuppressLint;
import android.os.Parcel;

import com.arlib.floatingsearchview.suggestions.model.SearchSuggestion;

/**
 * Created by yy on 2018/4/27.
 */

@SuppressLint("ParcelCreator")
public class SearchSuggestionModel implements SearchSuggestion {
    @Override
    public String getBody() {
        return null;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {

    }
}
