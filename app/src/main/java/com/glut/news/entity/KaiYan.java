package com.glut.news.entity;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by yy on 2018/2/7.
 */

public class KaiYan {

    @SerializedName("itemList")
    private List<KaiYanList> itemList;

    @SerializedName("count")
    private String count;

    @SerializedName("total")
    private String total;

    @SerializedName("nextPageUrl")
    private String nextPageUrl;

    @SerializedName("adExist")
    private String adExist;

    public List<KaiYanList> getItemList() {
        return itemList;
    }

    public void setItemList(List<KaiYanList> itemList) {
        this.itemList = itemList;
    }

    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public String getNextPageUrl() {
        return nextPageUrl;
    }

    public void setNextPageUrl(String nextPageUrl) {
        this.nextPageUrl = nextPageUrl;
    }

    public String getAdExist() {
        return adExist;
    }

    public void setAdExist(String adExist) {
        this.adExist = adExist;
    }

    public class KaiYanList {
        @SerializedName("type")
        private String type;    //"video"

        @SerializedName("data")
        private Data data;//	{…}

        @SerializedName("tag")
        private String tag;//	null

        @SerializedName("id")
        private String id;//	0

        @SerializedName("adIndex")
        private String adIndex;//	-1

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public Data getData() {
            return data;
        }

        public void setData(Data data) {
            this.data = data;
        }

        public String getTag() {
            return tag;
        }

        public void setTag(String tag) {
            this.tag = tag;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getAdIndex() {
            return adIndex;
        }

        public void setAdIndex(String adIndex) {
            this.adIndex = adIndex;
        }
    }

    public class Data {

        @SerializedName("dataType")
      private String  dataType;//	"VideoBeanForClient"

        @SerializedName("id")
      private String  id;//	79094

        @SerializedName("title")
      private String  title;//	"北京夫妻造1200㎡大宅，自己住！"

        @SerializedName("slogan")
      private String  slogan;//	null

        @SerializedName("description")
      private String  description;//	"建筑师王旎一家5口，在北京郊区造了一幢别墅自己住。为了让家人住的温暖舒适，夫妻俩颇费心思，用玻璃来引进阳光，用吸热的混凝土材料来保温。即使在室外零下20℃的寒冬，室内也能保持15℃的恒温。"

        @SerializedName("provider")
        private Prividers provider;//	{…}

        @SerializedName("category")
       private String category;//	"生活"

        @SerializedName("author")
       private Author author;//	{…}

        @SerializedName("cover")
       private Cover cover;//	{…}

        @SerializedName("playUrl")
       private String playUrl; //	"http://baobab.kaiyanapp.…e=default&source=aliyun"

        @SerializedName("thumbPlayUrl")
       private String thumbPlayUrl;//	null

        @SerializedName("duration")
       private String duration;//	241

        @SerializedName("webUrl")
       private WebUrl webUrl;//	{…}

        @SerializedName("releaseTime")
       private String releaseTime;//	1517193936000

        @SerializedName("library")
       private String library;//	"DEFAULT"

        @SerializedName("playInfo")
       private List<PlayInfo> playInfo;//	[…]


        @SerializedName("consumption")
       private Consumption consumption;//	{…}

        @SerializedName("campaign")
       private String campaign;//	null

        @SerializedName("waterMarks")
       private String waterMarks;//	null

        @SerializedName("adTrack")
       private String adTrack;//	null

        @SerializedName("tags")
        private List<Tags> tags	;//[…]

        @SerializedName("type")
       private String type;//	"NORMAL"

        @SerializedName("titlePgc")
       private String titlePgc;	//"北京夫妻造1200㎡大宅，自己住！"

        @SerializedName("descriptionPgc")
       private String descriptionPgc;//	"建筑师王旎一家5口，在北京郊区造了一幢别墅自己住…外零下20℃的寒冬，室内也能保持15℃的恒温。"

        @SerializedName("remark")
        private String remark	;//""

        @SerializedName("idx")
        private String  idx	;//0

        @SerializedName("shareAdTrack")
        private String shareAdTrack;//	null

        @SerializedName("favoriteAdTrack")
        private String favoriteAdTrack;//	null

        @SerializedName("webAdTrack")
        private String webAdTrack;//	null

        @SerializedName("date")
        private String date;//	1517193936000;//

        @SerializedName("promotion")
        private String promotion;//	null

        @SerializedName("label")
        private String label;//	null

        @SerializedName("descriptionEditor")
        private String  descriptionEditor;//	""

        @SerializedName("collectedl")
        private String  collectedl;//	false

        @SerializedName("played")
        private String  played;//	false

        public String getDataType() {
            return dataType;
        }

        public void setDataType(String dataType) {
            this.dataType = dataType;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getSlogan() {
            return slogan;
        }

        public void setSlogan(String slogan) {
            this.slogan = slogan;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public Prividers getProvider() {
            return provider;
        }

        public void setProvider(Prividers provider) {
            this.provider = provider;
        }

        public String getCategory() {
            return category;
        }

        public void setCategory(String category) {
            this.category = category;
        }

        public Author getAuthor() {
            return author;
        }

        public void setAuthor(Author author) {
            this.author = author;
        }

        public Cover getCover() {
            return cover;
        }

        public void setCover(Cover cover) {
            this.cover = cover;
        }

        public String getPlayUrl() {
            return playUrl;
        }

        public void setPlayUrl(String playUrl) {
            this.playUrl = playUrl;
        }

        public String getThumbPlayUrl() {
            return thumbPlayUrl;
        }

        public void setThumbPlayUrl(String thumbPlayUrl) {
            this.thumbPlayUrl = thumbPlayUrl;
        }

        public String getDuration() {
            return duration;
        }

        public void setDuration(String duration) {
            this.duration = duration;
        }

        public WebUrl getWebUrl() {
            return webUrl;
        }

        public void setWebUrl(WebUrl webUrl) {
            this.webUrl = webUrl;
        }

        public String getReleaseTime() {
            return releaseTime;
        }

        public void setReleaseTime(String releaseTime) {
            this.releaseTime = releaseTime;
        }

        public String getLibrary() {
            return library;
        }

        public void setLibrary(String library) {
            this.library = library;
        }

        public List<PlayInfo> getPlayInfo() {
            return playInfo;
        }

        public void setPlayInfo(List<PlayInfo> playInfo) {
            this.playInfo = playInfo;
        }

        public Consumption getConsumption() {
            return consumption;
        }

        public void setConsumption(Consumption consumption) {
            this.consumption = consumption;
        }

        public String getCampaign() {
            return campaign;
        }

        public void setCampaign(String campaign) {
            this.campaign = campaign;
        }

        public String getWaterMarks() {
            return waterMarks;
        }

        public void setWaterMarks(String waterMarks) {
            this.waterMarks = waterMarks;
        }

        public String getAdTrack() {
            return adTrack;
        }

        public void setAdTrack(String adTrack) {
            this.adTrack = adTrack;
        }

        public List<Tags> getTags() {
            return tags;
        }

        public void setTags(List<Tags> tags) {
            this.tags = tags;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getTitlePgc() {
            return titlePgc;
        }

        public void setTitlePgc(String titlePgc) {
            this.titlePgc = titlePgc;
        }

        public String getDescriptionPgc() {
            return descriptionPgc;
        }

        public void setDescriptionPgc(String descriptionPgc) {
            this.descriptionPgc = descriptionPgc;
        }

        public String getRemark() {
            return remark;
        }

        public void setRemark(String remark) {
            this.remark = remark;
        }

        public String getIdx() {
            return idx;
        }

        public void setIdx(String idx) {
            this.idx = idx;
        }

        public String getShareAdTrack() {
            return shareAdTrack;
        }

        public void setShareAdTrack(String shareAdTrack) {
            this.shareAdTrack = shareAdTrack;
        }

        public String getFavoriteAdTrack() {
            return favoriteAdTrack;
        }

        public void setFavoriteAdTrack(String favoriteAdTrack) {
            this.favoriteAdTrack = favoriteAdTrack;
        }

        public String getWebAdTrack() {
            return webAdTrack;
        }

        public void setWebAdTrack(String webAdTrack) {
            this.webAdTrack = webAdTrack;
        }

        public String getDate() {
            return date;
        }

        public void setDate(String date) {
            this.date = date;
        }

        public String getPromotion() {
            return promotion;
        }

        public void setPromotion(String promotion) {
            this.promotion = promotion;
        }

        public String getLabel() {
            return label;
        }

        public void setLabel(String label) {
            this.label = label;
        }

        public String getDescriptionEditor() {
            return descriptionEditor;
        }

        public void setDescriptionEditor(String descriptionEditor) {
            this.descriptionEditor = descriptionEditor;
        }

        public String getCollectedl() {
            return collectedl;
        }

        public void setCollectedl(String collectedl) {
            this.collectedl = collectedl;
        }

        public String getPlayed() {
            return played;
        }

        public void setPlayed(String played) {
            this.played = played;
        }
    }

    public class Prividers{
      private  String  name;//	"PGC"

        private  String  alias;//	"PGC"
        private  String  icon;//	""
        private  String category;//	"生活"

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getAlias() {
            return alias;
        }

        public void setAlias(String alias) {
            this.alias = alias;
        }

        public String getIcon() {
            return icon;
        }

        public void setIcon(String icon) {
            this.icon = icon;
        }

        public String getCategory() {
            return category;
        }

        public void setCategory(String category) {
            this.category = category;
        }
    }

    public class Author {

      private String  id;//	170
      private String  icon;//	"http://img.kaiyanapp.com/f00415e8c5b784e1ec42292e97d0bcbd.jpeg"
      private String  name;//	"一条"
       private String description;//	"所有未来美中度过的生活，都是被浪费了。"

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getIcon() {
            return icon;
        }

        public void setIcon(String icon) {
            this.icon = icon;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }
    }

    public class Cover {

      private String  feed	;//"http://img.kaiyanapp.com/b12ba9c2c504a8e9d51d695192d33210.png?imageMogr2/quality/60/format/jpg"
       private String detail;//	"http://img.kaiyanapp.com/b12ba9c2c504a8e9d51d695192d33210.png?imageMogr2/quality/60/format/jpg"
      private  String  blurred;

        public String getFeed() {
            return feed;
        }

        public void setFeed(String feed) {
            this.feed = feed;
        }

        public String getDetail() {
            return detail;
        }

        public void setDetail(String detail) {
            this.detail = detail;
        }

        public String getBlurred() {
            return blurred;
        }

        public void setBlurred(String blurred) {
            this.blurred = blurred;
        }
    }

    public class WebUrl {

      private String  raw;	//"http://www.eyepetizer.net/detail.html?vid=79094"
       private String forWeibo;

        public String getRaw() {
            return raw;
        }

        public void setRaw(String raw) {
            this.raw = raw;
        }

        public String getForWeibo() {
            return forWeibo;
        }

        public void setForWeibo(String forWeibo) {
            this.forWeibo = forWeibo;
        }
    }

    public class PlayInfo {

      private String  height;//	480
      private String  width;//	854
       private String name	;//"标清"
       private String type	;//"normal"
       private String url;

        public String getHeight() {
            return height;
        }

        public void setHeight(String height) {
            this.height = height;
        }

        public String getWidth() {
            return width;
        }

        public void setWidth(String width) {
            this.width = width;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }
    }

    public class Consumption {

      private String  collectionCount;//	852
      private String  shareCount;//	655
       private String replyCount;//	6

        public String getCollectionCount() {
            return collectionCount;
        }

        public void setCollectionCount(String collectionCount) {
            this.collectionCount = collectionCount;
        }

        public String getShareCount() {
            return shareCount;
        }

        public void setShareCount(String shareCount) {
            this.shareCount = shareCount;
        }

        public String getReplyCount() {
            return replyCount;
        }

        public void setReplyCount(String replyCount) {
            this.replyCount = replyCount;
        }
    }

    public class Tags {

       private String id;//	729
       private String name;//	"创意生活"

       private String bgPicture;//	"http://img.kaiyanapp.com/5260e075e03b6e828d12b26b0c2c4372.jpeg?imageMogr2/quality/60/format/jpg"
       private String headerImage;//	"http://img.kaiyanapp.com/5260e075e03b6e828d12b26b0c2c4372.jpeg?imageMogr2/quality/60/format/jpg"

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getBgPicture() {
            return bgPicture;
        }

        public void setBgPicture(String bgPicture) {
            this.bgPicture = bgPicture;
        }

        public String getHeaderImage() {
            return headerImage;
        }

        public void setHeaderImage(String headerImage) {
            this.headerImage = headerImage;
        }
    }
}