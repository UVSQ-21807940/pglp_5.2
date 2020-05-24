package exo52;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import exo52.AbstractDaoFactory.DaoType;

public class CompositePersonnelDaoJDBC
extends AbstractDao<CompositePersonnel> {
    
    public CompositePersonnelDaoJDBC(final Connection c) {
        super(c);
    }
    
    private void createGroupeComposite(final int idCp, final int idCp2)
            throws SQLException {
        final int un = 1;
        final int deux = 2;
        PreparedStatement prepare = connect.prepareStatement(
                "INSERT INTO GroupeComposite (IdCp, IdCp2) "
                + "VALUES (?,?)");
        prepare.setInt(un, idCp);
        prepare.setInt(deux, idCp2);
        prepare.executeUpdate();
    }
    
    private void createGroupePersonnel(final int idCp, final int idP)
            throws SQLException {
        final int un = 1;
        final int deux = 2;
        PreparedStatement prepare = connect.prepareStatement(
                "INSERT INTO GroupePersonnel (IdCp, IdP) "
                + "VALUES (?,?)");
        prepare.setInt(un, idCp);
        prepare.setInt(deux, idP);
        prepare.executeUpdate();
    }
    
    private ArrayList<PerType> findGroupeComposite(
            final int idCp) throws SQLException {
        ArrayList<PerType> list
        = new ArrayList<PerType>();
        PreparedStatement prepare = connect.prepareStatement(
                "SELECT IdCp2 FROM GroupeComposite "
                + "WHERE IdCp = ?");
        prepare.setInt(1, idCp);
        ResultSet result = prepare.executeQuery();
        while (result.next()) {
            list.add(this.find(result.getInt("IdCp2")));
        }
        return list;
    }
  
    @SuppressWarnings("static-access")
    private ArrayList<PerType> findGroupePersonnel(
            final int idCp) throws SQLException {
        ArrayList<PerType> list
        = new ArrayList<PerType>();
        PreparedStatement prepare = connect.prepareStatement(
                "SELECT IdP FROM GroupePersonnel "
                + "WHERE IdCp = ?");
        prepare.setInt(1, idCp);
        ResultSet result = prepare.executeQuery();
        while (result.next()) {
            FactoryDaoJDBC factory = (FactoryDaoJDBC)
                    AbstractDaoFactory.getFactory(DaoType.JDBC);
            AbstractDao<Personnel> daoPers = factory.getPersonnelDao();
            Personnel p = daoPers.find(result.getInt("IdP"));
            if (p != null) {
                list.add(p);
            }
        }
        return list;
    }
    
    private ArrayList<PerType> findGroupePersonnelComposite(
            final int idCp) throws SQLException {
        ArrayList<PerType> list = findGroupeComposite(idCp);
        ArrayList<PerType> list2 = findGroupePersonnel(idCp);
        for (PerType ip : list2) {
            list.add(ip);
        }
        return list;
    }
    
    private void deleteGroupePersonnelComposite(final int idCp)
            throws SQLException {
        PreparedStatement prepare = connect.prepareStatement(
                "DELETE FROM GroupePersonnel WHERE IdCp = ?");
        prepare.setInt(1, idCp);
        prepare.executeUpdate();
        PreparedStatement prepare2 = connect.prepareStatement(
                "DELETE FROM GroupeComposite WHERE IdCp = ?");
        prepare2.setInt(1, idCp);
        prepare2.executeUpdate();
    }
   
    @SuppressWarnings("static-access")
    @Override
    public CompositePersonnel create(final CompositePersonnel cpers) {
        final int un = 1;
        try {
            PreparedStatement prepare = connect.prepareStatement(
                    "INSERT INTO CompositePersonnel (Id)"
                    + "VALUES (?)");
            prepare.setInt(1, cpers.getId());
            int result = prepare.executeUpdate();
            assert result == un;
            for (PerType ip : cpers.getList()) {
                if (ip.getClass() == CompositePersonnel.class) {
                    CompositePersonnel cp = (CompositePersonnel) ip;
                    if (this.find(cp.getId()) == null) {
                        this.create(cp);
                    }
                    this.createGroupeComposite(cpers.getId(), cp.getId());
                } else {
                    FactoryDaoJDBC factory = (FactoryDaoJDBC)
                            AbstractDaoFactory.getFactory(DaoType.JDBC);
                    AbstractDao<Personnel> daoPers = factory.getPersonnelDao();
                    Personnel pers = (Personnel) ip;
                    if (daoPers.find(pers.getId()) == null) {
                        daoPers.create(pers);
                    }
                    this.createGroupePersonnel(cpers.getId(), pers.getId());
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            this.delete(cpers);
        }
        return cpers;
    }
    
    @Override
    public CompositePersonnel find(final int id) {
        CompositePersonnel cp = null;
        try {
            PreparedStatement prepare = connect.prepareStatement(
                    "SELECT * FROM CompositePersonnel WHERE id = ?");
            prepare.setInt(1, id);
            ResultSet result = prepare.executeQuery();
            if (result.next()) {
                cp = new CompositePersonnel();
                cp.setId(id);
                ArrayList<PerType> liste
                = this.findGroupePersonnelComposite(id);
                for (PerType ip : liste) {
                    cp.add(ip);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
        return cp;
    }
   
    @Override
    public CompositePersonnel update(final CompositePersonnel cpers) {
        CompositePersonnel save = this.find(cpers.getId());
        if (save != null) {
            this.delete(save);
            this.create(cpers);
            if (this.find(cpers.getId()) == null) {
                this.create(save);
            }
            return cpers;
        } else {
            return save;
        }
    }
   
    @Override
    public void delete(final CompositePersonnel cpers) {
        try {
            this.deleteGroupePersonnelComposite(cpers.getId());
            PreparedStatement prepare = connect.prepareStatement(
                    "DELETE FROM CompositePersonnel WHERE id = ?");
            prepare.setInt(1,  cpers.getId());
            int result = prepare.executeUpdate();
            assert result == 1;
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
