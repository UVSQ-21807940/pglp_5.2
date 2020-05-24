

import static org.junit.Assert.*;

import org.junit.Test;

import exo52.CompositePersonnel;
import exo52.CompositePersonnelDao;
import exo52.Personnel;

public class ComDaoTest {

	@Test
	public void testAjouter() {
		CompositePersonnelDao cpersonnels = new CompositePersonnelDao();
		CompositePersonnel cp = new CompositePersonnel();
		Personnel p = new Personnel.Builder("hadj said","mSohand",java.time.LocalDate.of(1995, 06, 28),null).build();
		cp.add(p);
		cpersonnels.ajouter(cp);
        assertEquals(cpersonnels.get(cp.getId()), cp);
	}
	
	@Test
	public void testRetirer() {
		CompositePersonnelDao cpersonnels = new CompositePersonnelDao();
		CompositePersonnel cp = new CompositePersonnel();
		Personnel p = new Personnel.Builder("hadj said","mohand",java.time.LocalDate.of(1995, 06, 28),null).build();
		cp.add(p);
		cpersonnels.ajouter(cp);
		cpersonnels.retirer(cp);
        assertEquals(cpersonnels.get(cp.getId()), null);
	}
	

	
	
	
	
}
