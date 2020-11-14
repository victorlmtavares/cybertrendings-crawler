package crawler.robots;

import java.net.URI;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.http.client.HttpClient;
import org.apache.http.HttpEntity;
import org.apache.http.HttpHeaders;
import org.apache.http.HttpRequest;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import crawler.classes.TrendingTopic;
import crawler.classes.Noticia;

public class GoogleController {
        
    private TrendingTopic trendingTopic;
    private List<Noticia> noticias = new ArrayList();

    public GoogleController(TrendingTopic trendingTopic) {
        this.trendingTopic = trendingTopic;
    }

    public List<Noticia> fetch(){

        HttpClient client = HttpClients.createMinimal();

        try {            
            HttpGet request = new HttpGet(URI.create("https://rapidapi.p.rapidapi.com/google-serps/?q=" 
            + this.trendingTopic.getTagEncoded() 
            + "&pages=1&gl=br&hl=pt-BR&duration=d&autocorrect=1"));
            request.addHeader("x-rapidapi-host", "google-search5.p.rapidapi.com");
            request.addHeader("x-rapidapi-key", "49a37b3e8emshf52c4aaf1519130p1aacecjsn51380c946174");
            request.addHeader(HttpHeaders.USER_AGENT, "Googlebot"); 
            HttpResponse response = client.execute(request);
            HttpEntity entity = response.getEntity();
            this.parseJson(EntityUtils.toString(entity));
            return this.noticias;      
        }catch ( Exception ex ) {
            ex.printStackTrace();
            return null;
        }    
    }

    public void parseJson(String response){
        try{
            JsonObject responseJsonObject = JsonParser.parseString(response).getAsJsonObject();
            JsonObject data = (JsonObject) responseJsonObject.get("data");
            JsonObject results = (JsonObject) data.get("results");
            JsonArray organic = (JsonArray) results.get("organic");
            Iterator<JsonElement> iter = organic.iterator();
            JsonElement temp;
            while(iter.hasNext()) {
                temp = iter.next();
                Noticia noticia = new Noticia(this.trendingTopic.getTag(), this.trendingTopic.getUrl(), 
                                temp.getAsJsonObject().get("url").toString(), temp.getAsJsonObject().get("title").toString(), 
                                temp.getAsJsonObject().get("snippet").toString());
                this.noticias.add(noticia);
            }
        }catch (Exception ex){
            ex.printStackTrace();
        }
    }
}