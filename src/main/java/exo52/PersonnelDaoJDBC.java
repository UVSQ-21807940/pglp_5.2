package exo52;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;

public class PersonnelDaoJDBC extends AbstractDao<Personnel> {
   
    public PersonnelDaoJDBC(final Connection c) {
        super(c);
    }
    
    private void createNumTel(final String numTel, final int idP)
            throws SQLException {
        final int un = 1;
        final int deux = 2;
        PreparedStatement prepare = connect.prepareStatement(
                "INSERT INTO NumeroTelephone (NumTel, IdP) "
                + "VALUES (?,?)");
        prepare.setString(un, numTel);
        prepare.setInt(deux, idP);
        prepare.executeUpdate();
    }
   
    private ArrayList<String> findNumTel(final int idP) throws SQLException {
        final int un = 1;
        ArrayList<String> numTel = new ArrayList<String>();
        PreparedStatement prepare = connect.prepareStatement(
                "SELECT NumTel FROM NumeroTelephone WHERE IdP = ?");
        prepare.setInt(un, idP);
        ResultSet result = prepare.executeQuery();
        while (result.next()) {
            numTel.add(result.getString("NumTel"));
        }
        return numTel;
    }
  
    private void updateNumTel(final int idP, final ArrayList<String> numTel)
            throws SQLException {
        deleteAllNumTel(idP);
        for (String num : numTel) {
            createNumTel(num, idP);
        }
    }
   
    private void deleteAllNumTel(final int idP) throws SQLException {
        final int un = 1;
        PreparedStatement prepare = connect.prepareStatement(
                "DELETE FROM NumeroTelephone WHERE IdP = ?");
        prepare.setInt(un, idP);
        prepare.executeUpdate();
    }
    
    private void deleteGroupePersonnel(final int idP) throws SQLException {
        final int un = 1;
        PreparedStatement prepare = connect.prepareStatement(
                "DELETE FROM GroupePersonnel WHERE IdP = ?");
        prepare.setInt(un, idP);
        prepare.executeUpdate();
    }
    
    @Override
    public Personnel create(final Personnel pers) {
        try {
            final int un = 1;
            final int deux = 2;
            final int trois = 3;
            final int quatre = 4;
            PreparedStatement prepare = connect.prepareStatement(
                    "INSERT INTO Personnel (Id,Nom,Prenom,"
                    + "DateNaissance)"
                    + "VALUES (?,?,?,?)");
            prepare.setInt(un, pers.getId());
            prepare.setString(deux, pers.getNom());
            prepare.setString(trois, pers.getPrenom());
            Date date = Date.valueOf(pers.getDateNaissance());
            prepare.setDate(quatre, date);
            int result = prepare.executeUpdate();
            assert result == un;
            for (String num : pers.getNumTel()) {
                createNumTel(num, pers.getId());
            }
        } catch (SQLException e) {
            e.printStackTrace();
            this.delete(pers);
        }
        return pers;
    }
    /**
     * Retourne le personnel recherché.
     * @param id L'identifiant du personnel
     * @return Le personnel trouvé
     */
    @Override
    public Personnel find(final int id) {
        Personnel p = null;
        try {
            final int un = 1;
            PreparedStatement prepare = connect.prepareStatement(
                    "SELECT * FROM Personnel WHERE Id = ?");
            prepare.setInt(un, id);
            ResultSet result = prepare.executeQuery();
            if (result.next()) {
                LocalDate date =
                        result.getDate("DateNaissance").toLocalDate();
                p = new Personnel.Builder(
                        result.getString("Nom"),
                        result.getString("Prenom"),
                        date,
                        findNumTel(id)
                        ).build();
                p.setId(id);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
        return p;
    }
   
    @Override
    public Personnel update(final Personnel pers) {
        Personnel pers2 = this.find(pers.getId());
        if (pers2 != null) {
            try {
                final int un = 1;
                final int deux = 2;
                final int trois = 3;
                final int quatre = 4;
                PreparedStatement prepare = connect.prepareStatement(
                        "UPDATE Personnel SET Nom = ?, Prenom = ?,"
                        + "DateNaissance = ? WHERE Id = ?");
                prepare.setString(un, pers.getNom());
                prepare.setString(deux, pers.getPrenom());
                Date date = Date.valueOf(pers.getDateNaissance());
                prepare.setDate(trois, date);
                prepare.setInt(quatre, pers.getId());
                int result = prepare.executeUpdate();
                assert result == 1;
                this.updateNumTel(pers.getId(), pers.getNumTel());
            } catch (SQLException e) {
                e.printStackTrace();
                this.delete(pers);
                this.create(pers2);
            }
        } else {
            return pers2;
        }
        return pers;
    }
    
    @Override
    public void delete(final Personnel pers) {
        final int un = 1;
        try {
            this.deleteGroupePersonnel(pers.getId());
            this.deleteAllNumTel(pers.getId());
            PreparedStatement prepare = connect.prepareStatement(
                    "DELETE FROM Personnel WHERE Id = ?");
            prepare.setInt(1, pers.getId());
            int result = prepare.executeUpdate();
            assert result == un;
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
