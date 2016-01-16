package devos;
import java.io.Serializable;

public class ChatItem implements Serializable
{
	private static final long serialVersionUID = 1L;
	private String pseudoEmmeteur;
    private String message;
    
    public ChatItem(String pseudoEmmeteur, String message)
    {
        this.pseudoEmmeteur=pseudoEmmeteur;
        this.message=message;
    }
    
    
    public String getPseudoEmmeteur() {
		return pseudoEmmeteur;
	}


	public String getMessage() {
		return message;
	}


	public String toString() {
    	return pseudoEmmeteur  + ": " + message;
     }

}
