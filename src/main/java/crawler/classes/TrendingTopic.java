package crawler.classes;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

public class TrendingTopic {
    private String tag;
    private String url;

    public TrendingTopic(String tag, String url){
        this.tag = tag.replace("#", "");
        this.url = url;
    }

    public String getTag(){
        return this.tag;
    }
    public String getUrl(){
        return this.url;
    }
    public String getTagEncoded(){
        return URLEncoder.encode(this.tag, StandardCharsets.UTF_8);
    }
}
