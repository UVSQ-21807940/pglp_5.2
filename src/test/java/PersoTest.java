import java.time.LocalDate;
import java.util.ArrayList;

import org.junit.Test;

import exo52.Personnel;

public class PersoTest {
	
	@Test
	public void test() {
		ArrayList<String> numTel = new ArrayList<String>();
    	numTel.add("0758520591");
    	numTel.add("0556922335");
        Personnel p = new Personnel.Builder("hadj said", "mohand", LocalDate.of(1995, 06, 28), numTel).build();
        p.print();
	}
	
	
	


}
