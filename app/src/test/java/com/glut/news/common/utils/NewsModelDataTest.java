package com.glut.news.common.utils;

import com.glut.news.model.NewsTest;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yy on 2018/1/25.
 */
public class NewsModelDataTest {
    @Test
    public List<NewsTest> getData() throws Exception {
        List<NewsTest> newsTests=new ArrayList<>();
        final  String url="http://news.qq.com/world_index.shtml";

                    Document document= Jsoup.connect(url)
                    .ignoreContentType(true).get();

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
                        //newsTests.add(new NewsTest(title,src,author,detail_url));



                    }
        return newsTests;


    }

    public void  test() {

    }
}