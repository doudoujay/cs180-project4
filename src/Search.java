import java.io.BufferedReader;
import java.io.File;
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
    private String wordListFile;
    private String pageListFile;

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
//
        FileUtils fileUtil = new FileUtils();

        pageList = fileUtil.getPageList(pageListFile);
        wordList = fileUtil.getWordList(wordListFile);
      //  fileUtil.savePageTable(pageList,pageListFile);
     //   fileUtil.saveWordTable(wordList,wordListFile);



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


    }

    public void sort() {
//        TODO
        //sort ArrayList of Results by their score

    }
}
