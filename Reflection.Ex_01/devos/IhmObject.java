package devos;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Container;
import java.awt.Frame;
import java.awt.GridLayout;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.im.spi.InputMethodDescriptor;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.Field;
import java.lang.reflect.Member;
import java.security.CodeSource;
import java.awt.Container;
import javax.swing.BoxLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import com.sun.xml.internal.messaging.saaj.soap.JpegDataContentHandler;


public class IhmObject{

	private static final long serialVersionUID = 1L;
	private static File objectFile = null;
	private static Object o = null;
	private static JPanel panelObjects = null;
	private static Person p = null;
	
	//----------------------------------------------------------
	// METHODE STATIC POUR AJOUTER DES COMPOSANTS - A SUPPRIMER
	//----------------------------------------------------------
	public static void addComponentsToPane(Container pane){
/*		pane.setLayout(new GridLayout(1, 1));
		JPanel panelBoutons = new JPanel();		
		panelBoutons.setLayout(new BoxLayout(panelBoutons, BoxLayout.LINE_AXIS));
		//pane.setLayout(new BoxLayout(pane, BoxLayout.LINE_AXIS));
		addButton("CHARGER", panelBoutons);
		addButton("SAUVER", panelBoutons);
		//pane.setLayout(new BoxLayout(pane, BoxLayout.PAGE_AXIS));
		pane.add(panelBoutons);*/
	}
	
	private static void addObjectsToPane(Frame frame, File f) throws IOException, ClassNotFoundException, IllegalArgumentException, IllegalAccessException{

		FileInputStream fis = new FileInputStream(f);
		ObjectInputStream ois = new ObjectInputStream(fis);
		o = ois.readObject();
		
		Class<?> c = o.getClass();
		
		ois.close();
		fis.close();
		
		if (o instanceof Person) {
			p = new Person(null, 0);
			//JOptionPane.showMessageDialog(null, "PERSON", "INFO", JOptionPane.INFORMATION_MESSAGE);
		}else {
			JOptionPane.showConfirmDialog(null, o.getClass().toString());
		}
		
		if (panelObjects == null) {
			panelObjects = new JPanel();
			panelObjects.setLayout(new BoxLayout(panelObjects, BoxLayout.PAGE_AXIS));
		}else {
			panelObjects.removeAll();
		}		
		
		Field[] fields = c.getDeclaredFields();
		//JOptionPane.showMessageDialog(null, fields.length, "INFO", JOptionPane.INFORMATION_MESSAGE);
		for (Field field : fields) {
			//System.out.println("Name : " + field.getName() + "Type : " + field.getType());			
			//System.out.println("-------------------------");
			JPanel panelObj = new JPanel();
			if (field.getType() == int.class) {			
				panelObj.setLayout(new BoxLayout(panelObj, BoxLayout.LINE_AXIS));
				int taille = field.getAnnotation(Taille.class).value();				
				field.setAccessible(true);
				addTextField(field.getName(), taille, panelObj, field.get(o).toString(), field.getName());				
				panelObjects.add(panelObj);
				p.set_age((int) field.get(o));
			}else if (field.getType() == String.class) {
				panelObj.setLayout(new BoxLayout(panelObj, BoxLayout.LINE_AXIS));
				int taille = field.getAnnotation(Taille.class).value();				
				field.setAccessible(true);
				addTextField(field.getName(), taille, panelObj, field.get(o).toString(), field.getName());				
				panelObjects.add(panelObj);
				p.set_prenom(field.get(o).toString());
			}
		}	

		
		//pane.setLayout(new BoxLayout(pane, BoxLayout.PAGE_AXIS));
		frame.add(panelObjects);	
		frame.pack();
		
	}
	
	//----------------------------------------------------------
	// METHODE STATIC POUR CREER DES BOUTONS
	//----------------------------------------------------------
	private static void addButton(String text, Container container, Frame frame){
		JButton button = new JButton(text);
		button.setAlignmentX(Component.LEFT_ALIGNMENT);
		container.add(button);
		
		// Add listeners
		button.addActionListener(new ActionListener() {			
			@Override
			public void actionPerformed(ActionEvent e) {
				if (((JButton) e.getSource()).getActionCommand() == "CHARGER") {
					JFileChooser fc = new JFileChooser();
					
					int result = fc.showOpenDialog(button);
					
					if (result == JFileChooser.APPROVE_OPTION) {
						objectFile = fc.getSelectedFile();
						JOptionPane.showConfirmDialog(container, objectFile.toString());
						try {
							try {
								addObjectsToPane(frame, objectFile);
							} catch (IllegalArgumentException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							} catch (IllegalAccessException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}
						} catch (ClassNotFoundException e1) {
							e1.printStackTrace();
						} catch (IOException e1) {
							e1.printStackTrace();
						}
					}
				}else {
					System.out.println(((JButton)e.getSource()).getActionCommand());
					try {
						try {
							SaveObject();
						} catch (IllegalAccessException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						} catch (IOException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
					} catch (IllegalArgumentException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
				
			}
		});
	}
	
	
	private static void addTextField(String labelField, int taille, Container container, String value, String txtName){
		JLabel label = new JLabel(labelField);
		label.setAlignmentX(Component.LEFT_ALIGNMENT);
		container.add(label);
		
		JTextField textField = new JTextField(taille);
		textField.setName(txtName);
		textField.setAlignmentX(Container.RIGHT_ALIGNMENT);
		textField.setMaximumSize(textField.getPreferredSize());
		textField.setText(value);
		textField.addKeyListener(new KeyListener() {
			@Override
			public void keyTyped(KeyEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void keyReleased(KeyEvent e) {
				JTextField jtf = (JTextField)e.getSource();
				if (jtf.getName() == "prenom") {
					p.set_prenom(jtf.getText());
				}else if (jtf.getName() == "age") {
					p.set_age(Integer.parseInt(jtf.getText()));
				}
			}
			
			@Override
			public void keyPressed(KeyEvent e) {
				// TODO Auto-generated method stub
				
			}
		});
		
		container.add(textField);
	}
	//----------------------------------------------------------
	// METHODE STATIC POUR CREER LA FENETRE
    /**
     * Create the GUI and show it.  For thread safety,
     * this method should be invoked from the
     * event-dispatching thread.
     */
	//----------------------------------------------------------
	private static void createAndShowGUI(){
		// Create and set up the window
		JFrame frame = new JFrame("IhmObject");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		frame.setLayout(new GridLayout(2, 1));
		
		JPanel panelBoutons = new JPanel();		
		panelBoutons.setLayout(new BoxLayout(panelBoutons, BoxLayout.LINE_AXIS));
		//pane.setLayout(new BoxLayout(pane, BoxLayout.LINE_AXIS));
		addButton("CHARGER", panelBoutons, frame);
		addButton("SAUVER", panelBoutons, frame);
		//pane.setLayout(new BoxLayout(pane, BoxLayout.PAGE_AXIS));
		frame.add(panelBoutons);
		
		// Set up the content pane
		//addComponentsToPane(frame.getContentPane());
		
		// Display the window
		frame.pack();
		frame.setVisible(true);
		
	}
	
	
	//----------------------------------------------------------
	// SAUVER
	//----------------------------------------------------------
	private static void SaveObject() throws IOException, IllegalArgumentException, IllegalAccessException{
		FileOutputStream fos = new FileOutputStream(objectFile);
		ObjectOutputStream oos = new ObjectOutputStream(fos);
		
		//Class<?> c = o.getClass();
		//Field[] fields = c.getDeclaredFields();
		//JOptionPane.showMessageDialog(null, fields.length, "INFO", JOptionPane.INFORMATION_MESSAGE);
/*		for (Field field : fields) {			
			if (field.getType() == int.class) {
				field.setAccessible(true);
				field.set(o, 22);
			}else if (field.getType() == String.class) {
				field.setAccessible(true);
				field.set(o, "Racso");
			}
		}*/
			
		oos.writeObject(p);
		oos.close();
		fos.close();
	}	
	
	//----------------------------------------------------------
	// MAIN
	//----------------------------------------------------------
    public static void main(String[] args) throws IOException {  	
        //Schedule a job for the event-dispatching thread:
        //creating and showing this application's GUI.
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                createAndShowGUI();
            }
        });
        
        // Serialization des objets
        SaveObject so = new SaveObject();
    }
    
    
    
    
/*	public IhmObject(){		

		
		// CREATION FENETRE
		JFrame fen = new JFrame();
		
		// TITRE
		fen.setTitle("IhmObject");
		
		// DEFINITION TAILLE
		fen.setSize(400, 400);
		
		// PLACEMENT AU CENTRE DE L'ECRAN
		fen.setLocationRelativeTo(null);		
		
		// SET LAYOUT POUR LA FENETRE PRINCIPALE
		setLayout(new BorderLayout());
		
		//----------------------------------------------------------
		// JPane
		//----------------------------------------------------------		
		// CREATION D'UN PANEL POUR INSERER LES COMPOSANTS GRAPHIQUES
		JPanel panel = new JPanel();
		
		// AJOUT DU PANEL A LA FENETRE PRINCIPALE
		add(panel, BorderLayout.CENTER);
		
		// CREATION DU LAYOUT POUR CE PANEL
		panel.setLayout(new BoxLayout(fen, BoxLayout.LINE_AXIS));
		
		// AJOUT BOUTON POUR TESTER
		panel.add(new JButton("SAUVER"));
		
		// ON DIT AU JFRAME QUE NOTRE PANEL SERA SONT CONTENTPANE
		this.setContentPane(panel);
		
		// RENDRE VISIBLE LA FENETRE
		fen.setVisible(true);
		
		// FIN DU PROCESSUS LORS DU CLIC SUR LA CROIX "FERMER"
		fen.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}*/
}
