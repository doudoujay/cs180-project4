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
    public static Object syncGate;
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

        // Split query String into array of terms
        String[] terms = query.split(" ");

        //Create 5 Threads using SearchThread class

        SearchThread[] sthreads ={ new SearchThread(0,200,terms), new SearchThread(201,400,terms),
                new SearchThread(401,600,terms), new SearchThread(601,800,terms),
                new SearchThread(801,1000,terms)};

        int i = 0;
        Thread[] threads = new Thread[5];
        for(Thread thr: threads){
            thr = new Thread(sthreads[i]);
            i++;
        }
        //Start Threads
        for( Thread t : threads)
            t.start();


        for( Thread t: threads)
            try {
                t.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }


        //sort list by score
        sort();

        return null;
    }

    public void nullCheck() {
//        TODO
        //method to check if we have not read from our lists from File


//        Utility method to check if we have not read in our lists from file. If we haven't, call the setup() method.
        if(pageList==null||wordList==null){
            setup(wordListFile,pageListFile);
        }
    }

    public void sort() {
//        TODO
        //sort ArrayList of Results by their score

//        Method to sort our ArrayList of Results by their score. Since the Result List is global, we don't need to pass it as a parameter or return it - we can simply access it within the method.
        Collections.sort(resultSet);
    }
}
