package com.wen.utils;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * 解析网页
 */
public class htmlParUtils {
    public static void main(String[] args) throws IOException {
        //获取请求
        String url="http://search.jd.com/Search?keyword=鲜花";
        //解析网页
        Document document = Jsoup.parse(new URL(url), 30000);
        //js方法这里都能使用
        Element element = document.getElementById("J_goodsList");
        //获取所有的li元素
        Elements elementsByTag = element.getElementsByTag("li");
        for (Element el : elementsByTag) {
            String img = el.getElementsByTag("img").eq(0).attr("source-data-lazy-img");
            String price = el.getElementsByClass("p-price").eq(0).text();
            String title = el.getElementsByClass("p-name").eq(0).text();
            System.out.println("=================================");

                System.out.println(img);
                System.out.println(price);
                System.out.println(title);



        }
    }
}
