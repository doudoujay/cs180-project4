import java.io.Serializable;

/**
 * Created by ma438 on 3/21/17.
 */
public class Page implements Serializable, Comparable<Page> {
    String url;
    private int urlID;
    static final long serialVersionUID = -1827677255104766839L;

    @Override

    public int compareTo(Page candidate) {
        if (urlID < candidate.urlID) return -1;
        if (urlID > candidate.urlID) return 1;
        return 0;
    }

    @Override

    public boolean equals(Object obj) {
        return url.equals(((Page) obj).url) || urlID == ((Page) obj).urlID;
    }

    Page(String url, int urlID) {
        this.url = url;
        this.urlID = urlID;
    }

    public int getURLID() {
        return urlID;
    }

    public String getURL() {
        return url;
    }
}
