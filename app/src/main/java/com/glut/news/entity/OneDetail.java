package com.glut.news.entity;

import java.util.List;

/**
 * Created by yy on 2018/2/8.
 */

public class OneDetail {
    private String res;
    private Data data;

    public String getRes() {
        return res;
    }

    public void setRes(String res) {
        this.res = res;
    }

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }


    public class Author {

        private String user_id;//	"7654034"
        private String user_name;//	"黄集伟"
        private String  desc;//	"黄集伟，专栏作者，曾有“阅读笔记”系列、“语词笔记”系列、《孤岛访谈录》等闲书出版。"
        private String  summary	;//"黄集伟，专栏作者。"
        private String  fans_total;//	"5759"
        private String web_url;//	"http://image.wufazhuce.com/FvVmWbqlle7jlUCTeozoval9NyBH"

        public String getUser_id() {
            return user_id;
        }

        public void setUser_id(String user_id) {
            this.user_id = user_id;
        }

        public String getUser_name() {
            return user_name;
        }

        public void setUser_name(String user_name) {
            this.user_name = user_name;
        }

        public String getDesc() {
            return desc;
        }

        public void setDesc(String desc) {
            this.desc = desc;
        }

        public String getSummary() {
            return summary;
        }

        public void setSummary(String summary) {
            this.summary = summary;
        }

        public String getFans_total() {
            return fans_total;
        }

        public void setFans_total(String fans_total) {
            this.fans_total = fans_total;
        }

        public String getWeb_url() {
            return web_url;
        }

        public void setWeb_url(String web_url) {
            this.web_url = web_url;
        }
    }

    public class AuthorList {

       private String user_id;//	"5957194"
        private String user_name;//	"壹毛镜"
        private String desc;//	"二十世纪美少女"
        private String  wb_name	;//""
        private String is_settled	;//"0"
        private String settled_type	;//"0"
        private String summary	;//"NO,THANK YOU"
        private String fans_total;//	"408"
        private String web_url	;//"http://image.wufazhuc

        public String getUser_id() {
            return user_id;
        }

        public void setUser_id(String user_id) {
            this.user_id = user_id;
        }

        public String getUser_name() {
            return user_name;
        }

        public void setUser_name(String user_name) {
            this.user_name = user_name;
        }

        public String getDesc() {
            return desc;
        }

        public void setDesc(String desc) {
            this.desc = desc;
        }

        public String getWb_name() {
            return wb_name;
        }

        public void setWb_name(String wb_name) {
            this.wb_name = wb_name;
        }

        public String getIs_settled() {
            return is_settled;
        }

        public void setIs_settled(String is_settled) {
            this.is_settled = is_settled;
        }

        public String getSettled_type() {
            return settled_type;
        }

        public void setSettled_type(String settled_type) {
            this.settled_type = settled_type;
        }

        public String getSummary() {
            return summary;
        }

        public void setSummary(String summary) {
            this.summary = summary;
        }

        public String getFans_total() {
            return fans_total;
        }

        public void setFans_total(String fans_total) {
            this.fans_total = fans_total;
        }

        public String getWeb_url() {
            return web_url;
        }

        public void setWeb_url(String web_url) {
            this.web_url = web_url;
        }
    }

    public class Data {


        /*文章字段*/
        private String content_id;//	"1713"
        private String hp_title	;//	"一周语文丨怀着极恐的心情细思下去"
        private String sub_title;//		""
        private String hp_author;//		"黄集伟"
        private String  auth_it	;//	"黄集伟，专栏作者，曾有“阅读笔记”系列、“语词笔记”系列、《孤岛访谈录》等闲书出版。"
        private String hp_author_introduce;//		"责任编辑：向可"
        private String hp_content	;//	"\n<strong>2017-1-2～2017-…省长”为“雀长”……真难听。<br>\r\n "
        private String hp_makettime	;//	"2010-01-27 07:25:00"
        private String wb_name;//		""
        private String wb_img_url	;//	""
        private String guide_word	;//	"怀着极恐的心情细思下去"
        private String top_media_type	;//	"0"
        private String top_media_file;//		""
        private String top_media_image	;//	""
        private String  next_id	;//	"2214"
        private String  previous_id	;//	"2131"

        // share_list	{…}

        /* 共有字段*/
        private String  web_url	;//	"http://m.wufazhuce.com/article/1713"
        private String audio	;//	""
        private String anchor	;//	""
        private String editor_email	;//	"xiangke@wufazhuce.com"
        private String hide_flag	;//	"0"
        private String last_update_date	;//	"2017-01-16 11:17:43"
        private String start_video	;//	""
        private String  copyright	;//	""
        private String audio_duration	;//	"0"
        private String  cover	;//	"0"
        private List<Author> author;//	[…]
        private String  maketime	;//	"2010-01-27 07:25:00"
        private String  praisenum	;//	415
        private String  sharenum	;//	124
        private String commentnum	;//	106
        private  String   charge_edt;//	"责任编辑：山山"
        private  String  info	;//"所属专辑：来来回回\r\n演唱者：陈楚生&SPY…16年12月05日\r\n专辑类别：EP、单曲"


        /*  音乐字段*/
        private  String  id;//	"1182"
        private  String   title	;//"来来回回"
        private  String   isfirst	;//"0"
        private  String  story_title;//	"彗星回归这一年"
        private  String  story	;//"彗星回归这一年<br>\r\n <br>\r\n…n <br>\r\n这句话，同样适用于陈楚生。"
        private  String   lyric;//	"在横七竖八街道寻找未来的风景\r\n在加油刹车之…亮又继续\r\n反复却无止境\r\n我无法按停"
        private  String  platform	;//"1"
        private  String   music_id	;//"1795311903"
        private  String   related_to	;//"0"
        private  String  sort	;//"0"
        private  String   read_num;//	"702906"
        private  String  story_summary;//	"这也不是一件了不起的事，我只是在做我能做的而已。"
        private  String  related_musics	;//""
        private  String   album	;//"来来回回"
       // private  String   story_author;//	{…}
        //private  List<AuthorList>  author_list;//	[…]
        private  String  feeds_cover;//	"http://image.wufazhuce.com/FiK4bhku06qMI094OnqT0x4ae5TF?imageMogr2/auto-orient/blur/50x50/quality/75|imagelim"

        /* 影视字段*/
        private String movie_id	;//"225"
        private String content	;//"按照往常的惯例，美国最老牌的媒体《纽约时报》此前…swim》将在圣丹斯电影节首映，真是超级脐带。"
        private String input_date;//"2017-01-03 18:11:30"
        private String story_type	;//"1"
        private String summary;//	"骚气十足，不失内涵，不明觉厉，不服不行。"
        private String detailcover	;//"http://image.wufazhuce.com/Flxh36MDPRjjY3M74LW0YGiQWnbM"
        private String  video	;//"http://music.wufazhuce.com/lutrSFGb2tAHlehp3I30tq3o5Hxd"
        private String  keywords;//	"影后于佩尔;中老年危机;柏林银熊;张力十足;生活的哲学"
        private String officialstory	;//"Nathalie Chazeaux（Isabel…在Nathalie面前，她必须重塑自己的生活。"
        private String  directors	;//"米娅·汉森-洛夫"
        private String  poster	;//"http://image.wufazhuce.com/Fit81aKGJpJBjcB4TkuXvQZcwuvh"
        //photo	[…]

        public String getContent_id() {
            return content_id;
        }

        public void setContent_id(String content_id) {
            this.content_id = content_id;
        }

        public String getHp_title() {
            return hp_title;
        }

        public void setHp_title(String hp_title) {
            this.hp_title = hp_title;
        }

        public String getSub_title() {
            return sub_title;
        }

        public void setSub_title(String sub_title) {
            this.sub_title = sub_title;
        }

        public String getHp_author() {
            return hp_author;
        }

        public void setHp_author(String hp_author) {
            this.hp_author = hp_author;
        }

        public String getAuth_it() {
            return auth_it;
        }

        public void setAuth_it(String auth_it) {
            this.auth_it = auth_it;
        }

        public String getHp_author_introduce() {
            return hp_author_introduce;
        }

        public void setHp_author_introduce(String hp_author_introduce) {
            this.hp_author_introduce = hp_author_introduce;
        }

        public String getHp_content() {
            return hp_content;
        }

        public void setHp_content(String hp_content) {
            this.hp_content = hp_content;
        }

        public String getHp_makettime() {
            return hp_makettime;
        }

        public void setHp_makettime(String hp_makettime) {
            this.hp_makettime = hp_makettime;
        }

        public String getWb_name() {
            return wb_name;
        }

        public void setWb_name(String wb_name) {
            this.wb_name = wb_name;
        }

        public String getWb_img_url() {
            return wb_img_url;
        }

        public void setWb_img_url(String wb_img_url) {
            this.wb_img_url = wb_img_url;
        }

        public String getGuide_word() {
            return guide_word;
        }

        public void setGuide_word(String guide_word) {
            this.guide_word = guide_word;
        }

        public String getTop_media_type() {
            return top_media_type;
        }

        public void setTop_media_type(String top_media_type) {
            this.top_media_type = top_media_type;
        }

        public String getTop_media_file() {
            return top_media_file;
        }

        public void setTop_media_file(String top_media_file) {
            this.top_media_file = top_media_file;
        }

        public String getTop_media_image() {
            return top_media_image;
        }

        public void setTop_media_image(String top_media_image) {
            this.top_media_image = top_media_image;
        }

        public String getNext_id() {
            return next_id;
        }

        public void setNext_id(String next_id) {
            this.next_id = next_id;
        }

        public String getPrevious_id() {
            return previous_id;
        }

        public void setPrevious_id(String previous_id) {
            this.previous_id = previous_id;
        }

        public String getWeb_url() {
            return web_url;
        }

        public void setWeb_url(String web_url) {
            this.web_url = web_url;
        }

        public String getAudio() {
            return audio;
        }

        public void setAudio(String audio) {
            this.audio = audio;
        }

        public String getAnchor() {
            return anchor;
        }

        public void setAnchor(String anchor) {
            this.anchor = anchor;
        }

        public String getEditor_email() {
            return editor_email;
        }

        public void setEditor_email(String editor_email) {
            this.editor_email = editor_email;
        }

        public String getHide_flag() {
            return hide_flag;
        }

        public void setHide_flag(String hide_flag) {
            this.hide_flag = hide_flag;
        }

        public String getLast_update_date() {
            return last_update_date;
        }

        public void setLast_update_date(String last_update_date) {
            this.last_update_date = last_update_date;
        }

        public String getStart_video() {
            return start_video;
        }

        public void setStart_video(String start_video) {
            this.start_video = start_video;
        }

        public String getCopyright() {
            return copyright;
        }

        public void setCopyright(String copyright) {
            this.copyright = copyright;
        }

        public String getAudio_duration() {
            return audio_duration;
        }

        public void setAudio_duration(String audio_duration) {
            this.audio_duration = audio_duration;
        }

        public String getCover() {
            return cover;
        }

        public void setCover(String cover) {
            this.cover = cover;
        }

        public List<Author> getAuthor() {
            return author;
        }

        public void setAuthor(List<Author> author) {
            this.author = author;
        }

        public String getMaketime() {
            return maketime;
        }

        public void setMaketime(String maketime) {
            this.maketime = maketime;
        }

        public String getPraisenum() {
            return praisenum;
        }

        public void setPraisenum(String praisenum) {
            this.praisenum = praisenum;
        }

        public String getSharenum() {
            return sharenum;
        }

        public void setSharenum(String sharenum) {
            this.sharenum = sharenum;
        }

        public String getCommentnum() {
            return commentnum;
        }

        public void setCommentnum(String commentnum) {
            this.commentnum = commentnum;
        }

        public String getCharge_edt() {
            return charge_edt;
        }

        public void setCharge_edt(String charge_edt) {
            this.charge_edt = charge_edt;
        }

        public String getInfo() {
            return info;
        }

        public void setInfo(String info) {
            this.info = info;
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

        public String getIsfirst() {
            return isfirst;
        }

        public void setIsfirst(String isfirst) {
            this.isfirst = isfirst;
        }

        public String getStory_title() {
            return story_title;
        }

        public void setStory_title(String story_title) {
            this.story_title = story_title;
        }

        public String getStory() {
            return story;
        }

        public void setStory(String story) {
            this.story = story;
        }

        public String getLyric() {
            return lyric;
        }

        public void setLyric(String lyric) {
            this.lyric = lyric;
        }

        public String getPlatform() {
            return platform;
        }

        public void setPlatform(String platform) {
            this.platform = platform;
        }

        public String getMusic_id() {
            return music_id;
        }

        public void setMusic_id(String music_id) {
            this.music_id = music_id;
        }

        public String getRelated_to() {
            return related_to;
        }

        public void setRelated_to(String related_to) {
            this.related_to = related_to;
        }

        public String getSort() {
            return sort;
        }

        public void setSort(String sort) {
            this.sort = sort;
        }

        public String getRead_num() {
            return read_num;
        }

        public void setRead_num(String read_num) {
            this.read_num = read_num;
        }

        public String getStory_summary() {
            return story_summary;
        }

        public void setStory_summary(String story_summary) {
            this.story_summary = story_summary;
        }

        public String getRelated_musics() {
            return related_musics;
        }

        public void setRelated_musics(String related_musics) {
            this.related_musics = related_musics;
        }

        public String getAlbum() {
            return album;
        }

        public void setAlbum(String album) {
            this.album = album;
        }

       /* public String getStory_author() {
            return story_author;
        }

        public void setStory_author(String story_author) {
            this.story_author = story_author;
        }

        public List<AuthorList> getAuthor_list() {
            return author_list;
        }

        public void setAuthor_list(List<AuthorList> author_list) {
            this.author_list = author_list;
        }
*/
        public String getFeeds_cover() {
            return feeds_cover;
        }

        public void setFeeds_cover(String feeds_cover) {
            this.feeds_cover = feeds_cover;
        }

        public String getMovie_id() {
            return movie_id;
        }

        public void setMovie_id(String movie_id) {
            this.movie_id = movie_id;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public String getInput_date() {
            return input_date;
        }

        public void setInput_date(String input_date) {
            this.input_date = input_date;
        }

        public String getStory_type() {
            return story_type;
        }

        public void setStory_type(String story_type) {
            this.story_type = story_type;
        }

        public String getSummary() {
            return summary;
        }

        public void setSummary(String summary) {
            this.summary = summary;
        }

        public String getDetailcover() {
            return detailcover;
        }

        public void setDetailcover(String detailcover) {
            this.detailcover = detailcover;
        }

        public String getVideo() {
            return video;
        }

        public void setVideo(String video) {
            this.video = video;
        }

        public String getKeywords() {
            return keywords;
        }

        public void setKeywords(String keywords) {
            this.keywords = keywords;
        }

        public String getOfficialstory() {
            return officialstory;
        }

        public void setOfficialstory(String officialstory) {
            this.officialstory = officialstory;
        }

        public String getDirectors() {
            return directors;
        }

        public void setDirectors(String directors) {
            this.directors = directors;
        }

        public String getPoster() {
            return poster;
        }

        public void setPoster(String poster) {
            this.poster = poster;
        }
    }
}
