package devos;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

/*
 * CLASSE POUR SERIALIZER UNE PREMIERE FOIS LES OBJETS 
 */
public class SaveObject {
	
	public SaveObject() throws IOException{
		
		// PERSON
		Person p = new Person("Oscar", 99);
		FileOutputStream fos = new FileOutputStream("person.csv");
		ObjectOutputStream oos = new ObjectOutputStream(fos);
		oos.writeObject(p);
		oos.close();
		fos.close();
		
		// FLEUR
		Fleur f = new Fleur("Tulipe", "Jaune", "Japon");
		FileOutputStream fosFleur = new FileOutputStream("fleur.csv");
		ObjectOutputStream oosFleur = new ObjectOutputStream(fosFleur);
		oosFleur.writeObject(f);
		oosFleur.close();
		fosFleur.close();
	}	
}
