package crawler.robots;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringJoiner;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import crawler.classes.Noticia;

public class PostController {
    private List<Noticia> noticias;

    public PostController(List<Noticia> noticias) {
        this.noticias = noticias;
    }

    public void facebookPost() throws IOException, InterruptedException {
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        final long timestampAgora = timestamp.getTime();
        long intervaloEntreNoticias = 3600000 / this.noticias.size();
        int contador = 0;
        for(final Noticia noticia : this.noticias){
            final long proximaPublicacao = (contador * intervaloEntreNoticias);
            HashMap<String, String> values = new HashMap<String, String>() {{
                put("published", "false");
                put ("message", noticia.getMateria());
                put ("link", noticia.getUrlNoticia().replace("\"", ""));
                put ("scheduled_publish_time", Long.toString((long) (timestampAgora + proximaPublicacao)));
                put ("access_token", "EAADWvX6MXwIBAFui8Y4ZBF5UbKQk6PJRmzSyztEk4OpZAKFoy7IOGDJmFvVz2qPilrIeZAZBciIB3KjXTPkoLne8Gjtv1VMh0ZAWEjNfQnlMZCKGN5yfKXGa4C7f4ypqh0XBKQYaa7BehrZB5N3YvWLkxBj5iMGrKHb4TpAKheAZAXvNWrjoLcLCkHNqEKrjztPOP57rUQpZCaOmV13z1bcCy3OtN0RZBqZB6v9ZBE2ttznyewZDZD");
            }};
            StringJoiner sj = new StringJoiner("&");
            for(Map.Entry<String,String> entry : values.entrySet())
                sj.add(entry.getKey() + "=" + entry.getValue());
            String parametros = sj.toString();

            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder()
                                .uri(URI.create("https://graph.facebook.com/110215154217282/feed"))
                                .POST(HttpRequest.BodyPublishers.ofString(parametros))
                                .setHeader("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8")
                                .build();
            HttpResponse<String> responseString = client.send(request, HttpResponse.BodyHandlers.ofString());
            JsonObject response = JsonParser.parseString(responseString.body()).getAsJsonObject();
            contador++;
            System.out.println(response);
        }
        
    }

    public void websitePost() throws IOException, InterruptedException {
        SimpleDateFormat sdf;
        sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSXXX");
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
            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder()
                                .uri(URI.create("https://cybertrending.herokuapp.com/noticia/create"))
                                .POST(HttpRequest.BodyPublishers.ofString(json.toString()))
                                .setHeader("Content-Type", "application/json; utf-8")
                                .build();
            HttpResponse<String> responseString = client.send(request, HttpResponse.BodyHandlers.ofString());
            System.out.println(responseString);
        }        
    }
}
