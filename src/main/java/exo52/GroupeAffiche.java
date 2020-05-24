package exo52;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayDeque;
import java.util.Iterator;

public class GroupeAffiche
implements Iterable<PerType>, Serializable {
    
    private static final long serialVersionUID = 1L;
   
    private ArrayDeque<PerType> file =
            new ArrayDeque<PerType>();
    
    private final int id;
   
    private static int idFin = 1;
    
    public int getId() {
        return this.id;
    }
    
    public final ArrayDeque<PerType> getList() {
        return this.file.clone();
    }
    
    public GroupeAffiche() {
        id = idFin++;
        file = new ArrayDeque<PerType>();
    }
    
    public Iterator<PerType> iterator() {
        return file.iterator();
    }
    
    public void add(final PerType intP) {
        file.add(intP);
    }
    
    public void remove(final PerType intP) {
        file.remove(intP);
    }
    
    public void parcoursLargeur(final PerType ip) {
        file = new ArrayDeque<PerType>();
        ArrayDeque<PerType> file2 =
                new ArrayDeque<PerType>();
        PerType i;
        file2.add(ip);
        while (!file2.isEmpty()) {
            i = file2.pollFirst();
            file.add(i);
            if (i.getClass() == CompositePersonnel.class) {
                CompositePersonnel cp = (CompositePersonnel) i;
                Iterator<PerType> it =
                        cp.iterator();
                while (it.hasNext()) {
                    i = it.next();
                    if (!file2.contains(i)
                            && !file.contains(i)) {
                        file2.add(i);
                    }
                }
            }
        }
    }
    
    public void print() {
        for (PerType ip2 : file) {
            if (ip2.getClass() == CompositePersonnel.class) {
                CompositePersonnel cp2 =
                        (CompositePersonnel) ip2;
                System.out.println("Id : " + cp2.getId());
            } else {
                ip2.print();
            }
        }
    }
    
    public String toString() {
        String s = "";
        for (PerType ip2 : file) {
            if (ip2.getClass() == CompositePersonnel.class) {
                s += ((CompositePersonnel) ip2).getId() + "\n";
            } else {
                s += ip2.toString();
            }
        }
        return s + "\n";
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
    
    public static GroupeAffiche deSerialization(final String path)
            throws ClassNotFoundException {
        ObjectInputStream ois = null;
        GroupeAffiche apg = null;
        try {
            final FileInputStream fichierIn = new FileInputStream(path);
            ois = new ObjectInputStream(fichierIn);
            apg = (GroupeAffiche) ois.readObject();
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
        return apg;
    }
}
