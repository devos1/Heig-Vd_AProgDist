import java.io.Serializable;

public class Balloon implements Serializable {

	private static final long serialVersionUID = 1L;
	private int rayon;

	public Balloon(int rayon) {
		this.rayon = rayon;
	}
	
	public int getRayon() {
		return rayon;
	}

	public void changeSize(int change) {
    	rayon += change;
    }
	
	@Override
	public String toString() {
		return "Rayon balloon : " + this.rayon;
	}


}
