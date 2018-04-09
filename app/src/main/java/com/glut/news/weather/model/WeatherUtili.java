package com.glut.news.weather.model;

import android.util.Log;

import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by yy on 2018/3/31.
 */

public class WeatherUtili {
    public static Weather2 handleWeatherResponse2(String response){
        try {
            JSONObject jsonObject=new JSONObject(response);
            JSONArray jsonArray=jsonObject.getJSONArray("HeWeather6");
            String weatherContent=jsonArray.getJSONObject(0).toString();
            Log.d("解析的天气数据",weatherContent);
            return new Gson().fromJson(weatherContent,Weather2.class);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;


    }
    public static Weather handleWeatherResponse(String response) {
        try {
            JSONObject jsonObject = new JSONObject(response);
            JSONArray jsonArray = jsonObject.getJSONArray("HeWeather6");
            String weatherContent = jsonArray.getJSONObject(0).toString();
            Log.d("解析的天气数据", weatherContent);
            return new Gson().fromJson(weatherContent, Weather.class);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;


    }

}
