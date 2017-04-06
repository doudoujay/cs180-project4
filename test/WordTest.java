import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

public class WordTest {
    Word w;

    static String testWord = new String("hello");
    static int testUrlId = 1;

    @Before
    public void setUp() throws Exception {
        w = new Word(testWord, testUrlId);
    }

    @Test(timeout = 1000)
    public void testSerialVersionUID() {
        assertTrue("Failed to implement correct serialVersionUID. Should be -3696191086353573895L", -3696191086353573895L == w.serialVersionUID);
    }

    @Test(timeout = 1000)
    // @ScoringWeight(.01)
    public void testGetWord() throws Exception {
        assertTrue("Failed to get the word initialized by the constructor.", testWord.equals(w.getWord()));
    }

    // ToDo: see if we need this.
//    @Test(timeout = 1000)
//    // @ScoringWeight(.01)
//    public void testGetCount() throws Exception {
//        assertTrue("Failed to get the word count initialized by the constructor.", w.getCount() == 1);
//    }

    @Test(timeout = 1000)
    // @ScoringWeight(.01)
    public void testGetList() throws Exception {
        List<Integer> li = w.getList();
        assertFalse("The returned list should NOT be null.", li == null);
        assertTrue("The returned list size should be 1 after the object is constructed.", li.size() == 1);
        assertTrue("The first entry in the returned list should be equal to the URLID given in the constructor " +
                "arguements.", li.get(0).intValue() == testUrlId);
    }

    @Test(timeout = 1000)
    // @ScoringWeight(.01)
    public void testAddURLID() throws Exception {
        List<Integer> li = w.getList();
        assertFalse("Before calling .addURLID(), the .getList() should NOT return null.", li == null);
        assertTrue("Before calling .addURLID(), the .getList() should return the list with size 1.",
                li.size() == 1);
        assertTrue("Before calling .addURLID(), the first entry in the list returned by .getList() should be equal to" +
                " the URLID given in the constructor arguements.", li.get(0).intValue() == testUrlId);

        w.addURLID(2);
        assertFalse("After calling .addURLID(2), the .getList() should NOT return null.", li == null);
        assertTrue("After calling .addURLID(2), the .getList() should return the list with size 2.",
                li.size() == 2);
        assertTrue("After calling .addURLID(2), the second entry in the list returned by .getList() should be equal " +
                "to 2", li.get(1).intValue() == 2);
    }

    @Test(timeout = 1000)
    // @ScoringWeight(.01)
    public void testEquals() throws Exception {
        assertTrue("Two Word objects should be equal if the word strings given in their constructors are " +
                "the same." , w.equals(new Word(testWord, 100)));
        assertFalse("Two Word objects should NOT be equal if the word strings given in their constructors are " +
                "different." , w.equals(new Word(testWord+"aaa", 100)));
    }
}