package com.glut.news.weather.model;

import java.util.List;

/**
 * Created by yy on 2018/5/15.
 */

public class MeiZuWeather {

    /**
     * code : 200
     * message :
     * value : [{"city":"南昌","provinceName":"江西省","cityid":101240101,"realtime":{"temp":"35","ziwaixian":"N/A","time":"2018-05-15 16:48:00","weather":"晴","img":"0","sendibleTemp":"33","sd":"45","wd":"西南风","ws":"3级"},"weathers":[{"date":"2018-05-15","week":"星期二","temp_day_c":"35","temp_day_f":"95.0","temp_night_c":"26","temp_night_f":"78.8","weather":"晴","img":"0","wd":"","ws":"","sun_rise_time":"05:26","sun_down_time":"19:00"},{"date":"2018-05-16","week":"星期三","temp_day_c":"35","temp_day_f":"95.0","temp_night_c":"27","temp_night_f":"80.6","weather":"晴","img":"0","wd":"","ws":"","sun_rise_time":"05:26","sun_down_time":"19:00"},{"date":"2018-05-17","week":"星期四","temp_day_c":"35","temp_day_f":"95.0","temp_night_c":"27","temp_night_f":"80.6","weather":"多云","img":"1","wd":"","ws":"","sun_rise_time":"05:26","sun_down_time":"19:00"},{"date":"2018-05-18","week":"星期五","temp_day_c":"33","temp_day_f":"91.4","temp_night_c":"25","temp_night_f":"77.0","weather":"雷阵雨","img":"4","wd":"","ws":"","sun_rise_time":"05:26","sun_down_time":"19:00"},{"date":"2018-05-19","week":"星期六","temp_day_c":"31","temp_day_f":"87.8","temp_night_c":"23","temp_night_f":"73.4","weather":"中雨","img":"8","wd":"","ws":"","sun_rise_time":"05:26","sun_down_time":"19:00"},{"date":"2018-05-20","week":"星期日","temp_day_c":"26","temp_day_f":"78.8","temp_night_c":"23","temp_night_f":"73.4","weather":"中雨","img":"8","wd":"","ws":"","sun_rise_time":"05:26","sun_down_time":"19:00"},{"date":"2018-05-14","week":"星期一","temp_day_c":"33","temp_day_f":"91.4","temp_night_c":"24","temp_night_f":"75.2","weather":"晴","img":"0","wd":"","ws":"","sun_rise_time":"05:26","sun_down_time":"19:00"}],"pm25":{"so2":"2","o3":"38","timestamp":"","co":"8","level":"0","color":"0","no2":"6","aqi":"42","quality":"优","pm10":"42","pm25":"16","advice":"0","upDateTime":"2018-05-15 16:00:00","citycount":578,"cityrank":74},"weatherDetailsInfo":{"publishTime":"2018-05-15 17:00:00","weather3HoursDetailsInfos":[{"img":"1","weather":"多云","highestTemperature":"35","lowerestTemperature":"35","wd":"","ws":"","isRainFall":"","precipitation":"0","startTime":"2018-05-15 18:00:00","endTime":"2018-05-15 21:00:00"},{"img":"1","weather":"多云","highestTemperature":"32","lowerestTemperature":"32","wd":"","ws":"","isRainFall":"","precipitation":"0","startTime":"2018-05-15 21:00:00","endTime":"2018-05-16 00:00:00"},{"img":"1","weather":"多云","highestTemperature":"31","lowerestTemperature":"31","wd":"","ws":"","isRainFall":"","precipitation":"0","startTime":"2018-05-16 00:00:00","endTime":"2018-05-16 03:00:00"},{"img":"1","weather":"多云","highestTemperature":"30","lowerestTemperature":"30","wd":"","ws":"","isRainFall":"","precipitation":"0","startTime":"2018-05-16 03:00:00","endTime":"2018-05-16 06:00:00"},{"img":"1","weather":"多云","highestTemperature":"30","lowerestTemperature":"30","wd":"","ws":"","isRainFall":"","precipitation":"0","startTime":"2018-05-16 06:00:00","endTime":"2018-05-16 09:00:00"},{"img":"0","weather":"晴","highestTemperature":"32","lowerestTemperature":"32","wd":"","ws":"","isRainFall":"","precipitation":"0","startTime":"2018-05-16 09:00:00","endTime":"2018-05-16 12:00:00"},{"img":"1","weather":"多云","highestTemperature":"35","lowerestTemperature":"35","wd":"","ws":"","isRainFall":"","precipitation":"0","startTime":"2018-05-16 12:00:00","endTime":"2018-05-16 15:00:00"}]},"indexes":[{"abbreviation":"gm","name":"感冒指数","level":"较易发","content":"感冒较易发生，干净整洁的环境和清新流通的空气都有利于降低感冒的几率，体质较弱的童鞋们要特别加强自我保护。","alias":""},{"abbreviation":"xc","name":"洗车指数","level":"较适宜","content":"洗车后，可保持2天车辆清洁，比较适宜洗车。","alias":""},{"abbreviation":"ct","name":"穿衣指数","level":"闷热","content":"潮湿闷热，衣物排汗透气，手帕擦汗环保时尚，不建议在露天场所逛街。","alias":""},{"abbreviation":"uv","name":"紫外线强度指数","level":"强","content":"涂擦SPF大于15、PA+防晒护肤品。","alias":""},{"abbreviation":"pp","name":"化妆指数","level":"去油防晒","content":"请选用水质无油粉底霜。","alias":""},{"abbreviation":"yd","name":"运动指数","level":"不适宜","content":"气温过高，特别容易中暑，较不适宜户外运动，建议室内运动。","alias":""}],"alarms":[{"alarmId":"387eeb3832c702413af76c322f83514b","alarmType":"07","alarmTypeDesc":"高温黄色","alarmLevelNo":"02","alarmLevelNoDesc":"黄色","alarmContent":"江西省气象台2018年05月15日12时28分发布高温黄色预警信号：预计未来三天赣北南部、赣中和赣南北部大部分地区最高气温将达35～36℃，请做好防暑降温工作。","publishTime":"2018-05-15 12:28:00","alarmDesc":"江西省气象台发布高温黄色预警","precaution":"1.避免在高温时段进行户外活动；\n\n2.对老.弱.病.幼人群要准备防暑降温措施。"}]}]
     * redirect :
     */

    private String code;
    private String message;
    private String redirect;
    private List<ValueBean> value;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getRedirect() {
        return redirect;
    }

    public void setRedirect(String redirect) {
        this.redirect = redirect;
    }

    public List<ValueBean> getValue() {
        return value;
    }

    public void setValue(List<ValueBean> value) {
        this.value = value;
    }

    public static class ValueBean {
        /**
         * city : 南昌
         * provinceName : 江西省
         * cityid : 101240101
         * realtime : {"temp":"35","ziwaixian":"N/A","time":"2018-05-15 16:48:00","weather":"晴","img":"0","sendibleTemp":"33","sd":"45","wd":"西南风","ws":"3级"}
         * weathers : [{"date":"2018-05-15","week":"星期二","temp_day_c":"35","temp_day_f":"95.0","temp_night_c":"26","temp_night_f":"78.8","weather":"晴","img":"0","wd":"","ws":"","sun_rise_time":"05:26","sun_down_time":"19:00"},{"date":"2018-05-16","week":"星期三","temp_day_c":"35","temp_day_f":"95.0","temp_night_c":"27","temp_night_f":"80.6","weather":"晴","img":"0","wd":"","ws":"","sun_rise_time":"05:26","sun_down_time":"19:00"},{"date":"2018-05-17","week":"星期四","temp_day_c":"35","temp_day_f":"95.0","temp_night_c":"27","temp_night_f":"80.6","weather":"多云","img":"1","wd":"","ws":"","sun_rise_time":"05:26","sun_down_time":"19:00"},{"date":"2018-05-18","week":"星期五","temp_day_c":"33","temp_day_f":"91.4","temp_night_c":"25","temp_night_f":"77.0","weather":"雷阵雨","img":"4","wd":"","ws":"","sun_rise_time":"05:26","sun_down_time":"19:00"},{"date":"2018-05-19","week":"星期六","temp_day_c":"31","temp_day_f":"87.8","temp_night_c":"23","temp_night_f":"73.4","weather":"中雨","img":"8","wd":"","ws":"","sun_rise_time":"05:26","sun_down_time":"19:00"},{"date":"2018-05-20","week":"星期日","temp_day_c":"26","temp_day_f":"78.8","temp_night_c":"23","temp_night_f":"73.4","weather":"中雨","img":"8","wd":"","ws":"","sun_rise_time":"05:26","sun_down_time":"19:00"},{"date":"2018-05-14","week":"星期一","temp_day_c":"33","temp_day_f":"91.4","temp_night_c":"24","temp_night_f":"75.2","weather":"晴","img":"0","wd":"","ws":"","sun_rise_time":"05:26","sun_down_time":"19:00"}]
         * pm25 : {"so2":"2","o3":"38","timestamp":"","co":"8","level":"0","color":"0","no2":"6","aqi":"42","quality":"优","pm10":"42","pm25":"16","advice":"0","upDateTime":"2018-05-15 16:00:00","citycount":578,"cityrank":74}
         * weatherDetailsInfo : {"publishTime":"2018-05-15 17:00:00","weather3HoursDetailsInfos":[{"img":"1","weather":"多云","highestTemperature":"35","lowerestTemperature":"35","wd":"","ws":"","isRainFall":"","precipitation":"0","startTime":"2018-05-15 18:00:00","endTime":"2018-05-15 21:00:00"},{"img":"1","weather":"多云","highestTemperature":"32","lowerestTemperature":"32","wd":"","ws":"","isRainFall":"","precipitation":"0","startTime":"2018-05-15 21:00:00","endTime":"2018-05-16 00:00:00"},{"img":"1","weather":"多云","highestTemperature":"31","lowerestTemperature":"31","wd":"","ws":"","isRainFall":"","precipitation":"0","startTime":"2018-05-16 00:00:00","endTime":"2018-05-16 03:00:00"},{"img":"1","weather":"多云","highestTemperature":"30","lowerestTemperature":"30","wd":"","ws":"","isRainFall":"","precipitation":"0","startTime":"2018-05-16 03:00:00","endTime":"2018-05-16 06:00:00"},{"img":"1","weather":"多云","highestTemperature":"30","lowerestTemperature":"30","wd":"","ws":"","isRainFall":"","precipitation":"0","startTime":"2018-05-16 06:00:00","endTime":"2018-05-16 09:00:00"},{"img":"0","weather":"晴","highestTemperature":"32","lowerestTemperature":"32","wd":"","ws":"","isRainFall":"","precipitation":"0","startTime":"2018-05-16 09:00:00","endTime":"2018-05-16 12:00:00"},{"img":"1","weather":"多云","highestTemperature":"35","lowerestTemperature":"35","wd":"","ws":"","isRainFall":"","precipitation":"0","startTime":"2018-05-16 12:00:00","endTime":"2018-05-16 15:00:00"}]}
         * indexes : [{"abbreviation":"gm","name":"感冒指数","level":"较易发","content":"感冒较易发生，干净整洁的环境和清新流通的空气都有利于降低感冒的几率，体质较弱的童鞋们要特别加强自我保护。","alias":""},{"abbreviation":"xc","name":"洗车指数","level":"较适宜","content":"洗车后，可保持2天车辆清洁，比较适宜洗车。","alias":""},{"abbreviation":"ct","name":"穿衣指数","level":"闷热","content":"潮湿闷热，衣物排汗透气，手帕擦汗环保时尚，不建议在露天场所逛街。","alias":""},{"abbreviation":"uv","name":"紫外线强度指数","level":"强","content":"涂擦SPF大于15、PA+防晒护肤品。","alias":""},{"abbreviation":"pp","name":"化妆指数","level":"去油防晒","content":"请选用水质无油粉底霜。","alias":""},{"abbreviation":"yd","name":"运动指数","level":"不适宜","content":"气温过高，特别容易中暑，较不适宜户外运动，建议室内运动。","alias":""}]
         * alarms : [{"alarmId":"387eeb3832c702413af76c322f83514b","alarmType":"07","alarmTypeDesc":"高温黄色","alarmLevelNo":"02","alarmLevelNoDesc":"黄色","alarmContent":"江西省气象台2018年05月15日12时28分发布高温黄色预警信号：预计未来三天赣北南部、赣中和赣南北部大部分地区最高气温将达35～36℃，请做好防暑降温工作。","publishTime":"2018-05-15 12:28:00","alarmDesc":"江西省气象台发布高温黄色预警","precaution":"1.避免在高温时段进行户外活动；\n\n2.对老.弱.病.幼人群要准备防暑降温措施。"}]
         */

        private String city;
        private String provinceName;
        private int cityid;
        private RealtimeBean realtime;
        private Pm25Bean pm25;
        private WeatherDetailsInfoBean weatherDetailsInfo;
        private List<WeathersBean> weathers;
        private List<IndexesBean> indexes;
        private List<AlarmsBean> alarms;

        public String getCity() {
            return city;
        }

        public void setCity(String city) {
            this.city = city;
        }

        public String getProvinceName() {
            return provinceName;
        }

        public void setProvinceName(String provinceName) {
            this.provinceName = provinceName;
        }

        public int getCityid() {
            return cityid;
        }

        public void setCityid(int cityid) {
            this.cityid = cityid;
        }

        public RealtimeBean getRealtime() {
            return realtime;
        }

        public void setRealtime(RealtimeBean realtime) {
            this.realtime = realtime;
        }

        public Pm25Bean getPm25() {
            return pm25;
        }

        public void setPm25(Pm25Bean pm25) {
            this.pm25 = pm25;
        }

        public WeatherDetailsInfoBean getWeatherDetailsInfo() {
            return weatherDetailsInfo;
        }

        public void setWeatherDetailsInfo(WeatherDetailsInfoBean weatherDetailsInfo) {
            this.weatherDetailsInfo = weatherDetailsInfo;
        }

        public List<WeathersBean> getWeathers() {
            return weathers;
        }

        public void setWeathers(List<WeathersBean> weathers) {
            this.weathers = weathers;
        }

        public List<IndexesBean> getIndexes() {
            return indexes;
        }

        public void setIndexes(List<IndexesBean> indexes) {
            this.indexes = indexes;
        }

        public List<AlarmsBean> getAlarms() {
            return alarms;
        }

        public void setAlarms(List<AlarmsBean> alarms) {
            this.alarms = alarms;
        }

        public static class RealtimeBean {
            /**
             * temp : 35
             * ziwaixian : N/A
             * time : 2018-05-15 16:48:00
             * weather : 晴
             * img : 0
             * sendibleTemp : 33
             * sd : 45
             * wd : 西南风
             * ws : 3级
             */

            private String temp;
            private String ziwaixian;
            private String time;
            private String weather;
            private String img;
            private String sendibleTemp;
            private String sd;
            private String wd;
            private String ws;

            public String getTemp() {
                return temp;
            }

            public void setTemp(String temp) {
                this.temp = temp;
            }

            public String getZiwaixian() {
                return ziwaixian;
            }

            public void setZiwaixian(String ziwaixian) {
                this.ziwaixian = ziwaixian;
            }

            public String getTime() {
                return time;
            }

            public void setTime(String time) {
                this.time = time;
            }

            public String getWeather() {
                return weather;
            }

            public void setWeather(String weather) {
                this.weather = weather;
            }

            public String getImg() {
                return img;
            }

            public void setImg(String img) {
                this.img = img;
            }

            public String getSendibleTemp() {
                return sendibleTemp;
            }

            public void setSendibleTemp(String sendibleTemp) {
                this.sendibleTemp = sendibleTemp;
            }

            public String getSd() {
                return sd;
            }

            public void setSd(String sd) {
                this.sd = sd;
            }

            public String getWd() {
                return wd;
            }

            public void setWd(String wd) {
                this.wd = wd;
            }

            public String getWs() {
                return ws;
            }

            public void setWs(String ws) {
                this.ws = ws;
            }
        }

        public static class Pm25Bean {
            /**
             * so2 : 2
             * o3 : 38
             * timestamp :
             * co : 8
             * level : 0
             * color : 0
             * no2 : 6
             * aqi : 42
             * quality : 优
             * pm10 : 42
             * pm25 : 16
             * advice : 0
             * upDateTime : 2018-05-15 16:00:00
             * citycount : 578
             * cityrank : 74
             */

            private String so2;
            private String o3;
            private String timestamp;
            private String co;
            private String level;
            private String color;
            private String no2;
            private String aqi;
            private String quality;
            private String pm10;
            private String pm25;
            private String advice;
            private String upDateTime;
            private int citycount;
            private int cityrank;

            public String getSo2() {
                return so2;
            }

            public void setSo2(String so2) {
                this.so2 = so2;
            }

            public String getO3() {
                return o3;
            }

            public void setO3(String o3) {
                this.o3 = o3;
            }

            public String getTimestamp() {
                return timestamp;
            }

            public void setTimestamp(String timestamp) {
                this.timestamp = timestamp;
            }

            public String getCo() {
                return co;
            }

            public void setCo(String co) {
                this.co = co;
            }

            public String getLevel() {
                return level;
            }

            public void setLevel(String level) {
                this.level = level;
            }

            public String getColor() {
                return color;
            }

            public void setColor(String color) {
                this.color = color;
            }

            public String getNo2() {
                return no2;
            }

            public void setNo2(String no2) {
                this.no2 = no2;
            }

            public String getAqi() {
                return aqi;
            }

            public void setAqi(String aqi) {
                this.aqi = aqi;
            }

            public String getQuality() {
                return quality;
            }

            public void setQuality(String quality) {
                this.quality = quality;
            }

            public String getPm10() {
                return pm10;
            }

            public void setPm10(String pm10) {
                this.pm10 = pm10;
            }

            public String getPm25() {
                return pm25;
            }

            public void setPm25(String pm25) {
                this.pm25 = pm25;
            }

            public String getAdvice() {
                return advice;
            }

            public void setAdvice(String advice) {
                this.advice = advice;
            }

            public String getUpDateTime() {
                return upDateTime;
            }

            public void setUpDateTime(String upDateTime) {
                this.upDateTime = upDateTime;
            }

            public int getCitycount() {
                return citycount;
            }

            public void setCitycount(int citycount) {
                this.citycount = citycount;
            }

            public int getCityrank() {
                return cityrank;
            }

            public void setCityrank(int cityrank) {
                this.cityrank = cityrank;
            }
        }

        public static class WeatherDetailsInfoBean {
            /**
             * publishTime : 2018-05-15 17:00:00
             * weather3HoursDetailsInfos : [{"img":"1","weather":"多云","highestTemperature":"35","lowerestTemperature":"35","wd":"","ws":"","isRainFall":"","precipitation":"0","startTime":"2018-05-15 18:00:00","endTime":"2018-05-15 21:00:00"},{"img":"1","weather":"多云","highestTemperature":"32","lowerestTemperature":"32","wd":"","ws":"","isRainFall":"","precipitation":"0","startTime":"2018-05-15 21:00:00","endTime":"2018-05-16 00:00:00"},{"img":"1","weather":"多云","highestTemperature":"31","lowerestTemperature":"31","wd":"","ws":"","isRainFall":"","precipitation":"0","startTime":"2018-05-16 00:00:00","endTime":"2018-05-16 03:00:00"},{"img":"1","weather":"多云","highestTemperature":"30","lowerestTemperature":"30","wd":"","ws":"","isRainFall":"","precipitation":"0","startTime":"2018-05-16 03:00:00","endTime":"2018-05-16 06:00:00"},{"img":"1","weather":"多云","highestTemperature":"30","lowerestTemperature":"30","wd":"","ws":"","isRainFall":"","precipitation":"0","startTime":"2018-05-16 06:00:00","endTime":"2018-05-16 09:00:00"},{"img":"0","weather":"晴","highestTemperature":"32","lowerestTemperature":"32","wd":"","ws":"","isRainFall":"","precipitation":"0","startTime":"2018-05-16 09:00:00","endTime":"2018-05-16 12:00:00"},{"img":"1","weather":"多云","highestTemperature":"35","lowerestTemperature":"35","wd":"","ws":"","isRainFall":"","precipitation":"0","startTime":"2018-05-16 12:00:00","endTime":"2018-05-16 15:00:00"}]
             */

            private String publishTime;
            private List<Weather3HoursDetailsInfosBean> weather3HoursDetailsInfos;

            public String getPublishTime() {
                return publishTime;
            }

            public void setPublishTime(String publishTime) {
                this.publishTime = publishTime;
            }

            public List<Weather3HoursDetailsInfosBean> getWeather3HoursDetailsInfos() {
                return weather3HoursDetailsInfos;
            }

            public void setWeather3HoursDetailsInfos(List<Weather3HoursDetailsInfosBean> weather3HoursDetailsInfos) {
                this.weather3HoursDetailsInfos = weather3HoursDetailsInfos;
            }

            public static class Weather3HoursDetailsInfosBean {
                /**
                 * img : 1
                 * weather : 多云
                 * highestTemperature : 35
                 * lowerestTemperature : 35
                 * wd :
                 * ws :
                 * isRainFall :
                 * precipitation : 0
                 * startTime : 2018-05-15 18:00:00
                 * endTime : 2018-05-15 21:00:00
                 */

                private String img;
                private String weather;
                private String highestTemperature;
                private String lowerestTemperature;
                private String wd;
                private String ws;
                private String isRainFall;
                private String precipitation;
                private String startTime;
                private String endTime;

                public String getImg() {
                    return img;
                }

                public void setImg(String img) {
                    this.img = img;
                }

                public String getWeather() {
                    return weather;
                }

                public void setWeather(String weather) {
                    this.weather = weather;
                }

                public String getHighestTemperature() {
                    return highestTemperature;
                }

                public void setHighestTemperature(String highestTemperature) {
                    this.highestTemperature = highestTemperature;
                }

                public String getLowerestTemperature() {
                    return lowerestTemperature;
                }

                public void setLowerestTemperature(String lowerestTemperature) {
                    this.lowerestTemperature = lowerestTemperature;
                }

                public String getWd() {
                    return wd;
                }

                public void setWd(String wd) {
                    this.wd = wd;
                }

                public String getWs() {
                    return ws;
                }

                public void setWs(String ws) {
                    this.ws = ws;
                }

                public String getIsRainFall() {
                    return isRainFall;
                }

                public void setIsRainFall(String isRainFall) {
                    this.isRainFall = isRainFall;
                }

                public String getPrecipitation() {
                    return precipitation;
                }

                public void setPrecipitation(String precipitation) {
                    this.precipitation = precipitation;
                }

                public String getStartTime() {
                    return startTime;
                }

                public void setStartTime(String startTime) {
                    this.startTime = startTime;
                }

                public String getEndTime() {
                    return endTime;
                }

                public void setEndTime(String endTime) {
                    this.endTime = endTime;
                }
            }
        }

        public static class WeathersBean {
            /**
             * date : 2018-05-15
             * week : 星期二
             * temp_day_c : 35
             * temp_day_f : 95.0
             * temp_night_c : 26
             * temp_night_f : 78.8
             * weather : 晴
             * img : 0
             * wd :
             * ws :
             * sun_rise_time : 05:26
             * sun_down_time : 19:00
             */

            private String date;
            private String week;
            private String temp_day_c;
            private String temp_day_f;
            private String temp_night_c;
            private String temp_night_f;
            private String weather;
            private String img;
            private String wd;
            private String ws;
            private String sun_rise_time;
            private String sun_down_time;

            public String getDate() {
                return date;
            }

            public void setDate(String date) {
                this.date = date;
            }

            public String getWeek() {
                return week;
            }

            public void setWeek(String week) {
                this.week = week;
            }

            public String getTemp_day_c() {
                return temp_day_c;
            }

            public void setTemp_day_c(String temp_day_c) {
                this.temp_day_c = temp_day_c;
            }

            public String getTemp_day_f() {
                return temp_day_f;
            }

            public void setTemp_day_f(String temp_day_f) {
                this.temp_day_f = temp_day_f;
            }

            public String getTemp_night_c() {
                return temp_night_c;
            }

            public void setTemp_night_c(String temp_night_c) {
                this.temp_night_c = temp_night_c;
            }

            public String getTemp_night_f() {
                return temp_night_f;
            }

            public void setTemp_night_f(String temp_night_f) {
                this.temp_night_f = temp_night_f;
            }

            public String getWeather() {
                return weather;
            }

            public void setWeather(String weather) {
                this.weather = weather;
            }

            public String getImg() {
                return img;
            }

            public void setImg(String img) {
                this.img = img;
            }

            public String getWd() {
                return wd;
            }

            public void setWd(String wd) {
                this.wd = wd;
            }

            public String getWs() {
                return ws;
            }

            public void setWs(String ws) {
                this.ws = ws;
            }

            public String getSun_rise_time() {
                return sun_rise_time;
            }

            public void setSun_rise_time(String sun_rise_time) {
                this.sun_rise_time = sun_rise_time;
            }

            public String getSun_down_time() {
                return sun_down_time;
            }

            public void setSun_down_time(String sun_down_time) {
                this.sun_down_time = sun_down_time;
            }
        }

        public static class IndexesBean {
            /**
             * abbreviation : gm
             * name : 感冒指数
             * level : 较易发
             * content : 感冒较易发生，干净整洁的环境和清新流通的空气都有利于降低感冒的几率，体质较弱的童鞋们要特别加强自我保护。
             * alias :
             */

            private String abbreviation;
            private String name;
            private String level;
            private String content;
            private String alias;

            public String getAbbreviation() {
                return abbreviation;
            }

            public void setAbbreviation(String abbreviation) {
                this.abbreviation = abbreviation;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getLevel() {
                return level;
            }

            public void setLevel(String level) {
                this.level = level;
            }

            public String getContent() {
                return content;
            }

            public void setContent(String content) {
                this.content = content;
            }

            public String getAlias() {
                return alias;
            }

            public void setAlias(String alias) {
                this.alias = alias;
            }
        }

        public static class AlarmsBean {
            /**
             * alarmId : 387eeb3832c702413af76c322f83514b
             * alarmType : 07
             * alarmTypeDesc : 高温黄色
             * alarmLevelNo : 02
             * alarmLevelNoDesc : 黄色
             * alarmContent : 江西省气象台2018年05月15日12时28分发布高温黄色预警信号：预计未来三天赣北南部、赣中和赣南北部大部分地区最高气温将达35～36℃，请做好防暑降温工作。
             * publishTime : 2018-05-15 12:28:00
             * alarmDesc : 江西省气象台发布高温黄色预警
             * precaution : 1.避免在高温时段进行户外活动；

             2.对老.弱.病.幼人群要准备防暑降温措施。
             */

            private String alarmId;
            private String alarmType;
            private String alarmTypeDesc;
            private String alarmLevelNo;
            private String alarmLevelNoDesc;
            private String alarmContent;
            private String publishTime;
            private String alarmDesc;
            private String precaution;

            public String getAlarmId() {
                return alarmId;
            }

            public void setAlarmId(String alarmId) {
                this.alarmId = alarmId;
            }

            public String getAlarmType() {
                return alarmType;
            }

            public void setAlarmType(String alarmType) {
                this.alarmType = alarmType;
            }

            public String getAlarmTypeDesc() {
                return alarmTypeDesc;
            }

            public void setAlarmTypeDesc(String alarmTypeDesc) {
                this.alarmTypeDesc = alarmTypeDesc;
            }

            public String getAlarmLevelNo() {
                return alarmLevelNo;
            }

            public void setAlarmLevelNo(String alarmLevelNo) {
                this.alarmLevelNo = alarmLevelNo;
            }

            public String getAlarmLevelNoDesc() {
                return alarmLevelNoDesc;
            }

            public void setAlarmLevelNoDesc(String alarmLevelNoDesc) {
                this.alarmLevelNoDesc = alarmLevelNoDesc;
            }

            public String getAlarmContent() {
                return alarmContent;
            }

            public void setAlarmContent(String alarmContent) {
                this.alarmContent = alarmContent;
            }

            public String getPublishTime() {
                return publishTime;
            }

            public void setPublishTime(String publishTime) {
                this.publishTime = publishTime;
            }

            public String getAlarmDesc() {
                return alarmDesc;
            }

            public void setAlarmDesc(String alarmDesc) {
                this.alarmDesc = alarmDesc;
            }

            public String getPrecaution() {
                return precaution;
            }

            public void setPrecaution(String precaution) {
                this.precaution = precaution;
            }
        }
    }
}
