import java.io.*;
import java.util.List;

/**
 * Created by jay on 3/30/17.
 */
public class FileUtils {
    public FileUtils() {
    }

    public void writeObj(Object obj,
                         String filePath) throws IOException {
        File f = new File(filePath);
        FileOutputStream fos = null;
        ObjectOutputStream oos = null;


        fos = new FileOutputStream(f);
        oos = new ObjectOutputStream(fos);
        oos.writeObject(obj);
        fos.close();
        oos.close();


    }

    public boolean saveWordTable(List<Word> wordTable,
                                 String filePath) {
        try {
            writeObj(wordTable, filePath);
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }

    }

    public boolean savePageTable(List<Page> pageTable,
                                 String filePath) {
        try {
            writeObj(pageTable, filePath);
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }

    }

    public List<Word> getWordList(String filePath) {
        try {
            FileInputStream fis = new FileInputStream(new File(filePath));
            ObjectInputStream ois = new ObjectInputStream(fis);
            List<Word> result = (List<Word>) ois.readObject();
            ois.close();
            fis.close();
            return result;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            return null;
        }


    }

    public List<Page> getPageList(String filePath) {
        try {
            FileInputStream fis = new FileInputStream(new File(filePath));
            ObjectInputStream ois = new ObjectInputStream(fis);
            List<Page> result = (List<Page>) ois.readObject();
            ois.close();
            fis.close();
            return result;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }
}
