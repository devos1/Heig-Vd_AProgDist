package devos;

import java.io.Serializable;

public class Fleur implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@Taille(10)
	private String variete;
	
	@Taille(6)
	private String couleur;
	
	@Taille(8)
	private String provenance;
	
	public Fleur(String v, String c, String p){
		variete = v;
		couleur = c;
		provenance = p;
	}
}
