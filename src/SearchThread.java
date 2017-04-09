import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by jay on 3/30/17.
 */
public class SearchThread implements Runnable {
    public int start;
    public int finish;
    public String[] terms;

    public SearchThread(int start,
                        int finish,
                        String[] terms) {
        System.out.println("Thread start "+start +" finish: "+finish);
        this.start = start;
        this.finish = finish;
        this.terms = terms;
    }

    @Override
    public void run() {
        synchronized (Search.resultSet){
            for (String term :terms) {
                Word keyWord = findTerm(term);
                if (keyWord != null) {
                    for (int id:keyWord.getList()
                            ) {
                        Result result = new Result(getUrlStringFromID(id),id);
                        if(Search.resultSet.contains(result)){
                            int index = Search.resultSet.indexOf(result);
                            Search.resultSet.get(index).incrementScore();
                        }else {
                            Search.resultSet.add(result);
                        }
                    }
                }

            }

        }

    }
    public Word findTerm(String term){
        ArrayList<Word> qualifiedWords = new ArrayList<>();
        for (int i = start; i < finish; i++) {
//            System.out.println(i);

            if(Search.wordList.get(i).getWord().equals(term)){
                System.out.println(Search.wordList.get(i).getWord() + " ID: " +Search.wordList.get(i).getList()+ " num " + i);
                qualifiedWords.add(Search.wordList.get(i));
            }
        }
        List<Integer> combined = new ArrayList<>();
        for (Word w:qualifiedWords
             ) {
            combined.addAll(w.getList());
        }
        System.out.println(Arrays.toString(combined.toArray()));
        return new Word(term,combined);

    }
    public String getUrlStringFromID(int id){
        for (Page page:Search.pageList
             ) {
            if(page.getURLID() == id){
                return page.getURL();
            }
        }
        return null;
    }

}
