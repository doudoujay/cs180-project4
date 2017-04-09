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
        setup(wordListFile,pageListFile);
    }

    public void setup(String wordListFile,
                      String pageListFile) {


        pageList = fu.getPageList(pageListFile);
        wordList = fu.getWordList(wordListFile);
    }

    public List<Result> executeQuery(String query) {
        //Execute a query over built tables and return an arraylist of Result objects.

        nullCheck();
        String[] terms = query.toLowerCase().split(" ");

//        for (Word w : wordList
//                ) {
//            System.out.println(w.getWord());
//        }

        //Create 5 Threads using SearchThread class

        int start = 0;
        int increment = wordList.size() / 5;
        ArrayList<Thread> threads = new ArrayList<>();
        threads.add(new Thread(new SearchThread(0, (wordList.size() / 5), terms)));
        threads.add(new Thread(new SearchThread((wordList.size() / 5),(wordList.size()*2 / 5) , terms)));
        threads.add(new Thread(new SearchThread((wordList.size()*2 / 5),(wordList.size()*3 / 5) , terms)));
        threads.add(new Thread(new SearchThread((wordList.size()*3 / 5),(wordList.size()*4 / 5) , terms)));
        threads.add(new Thread(new SearchThread((wordList.size()*4 / 5),wordList.size() , terms)));
//        threads.add(new Thread(new SearchThread(0, wordList.size(), terms)));

        for (Thread t : threads
                ) {
            t.start();
        }
        for (Thread t : threads) {
            try {
                t.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        //sort list by score
        sort();
        for (int i = 0; i < 10 && i < resultSet.size(); i++) {
            Result r = resultSet.get(i);
            System.out.println((i + 1) + ") " + r.getURL() + ", score: " + r.getScore());
        }
        return resultSet;

    }

    public void nullCheck() {
        //method to check if we have not read from our lists from File


//        Utility method to check if we have not read in our lists from file. If we haven't, call the setup() method.
        if (pageList.size() == 0 || wordList.size() == 0) {
            setup(wordListFile, pageListFile);
        }
    }

    public void sort() {
        //sort ArrayList of Results by their score

//        Method to sort our ArrayList of Results by their score. Since the Result List is global, we don't need to pass it as a parameter or return it - we can simply access it within the method.
        Collections.sort(resultSet);
    }
}
