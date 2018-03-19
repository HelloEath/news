package com.glut.news.common.utils;

import com.glut.news.discover.model.entity.ZhiHuDetailModel;

import java.util.List;

/**
 * Created by yy on 2018/2/3.
 */

public class HttpUtil {
    public String getHtmlData(String content) {
        //TLog.e("webContent", "content:" + content);
        String htmlData = "<html>\n" +
                "<head>\n" +
                "<meta charset=\"UTF-8\">" +
                "<link rel=\"stylesheet\" href=\"file:///android_asset/common.css\"/>\n" +
                "<link rel=\"stylesheet\" href=\"file:///android_asset/mobile.css\"/>\n" +
                "<link rel=\"stylesheet\" href=\"file:///android_asset/bootstrap.min.css\"/>\n" +
                "<script src=\"file:///android_asset/base.js\"></script>" +
                "<meta http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\">\n" +
                "<meta name=\"viewport\" content=\"initial-scale=1, maximum-scale=1, user-scalable=no, width=device-width\">" +
                "<style type=\"text/css\">\n" +
                ".detail iframe{" +
                "width: 100%;" +
                "}" +
                "</style>\n" +
                "</head>\n" +
                "<body>\n" +
                "<div class=\"detail\">\n" +
                content +
                "</div>\n" +
                "</body>\n" +
                "</html>";
        return htmlData;
    }
        //css样式,隐藏header
        private static final String HIDE_HEADER_STYLE = "<style>div.headline{display:none;}</style>";

        //css style tag,需要格式化
        private static final String NEEDED_FORMAT_CSS_TAG = "<link rel=\"stylesheet\" type=\"text/css\" href=\"%s\"/>";

        // js script tag,需要格式化
        private static final String NEEDED_FORMAT_JS_TAG = "<script src=\"%s\"></script>";

        public static final String MIME_TYPE = "text/html; charset=utf-8";
        public static final String ENCODING = "utf-8";



        /**
         * 根据css链接生成Link标签
         *
         * @param url String
         * @return String
         */
        public static String createCssTag(String url) {
            return String.format(NEEDED_FORMAT_CSS_TAG, url);
        }

        /**
         * 根据多个css链接生成Link标签
         *
         * @param urls List<String>
         * @return String
         */
        public static String createCssTag(List<String> urls) {
            final StringBuilder sb = new StringBuilder();
            for (String url : urls) {
                sb.append(createCssTag(url));
            }
            return sb.toString();
        }

        /**
         * 根据js链接生成Script标签
         *
         * @param url String
         * @return String
         */
        public static String createJsTag(String url) {
            return String.format(NEEDED_FORMAT_JS_TAG, url);
        }

        /**
         * 根据多个js链接生成Script标签
         *
         * @param urls List<String>
         * @return String
         */
        public static String createJsTag(List<String> urls) {
            final StringBuilder sb = new StringBuilder();
            for (String url : urls) {
                sb.append(createJsTag(url));
            }
            return sb.toString();
        }

        /**
         * 根据样式标签,html字符串,js标签
         * 生成完整的HTML文档
         *
         * @param html string
         * @param css  string
         * @param js   string
         * @return string
         */
        private static String createHtmlData(String html, String css, String js) {
            return "<html>"+css.concat(HIDE_HEADER_STYLE).concat(html).concat(js);
        }

        /**
         * 根据News
         * 生成完整的HTML文档
         *
         * @param newsDetail NewsDetail
         * @return String
         */
        public static String createHtmlData(ZhiHuDetailModel newsDetail) {
            final String css = HttpUtil.createCssTag(newsDetail.getCss());
            final String js = HttpUtil.createJsTag(newsDetail.getJs());
            final String body = handleHtml(newsDetail.getBody()).toString();
            return createHtmlData(body, css, js);
        }

        public static StringBuffer handleHtml(String body) {
            StringBuffer stringBuffer = new StringBuffer();
            stringBuffer.append("<body>");
            stringBuffer.append(body);
            stringBuffer.append("</body></html>");
            return stringBuffer;
        }

    public static String create2(String body1){
        final String css = HttpUtil.createCssTag("http://news-at.zhihu.com/css/news_qa.auto.css?v=4b3e3");
        final  String body=handleHtml(body1).toString();
        return createHtmlData(body,css,"");
    }
    }


