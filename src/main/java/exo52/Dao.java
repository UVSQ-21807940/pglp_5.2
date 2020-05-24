package exo52;

import java.util.ArrayList;
import java.util.Map;

public interface Dao<T> {
    
    T get(int id);
    
    ArrayList<T> getAll();
   
    void ajouter(T t);
    
    void update(T t, Map<String, Object> params);
    
    void retirer(T t);
}
