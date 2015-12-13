package devos;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;

public class Person implements Serializable {

	private static final long serialVersionUID = 1L;

	public String get_prenom() {
		return this.prenom;
	}

	public void set_prenom(String _prenom) {
		this.prenom = _prenom;
	}

	public int get_age() {
		return this.age;
	}

	public void set_age(int age) {
		this.age = age;
	}

	@Taille(10)
	private String prenom;
	@Taille(2)
	private int age;
	
	public Person(String prenom, int age){
		this.prenom = prenom;
		this.age = age;
	}
}
