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
        this.start = start;
        this.finish = finish;
        this.terms = terms;
    }

    @Override
    public void run() {
//        TODO

    }
    public Word findTerm(String term){
//        TODO
//        Find and return the associated Word object for a given term
        return null;
    }
}
