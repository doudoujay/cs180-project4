import java.util.*;
import java.io.BufferedReader;
import java.io.File;
import java.util.Collections;

/**
 * Created by jay on 3/30/17.
 */
public class Search {
    public static List<Page> pageList;
    public static List<Word> wordList;
    public static List<Result> resultSet;
    private String wordListFile;
    private String pageListFile;
    private FileUtils fu = new FileUtils();
    public static Object syncGate;

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
        int start = 0;
        int end = 200;
        Thread[] threads ={ new SearchThread(0,200,terms), new SearchThread(201,400,terms)};//add more threads?

        //Start Threads
        for( Thread t : threads)
            t.start();


        for( Thread t: threads)
            t.join();


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
