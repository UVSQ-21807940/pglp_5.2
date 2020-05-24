package exo52;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Map;

public class CompositePersonnelDao
implements Dao<CompositePersonnel>, Serializable {
    
    private static final long serialVersionUID = 1L;
    
    private ArrayList<CompositePersonnel> compositePersonnels;
    
    public CompositePersonnelDao() {
        this.compositePersonnels = new ArrayList<CompositePersonnel>();
    }
   
    public CompositePersonnel get(final int id) {
        for (CompositePersonnel cp : compositePersonnels) {
            if (cp.getId() == id) {
                return cp;
            }
        }
        return null;
    }
  
    @SuppressWarnings("unchecked")
    public ArrayList<CompositePersonnel> getAll() {
        return (ArrayList<CompositePersonnel>)
                compositePersonnels.clone();
    }
   
    public void ajouter(final CompositePersonnel cp) {
        compositePersonnels.add(cp);
    }
   
    @SuppressWarnings("unchecked")
    public void update(final CompositePersonnel cp,
            final Map<String, Object> params) {
        if (params.containsKey("list")) {
            while (!cp.getList().isEmpty()) {
                cp.remove(cp.getList().get(0));
            }
            ArrayList<PerType> nouvelle =
                    (ArrayList<PerType>)
                    params.get("list");
            for (PerType ip : nouvelle) {
                cp.add(ip);
            }
        }
    }
   
    public void retirer(final CompositePersonnel cp) {
        compositePersonnels.remove(cp);
    }
  
    public void serialization(final String path) {
        ObjectOutputStream oos = null;
        try {
            final FileOutputStream fichierOut = new FileOutputStream(path);
            oos = new ObjectOutputStream(fichierOut);
            oos.writeObject(this);
            oos.flush();
            oos.close();
        } catch (final java.io.IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (oos != null) {
                    oos.flush();
                    oos.close();
                }
            } catch (final java.io.IOException ex) {
                ex.printStackTrace();
            }
        }
    }
   
    public static CompositePersonnelDao deSerialization(final String path)
            throws ClassNotFoundException {
        ObjectInputStream ois = null;
        CompositePersonnelDao cp = null;
        try {
            final FileInputStream fichierIn = new FileInputStream(path);
            ois = new ObjectInputStream(fichierIn);
            cp = (CompositePersonnelDao) ois.readObject();
        } catch (final java.io.IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (ois != null) {
                    ois.close();
                }
            } catch (final java.io.IOException ex) {
                ex.printStackTrace();
            }
        }
        return cp;
    }
}
