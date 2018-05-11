package com.glut.news.weather.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by yy on 2018/3/31.
 */

public class Weather2 {
    public String status;
    public HeWeather6.HeWeather6Bean.BasicBean basic;
    /* public Aqi aqi;*/
    public HeWeather6.HeWeather6Bean.UpdateBean update;
    @SerializedName("air_now_city")
    public AirNow now;

    public class AirNow {
        public String pub_time;//更新时间
        public String aqi;//空气质量指数
        public String main;//主要污染物
        public String qlty;//空气质量
        public String pm25;
    }
}
