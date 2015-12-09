import junit.framework.Test;

/*
 * COMMENATAIRES :
 * - Enum Opertation pas utilisée
 * 
 */
public class Transferts implements Runnable {
	
	// VARIABLES
	private Compte _cptSrc;
	private Compte _cptDest;
	private Operation _op;
	private int _nbTransferts;
	private boolean _protege;
	private int _montant;	// Entre 1 et 5.-
	private TestOpersation _t;
	
	// CONSTRUCTEUR
	public Transferts(Compte cptSrc, Compte cptDest, Operation op, int nbTrasnferts, boolean protege, TestOpersation t){
		_cptSrc = cptSrc;
		_cptDest = cptDest;
		_op = op;
		_nbTransferts = nbTrasnferts;
		_protege = protege;
		_t = t;
	}

	@Override
	public void run() {
		for (int i = 0; i < _nbTransferts; i++) {				
			synchronized (_t) {
				_montant = (int)(Math.random() * ((5 - 1) + 1)) + 1;
				
				if (i % 2 == 0) {
					
					if (_cptSrc.Debit(_montant)) {
						_cptDest.Credit(_montant);
					}
					//System.out.println("A vers B => " + _montant);
					
					//System.out.println("B a reçu de A => " + _montant);
				}else {
					if (_cptDest.Debit(_montant)) {
						_cptSrc.Credit(_montant);
					}					
					//System.out.println("B vers A => " + _montant);
					
					//System.out.println("A a reçu de B => " + _montant);
					}
			}
			
/*			if (i % 2 == 0) {
				if (_protege) {
					synchronized(_cptSrc){
						_cptSrc.Debit(_montant);
						synchronized (_cptDest) {
							_cptDest.Credit(_montant);
						}
					}
				}else {
					_cptSrc.Debit(_montant);
					_cptDest.Credit(_montant);
				}
			}else {
				if (_protege) {
					synchronized(_cptDest){
						_cptDest.Debit(_montant);
						synchronized (_cptDest) {
							_cptSrc.Credit(_montant);
						}
					}
				}else {
					_cptSrc.Debit(_montant);
					_cptDest.Credit(_montant);
				}
			}*/
				
		}
		
	}
	
	
	
}
