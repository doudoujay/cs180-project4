import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Paulina on 3/23/2017.
 */
public class Crawler extends Object {

    private static int currentID;
    private static String domain;
    private static int limit;
    public static List<Page> parsed;
    private static Parser parser;
    public MyQueue toParse;
    public static int totalURLs;
    private static List<String> visited;
    public static List<Word> words;
    public FileUtils fu = new FileUtils();

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

    }

    public void crawl() throws ParseException {
        currentID = 0;
        System.out.println("toParse.isEmpty: " + toParse.isEmpty());

//        process continues either until the Queue is empty or a preset limit to the number of URLs to visit is reached
        while ((toParse != null) && !toParse.isEmpty() && currentID < limit) {
            //grab url from MyQueue
            Object url = toParse.remove().getData();
            //parse method

            try {
                if (parse(parser.getDocument(url.toString()), currentID)) {
                    addPageToList(new Page(url.toString(), currentID));
                    currentID++;
                }
            }catch (Exception e){
                e.printStackTrace();
            }


            //increment currentID upon successful parse)

        }
        System.out.println("End of Crawl(), total url:" + parsed.size());
//        Same the results in the file
        fu.savePageTable(parsed, "data/parsed.txt");
        fu.saveWordTable(words, "data/words.txt");
//        for (Word w: words
//             ) {
//            System.out.println(w.getWord());
//            for (int i:w.getList()
//                 ) {
//                System.out.printf(Integer.toString(i));
//            }
//            System.out.println("-------------------");
//        }

    }

    public boolean parse(Document doc,
                         int id) throws ParseException {
        try {
            parseLinks(doc);
            parseText(doc, id);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }


    }

    public void parseLinks(Document doc) throws ParseException {
        Elements links = parser.getLinks(doc);
        for (Element link : links) {
            String oneLink = link.attr("abs:href");

            if (isValidURL(oneLink) && isInDomain(oneLink)) {

                visited.add(oneLink);
//                Add to queue
                addToQueue(oneLink);



            }
        }
        System.out.println("Pages amount: " + parsed.size());

    }

    public void parseText(Document doc,
                          int id) throws ParseException {
//        Parse a Document for the body of text
        // This method parses through the document for the body
        //The body of the document should contain links???? add words?

        //separate links and add to visited??
        String texts = parser.getBody(doc);
        Pattern p = Pattern.compile("[\\w']+");
        Matcher m = p.matcher(texts);

        while (m.find()) {
            String foundText = texts.substring(m.start(), m.end());
            addWordToList(foundText, id);
        }

        System.out.println("words: " + words.size() + " url id " + id);


    }

    public void addWordToList(String word,
                              int id) {
//    Same word shoud in the same entry.
        Word n = new Word(word.toLowerCase(), id);
        if (words.contains(n)) {
            int index = words.indexOf(n);
            words.get(index).addURLID(id);
        } else {
            words.add(n);

        }


    }

    public void addToQueue(String url) {
//         Should avoid duplicated URLs.
        if (url == null) {
            return;
        }
        toParse.add(url);
        totalURLs++;

    }

    public void addPageToList(Page p) {
        parsed.add(p);

    }

    public boolean isInDomain(String url) {
        if (url.contains(domain)) {
            return true;
        }
        return false;

    }

    public boolean isValidURL(String url) {

        for (int i = 0; i < url.length() - 3; i++) {
            if (url.substring(i, i + 3).equals("://")) {
                return (url.substring(0, i).equals("http") || url.substring(0, i).equals("https"));
            }
        }
        return false;

    }
}