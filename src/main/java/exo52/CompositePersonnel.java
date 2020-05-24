package exo52;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;


public class CompositePersonnel
implements PerType, Iterable<PerType>, Serializable {
    
    private static final long serialVersionUID = 1L;
    
    private ArrayList<PerType> list =
            new ArrayList<PerType>();
    
    private int id;
    
    private static int idFin = 1;
    
    public final int getId() {
        return this.id;
    }
    
    public void setId(final int identifiant) {
        this.id = identifiant;
    }
    
    @SuppressWarnings("unchecked")
    public final ArrayList<PerType> getList() {
        return (ArrayList<PerType>) this.list.clone();
    }
   
    public CompositePersonnel() {
        id = idFin++;
        list = new ArrayList<PerType>();
    }
    
    public void print() {
        System.out.println("Id : " + id);
        for (PerType intP : list) {
            intP.print();
        }
    }
    
    public String toString() {
        String s = "Id : " + id + "\n";
        for (PerType intP : list) {
            s += intP.toString();
        }
        return s + "\n";
    }
    
    public void add(final PerType intP) {
        list.add(intP);
    }
    
   
    public void remove(final PerType intP) {
        list.remove(intP);
    }
   
    public Iterator<PerType> iterator() {
        return list.iterator();
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
   
    public static CompositePersonnel deSerialization(final String path)
            throws ClassNotFoundException {
        ObjectInputStream ois = null;
        CompositePersonnel cp = null;
        try {
            final FileInputStream fichierIn = new FileInputStream(path);
            ois = new ObjectInputStream(fichierIn);
            cp = (CompositePersonnel) ois.readObject();
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
