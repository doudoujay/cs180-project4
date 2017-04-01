import org.jsoup.nodes.Document;

import java.util.List;

/**
 * Created by Paulina on 3/23/2017.
 */
public class Crawler extends Object {

    private static int currentID;
    private static String domain;
    private static int limit;
    private static List<Page> parsed;
    private static Parser parser;
    private MyQueue toParse;
    private static int totalURLs;
    private static List<String> visited;
    private static List<Word> words;

    public Crawler(String seed, String domain, int limit) { //crawler.words and crawler.parsed should be stored

        totalURLs++;
        this.domain = domain;
        this.limit = limit;
        // Word cs = new Word(domain+seed, currentID);
        // words.add(cs);
        //Page ps = new Page(domain+seed,currentID);
        //parsed.add(ps);
    }

    public void crawl(){
        
    }

    public boolean parse(Document doc,
                         int id) {

    }

    public void parseLinks(Document doc) {

    }

    public void parseText(Document doc,
                          int id) {

    }

    public void addWordToList(String word,
                              int id) {

    }

    public void addToQueue(String url) {
        toParse.add(url);

    }

    public void addPageToList(Page p) {
        parsed.add(p);

    }

    public boolean isInDomain(String url) {


    }

    public boolean isValidURL(String url) {
        for (int i = 0; i < url.length(); i++) {
            if (url.substring(i, i + 3).equals(".//")) {
                return (url.substring(0, i).equals("http") || url.substring(0, i).equals("https"));
            }
        }
        return false;
    }
}