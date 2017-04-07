import java.io.Serializable;
import java.util.Comparator;

/**
 * Created by jay on 2017/3/23.
 */
public class Result implements Serializable, Comparable<Result> {
    static long serialVersionUID;
    public int score = 1;
    public int urlID;
    public String url;

    public Result(String url,
                  int urlID) {
        serialVersionUID = -938761094876384658L;
        this.url = url;
        this.urlID = urlID;
    }

    public void updateScore(int score) {
        this.score += score;
    }

    public void incrementScore() {
        this.score++;
    }

    public int getScore() {
        return score;
    }

    public String getURL() {
        return url;
    }

    public int getURLID() {
        return urlID;
    }

    @Override
    public int compareTo(Result o) {
        if (this.score > o.score) return -1;
        if (this.score < o.score) return 1;
        return 0;

    }

    @Override
    public boolean equals(Object obj) {
        return this.urlID == ((Result) obj).urlID;
    }
}
