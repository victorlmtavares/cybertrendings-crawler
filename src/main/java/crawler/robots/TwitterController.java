package crawler.robots;

import java.io.IOException;
import java.net.URI;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import org.apache.http.client.HttpClient;
import org.apache.http.HttpEntity;
import org.apache.http.HttpHeaders;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import crawler.classes.TrendingTopic;

public class TwitterController {
    private String bearerToken;
    private String apiUrl;

    public TwitterController(){
        this.apiUrl = "https://api.twitter.com/1.1/trends/place.json?id=23424768";
        this.bearerToken = "AAAAAAAAAAAAAAAAAAAAAOqZIwEAAAAAdpegqJiKYYHL%2FGDroVT5x%2FYOzbw%3DMPQ0m3sw0rzU8eY7ByLEobp5slwe2JvjNfCV0EN2R7Pya7F6XE";
    }

    public TrendingTopic fetch() {

        HttpClient client = HttpClients.createMinimal();

        try {
            HttpGet request = new HttpGet(URI.create(this.apiUrl));
            request.addHeader("Authorization", "Bearer "+this.bearerToken);
            request.addHeader(HttpHeaders.USER_AGENT, "Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/51.0.2704.103 Safari/537.36"); 
            HttpResponse response = client.execute(request);
            HttpEntity entity = response.getEntity();
            JsonArray responseJsonArray = JsonParser.parseString(EntityUtils.toString(entity)).getAsJsonArray();
            JsonObject responseJsonObject = (JsonObject) responseJsonArray.get(0);
            JsonArray trendsJsonArray = (JsonArray) responseJsonObject.get("trends");
            JsonObject trendingTopic = (JsonObject) trendsJsonArray.get(0);
            TrendingTopic twiterFirstTrending = new TrendingTopic(trendingTopic.get("name").toString(), trendingTopic.get("url").toString());
            return twiterFirstTrending;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
