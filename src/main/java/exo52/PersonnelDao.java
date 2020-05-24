package exo52;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Map;

public class PersonnelDao implements Dao<Personnel>, Serializable {
    
    private static final long serialVersionUID = 1L;
    
    private ArrayList<Personnel> personnels;
    
    public PersonnelDao() {
        this.personnels = new ArrayList<Personnel>();
    }
    
    public Personnel get(final int id) {
        for (Personnel pers : personnels) {
            if (pers.getId() == id) {
                return pers;
            }
        }
        return null;
    }
    
    @SuppressWarnings("unchecked")
    public ArrayList<Personnel> getAll() {
        return (ArrayList<Personnel>) personnels.clone();
    }
   
    public void ajouter(final Personnel pers) {
        personnels.add(pers);
    }
   
    @SuppressWarnings("unchecked")
    public void update(final Personnel pers,
            final Map<String, Object> params) {
        if (personnels.remove(pers)) {
            Personnel nouveau;
            String nom;
            String prenom;
            LocalDate date;
            ArrayList<String> numTel;
            if (params.containsKey("nom")) {
                nom = (String) params.get("nom");
            } else {
                nom = pers.getNom();
            }
            if (params.containsKey("prenom")) {
                prenom = (String) params.get("prenom");
            } else {
                prenom = pers.getPrenom();
            }
            if (params.containsKey("dateNaissance")) {
                date = (LocalDate) params.get("dateNaissance");
            } else {
                date = pers.getDateNaissance();
            }
            if (params.containsKey("numTel")) {
                numTel = (ArrayList<String>) params.get("numTel");
            } else {
                numTel = pers.getNumTel();
            }
            nouveau = new Personnel
                    .Builder(nom, prenom, date, numTel).build();
            personnels.add(nouveau);
        }
    }
    
    public void retirer(final Personnel pers) {
        personnels.remove(pers);
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
    
    public static PersonnelDao deSerialization(final String path)
            throws ClassNotFoundException {
        ObjectInputStream ois = null;
        PersonnelDao p = null;
        try {
            final FileInputStream fichierIn = new FileInputStream(path);
            ois = new ObjectInputStream(fichierIn);
            p = (PersonnelDao) ois.readObject();
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
        return p;
    }
}
