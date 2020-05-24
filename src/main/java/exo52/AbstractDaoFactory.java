package exo52;


import java.sql.Connection;
import java.sql.SQLException;

public abstract class AbstractDaoFactory {
   
    public enum DaoType {
        
        CRUD,
        
        JDBC;
    }
    
    public static Object getFactory(final DaoType type) throws SQLException {
        Connection connect = dbConn.createBase();
        if (type == DaoType.CRUD) {
            return new FactoryDao();
        } else if (type == DaoType.JDBC) {
            return new FactoryDaoJDBC(connect);
        } else {
            return null;
        }
    }
}
