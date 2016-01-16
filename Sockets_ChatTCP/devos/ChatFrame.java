package devos;



import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;

import javax.swing.JFrame;
import javax.swing.JTextArea;
import javax.swing.JTextField;



@SuppressWarnings("serial")
public class ChatFrame extends JFrame implements ActionListener, UserInterface {
  private ChatUser chatUser;
  private JTextArea textArea;
  private JTextField saisie;

  public ChatFrame(ChatUser chatUser) {
    super("Chat pour ???");
    this.chatUser=chatUser;
    chatUser.setUserInterface(this);

    textArea = new JTextArea();
    textArea.setEditable(false);

    saisie = new JTextField();
    saisie.addActionListener(this);

    add(textArea, BorderLayout.CENTER);
    add(saisie, BorderLayout.SOUTH);
    
    addWindowListener(new WindowAdapter() {
      public void windowClosing(WindowEvent e) {
        System.exit(0);
      }
    });
    
    setSize(200,200);
    setVisible(true);
  }

  @Override
  public void actionPerformed(ActionEvent e) {
	  if (chatUser.getPseudo() == null) {
		  String pseudo = saisie.getText();
		  chatUser.setPseudo(pseudo);
		  setTitle("Chat pour " + pseudo);
	  } else {
		  try {
			chatUser.send(saisie.getText());
		} catch (IOException e1) {
			saisie.setText("pb réseau");
			e1.printStackTrace();
		}
	  }
    saisie.setText("");
  }

  @Override
  public void display(ChatItem chatItem) {
    textArea.append(chatItem.getPseudoEmmeteur() + "> " + chatItem.getMessage()
        + "\n");
  }

}