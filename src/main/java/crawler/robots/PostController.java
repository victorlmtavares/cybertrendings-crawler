package crawler.robots;

import java.io.IOException;
import java.net.URI;
import java.text.SimpleDateFormat;
import java.util.List;

import org.apache.http.client.HttpClient;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import com.google.gson.JsonObject;

import crawler.classes.Noticia;

public class PostController {
    private List<Noticia> noticias;

    public PostController(List<Noticia> noticias) {
        this.noticias = noticias;
    }

    public void websitePost() throws IOException, InterruptedException {
        SimpleDateFormat sdf;
        sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSXXX");
        HttpClient client = HttpClients.createMinimal();
        for(Noticia noticia : this.noticias){
            if(noticia.getUrlNoticia().indexOf("twitter.com") != -1 
            || noticia.getUrlNoticia().indexOf("youtube.com") != -1 
            || noticia.getUrlNoticia().indexOf("facebook.com") != -1
            || noticia.getMateria().indexOf('#'+noticia.getTagTwitter()) != -1
            || !noticia.getSanitizada())
                continue;

            JsonObject json = new JsonObject();
            json.addProperty("dataInclusao", sdf.format(noticia.getDate()));
            json.addProperty("dataFinal", sdf.format(noticia.getDate()));
            json.addProperty("tagTwitter", noticia.getTagTwitter());
            json.addProperty("urlTagTwitter", noticia.getUrlTagTwitter());
            json.addProperty("urlNoticia", noticia.getUrlNoticia());
            json.addProperty("tituloNoticia", noticia.getTituloNoticia());
            json.addProperty("conteudoNoticia", noticia.getMateria());
            json.addProperty("upQtd", 0);
            json.addProperty("downQtd", 0);

            StringEntity  requestEntity = new StringEntity (
            json.toString(),
            ContentType.APPLICATION_JSON);
            HttpPost request = new HttpPost(URI.create("https://cybertrending.herokuapp.com/noticia/create"));
            request.setEntity(requestEntity);
            HttpResponse response = client.execute(request);
            HttpEntity entity = response.getEntity();
            System.out.println(EntityUtils.toString(entity));
        }        
    }
}
