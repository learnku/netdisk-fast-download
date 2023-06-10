package cn.qaiu.lz.common.util;

import java.net.MalformedURLException;
import java.net.URL;
import java.text.ParseException;
import java.util.HashMap;
import java.util.Map;

public class CommonUtils {

    public static String adaptShortPaths(String urlPrefix, String url) {
        if (!url.startsWith(urlPrefix)) {
            url = urlPrefix + url;
        }
        if (url.endsWith(".html")) {
            url = url.substring(0, url.length() - 5);
        }
        return url.substring(urlPrefix.length());
    }

    public static Map<String, String> getURLParams(String url) throws MalformedURLException {
        URL fullUrl = new URL(url);
        String query = fullUrl.getQuery();
        String[] params = query.split("&");
        Map<String, String> map = new HashMap<>();
        for (String param : params) {
            if (!param.contains("=")) {
                throw new RuntimeException("解析URL异常: 匹配不到参数中的=");
            }
            int endIndex = param.indexOf('=');
            String key = param.substring(0, endIndex);
            String value = param.substring(endIndex + 1);
            map.put(key, value);
        }
        return map;
    }

}
