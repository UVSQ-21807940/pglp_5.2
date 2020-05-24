package exo52;


import java.sql.Connection;

public abstract class AbstractDao<T> {
    
    protected Connection connect;
    
    public AbstractDao(final Connection c) {
        connect = c;
    }
   
    public abstract T find(int id);
   
    public abstract T create(T t);
    
    public abstract T update(T t);
    
    public abstract void delete(T t);
}

