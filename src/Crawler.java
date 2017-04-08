import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
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

        currentID = 0;
        totalURLs = 0;
        this.domain = domain;
        this.limit = limit;
        parser = new Parser();
        toParse = new MyQueue();
        //add to toParse???
        toParse.add(seed);
        words = new ArrayList<>();
        parsed = new ArrayList<>();
        visited = new ArrayList<>();
        words.add(new Word(seed, currentID));
        parsed.add(new Page(seed, currentID));
        // Word cs = new Word(domain+seed, currentID);
        // words.add(cs);
        //Page ps = new Page(domain+seed,currentID);
        //parsed.add(ps);
    }

    public void crawl() throws ParseException {
        currentID = 0;
        System.out.println("toParse.isEmpty: " + toParse.isEmpty());

//        process continues either until the Queue is empty or a preset limit to the number of URLs to visit is reached
        while((toParse != null) && !toParse.isEmpty() && currentID < limit){
            //grab url from MyQueue
            String url = (String)toParse.remove().getData();
            //parse method

            if(parse(parser.getDocument(url),currentID)){
                //parsed.add()
                currentID++;
            }
            //increment currentID upon successful parse
            System.out.println("MyQueue: " + toParse.peek());
        }
        System.out.println("End of Crawl()");
    }

    public boolean parse(Document doc,
                         int id) throws ParseException {
        parseText(doc, id);
        parseLinks(doc);

        return true;

    }

    public void parseLinks(Document doc) throws ParseException {
        Elements links = parser.getLinks(doc);
        for(Element link: links){
            String oneLink = link.attr("abs:href");
            System.out.println("parseLinks: " + oneLink);
            if(isValidURL(oneLink) && isInDomain(oneLink)){
                visited.add(oneLink);
                totalURLs++;


            }
        }
        //parsed.add()

    }

    public void parseText(Document doc,
                          int id) throws ParseException {
//        Parse a Document for the body of text
        // This method parses through the document for the body
        //The body of the document should contain links????
        //separate links and add to visited??
        String texts = parser.getBody(doc);
        String[] links = texts.split("\n");
        for(int i = 0; i < links.length; i++){
            if(isValidURL(links[i]) && isInDomain(links[i])) {
                totalURLs++; // Do I increment totalURLs here????
                Page page = new Page(links[i],id);
                parsed.add(page);
                visited.add(links[i]);
            }
        }


    }

    public void addWordToList(String word,
                              int id) {

        Word n = new Word(word.toLowerCase(),id);

        words.add(n);

    }

    public void addToQueue(String url) {
        if(url == null){
            return;
        }
        toParse.add(url);
        totalURLs++;

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

        for (int i = 0; i < url.length()-3; i++) {
            if (url.substring(i, i + 3).equals("://")) {
                return (url.substring(0, i).equals("http") || url.substring(0, i).equals("https"));
            }
        }
        return false;

    }
}