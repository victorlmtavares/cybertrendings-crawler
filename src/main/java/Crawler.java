import java.io.IOException;
import java.util.List;

import crawler.classes.Noticia;
import crawler.robots.PostController;
import crawler.robots.GoogleController;
import crawler.robots.NoticiaCrawlerController;
import crawler.robots.TwitterController;

public class Crawler {
    public static void main(String[] args) throws IOException, InterruptedException {
        TwitterController twitterRobot = new TwitterController();
        GoogleController google = new GoogleController(twitterRobot.fetch());
        List<Noticia> noticias = google.fetch();       
        for(Noticia noticia : noticias){
            NoticiaCrawlerController formatadorDeNoticias = new NoticiaCrawlerController(noticia);
            formatadorDeNoticias.processarNoticia();
        }
        PostController postController = new PostController(noticias);
        postController.websitePost();
    }
}