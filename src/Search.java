import java.util.ArrayList;
import java.util.Arrays;
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
        System.out.println("Query: " + query);

        System.out.println("terms size: " + terms.length);
        //Create 5 Threads using SearchThread class

        int start = 0;
        int increment = terms.length/5;
        int end = terms.length/5 ;
        Thread[] threads = new Thread[5];
        boolean first = true;

        for(Thread thr: threads){

            System.out.println("Start: " + start + "\tIncrement: " + increment + "\tEnd: " + end);
            thr = new Thread(new SearchThread(start, end, terms));
            // System.out.println("i: " + i);
            System.out.println("Thread Created!");
            start = start + increment;
            end = end + increment;
            if(first){
                start++;
                first = false;
            }

        }

        System.out.println(Arrays.toString(threads));
        //Start Threads
        for( Thread ts : threads) {
            ts.start();
            System.out.println("Thread Start!");
        }




        for( Thread t: threads)
            try {
                t.join();
                System.out.println("Thread Join!");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }


        //sort list by score
        sort();
        System.out.println("ResultSet" + resultSet);
        return resultSet;

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
