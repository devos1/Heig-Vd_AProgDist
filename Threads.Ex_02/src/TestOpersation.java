import static org.junit.Assert.*;

import org.junit.Test;

public class TestOpersation {
	
	// CREATION DES 2 COMPTES AVEC UN MONTANT INITIAL DE 5
	Compte cptA = new Compte("A", 5);
	Compte cptB = new Compte("B", 5);
	
	@Test
	public void testSynchro() throws InterruptedException{
		for (int i = 0; i < 10; i++) {
			Transferts t = new Transferts(cptA, cptB, null, 1000, true);
			Thread th = new Thread(t);
			th.start();
			System.out.println("Total montant => " + (cptA.get_montant() + cptB.get_montant()));
		}		
		assertEquals(10, cptA.get_montant() + cptB.get_montant());
	}

}
