package exo52;

public final class FactoryDao extends AbstractDaoFactory {
   
    public FactoryDao() {
    }
   
    public static Dao<Personnel> getPersonnelDao() {
        return new PersonnelDao();
    }
    
    public static Dao<CompositePersonnel> getCompositePersonnelDao() {
        return new CompositePersonnelDao();
    }
    
    public static Dao<Personnel> getPersonnelDao(final String deserialize)
            throws ClassNotFoundException {
        return PersonnelDao.deSerialization(deserialize);
    }
    
    public static Dao<CompositePersonnel> getCompositePersonnelDao(
            final String deserialize) throws ClassNotFoundException {
        return CompositePersonnelDao.deSerialization(deserialize);
    }
}
