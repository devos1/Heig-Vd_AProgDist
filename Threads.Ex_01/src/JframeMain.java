import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

/**
 * @author oscardasilva
 * @REMARQUES : Mauvaise façon de faire car utilisation de méthodes dépréciées! 
 */
@SuppressWarnings("serial")
public class JframeMain extends JFrame {

	private JButton startStop;
	private JpanelCircle rond;
	private Thread t1;
	private boolean started = false;
	private boolean stopped = false;
	
	public static void main(String[] args) {
		JframeMain f = new JframeMain();
		f.pack();
		f.setVisible(true);
	}
	
	public JframeMain(){
		setTitle("Exercice 01");
		Container contentPane = getContentPane();
		rond = new JpanelCircle();
		t1 = new Thread(rond);
		contentPane.add(rond, BorderLayout.CENTER);		

		JPanel boutons = new JPanel();
		boutons.setLayout(new GridLayout(1,2));
		contentPane.add(boutons,BorderLayout.NORTH);
		
		startStop = new JButton("Start/Stop");
		boutons.add(startStop);
		startStop.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {	
				if (!started) {
					t1.start();
					started = true;	
					JOptionPane.showMessageDialog(null, "Début clignotement", "INFO", JOptionPane.INFORMATION_MESSAGE);
				}else if(started && !stopped){
					t1.suspend();
					//rond.setStop(true);
					stopped = true;
					JOptionPane.showMessageDialog(null, "Fin clignotement", "INFO", JOptionPane.INFORMATION_MESSAGE);
				}else{
					//rond.setStop(false);
					t1.resume();
					JOptionPane.showMessageDialog(null, "Début clignotement", "INFO", JOptionPane.INFORMATION_MESSAGE);
					stopped = false;
				}
			}
		});	
		
		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
		});
	}
	
}
