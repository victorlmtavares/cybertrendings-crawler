package crawler.classes;

import java.util.Date;

public class Noticia {
    String tag;
    String urlTag;
    String urlNoticia;
    String titulo;
    String materia;
    Date data;
    boolean sanitizada;

    public Noticia(String tag, String urlTag, String urlNoticia, String titulo, String materia){
        this.tag = tag;
        this.urlTag = urlTag;
        this.urlNoticia = urlNoticia;
        this.titulo = titulo;
        this.materia = materia;
        this.data = new Date();
        this.sanitizada = false;
    }
    public String getUrlNoticia(){
        String urlNoticia = this.urlNoticia.replaceAll("\"", "");
        return urlNoticia;
    }
    public String getMateria(){
        String materia = this.materia
        .replaceAll("\"", "")
        .replaceAll("\\;"," ")
        .replaceAll("\\["," ")
        .replaceAll("\\]"," ")
        .replaceAll("\\{"," ")
        .replaceAll("\\}"," ");;
        return materia;
    }
    public Date getDate(){
        return this.data;
    }
    public String getTagTwitter(){
        String tag = this.tag
        .replaceAll("\"", "")
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
        .replaceAll("\\}"," ");
        return tag;
    }
    public String getUrlTagTwitter(){
        String urlTag = this.urlTag.replaceAll("\"", "");
        return urlTag;
    }
    public String getTituloNoticia(){
        String titulo = this.titulo
        .replaceAll("\"", "")
        .replaceAll("\\."," ")
        .replaceAll("\\,"," ")
        .replaceAll("\\;"," ")
        .replaceAll("\\:"," ")
        .replaceAll("\\("," ")
        .replaceAll("\\)"," ")
        .replaceAll("\\["," ")
        .replaceAll("\\]"," ")
        .replaceAll("\\{"," ")
        .replaceAll("\\}"," ");;
        return titulo;
    }
    public boolean getSanitizada(){
        return this.sanitizada;
    }
    public void setMateria(String materia){
        this.materia = materia;
    }
    public void setTitulo(String titulo){
        this.titulo = titulo;
    }
    public void setSanitizada(boolean valor){
        this.sanitizada = valor;
    }
}
