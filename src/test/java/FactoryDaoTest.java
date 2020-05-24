

import static org.junit.Assert.*;

import java.io.File;
import java.time.LocalDate;
import java.util.ArrayList;

import org.junit.Test;

import exo52.CompositePersonnel;
import exo52.CompositePersonnelDao;
import exo52.Dao;
import exo52.FactoryDao;
import exo52.Personnel;
import exo52.PersonnelDao;

public class FactoryDaoTest {

	@Test
    public void testPersonnelDao() {
        Dao<Personnel> dao = FactoryDao.getPersonnelDao();
        assertTrue(dao.getAll().isEmpty());
    }
	
	@Test
    public void testCompositePersonnelDao() {
        Dao<CompositePersonnel> dao = FactoryDao.getCompositePersonnelDao();
        assertTrue(dao.getAll().isEmpty());
    }
	
	@Test
    public void testPersonnelDaoExistant() throws ClassNotFoundException {
		ArrayList<String> numTel = new ArrayList<String>();
        PersonnelDao persDao = (PersonnelDao) FactoryDao.getPersonnelDao();
    	numTel.add("0123456789");
    	numTel.add("0987654321");
        Personnel p = new Personnel.Builder("Daoulas", "Priscille", LocalDate.of(1996, 05, 23), numTel).build();
        persDao.ajouter(p);
        persDao.serialization("persDao");
        PersonnelDao persDao1 = (PersonnelDao) FactoryDao.getPersonnelDao("persDao");
        
        PersonnelDao persDao2 = PersonnelDao.deSerialization("persDao");
        File f = new File("persDao");
        f.delete();
        assertEquals(persDao1.getAll().toString(),persDao2.getAll().toString());
	}
	
	@Test
    public void testCompositePersonnelExistant() throws ClassNotFoundException {
        CompositePersonnelDao CpersDao = (CompositePersonnelDao) FactoryDao.getCompositePersonnelDao();
        CompositePersonnel cp = new CompositePersonnel();
        ArrayList<String> numTel = new ArrayList<String>();
    	numTel.add("0123456789");
    	numTel.add("0987654321");
        Personnel p = new Personnel.Builder("Daoulas", "Priscille", LocalDate.of(1996, 05, 23), numTel).build();
        cp.add(p);
        CpersDao.ajouter(cp);
        CpersDao.serialization("CpersDao");
        CompositePersonnelDao CpersDao1 = (CompositePersonnelDao) FactoryDao.getCompositePersonnelDao("CpersDao");
        
        CompositePersonnelDao CpersDao2 = CompositePersonnelDao.deSerialization("CpersDao");
        File f = new File("CpersDao");
        f.delete();
        assertEquals(CpersDao1.getAll().toString(),CpersDao2.getAll().toString());
	}
}
