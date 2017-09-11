package com.javarcn.service;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.javarcn.util.Constants;
import com.javarcn.util.HttpUtil;
import org.apache.commons.lang.StringEscapeUtils;
import org.apache.commons.lang.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by jiacheng on 2017/9/11 0011.
 */
public class FmService {
    private final static Logger log = LoggerFactory.getLogger(FmService.class);

    public static void main(String[] args) {
        String strHTMLInput = "<P>MyName<P>";
        String strEscapeHTML = StringEscapeUtils.escapeHtml(strHTMLInput);
        String strUnEscapeHTML = StringEscapeUtils.unescapeHtml(strEscapeHTML);
        System.out.println("Escaped HTML >>> " + strEscapeHTML);
        System.out.println("UnEscaped HTML >>> " + strUnEscapeHTML);
    }

    public static String replaceBlank(String str) {
        String dest = "";
        if (str != null) {
            Pattern p = Pattern.compile("\\s*|\t|\r|\n");
            Matcher m = p.matcher(str);
            dest = m.replaceAll("");
        }
        return dest;
    }


    public static List<String> getAllCategory() {
        List<String> list = new ArrayList<String>();

        return list;
    }

    public static List<String> getFmByCategory(String type) {
        String resultHtml = null;
        String url = Constants.GENERAL_URL;
        List<String> list = new ArrayList<String>();
        for (int page = 1; page <= Constants.PAGE_NUM; page++) {
            if (page == 1) {
                url = String.format(url, type);
            } else {
                url = String.format(url, type + "/" + page);
            }
            resultHtml = HttpUtil.get(url);
            JsonObject jsonObject = new Gson().fromJson(resultHtml, JsonObject.class);
            resultHtml = jsonObject.get("html").toString();
            if (StringUtils.isNotBlank(resultHtml)) {
                praseHtml(resultHtml);
            }
        }
        return list;
    }

    private static List<String> praseHtml(String html) {
        List<String> list = new ArrayList<String>();
        Document doc = Jsoup.parse(html);
        Elements elements = doc.getElementsByClass("discoverAlbum_title");
        for (Element e : elements) {
            System.out.println(e.text());
        }
        return list;
    }
}
