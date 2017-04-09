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

        for (int i = start; i < finish; i++) {
            if(Search.wordList.get(i).getWord().contains(term)){
                return Search.wordList.get(i);
            }
        }
        return null;
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
