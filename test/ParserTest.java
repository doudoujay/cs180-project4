import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;

/**
 * The test cases for Parser
 */
public class ParserTest {

    Parser ps;

    @Before
    public void setUp() throws Exception {
        ps = new Parser();
    }

    @Test(timeout = 1000)
    // @ScoringWeight(.01)
    public void testGetDocumentNull() throws Exception {
        boolean getException = false;
        try {
            ps.getDocument(null);
        }
        catch (ParseException e) {
            getException = true;
        }
        assertTrue("Attempt to getDocument(null), but the expected ParseException is not caught. ",
                getException);
    }

    @Test(timeout = 1000)
    // @ScoringWeight(.01)
    public void testGetDocumentInvalidURL() throws Exception {
        boolean getException = false;
        try {
            ps.getDocument("abc");
        }
        catch (ParseException e) {
            getException = true;
        }
        assertTrue("Attempt to getDocument() with an invalid URL, but the expected ParseException is not caught. ",
                getException);
    }

    @Test(timeout = 5000)
    // @ScoringWeight(.01)
    public void testGetDocument() throws Exception {
        Document d = ps.getDocument("https://en.wikipedia.org/wiki/Purdue_University");
        assertFalse("Attempt to get the document from https://en.wikipedia.org/wiki/Purdue_University, but " +
                ".getDocument() returns a null.", d == null);
    }

    @Test(timeout = 1000)
    // @ScoringWeight(.01)
    public void testGetLinksNull() throws Exception {
        boolean getException = false;
        try {
            ps.getLinks(null);
        }
        catch (ParseException e) {
            getException = true;
        }
        assertTrue(".getLinks() should throw a ParseException, while we pass a null document in.", getException);
    }

    @Test(timeout = 5000)
    // @ScoringWeight(.01)
    public void testGetLinks() throws Exception {

        Document d = ps.getDocument("https://www.cs.purdue.edu/");
        assertFalse("Attempt to get the document from https://www.cs.purdue.edu/, but " +
                ".getDocument() returns a null.", d == null);

        Elements links = ps.getLinks(d);
        assertFalse("Attempt to .getLinks() from https://www.cs.purdue.edu/, but the returned null is not expected.",
                links == null);

        // We test this by checking if several links are existed in the returned links.
        ArrayList<String> linkArray = new ArrayList<String>();
        for (Element link: links) {
            linkArray.add(link.attr("abs:href"));
        }
        assertTrue("http://purdue.edu is expected to be founded in the returning links, but it is not.",
                linkArray.contains("http://purdue.edu"));
        assertTrue("https://www.cs.purdue.edu/about/welcome.html is expected to be founded in the returning links, but it is not.",
                linkArray.contains("https://www.cs.purdue.edu/about/welcome.html"));
        assertTrue("https://www.facebook.com/PurdueCS is expected to be founded in the returning links, but it is not.",
                linkArray.contains("https://www.facebook.com/PurdueCS"));
        assertTrue("https://twitter.com/PurdueCS is expected to be founded in the returning links, but it is not.",
                linkArray.contains("https://twitter.com/PurdueCS"));
        assertTrue("https://my.cs.purdue.edu/ is expected to be founded in the returning links, but it is not.",
                linkArray.contains("https://my.cs.purdue.edu/"));
    }

    @Test(timeout = 1000)
    // @ScoringWeight(.01)
    public void testGetBodyNull() throws Exception {
        boolean getException = false;
        try {
            ps.getBody(null);
        }
        catch (ParseException e) {
            getException = true;
        }
        assertTrue(".getBody() should throw a ParseException, while we pass a null document in.", getException);
    }

    @Test(timeout = 5000)
    // @ScoringWeight(.01)
    public void testGetBody() throws Exception {
        Document d = ps.getDocument("https://www.cs.purdue.edu/");
        assertFalse("Attempt to get the document from https://www.cs.purdue.edu/, but " +
                ".getDocument() returns a null.", d == null);

        // We test this by checking if some sentences are in the returned body.
        String body = ps.getBody(d);
        assertTrue(".getBody(d) should not return null, while we pass a valid document.", body != null);

        String s = "Purdue CS Facebook Purdue CS Twitter CS";
        assertTrue("Substring \"" + s + "\" should be found in the " +
                        "body of https://www.cs.purdue.edu/",
                body.contains(s));

        s = "Events Internships Graduation Survey Careers Resources Business Office Campus Map Facilities FTP";
        assertTrue("Substring \"" + s + "\" should be found in the " +
                        "body of https://www.cs.purdue.edu/",
                body.contains(s));

        s = "Alumni Distinguished Alumnus Past Distinguished Alumni Outstanding Alumni Past Outstanding Alumni Keep in Touch";
        assertTrue("Substring \"" + s + "\" should be found in the " +
                        "body of https://www.cs.purdue.edu/",
                body.contains(s));
    }
}
