package exo52;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;

public final class Personnel implements PerType, Serializable {
    
    private static final long serialVersionUID = 1L;
    
    private int id;
    
    private static int idFin = 1;
    
    private final String nom;
   
    private final String prenom;
   
    private final java.time.LocalDate dateNaissance;
    
    private final ArrayList<String> numTel;
    
    public int getId() {
        return this.id;
    }
    
    public void setId(final int identifiant) {
        this.id = identifiant;
    }
   
    public String getNom() {
        return this.nom;
    }
   
    public String getPrenom() {
        return this.prenom;
    }
  
    public LocalDate getDateNaissance() {
        return this.dateNaissance;
    }
    
    @SuppressWarnings("unchecked")
    public ArrayList<String> getNumTel() {
        return (ArrayList<String>) this.numTel.clone();
    }
   
    public static class Builder {
        
        private final String nom;
        
        private final String prenom;
       
        private final java.time.LocalDate dateNaissance;
        
        private final ArrayList<String> numTel;
        
        public Builder(final String n, final String p,
                final java.time.LocalDate date,
                final ArrayList<String> num) {
            this.nom = n;
            this.prenom = p;
            this.dateNaissance = date;
            if (num != null) {
                this.numTel = num;
            } else {
                this.numTel = new ArrayList<String>();
            }
        }
       
        public Personnel build() {
            return new Personnel(this);
        }
    }
    
    public Personnel(final Builder builder) {
        id = idFin++;
        nom = builder.nom;
        prenom = builder.prenom;
        dateNaissance = builder.dateNaissance;
        numTel = builder.numTel;
    }
    
    public void print() {
        System.out.println("Nom : " + nom + ", Prenom : " + prenom
                + ", Date de Naissance : " + dateNaissance
                + ", Numero(s) de telephone : " + numTel);
    }
    
    public String toString() {
        return "Nom : " + nom + ", Prenom : " + prenom
                + ", Date de Naissance : " + dateNaissance
                + ", Numero(s) de telephone : " + numTel + "\n";
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
   
    public static Personnel deSerialization(final String path)
            throws ClassNotFoundException {
        ObjectInputStream ois = null;
        Personnel p = null;
        try {
            final FileInputStream fichierIn = new FileInputStream(path);
            ois = new ObjectInputStream(fichierIn);
            p = (Personnel) ois.readObject();
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
