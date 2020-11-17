package crawler.robots;

import java.util.ArrayList;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import crawler.classes.Noticia;

public class NoticiaCrawlerController {
    private Noticia noticia;

    public NoticiaCrawlerController(Noticia noticia) {
        this.noticia = noticia;
    }

    public void processarNoticia() {
        try {
            boolean sanitizouParagrafo = false, sanitizouTitulo = false;
            String urlNoticia = noticia.getUrlNoticia().replace("\"", "");
            Document noticiaDOM = Jsoup.connect(urlNoticia).get();
            List<String> palavrasMateria = this.prepararPreviewMateria();
            Elements paragrafosNoticia = noticiaDOM.select("p");
            List<String> paragrafosSelecionados = new ArrayList();
            for(Element paragrafo : paragrafosNoticia){
                int pontos = 0;
                String[] palavrasParagrafos = paragrafo.text()
                .replaceAll("\\."," ")
                .replaceAll("\\?"," ")
                .replaceAll("\\!"," ")
                .replaceAll("\\,"," ")
                .replaceAll("\\;"," ")
                .replaceAll("\\:"," ")
                .replaceAll("\\("," ")
                .replaceAll("\\)"," ")
                .replaceAll("\\["," ")
                .replaceAll("\\]"," ")
                .replaceAll("\\{"," ")
                .replaceAll("\\}"," ")
                .split(" ");
                for(String palavraParagrafo : palavrasParagrafos){
                    for(String palavraMateria : palavrasMateria){
                        if(palavraMateria.equals(palavraParagrafo)){
                            pontos += palavraMateria.length();
                        }                        
                    }
                }                
                if(pontos >= (palavrasMateria.size()) && paragrafo.text().indexOf("pic.twitter.com") == -1){
                    paragrafosSelecionados.add(paragrafo.text());
                }
            }
            if(paragrafosSelecionados.size() > 0){
                String materia = "";
                for(String paragrafo : paragrafosSelecionados){
                    materia += paragrafo;
                }
                this.noticia.setMateria(materia);
                sanitizouParagrafo = true;
            }
            Elements titulosNoticia = noticiaDOM.select("h1, h2, h3, h4, h5");
            int pontosAnteriores = 0;
            for(Element titulo : titulosNoticia){
                int pontos = 0;
                String[] palavrasTitulo = titulo.text()
                .replaceAll("\\."," ")
                .replaceAll("\\?"," ")
                .replaceAll("\\!"," ")
                .replaceAll("\\,"," ")
                .replaceAll("\\;"," ")
                .replaceAll("\\:"," ")
                .replaceAll("\\("," ")
                .replaceAll("\\)"," ")
                .replaceAll("\\["," ")
                .replaceAll("\\]"," ")
                .replaceAll("\\{"," ")
                .replaceAll("\\}"," ")
                .split(" ");                
                for(String palavraTitulo : palavrasTitulo){
                    for(String palavraMateria : palavrasMateria){
                        if(palavraMateria.equals(palavraTitulo)){
                            pontos += palavraMateria.length();
                        }                        
                    }
                }                
                if(pontos > pontosAnteriores){
                    pontosAnteriores = pontos;
                    this.noticia.setTitulo(titulo.text());
                    sanitizouTitulo = true;
                }
            }
            if(sanitizouParagrafo && sanitizouTitulo){
                this.noticia.setSanitizada(true);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    private List<String> prepararPreviewMateria(){
        String previewMateria = this.noticia.getMateria();
        String[] arrayPreviewSplitted2 = previewMateria
        .replaceAll("\\."," ")
        .replaceAll("\\?"," ")
        .replaceAll("\\!"," ")
        .replaceAll("\\,"," ")
        .replaceAll("\\;"," ")
        .replaceAll("\\:"," ")
        .replaceAll("\\("," ")
        .replaceAll("\\)"," ")
        .replaceAll("\\["," ")
        .replaceAll("\\]"," ")
        .replaceAll("\\{"," ")
        .replaceAll("\\}"," ")
        .split(" ");
        List<String> palavrasPreviewSelecionadas = new ArrayList();
        for(String palavra : arrayPreviewSplitted2){
            if(palavra.length() > 3){
                palavrasPreviewSelecionadas.add(palavra);
            }
        }
        return palavrasPreviewSelecionadas;
    }
    
}
