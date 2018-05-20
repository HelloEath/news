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
import com.glut.news.common.model.db.DBManager;
import com.glut.news.common.utils.NetUtil;
import com.glut.news.common.utils.manager.RetrofitManager;
import com.glut.news.common.utils.service.RetrofitService;
import com.glut.news.weather.model.MeiZuWeather;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.zaaach.citypicker.CityPicker;
import com.zaaach.citypicker.adapter.OnPickListener;
import com.zaaach.citypicker.model.City;
import com.zaaach.citypicker.model.HotCity;
import com.zaaach.citypicker.model.LocateState;
import com.zaaach.citypicker.model.LocatedCity;

import java.util.ArrayList;
import java.util.List;

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
    private TextView comfortText1;
    private TextView carWashText1;
    private TextView sportText1;
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
    private DBManager dbManager;
    private City mCity;
    CityPicker cityPicker;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_weather, container, false);
//在使用SDK各组件之前初始化context信息，传入ApplicationContext（初始化百度地图API）
        SDKInitializer.initialize(AppApplication.getContext());
        locationClient = new LocationClient(AppApplication.getContext());
        dbManager = new DBManager(getContext());
        initView(v);
        initLocation();
        return v;

    }

    //地理位置定位
    private void initLocation() {
        if (NetUtil.isNetworkConnected()) {
            getCityInfo();
            locationClient.registerLocationListener(myLocationListener);
            initLocationOption();//初始化定位设置
            locationClient.start();

        } else {
            Toast.makeText(getContext(), "网络走失了", Toast.LENGTH_SHORT).show();
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
            String cityCode = bdLocation.getCityCode();
            String dfdif = bdLocation.getCountryCode();
            String dd = bdLocation.getDistrict();

            Log.d("省份", city);
            stringBuilder.append(latitude + "," + longitude);
            //定位完成之后更新数据

            List<City> cities = dbManager.searchCitys(city.substring(0, city.length() - 1));
            localtionId =cities.get(0).getCode();
            CityPicker.getInstance()
                    .locateComplete(new LocatedCity(city, province, cities.get(0).getCode()), LocateState.SUCCESS);
            if (district != null) {
                city =district;
            }
            locationClient.stop();
            titleCity.setText(city);
            initData(cities.get(0).getCode());//

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
                if (NetUtil.isNetworkConnected()) {
                    cityPicker.show();
                } else {
                    Toast.makeText(getContext(), "网络走失了", Toast.LENGTH_SHORT).show();
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
        cityPicker = CityPicker.getInstance()
                .setFragmentManager(getActivity().getSupportFragmentManager())    //此方法必须调用
                .enableAnimation(true)    //启用动画效果
                .setOnPickListener(new OnPickListener() {
                    @Override
                    public void onPick(int position, City data) {
                        titleCity.setText(data.getName());
                        initData(data.getCode());//
                    }
                    @Override
                    public void onLocate() {
                        locationClient.start();//开始定位
                    }
                })
        ;
    }

    private void initData(String weatherCity) {
        String weatherUrl = "http://aider.meizu.com/app/weather/listWeather";
        String weatherUrl2 = "https://free-api.heweather.com/s6/air?location=" + weatherCity + "&key=03943ac06f46426e8bf112b074dc2f26";
        //请求基本天气数据
        RetrofitManager.builder(RetrofitService.HE_WEATHER_URL, "WeatherService").getWeather(weatherCity)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(new Action0() {
                    @Override
                    public void call() {
                    }
                })
                .map(new Func1<MeiZuWeather, MeiZuWeather>() {
                    @Override
                    public MeiZuWeather call(MeiZuWeather h) {
                        return h;
                    }
                })
                .subscribe(new Action1<MeiZuWeather>() {
                    @Override
                    public void call(final MeiZuWeather weather) {
                        if (weather != null) {
                            Log.d("weather.status", weather.getCode());
                            //判断请求到的数据是否合法
                            if (weather != null && "200".equals(weather.getCode())) {
                                //把数据存入本地缓存
                                //SpUtil.saveUserToSp("weather", new Gson().toJson(weather, HeWeather6.class));
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


    }

    /*获并展示weather实体中的数据*/
    private void showWeatherInfo(MeiZuWeather meiZuWeather) {
        String t_max = meiZuWeather.getValue().get(0).getWeathers().get(0).getTemp_day_c();
        String t_min = meiZuWeather.getValue().get(0).getWeathers().get(0).getTemp_night_c();
        t_min_max.setText(t_min + "℃~" + t_max + "℃");
        String updateTime = meiZuWeather.getValue().get(0).getRealtime().getTime();
        String degree = meiZuWeather.getValue().get(0).getRealtime().getSendibleTemp() + "℃";
        String weatherInfo = meiZuWeather.getValue().get(0).getRealtime().getWeather();
        //titleCity.setText(cityName);
        //titleUpdateTime.setText(updateTime)
        degreeText.setText(degree);
        weatherInfoText.setText(weatherInfo);
        String comfort = "穿衣指数：" + meiZuWeather.getValue().get(0).getIndexes().get(3).getLevel();
        String carWash = "洗车指数：" + meiZuWeather.getValue().get(0).getIndexes().get(4).getLevel();
        String sport = "运动指数：" + meiZuWeather.getValue().get(0).getIndexes().get(2).getLevel();
        comfortText.setText(comfort);
        carWashText.setText(carWash);
        sportText.setText(sport);
        comfortText1.setText(meiZuWeather.getValue().get(0).getIndexes().get(3).getContent());
        carWashText1.setText(meiZuWeather.getValue().get(0).getIndexes().get(4).getContent());
        sportText1.setText(meiZuWeather.getValue().get(0).getIndexes().get(2).getContent());
        aqiText.setText(meiZuWeather.getValue().get(0).getPm25().getAqi());
        pm25Text.setText(meiZuWeather.getValue().get(0).getPm25().getPm25());
        sfresh.finishRefresh();
    }

    private void initView(View v) {
        t_min_max = (TextView) v.findViewById(R.id.weather_info_t_min_max);
        carWashText1 = v.findViewById(R.id.car_wash_text1);
        comfortText1 = v.findViewById(R.id.comfort_text1);
        sportText1 = v.findViewById(R.id.sport_text1);
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

                if (NetUtil.isNetworkConnected()) {
                    if (!titleCity.getText().toString().equals("")) {
                        initData(localtionId);
                    } else {
                        locationClient.start();
                    }
                } else {
                    Toast.makeText(getContext(), "网络走失了", Toast.LENGTH_SHORT).show();
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
