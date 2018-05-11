package com.glut.news.weather.model;

import java.util.List;

/**
 * Created by yy on 2018/5/4.
 */

public class HeWeather6 {


    private List<HeWeather6Bean> HeWeather6;

    public List<HeWeather6Bean> getHeWeather6() {
        return HeWeather6;
    }

    public void setHeWeather6(List<HeWeather6Bean> HeWeather6) {
        this.HeWeather6 = HeWeather6;
    }

    public static class HeWeather6Bean {
        /**
         * basic : {"cid":"CN101300518","location":"雁山","parent_city":"桂林","admin_area":"广西","cnty":"中国","lat":"25.07764626","lon":"110.30566406","tz":"+8.00"}
         * update : {"loc":"2018-05-10 13:47","utc":"2018-05-10 05:47"}
         * status : ok
         * now : {"cloud":"100","cond_code":"104","cond_txt":"阴","fl":"26","hum":"83","pcpn":"0.0","pres":"1014","tmp":"23","vis":"5","wind_deg":"185","wind_dir":"南风","wind_sc":"1","wind_spd":"2"}
         * daily_forecast : [{"cond_code_d":"305","cond_code_n":"305","cond_txt_d":"小雨","cond_txt_n":"小雨","date":"2018-05-10","hum":"83","mr":"02:32","ms":"14:14","pcpn":"0.1","pop":"41","pres":"1014","sr":"05:57","ss":"19:14","tmp_max":"23","tmp_min":"18","uv_index":"10","vis":"18","wind_deg":"350","wind_dir":"北风","wind_sc":"1-2","wind_spd":"2"},{"cond_code_d":"305","cond_code_n":"305","cond_txt_d":"小雨","cond_txt_n":"小雨","date":"2018-05-11","hum":"80","mr":"03:09","ms":"15:08","pcpn":"0.0","pop":"0","pres":"1011","sr":"05:56","ss":"19:15","tmp_max":"25","tmp_min":"20","uv_index":"6","vis":"17","wind_deg":"183","wind_dir":"南风","wind_sc":"1-2","wind_spd":"3"},{"cond_code_d":"305","cond_code_n":"305","cond_txt_d":"小雨","cond_txt_n":"小雨","date":"2018-05-12","hum":"86","mr":"03:46","ms":"16:04","pcpn":"0.0","pop":"0","pres":"1010","sr":"05:56","ss":"19:15","tmp_max":"28","tmp_min":"22","uv_index":"6","vis":"19","wind_deg":"187","wind_dir":"南风","wind_sc":"1-2","wind_spd":"9"}]
         * hourly : [{"cloud":"97","cond_code":"305","cond_txt":"小雨","dew":"19","hum":"71","pop":"20","pres":"1010","time":"2018-05-10 16:00","tmp":"23","wind_deg":"17","wind_dir":"东北风","wind_sc":"1-2","wind_spd":"10"},{"cloud":"90","cond_code":"305","cond_txt":"小雨","dew":"20","hum":"82","pop":"7","pres":"1011","time":"2018-05-10 19:00","tmp":"22","wind_deg":"9","wind_dir":"北风","wind_sc":"1-2","wind_spd":"6"},{"cloud":"92","cond_code":"305","cond_txt":"小雨","dew":"20","hum":"90","pop":"7","pres":"1012","time":"2018-05-10 22:00","tmp":"22","wind_deg":"170","wind_dir":"南风","wind_sc":"1-2","wind_spd":"3"},{"cloud":"98","cond_code":"305","cond_txt":"小雨","dew":"19","hum":"93","pop":"68","pres":"1012","time":"2018-05-11 01:00","tmp":"21","wind_deg":"214","wind_dir":"西南风","wind_sc":"1-2","wind_spd":"8"},{"cloud":"99","cond_code":"104","cond_txt":"阴","dew":"19","hum":"94","pop":"52","pres":"1010","time":"2018-05-11 04:00","tmp":"21","wind_deg":"212","wind_dir":"西南风","wind_sc":"1-2","wind_spd":"10"},{"cloud":"99","cond_code":"305","cond_txt":"小雨","dew":"20","hum":"94","pop":"64","pres":"1011","time":"2018-05-11 07:00","tmp":"21","wind_deg":"229","wind_dir":"西南风","wind_sc":"1-2","wind_spd":"7"},{"cloud":"99","cond_code":"305","cond_txt":"小雨","dew":"21","hum":"89","pop":"49","pres":"1011","time":"2018-05-11 10:00","tmp":"23","wind_deg":"185","wind_dir":"南风","wind_sc":"1-2","wind_spd":"1"},{"cloud":"99","cond_code":"305","cond_txt":"小雨","dew":"21","hum":"83","pop":"25","pres":"1009","time":"2018-05-11 13:00","tmp":"25","wind_deg":"183","wind_dir":"南风","wind_sc":"1-2","wind_spd":"3"}]
         * lifestyle : [{"type":"comf","brf":"较舒适","txt":"白天有降雨，但会使人们感觉有些热，不过大部分人仍会有比较舒适的感觉。"},{"type":"drsg","brf":"舒适","txt":"建议着长袖T恤、衬衫加单裤等服装。年老体弱者宜着针织长袖衬衫、马甲和长裤。"},{"type":"flu","brf":"少发","txt":"各项气象条件适宜，无明显降温过程，发生感冒机率较低。"},{"type":"sport","brf":"较不宜","txt":"有降水，推荐您在室内进行健身休闲运动；若坚持户外运动，须注意携带雨具并注意避雨防滑。"},{"type":"trav","brf":"适宜","txt":"有降水，温度适宜，在细雨中游玩别有一番情调，可不要错过机会呦！但记得出门要携带雨具。"},{"type":"uv","brf":"弱","txt":"紫外线强度较弱，建议出门前涂擦SPF在12-15之间、PA+的防晒护肤品。"},{"type":"cw","brf":"不宜","txt":"不宜洗车，未来24小时内有雨，如果在此期间洗车，雨水和路上的泥水可能会再次弄脏您的爱车。"},{"type":"air","brf":"良","txt":"气象条件有利于空气污染物稀释、扩散和清除，可在室外正常活动。"}]
         */

        private BasicBean basic;
        private UpdateBean update;
        private String status;
        private NowBean now;
        private List<DailyForecastBean> daily_forecast;
        private List<HourlyBean> hourly;
        private List<LifestyleBean> lifestyle;

        public BasicBean getBasic() {
            return basic;
        }

        public void setBasic(BasicBean basic) {
            this.basic = basic;
        }

        public UpdateBean getUpdate() {
            return update;
        }

        public void setUpdate(UpdateBean update) {
            this.update = update;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public NowBean getNow() {
            return now;
        }

        public void setNow(NowBean now) {
            this.now = now;
        }

        public List<DailyForecastBean> getDaily_forecast() {
            return daily_forecast;
        }

        public void setDaily_forecast(List<DailyForecastBean> daily_forecast) {
            this.daily_forecast = daily_forecast;
        }

        public List<HourlyBean> getHourly() {
            return hourly;
        }

        public void setHourly(List<HourlyBean> hourly) {
            this.hourly = hourly;
        }

        public List<LifestyleBean> getLifestyle() {
            return lifestyle;
        }

        public void setLifestyle(List<LifestyleBean> lifestyle) {
            this.lifestyle = lifestyle;
        }

        public static class BasicBean {
            /**
             * cid : CN101300518
             * location : 雁山
             * parent_city : 桂林
             * admin_area : 广西
             * cnty : 中国
             * lat : 25.07764626
             * lon : 110.30566406
             * tz : +8.00
             */

            private String cid;
            private String location;
            private String parent_city;
            private String admin_area;
            private String cnty;
            private String lat;
            private String lon;
            private String tz;

            public String getCid() {
                return cid;
            }

            public void setCid(String cid) {
                this.cid = cid;
            }

            public String getLocation() {
                return location;
            }

            public void setLocation(String location) {
                this.location = location;
            }

            public String getParent_city() {
                return parent_city;
            }

            public void setParent_city(String parent_city) {
                this.parent_city = parent_city;
            }

            public String getAdmin_area() {
                return admin_area;
            }

            public void setAdmin_area(String admin_area) {
                this.admin_area = admin_area;
            }

            public String getCnty() {
                return cnty;
            }

            public void setCnty(String cnty) {
                this.cnty = cnty;
            }

            public String getLat() {
                return lat;
            }

            public void setLat(String lat) {
                this.lat = lat;
            }

            public String getLon() {
                return lon;
            }

            public void setLon(String lon) {
                this.lon = lon;
            }

            public String getTz() {
                return tz;
            }

            public void setTz(String tz) {
                this.tz = tz;
            }
        }

        public static class UpdateBean {
            /**
             * loc : 2018-05-10 13:47
             * utc : 2018-05-10 05:47
             */

            private String loc;
            private String utc;

            public String getLoc() {
                return loc;
            }

            public void setLoc(String loc) {
                this.loc = loc;
            }

            public String getUtc() {
                return utc;
            }

            public void setUtc(String utc) {
                this.utc = utc;
            }
        }

        public static class NowBean {
            /**
             * cloud : 100
             * cond_code : 104
             * cond_txt : 阴
             * fl : 26
             * hum : 83
             * pcpn : 0.0
             * pres : 1014
             * tmp : 23
             * vis : 5
             * wind_deg : 185
             * wind_dir : 南风
             * wind_sc : 1
             * wind_spd : 2
             */

            private String cloud;
            private String cond_code;
            private String cond_txt;
            private String fl;
            private String hum;
            private String pcpn;
            private String pres;
            private String tmp;
            private String vis;
            private String wind_deg;
            private String wind_dir;
            private String wind_sc;
            private String wind_spd;

            public String getCloud() {
                return cloud;
            }

            public void setCloud(String cloud) {
                this.cloud = cloud;
            }

            public String getCond_code() {
                return cond_code;
            }

            public void setCond_code(String cond_code) {
                this.cond_code = cond_code;
            }

            public String getCond_txt() {
                return cond_txt;
            }

            public void setCond_txt(String cond_txt) {
                this.cond_txt = cond_txt;
            }

            public String getFl() {
                return fl;
            }

            public void setFl(String fl) {
                this.fl = fl;
            }

            public String getHum() {
                return hum;
            }

            public void setHum(String hum) {
                this.hum = hum;
            }

            public String getPcpn() {
                return pcpn;
            }

            public void setPcpn(String pcpn) {
                this.pcpn = pcpn;
            }

            public String getPres() {
                return pres;
            }

            public void setPres(String pres) {
                this.pres = pres;
            }

            public String getTmp() {
                return tmp;
            }

            public void setTmp(String tmp) {
                this.tmp = tmp;
            }

            public String getVis() {
                return vis;
            }

            public void setVis(String vis) {
                this.vis = vis;
            }

            public String getWind_deg() {
                return wind_deg;
            }

            public void setWind_deg(String wind_deg) {
                this.wind_deg = wind_deg;
            }

            public String getWind_dir() {
                return wind_dir;
            }

            public void setWind_dir(String wind_dir) {
                this.wind_dir = wind_dir;
            }

            public String getWind_sc() {
                return wind_sc;
            }

            public void setWind_sc(String wind_sc) {
                this.wind_sc = wind_sc;
            }

            public String getWind_spd() {
                return wind_spd;
            }

            public void setWind_spd(String wind_spd) {
                this.wind_spd = wind_spd;
            }
        }

        public static class DailyForecastBean {
            /**
             * cond_code_d : 305
             * cond_code_n : 305
             * cond_txt_d : 小雨
             * cond_txt_n : 小雨
             * date : 2018-05-10
             * hum : 83
             * mr : 02:32
             * ms : 14:14
             * pcpn : 0.1
             * pop : 41
             * pres : 1014
             * sr : 05:57
             * ss : 19:14
             * tmp_max : 23
             * tmp_min : 18
             * uv_index : 10
             * vis : 18
             * wind_deg : 350
             * wind_dir : 北风
             * wind_sc : 1-2
             * wind_spd : 2
             */

            private String cond_code_d;
            private String cond_code_n;
            private String cond_txt_d;
            private String cond_txt_n;
            private String date;
            private String hum;
            private String mr;
            private String ms;
            private String pcpn;
            private String pop;
            private String pres;
            private String sr;
            private String ss;
            private String tmp_max;
            private String tmp_min;
            private String uv_index;
            private String vis;
            private String wind_deg;
            private String wind_dir;
            private String wind_sc;
            private String wind_spd;

            public String getCond_code_d() {
                return cond_code_d;
            }

            public void setCond_code_d(String cond_code_d) {
                this.cond_code_d = cond_code_d;
            }

            public String getCond_code_n() {
                return cond_code_n;
            }

            public void setCond_code_n(String cond_code_n) {
                this.cond_code_n = cond_code_n;
            }

            public String getCond_txt_d() {
                return cond_txt_d;
            }

            public void setCond_txt_d(String cond_txt_d) {
                this.cond_txt_d = cond_txt_d;
            }

            public String getCond_txt_n() {
                return cond_txt_n;
            }

            public void setCond_txt_n(String cond_txt_n) {
                this.cond_txt_n = cond_txt_n;
            }

            public String getDate() {
                return date;
            }

            public void setDate(String date) {
                this.date = date;
            }

            public String getHum() {
                return hum;
            }

            public void setHum(String hum) {
                this.hum = hum;
            }

            public String getMr() {
                return mr;
            }

            public void setMr(String mr) {
                this.mr = mr;
            }

            public String getMs() {
                return ms;
            }

            public void setMs(String ms) {
                this.ms = ms;
            }

            public String getPcpn() {
                return pcpn;
            }

            public void setPcpn(String pcpn) {
                this.pcpn = pcpn;
            }

            public String getPop() {
                return pop;
            }

            public void setPop(String pop) {
                this.pop = pop;
            }

            public String getPres() {
                return pres;
            }

            public void setPres(String pres) {
                this.pres = pres;
            }

            public String getSr() {
                return sr;
            }

            public void setSr(String sr) {
                this.sr = sr;
            }

            public String getSs() {
                return ss;
            }

            public void setSs(String ss) {
                this.ss = ss;
            }

            public String getTmp_max() {
                return tmp_max;
            }

            public void setTmp_max(String tmp_max) {
                this.tmp_max = tmp_max;
            }

            public String getTmp_min() {
                return tmp_min;
            }

            public void setTmp_min(String tmp_min) {
                this.tmp_min = tmp_min;
            }

            public String getUv_index() {
                return uv_index;
            }

            public void setUv_index(String uv_index) {
                this.uv_index = uv_index;
            }

            public String getVis() {
                return vis;
            }

            public void setVis(String vis) {
                this.vis = vis;
            }

            public String getWind_deg() {
                return wind_deg;
            }

            public void setWind_deg(String wind_deg) {
                this.wind_deg = wind_deg;
            }

            public String getWind_dir() {
                return wind_dir;
            }

            public void setWind_dir(String wind_dir) {
                this.wind_dir = wind_dir;
            }

            public String getWind_sc() {
                return wind_sc;
            }

            public void setWind_sc(String wind_sc) {
                this.wind_sc = wind_sc;
            }

            public String getWind_spd() {
                return wind_spd;
            }

            public void setWind_spd(String wind_spd) {
                this.wind_spd = wind_spd;
            }
        }

        public static class HourlyBean {
            /**
             * cloud : 97
             * cond_code : 305
             * cond_txt : 小雨
             * dew : 19
             * hum : 71
             * pop : 20
             * pres : 1010
             * time : 2018-05-10 16:00
             * tmp : 23
             * wind_deg : 17
             * wind_dir : 东北风
             * wind_sc : 1-2
             * wind_spd : 10
             */

            private String cloud;
            private String cond_code;
            private String cond_txt;
            private String dew;
            private String hum;
            private String pop;
            private String pres;
            private String time;
            private String tmp;
            private String wind_deg;
            private String wind_dir;
            private String wind_sc;
            private String wind_spd;

            public String getCloud() {
                return cloud;
            }

            public void setCloud(String cloud) {
                this.cloud = cloud;
            }

            public String getCond_code() {
                return cond_code;
            }

            public void setCond_code(String cond_code) {
                this.cond_code = cond_code;
            }

            public String getCond_txt() {
                return cond_txt;
            }

            public void setCond_txt(String cond_txt) {
                this.cond_txt = cond_txt;
            }

            public String getDew() {
                return dew;
            }

            public void setDew(String dew) {
                this.dew = dew;
            }

            public String getHum() {
                return hum;
            }

            public void setHum(String hum) {
                this.hum = hum;
            }

            public String getPop() {
                return pop;
            }

            public void setPop(String pop) {
                this.pop = pop;
            }

            public String getPres() {
                return pres;
            }

            public void setPres(String pres) {
                this.pres = pres;
            }

            public String getTime() {
                return time;
            }

            public void setTime(String time) {
                this.time = time;
            }

            public String getTmp() {
                return tmp;
            }

            public void setTmp(String tmp) {
                this.tmp = tmp;
            }

            public String getWind_deg() {
                return wind_deg;
            }

            public void setWind_deg(String wind_deg) {
                this.wind_deg = wind_deg;
            }

            public String getWind_dir() {
                return wind_dir;
            }

            public void setWind_dir(String wind_dir) {
                this.wind_dir = wind_dir;
            }

            public String getWind_sc() {
                return wind_sc;
            }

            public void setWind_sc(String wind_sc) {
                this.wind_sc = wind_sc;
            }

            public String getWind_spd() {
                return wind_spd;
            }

            public void setWind_spd(String wind_spd) {
                this.wind_spd = wind_spd;
            }
        }

        public static class LifestyleBean {
            /**
             * type : comf
             * brf : 较舒适
             * txt : 白天有降雨，但会使人们感觉有些热，不过大部分人仍会有比较舒适的感觉。
             */

            private String type;
            private String brf;
            private String txt;

            public String getType() {
                return type;
            }

            public void setType(String type) {
                this.type = type;
            }

            public String getBrf() {
                return brf;
            }

            public void setBrf(String brf) {
                this.brf = brf;
            }

            public String getTxt() {
                return txt;
            }

            public void setTxt(String txt) {
                this.txt = txt;
            }
        }
    }
}
