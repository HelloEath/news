package com.glut.news.weather.view;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.SDKInitializer;
import com.glut.news.AppApplication;
import com.glut.news.BaseFragment;
import com.glut.news.R;
import com.glut.news.common.utils.NetUtil;
import com.glut.news.common.utils.SpUtil;
import com.glut.news.common.utils.manager.RetrofitManager;
import com.glut.news.common.utils.service.RetrofitService;
import com.glut.news.weather.model.HeWeather6;
import com.glut.news.weather.model.Weather;
import com.glut.news.weather.model.Weather2;
import com.glut.news.weather.model.WeatherUtili;
import com.google.gson.Gson;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.zaaach.citypicker.CityPicker;
import com.zaaach.citypicker.adapter.OnPickListener;
import com.zaaach.citypicker.model.City;
import com.zaaach.citypicker.model.HotCity;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action0;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by yy on 2018/3/31.
 */

public class WeatherFragment extends BaseFragment implements IWeatherFragmentView {
    private TextView titleCity;
    private TextView titleUpdateTime;
    private TextView degreeText;
    private TextView weatherInfoText;
    private LinearLayout forcastLayout;
    private TextView aqiText;
    private TextView pm25Text;
    private TextView comfortText;
    private TextView carWashText;
    private TextView sportText;
    private TextView t_min_max;
    private CardView suggtion_car;
    private MyLocationListener myLocationListener = new MyLocationListener();
    private TextView btn_info;
    private LocationClientOption option = new LocationClientOption();
    private String mWeatherId;
    private String mWeatherId2;
    public LocationClient locationClient;
    private Toolbar toolbar;
    private SmartRefreshLayout sfresh;
    private String localtionId;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_weather, container, false);

//在使用SDK各组件之前初始化context信息，传入ApplicationContext（初始化百度地图API）
        SDKInitializer.initialize(AppApplication.getContext());

        locationClient = new LocationClient(AppApplication.getContext());
        initView(v);
        initLocation();
        //initData(mWeatherId2);
        return v;
    }

    //地理位置定位
    private void initLocation() {
        if (NetUtil.isNetworkConnected()){
            locationClient.registerLocationListener(myLocationListener);
            initLocationOption();//初始化定位设置
            locationClient.start();
        }else {
            Toast.makeText(getContext(),"网络走失了",Toast.LENGTH_SHORT).show();
        }


    }

    @Override
    public void onDestroy() {
        locationClient.stop();
        super.onDestroy();
    }

    private void initLocationOption() {
        //可选，设置定位模式，默认高精度
//LocationMode.Hight_Accuracy：高精度；
//LocationMode. Battery_Saving：低功耗；
//LocationMode. Device_Sensors：仅使用设备；
        option.setLocationMode(LocationClientOption.LocationMode.Hight_Accuracy);
        option.setIsNeedAddress(true);
        option.setCoorType("bd09ll");
        //可选，设置返回经纬度坐标类型，默认gcj02
//gcj02：国测局坐标；
//bd09ll：百度经纬度坐标；
//bd09：百度墨卡托坐标；
//海外地区定位，无需设置坐标类型，统一返回wgs84类型坐标

        option.setScanSpan(2000);
//可选，设置发起定位请求的间隔，int类型，单位ms
//如果设置为0，则代表单次定位，即仅定位一次，默认为0
//如果设置非0，需设置1000ms以上才有效

        option.setOpenGps(true);
//可选，设置是否使用gps，默认false
//使用高精度和仅用设备两种定位模式的，参数必须设置为true

        option.setLocationNotify(true);
//可选，设置是否当GPS有效时按照1S/1次频率输出GPS结果，默认false

        option.setIgnoreKillProcess(true);
//可选，定位SDK内部是一个service，并放到了独立进程。
//设置是否在stop的时候杀死这个进程，默认（建议）不杀死，即setIgnoreKillProcess(true)

        option.SetIgnoreCacheException(false);
//可选，设置是否收集Crash信息，默认收集，即参数为false

        option.setWifiCacheTimeOut(5 * 60 * 1000);
//可选，7.2版本新增能力
//如果设置了该接口，首次启动定位时，会先判断当前WiFi是否超出有效期，若超出有效期，会先重新扫描WiFi，然后定位

        option.setEnableSimulateGps(false);
//可选，设置是否需要过滤GPS仿真结果，默认需要，即参数为false

        locationClient.setLocOption(option);
//mLocationClient为第二步初始化过的LocationClient对象
//需将配置好的LocationClientOption对象，通过setLocOption方法传递给LocationClient对象使用
//更多LocationClientOption的配置，请参照类参考中LocationClientOption类的详细说明
    }

    public class MyLocationListener implements BDLocationListener {

        @Override
        public void onReceiveLocation(BDLocation bdLocation) {
            if (bdLocation == null) return;

            double latitude = bdLocation.getLatitude();    //获取纬度信息
            double longitude = bdLocation.getLongitude();    //获取经度信息
            StringBuilder stringBuilder = new StringBuilder();
            String addr = bdLocation.getAddrStr();    //获取详细地址信息
            String country = bdLocation.getCountry();    //获取国家
            String province = bdLocation.getProvince();    //获取省份
            String city = bdLocation.getCity();    //获取城市
            String district = bdLocation.getDistrict();    //获取区县
            String street = bdLocation.getStreet();    //获取街道信息
            Log.d("国家", country);
            Log.d("省份", city);
            stringBuilder.append(latitude + "," + longitude);
            //定位完成之后更新数据
            //CityPicker.getInstance()
                  //  .locateComplete(new LocatedCity(city, province, "101280601"), LocateState.SUCCESS);

            localtionId = stringBuilder.toString();
            if (district!=null){
                city=city+district;
            }
            locationClient.stop();
            titleCity.setText(city);
            initData(localtionId);//


        }


    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);//处理fragment中onOptionsItemSelected方法不被调用问题

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                if (NetUtil.isNetworkConnected()){
                    getCityInfo();

                }else {
                    Toast.makeText(getContext(),"网络走失了",Toast.LENGTH_SHORT).show();
                }
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void getCityInfo() {
        List<HotCity> hotCities = new ArrayList<>();
        hotCities.add(new HotCity("北京", "北京", "101010100"));
        hotCities.add(new HotCity("上海", "上海", "101020100"));
        hotCities.add(new HotCity("广州", "广东", "101280101"));
        hotCities.add(new HotCity("深圳", "广东", "101280601"));
        hotCities.add(new HotCity("杭州", "浙江", "101210101"));


        CityPicker.getInstance()
                .setFragmentManager(getActivity().getSupportFragmentManager())    //此方法必须调用
                .enableAnimation(true)    //启用动画效果



                .setOnPickListener(new OnPickListener() {
                    @Override
                    public void onPick(int position, City data) {
                        Toast.makeText(getActivity(), data.getName(), Toast.LENGTH_SHORT).show();
                        titleCity.setText(data.getName());
                        initData(data.getName());//第一次初始化数据

                    }


                    @Override
                    public void onLocate() {
                        locationClient.start();//开始定位
                       /* //开始定位，这里模拟一下定位
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {

                                //定位完成之后更新数据
                                CityPicker.getInstance()
                                        .locateComplete(new LocatedCity("深圳", "广东", "101280601"), LocateState.SUCCESS);

                                Toast.makeText(getActivity(),mWeatherId2 , Toast.LENGTH_SHORT).show();

                                titleCity.setText(mWeatherId2);
                            }


                        }, 2000);*/
                    }
                })
                .show();
    }

    private void initData(String weatherCity) {

        //http://guolin.tech/api/weather?cityid=
//https://free-api.heweather.com/s6/weather?location=
        String weatherUrl = "https://free-api.heweather.com/s6/weather?location=" + weatherCity + "&key=03943ac06f46426e8bf112b074dc2f26";
        String weatherUrl2 = "https://free-api.heweather.com/s6/air?location=" + weatherCity + "&key=03943ac06f46426e8bf112b074dc2f26";
        String weatherUrl3 = "https://free-api.heweather.com/s6/air?location=" + weatherCity + "&key=03943ac06f46426e8bf112b074dc2f26";

        String key="7c6f7ed1b2e749a688a2f858294281cd";
        Log.d("和风天气api", weatherUrl);
        //请求基本天气数据
        RetrofitManager.builder(RetrofitService.HE_WEATHER_URL, "WeatherService").getWeather(weatherCity,key)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(new Action0() {
                    @Override
                    public void call() {
                    }
                })
                .map(new Func1<HeWeather6, HeWeather6>() {
                    @Override
                    public HeWeather6 call(HeWeather6 h) {
                        return h;
                    }
                })
                .subscribe(new Action1<HeWeather6>() {
                    @Override
                    public void call(final HeWeather6 response) {

                        //final Weather weather = WeatherUtili.handleWeatherResponse(responseText);
                         final Weather weather =null;
                       final String responseText= new Gson().toJson(response, HeWeather6.class);
                        if (weather != null) {

                            //开启新线程处理请求


                            getActivity().runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Log.d("weather.status", weather.status);
                                    //判断请求到的数据是否合法
                                    if (weather != null && "ok".equals(weather.status)) {
                                        //把数据存入本地缓存
                                        SpUtil.saveUserToSp("weather", responseText);
                                        showWeatherInfo(weather);

                                    } else {
                                        Snackbar s = Snackbar.make(getView(), "天气数据异常", Snackbar.LENGTH_LONG).setAction("点我再次获取", new View.OnClickListener() {
                                            @Override
                                            public void onClick(View view) {
                                                locationClient.start();//再次定位

                                            }
                                        });
                                        View sv = s.getView();
//文字的颜色
                                        ((TextView) sv.findViewById(R.id.snackbar_text)).setTextColor(getResources().getColor(R.color.side_1));
                                        sv.setBackgroundColor(0xffffffff);
                                        s.show();

                                    }
                                    // swipeRefreshLayout.setRefreshing(false);
                                }
                            });

                        } else {
                            Snackbar s = Snackbar.make(getView(), "天气数据为空", Snackbar.LENGTH_LONG).setAction("点我再次获取", new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    locationClient.start();//再次定位

                                }
                            });
                            View sv = s.getView();
//文字的颜色
                            ((TextView) sv.findViewById(R.id.snackbar_text)).setTextColor(getResources().getColor(R.color.side_1));
                            sv.setBackgroundColor(0xffffffff);
                            s.show();

                        }

                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {



                    }
                });







/*

        NetUtil.sendHttpRequest(weatherUrl, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {


                        Snackbar s = Snackbar.make(getView(), "获取天气数据失败", Snackbar.LENGTH_LONG).setAction("点我再次获取", new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                locationClient.start();//再次定位

                            }
                        });
                        View sv = s.getView();
//文字的颜色
                        ((TextView) sv.findViewById(R.id.snackbar_text)).setTextColor(getResources().getColor(R.color.side_1));
                        sv.setBackgroundColor(0xffffffff);
                        s.show();

                    }
                });
            }


            @Override
            public void onResponse(Call call, Response response) throws IOException {

                final String responseText = response.body().string();

                final Weather weather = WeatherUtili.handleWeatherResponse(responseText);
                if (weather != null) {

                    //开启新线程处理请求
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Log.d("weather.status", weather.status);
                            //判断请求到的数据是否合法
                            if (weather != null && "ok".equals(weather.status)) {
                                //把数据存入本地缓存
                                SpUtil.saveUserToSp("weather", responseText);
                                showWeatherInfo(weather);

                            } else {
                                Snackbar s = Snackbar.make(getView(), "天气数据异常", Snackbar.LENGTH_LONG).setAction("点我再次获取", new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        locationClient.start();//再次定位

                                    }
                                });
                                View sv = s.getView();
//文字的颜色
                                ((TextView) sv.findViewById(R.id.snackbar_text)).setTextColor(getResources().getColor(R.color.side_1));
                                sv.setBackgroundColor(0xffffffff);
                                s.show();

                            }
                            // swipeRefreshLayout.setRefreshing(false);
                        }
                    });

                } else {
                    Snackbar s = Snackbar.make(getView(), "天气数据为空", Snackbar.LENGTH_LONG).setAction("点我再次获取", new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            locationClient.start();//再次定位

                        }
                    });
                    View sv = s.getView();
//文字的颜色
                    ((TextView) sv.findViewById(R.id.snackbar_text)).setTextColor(getResources().getColor(R.color.side_1));
                    sv.setBackgroundColor(0xffffffff);
                    s.show();

                }


            }
        });
*/



//请求空气质量数据
        NetUtil.sendHttpRequest(weatherUrl2, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Snackbar s = Snackbar.make(getView(), "获取天气数据失败", Snackbar.LENGTH_LONG).setAction("点我再次获取", new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                locationClient.start();//再次定位

                            }
                        });
                        View sv = s.getView();
//文字的颜色
                        ((TextView) sv.findViewById(R.id.snackbar_text)).setTextColor(getResources().getColor(R.color.side_1));
                        sv.setBackgroundColor(0xffffffff);
                        s.show();

                    }
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {

                final String responseText = response.body().string();

                final Weather2 weather2 = WeatherUtili.handleWeatherResponse2(responseText);
                ;
                if (weather2 != null) {

                    //开启新线程处理请求
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Log.d("weather.status", weather2.status);
                            //判断请求到的数据是否合法
                            if (weather2 != null && "ok".equals(weather2.status)) {

                                //把数据存入本地缓存
                                SpUtil.saveUserToSp("weather2", responseText);

                                showWeatherInfo(weather2);

                            } else {
                                Snackbar s = Snackbar.make(getView(), "天气数据异常", Snackbar.LENGTH_LONG).setAction("点我再次获取", new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        locationClient.start();//再次定位

                                    }
                                });
                                View sv = s.getView();
//文字的颜色
                                ((TextView) sv.findViewById(R.id.snackbar_text)).setTextColor(getResources().getColor(R.color.side_1));
                                sv.setBackgroundColor(0xffffffff);
                                s.show();
                            }
                            // swipeRefreshLayout.setRefreshing(false);
                        }
                    });

                } else {
                    Snackbar s = Snackbar.make(getView(), "天气数据为空", Snackbar.LENGTH_LONG).setAction("点我再次获取", new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            locationClient.start();//再次定位

                        }
                    });
                    View sv = s.getView();
//文字的颜色
                    ((TextView) sv.findViewById(R.id.snackbar_text)).setTextColor(getResources().getColor(R.color.side_1));
                    sv.setBackgroundColor(0xffffffff);
                    s.show();

                }
                sfresh.finishRefresh(true);//结束刷新状态
            }
        });
    }

    /*获并展示weather实体中的数据*/
    private void showWeatherInfo(Object o) {

        String updateTime = null;
        if (o instanceof Weather) {

            Weather weather = (Weather) o;

            String t_max = weather.forecast.get(0).tmp_max;
            String t_min = weather.forecast.get(0).tmp_min;
            t_min_max.setText(t_min + "℃~" + t_max + "℃");
            String cityName = weather.basic.location;

            updateTime = weather.update.loc;


            String degree = weather.now.temperature + "℃";
            String weatherInfo = weather.now.cond_txt;
            //titleCity.setText(cityName);
            //titleUpdateTime.setText(updateTime);
            degreeText.setText(degree);
            weatherInfoText.setText(weatherInfo);
            forcastLayout.removeAllViews();


            for (Weather.Forecast forcast : weather.forecast) {
                View view = LayoutInflater.from(getContext()).inflate(R.layout.forecast_item, forcastLayout, false);

                TextView dateText = (TextView) view.findViewById(R.id.date_text);
                TextView infoText = (TextView) view.findViewById(R.id.info_text);
                TextView maxText = (TextView) view.findViewById(R.id.max_text);
                TextView minText = (TextView) view.findViewById(R.id.min_text);
                dateText.setText(forcast.date);
                // infoText.setText(forcast.more.info);
                maxText.setText(forcast.tmp_max + "℃");
                minText.setText(forcast.tmp_min + "℃");
                forcastLayout.addView(view);
            }

            String comfort = "舒适度：" + weather.suggestionList.get(0).txt;
            String carWash = "洗车指数：" + weather.suggestionList.get(6).txt;
            String sport = "运动指数：" + weather.suggestionList.get(3).txt;
            comfortText.setText(comfort);
            carWashText.setText(carWash);
            sportText.setText(sport);
            // weatherLayout.setVisibility(View.VISIBLE);
        } else {

            Weather2 weather2 = (Weather2) o;
            if (weather2.now != null) {
                aqiText.setText(weather2.now.aqi);
                pm25Text.setText(weather2.now.pm25);
            }


        }


    }

    private void initView(View v) {
        t_min_max = (TextView) v.findViewById(R.id.weather_info_t_min_max);

        toolbar = v.findViewById(R.id.toolbar);
        suggtion_car = (CardView) v.findViewById(R.id.suggtion);
        titleCity = (TextView) v.findViewById(R.id.title_city);
        //titleUpdateTime= (TextView) findViewById(R.id.title_update_time);
        degreeText = (TextView) v.findViewById(R.id.degree_text);
        weatherInfoText = (TextView) v.findViewById(R.id.weather_info_text);
        forcastLayout = (LinearLayout) v.findViewById(R.id.forecast_layout);
        aqiText = (TextView) v.findViewById(R.id.aqi_text);
        pm25Text = (TextView) v.findViewById(R.id.pm25_text);
        comfortText = (TextView) v.findViewById(R.id.comfort_text);
        carWashText = (TextView) v.findViewById(R.id.car_wash_text);
        sportText = (TextView) v.findViewById(R.id.sport_text);

        AppCompatActivity a = (AppCompatActivity) getActivity();
        a.setSupportActionBar(toolbar);
        ActionBar actionBar = a.getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        // titleCity.setText(weatherCity);
        toolbar.setTitle("");

        toolbar.setNavigationIcon(R.drawable.weather_btn_location);
        sfresh = v.findViewById(R.id.refreshLayout);

        sfresh.setEnableLoadMore(false);
        sfresh.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {

                if (NetUtil.isNetworkConnected()){
                    refreshlayout.finishRefresh(3000);
                    if (!titleCity.getText().toString().equals("")){
                        initData(localtionId);
                    }else {
                        locationClient.start();
                    }
                }else {
                    Toast.makeText(getContext(),"网络走失了",Toast.LENGTH_SHORT).show();
                }



            }
        });

    }

    @Override
    public void loadWeatherSuccesss() {

    }

    @Override
    public void loadWeatherFail() {

    }
}
