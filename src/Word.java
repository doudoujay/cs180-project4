import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by jay on 2017/3/21.
 */
public class Word implements Serializable {
    private String word;
    private List<Integer> postings = new ArrayList<>();
    static final long serialVersionUID = -3696191086353573895L;

    public Word(String word, int urlID) {
        this.word = word;
        this.postings.add(urlID);
    }
    public Word(String word, List<Integer> postings){
        this.word = word;
        this.postings = postings;
    }

    public void addURLID(int urlID) {
        postings.add(urlID);
    }
    public void setPostings(List<Integer> a){
        this.postings = a;
    }

    public String getWord() {
        return word;
    }

    public List<Integer> getList() {
        return postings;
    }

    @Override
    public boolean equals(Object obj) {
        return word.equals(((Word) obj).word);
    }

}