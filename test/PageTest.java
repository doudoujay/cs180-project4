import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class PageTest {
    Page pg;
    static private String testUrl = "http://purdue.edu";
    static private int testUrlId = 100;

    @Before
    public void setUp() throws Exception {
        pg = new Page(testUrl, testUrlId);
    }

    @Test(timeout = 1000)
    public void testSerialVersionUID() {
        assertTrue("Failed to implement correct serialVersionUID. Should be -1827677255104766839L", -1827677255104766839L == pg.serialVersionUID);
    }

    @Test(timeout = 1000)
    // @ScoringWeight(.01)
    public void testGetters() throws Exception {
        assertTrue("Failed to get the URL initialized by the constructor.", testUrl.equals(pg.getURL()));
        assertTrue("Failed to get the URLID initialized by the constructor.", testUrlId == pg.getURLID());
    }

    @Test(timeout = 1000)
    // @ScoringWeight(.01)
    public void testEquals() throws Exception {
        Page testpg = new Page(testUrl, testUrlId);
        assertTrue("Attempt to test two pages that are equal using .equals(), but it failed.", pg.equals(testpg));
        Page testpg2 = new Page("http://www.google.com", 2);
        assertFalse(".equals() should return True while two inputs are equal.", pg.equals(testpg2));

        Page testpg3 = new Page(testUrl, 2);
        assertTrue(".equals() should return True while the two URLs are equal.", pg.equals(testpg3));

        Page testpg4 = new Page("aabccdd", testUrlId);
        assertTrue(".equals() should return True while the two URLIDs are equal.", pg.equals(testpg4));
    }
}