import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by jay on 3/30/17.
 */
public class Search {
    public static List<Page> pageList;
    public static List<Word> wordList;
    public static List<Result> resultSet;
    public final static Object syncGate = new Object();
    private String wordListFile;
    private String pageListFile;
    private FileUtils fu = new FileUtils();

    public Search(String wordListFile,
                  String pageListFile) {
        this.wordListFile = wordListFile;
        this.pageListFile = pageListFile;
        pageList = Collections.synchronizedList(new ArrayList<Page>());
        wordList = Collections.synchronizedList(new ArrayList<Word>());
        resultSet = Collections.synchronizedList(new ArrayList<Result>());

    }

    public void setup(String wordListFile,
                      String pageListFile) {


        pageList = fu.getPageList(pageListFile);
        wordList = fu.getWordList(wordListFile);
        System.out.printf("Data loading completed");
    }

    public List<Result> executeQuery(String query) {
//        TODO
        //Execute a query over built tables and return an arraylist of Result objects.

        nullCheck();
        // Split query String into array of terms
        String[] terms = query.toLowerCase().split(" ");

        System.out.println("terms size: " + terms.length);
        //Create 5 Threads using SearchThread class

        int start = 0;
        int increment = wordList.size() / 5;
        ArrayList<Thread> threads = new ArrayList<>();

        for (int i = 0;i<5;i++) {
            System.out.println("Start: " + start + "\tIncrement: " + increment + "\tEnd: " + (start+increment));
            Thread t = new Thread(new SearchThread(start, start+increment, terms));
            threads.add(t);
            t.start();
            start+= increment;


        }


        for (Thread t : threads) {
            try {
                t.join();
                System.out.println("Thread Join!");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        //sort list by score
        sort();
        for(int i = 0; i<10 && i < resultSet.size(); i++)
        {
            Result r = resultSet.get(i);
            System.out.println((i + 1) + ") " + r.getURL() + ", score: " + r.getScore());
        }
        return resultSet;

    }

    public void nullCheck() {
//        TODO
        //method to check if we have not read from our lists from File


//        Utility method to check if we have not read in our lists from file. If we haven't, call the setup() method.
        if (pageList.size() == 0 || wordList.size() == 0) {
            setup(wordListFile, pageListFile);
        }
    }

    public void sort() {
//        TODO
        //sort ArrayList of Results by their score

//        Method to sort our ArrayList of Results by their score. Since the Result List is global, we don't need to pass it as a parameter or return it - we can simply access it within the method.
        Collections.sort(resultSet);
    }
}
