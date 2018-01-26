package com.glut.news.utils;

import com.glut.news.model.NewsTest;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by yy on 2018/1/25.
 */

public class NewsData {

    public static  List<NewsTest> getData(){
      final   List<NewsTest> newsTests=new ArrayList<>();
        new Thread(new Runnable() {
            @Override
            public void run() {

                final  String url="http://news.qq.com/world_index.shtml";

                Document document= null;
                try {
                    document = Jsoup.connect(url)
                            .ignoreContentType(true).get();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                // Elements element=document.getElementsByClass("Q-tpList");
                Elements elements=document.select(".Q-tpList");

                for (int i=0;i<elements.size();i++){
                    Element element1=elements.get(i);
                    String title=element1.getElementsByClass("linkto").text();
                    String src=element1.getElementsByClass("picto").attr("src");
                    String author=element1.getElementsByClass("from").text();
                    String detail_url=element1.getElementsByClass("pic").text();

                    System.out.print("title"+title);
                    System.out.print("src"+src);
                    System.out.print("author"+author);
                    newsTests.add(new NewsTest(title,src,author,detail_url));



                }

            }
        }).start();
        return newsTests;

    }

}
