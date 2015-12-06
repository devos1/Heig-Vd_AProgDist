
public class Compte {
	private String _titulaire;
	private int _montant;
	
	public Compte(String titulaire, int montantInit){
		_titulaire = titulaire;
		_montant = montantInit;
	}
	
	public boolean Debit(int valeur){		
		if (_montant - valeur >= 0) {
			_montant -= valeur;
			//System.out.println("Opération effectuée avec succés");
			return true;
		}else{
			//System.out.println("Montant insuffisant, opération annulée");
			return false;
		}
	}
	
	public boolean Credit(int valeur){
		_montant += valeur;
		//System.out.println("Le montant a été crédité avec succés");
		return true;
	}

	/* 
	 * Affichage du solde pour ce compte
	 * Exemple = Solde compte A : 5.-
	 */
	@Override
	public String toString() {
		return "Solde compte " + _titulaire + " : " + _montant + ".-";
	}

	//------------------------------------------------------------------
	// GETTERS & SETTERS
	//------------------------------------------------------------------
	/**
	 * @return the _montant
	 */
	public int get_montant() {
		return _montant;
	}
	
	
	
}
