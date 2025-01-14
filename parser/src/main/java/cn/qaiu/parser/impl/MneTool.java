package cn.qaiu.parser.impl;

import cn.qaiu.entity.ShareLinkInfo;
import cn.qaiu.parser.PanBase;
import io.vertx.core.Future;
import io.vertx.uritemplate.UriTemplate;

/**
 * 网易云音乐, 单歌曲直链解析
 * <a href="http://163cn.tv/ykLZJJT">示例分享</a>
 */
public class MneTool extends PanBase {


    public static final String API_URL = "https://music.163.com/song/media/outer/url?id={id}";


    public MneTool(ShareLinkInfo shareLinkInfo) {
        super(shareLinkInfo);
    }

    public Future<String> parse() {
        String shareUrl = shareLinkInfo.getStandardUrl();
        clientNoRedirects.getAbs(shareUrl).send().onSuccess(res -> {
            String locationURL = res.headers().get("Location");
            String substring = locationURL.substring(locationURL.indexOf("id="));
            String id = substring.substring("id=".length(), substring.indexOf('&'));
            clientNoRedirects.getAbs(UriTemplate.of(API_URL)).setTemplateParam("id", id).send()
                    .onSuccess(res2 -> {
                        promise.complete(res2.headers().get("Location"));
                    }).onFailure(handleFail(API_URL.replace("{id}", id)));
        }).onFailure(handleFail(shareUrl));
        return promise.future();
    }
}
