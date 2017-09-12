package com.javarcn.service;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.javarcn.util.Constants;
import com.javarcn.util.HttpUtil;
import org.apache.commons.lang.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jiacheng on 2017/9/11 0011.
 */
public class FmService {
    private final static Logger log = LoggerFactory.getLogger(FmService.class);

    public static List<String> getCategoryList() {
        List<String> list = new ArrayList<String>();
        String resultHtml = HttpUtil.get(Constants.CATEGORY_URL);
        JsonObject jsonObject = new Gson().fromJson(resultHtml, JsonObject.class);
        resultHtml = jsonObject.get("html").toString();
        if (StringUtils.isNotBlank(resultHtml)) {
            resultHtml = resultHtml.replace("\\\"", "\"");
            resultHtml = resultHtml.replace("\\n", "");
            Document doc = Jsoup.parse(resultHtml);
            Elements elements = doc.getElementsByClass("sort_list").first().getElementsByTag("li");
            for (Element e : elements) {
                list.add(e.attr("cname"));
            }
        }
        list.remove("all");
        return list;
    }

    public static List<String> getFmListByCategory(String type) {
        String resultHtml = null;
        List<String> list = new ArrayList<String>();
        for (int page = 1; page <= Constants.PAGE_NUM; page++) {
            String url = null;
            if (page == 1) {
                url = String.format(Constants.GENERAL_URL, type);
            } else {
                url = String.format(Constants.GENERAL_URL, type + "/" + page);
            }
            resultHtml = HttpUtil.get(url);
            JsonObject jsonObject = new Gson().fromJson(resultHtml, JsonObject.class);
            resultHtml = jsonObject.get("html").toString();
            if (StringUtils.isNotBlank(resultHtml)) {
                List<String> stringList = praseHtml(resultHtml);
                if (stringList.size() > 0) {
                    list.addAll(stringList);
                }
            }
        }
        return list;
    }

    private static List<String> praseHtml(String html) {
        html = html.replace("\\\"", "\"");
        html = html.replace("\\n", "");
        List<String> list = new ArrayList<String>();
        Document doc = Jsoup.parse(html);
        Elements elements = doc.getElementsByClass("discoverAlbum_title");
        for (Element e : elements) {
            list.add(e.text().trim());
        }
        return list;
    }
}
