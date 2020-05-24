package exo52;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public abstract class dbConn {
   
    public static Connection createBase() throws SQLException {
        return DriverManager.getConnection(
                "jdbc:derby:DataPersonnel;create=true");
    }
    
    public static void deleteAllTables(final Connection connect)
            throws SQLException {
        Statement s = null;
        s = connect.createStatement();
        try {
            s.execute("DROP TABLE NumeroTelephone");
        } catch (SQLException e) {
        }
        try {
            s.execute("DROP TABLE GroupeComposite");
        } catch (SQLException e) {
        }
        try {
            s.execute("DROP TABLE GroupePersonnel");
        } catch (SQLException e) {
        }
        try {
            s.execute("DROP TABLE CompositePersonnel");
        } catch (SQLException e) {
        }
        try {
            s.execute("DROP TABLE Personnel");
        } catch (SQLException e) {
        }
    }
    
    public static void createAllTables(final Connection connect)
            throws SQLException {
        String pers = "CREATE TABLE Personnel ("
                + "Id int,"
                + "Nom varchar(30),"
                + "Prenom varchar(30),"
                + "DateNaissance date,"
                + "PRIMARY KEY (Id)"
                + ")";
        String cp = "CREATE TABLE CompositePersonnel ("
                + "Id int,"
                + "PRIMARY KEY (Id)"
                + ")";
        String numTel = "CREATE TABLE NumeroTelephone ("
                + "NumTel varchar(30),"
                + "IdP int,"
                + "PRIMARY KEY (NumTel),"
                + "FOREIGN KEY (IdP) REFERENCES Personnel (Id)"
                + ")";
        String groupePers = "CREATE TABLE GroupePersonnel ("
                + "IdCp int,"
                + "IdP int,"
                + "PRIMARY KEY (IdCp, IdP),"
                + "FOREIGN KEY (IdCp) REFERENCES CompositePersonnel (Id),"
                + "FOREIGN KEY (IdP) REFERENCES Personnel (Id)"
                + ")";
        String groupeCp = "CREATE TABLE GroupeComposite ("
                + "IdCp int,"
                + "IdCp2 int,"
                + "PRIMARY KEY (IdCp, IdCp2),"
                + "FOREIGN KEY (IdCp) REFERENCES CompositePersonnel (Id),"
                + "FOREIGN KEY (IdCp2) REFERENCES CompositePersonnel (Id)"
                + ")";
        Statement s = connect.createStatement();
        s.execute(pers);
        s.execute(cp);
        s.execute(numTel);
        s.execute(groupePers);
        s.execute(groupeCp);
    }
}
