package IMDatabase;

import java.util.*;

public class Index {
    private Map<Object, List<Integer>> hashIndex= new HashMap<>();


    public void  addToHashIndex(Object key, Integer rowIndex) {
        hashIndex.computeIfAbsent(key, k-> new ArrayList<>()).add(rowIndex);
    }

    public List<Integer> getFromHashIndex(Object key) {
        return hashIndex.getOrDefault(key, new ArrayList<>());
    }

    public Set<Object> keySets(){
        return hashIndex.keySet();
    }

}
