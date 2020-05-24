package exo52;

import java.sql.Connection;

public class FactoryDaoJDBC extends AbstractDaoFactory {
    
    private static Connection connect;
    
    public FactoryDaoJDBC(final Connection c) {
        connect = c;
    }
   
    public static AbstractDao<Personnel> getPersonnelDao() {
        return new PersonnelDaoJDBC(connect);
    }
    
    public static AbstractDao<CompositePersonnel> getCompositePersonnelDao() {
        return new CompositePersonnelDaoJDBC(connect);
    }
}
