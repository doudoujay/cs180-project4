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
    }

    public void setup(String wordListFile,
                      String pageListFile) {
//        TODO

    }

    public List<Result> executeQuery(String query) {
//        TODO
        return null;
    }

    public void nullCheck() {
//        TODO
    }

    public void sort() {
//        TODO
    }
}
