package crawler.robots;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import crawler.classes.TrendingTopic;

public class TwitterController {
    private String bearerToken;
    private String apiUrl;

    public TwitterController(){
        this.apiUrl = "https://api.twitter.com/1.1/trends/place.json?id=23424768";
        this.bearerToken = "AAAAAAAAAAAAAAAAAAAAAOqZIwEAAAAAdpegqJiKYYHL%2FGDroVT5x%2FYOzbw%3DMPQ0m3sw0rzU8eY7ByLEobp5slwe2JvjNfCV0EN2R7Pya7F6XE";
    }

    public TrendingTopic fetch() {
        try {
            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder()
                                .uri(URI.create(this.apiUrl))
                                .setHeader("Authorization", "Bearer "+this.bearerToken)
                                .build();
            HttpResponse<String> responseString = client.send(request, HttpResponse.BodyHandlers.ofString());
            JsonArray responseJsonArray = JsonParser.parseString(responseString.body()).getAsJsonArray();
            JsonObject responseJsonObject = (JsonObject) responseJsonArray.get(0);
            JsonArray trendsJsonArray = (JsonArray) responseJsonObject.get("trends");
            JsonObject trendingTopic = (JsonObject) trendsJsonArray.get(0);
            TrendingTopic twiterFirstTrending = new TrendingTopic(trendingTopic.get("name").toString(), trendingTopic.get("url").toString());
            return twiterFirstTrending;
        } catch (IOException | InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return null;
        }
    }
}
