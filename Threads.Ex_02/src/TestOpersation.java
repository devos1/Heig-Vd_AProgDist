import static org.junit.Assert.*;

import org.junit.Test;

public class TestOpersation {
	
	// CREATION DES 2 COMPTES AVEC UN MONTANT INITIAL DE 5
	Compte cptA = new Compte("A", 5);
	Compte cptB = new Compte("B", 5);
	final int nb = 1000;
	Thread thArray[] = new Thread[nb];
	
	@Test
	public void testSynchro() throws InterruptedException{
		for (int i = 0; i < nb; i++) {
			Transferts t = new Transferts(cptA, cptB, null, nb, true, this);
			Thread th = new Thread(t);
			thArray[i] = th;			
			th.start();
			System.out.println("Total montant => " + (cptA.get_montant() + cptB.get_montant()));
		}		
		for (int i = 0; i < nb; i++) {
			thArray[i].join();
		}
		
		assertEquals(10, cptA.get_montant() + cptB.get_montant());
	}

}
