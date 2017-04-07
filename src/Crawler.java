import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.util.List;

/**
 * Created by Paulina on 3/23/2017.
 */
public class Crawler extends Object{

    private static int currentID;
    private static String domain;
    private static int limit;
    public static List<Page> parsed;
    private static Parser parser;
    public MyQueue toParse;
    public static int totalURLs;
    private static List<String> visited;
    public static List<Word> words;

    public Crawler(String seed, String domain, int limit) { //crawler.words and crawler.parsed should be stored

        totalURLs++;
        this.domain = domain;
        this.limit = limit;
        this.parser = new Parser();
        // Word cs = new Word(domain+seed, currentID);
        // words.add(cs);
        //Page ps = new Page(domain+seed,currentID);
        //parsed.add(ps);
    }

    public void crawl() throws ParseException {
        currentID = 0;
        while(!toParse.isEmpty() && currentID < limit){
            //grab url from MyQueue
            Object url = toParse.remove().getData();
            //parse method

            if(parse(parser.getDocument(url.toString()),currentID)){
                //parsed.add()
                currentID++;
            }
            //increment currentID upon successful parse

        }

    }

    public boolean parse(Document doc,
                         int id) throws ParseException {
        String body = parser.getBody(doc);
        Elements links = parser.getLinks(doc);
        if(body == null || links == null){
            return false;
        }
        return true;

    }

    public void parseLinks(Document doc) {


    }

    public void parseText(Document doc,
                          int id) {

    }

    public void addWordToList(String word,
                              int id) {
        Word n = new Word(word.toLowerCase(),id);
        words.add(n);

    }

    public void addToQueue(String url) {
        toParse.add(url);

    }

    public void addPageToList(Page p) {
        parsed.add(p);

    }

    public boolean isInDomain(String url) {
        if(url.contains(domain)){
            return true;
        }
        return false;

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