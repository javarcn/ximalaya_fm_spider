package com.javarcn.util;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

/**
 * Created by jiacheng on 2017/8/30 0030.
 */
public class HttpUtil {
    private static final Logger log = LoggerFactory.getLogger(HttpUtil.class);

    public static String get(String url) {
        String result=null;
        int error_num=0;
        int retryTimes = 1;
        do {
            HttpGet get = new HttpGet(url);
            CloseableHttpClient client = HttpClients.createDefault();
            get.addHeader("Accept","application/json, text/javascript, */*; q=0.01");
            get.addHeader("Accept-Encoding","gzip, deflate");
            get.addHeader("Accept-Language","zh-CN,zh;q=0.8");
            get.addHeader("Connection","keep-alive");
            get.addHeader("User-Agent","Mozilla/5.0 (Windows NT 6.1; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/61.0.3163.79 Safari/537.36");
            get.addHeader("X-Requested-With","XMLHttpRequest");
            get.addHeader("Cookie","_xmLog=xm_1502683692592_j6bn2lv4ks404c; trackType=web; x_xmly_traffic=utm_source%3A%26utm_medium%3A%26utm_campaign%3A%26utm_content%3A%26utm_term%3A%26utm_from%3A; _ga=GA1.2.1952331086.1502683693\n" +
                    "Host:www.ximalaya.com");
            try {
                HttpResponse response = client.execute(get);
                if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                    HttpEntity entity = response.getEntity();
                    if (entity != null) {
                        result= EntityUtils.toString(entity);
                    }
                }
            } catch (IOException e) {
                try {
                    log.error("第{}次请求异常，系统睡眠1秒后重试,URL:============{}",++error_num,url);
                    Thread.sleep(1000);
                } catch (InterruptedException e1) {
                    e1.printStackTrace();
                }
                e.printStackTrace();
            }
        } while (++retryTimes <=3);
        return result;
//        throw new RuntimeException("服务端异常，超出重试次数!");
    }
}
