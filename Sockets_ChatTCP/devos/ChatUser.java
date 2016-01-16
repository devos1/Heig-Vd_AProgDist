package devos;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.net.UnknownHostException;

public class ChatUser {
	/*
	 * PORT SUR LEQUEL ECOUTE LE SERVEUR
	 */
	private static final int PORT = 8000;

	/*
	 * ADRESSE SERVEUR
	 */
	private static final String ADDRESS = "localhost";
	
	/*
	 * VARIABLES
	 */
	private String pseudo;
	private UserInterface userInterface;
	private Socket socket;
	private ObjectOutputStream oos;
	private static ObjectInputStream ois;
	private static ChatItem item;

	public static void main(String[] args) throws Exception {
		ChatUser chatUser = new ChatUser();
		new ChatFrame(chatUser);
		chatUser.receive();
	}

	public ChatUser() throws Exception, IOException {
		socket = new Socket("localhost", PORT);
		oos = new ObjectOutputStream(socket.getOutputStream());
	}

	public void setUserInterface(UserInterface userInterface) {
		this.userInterface = userInterface;
	}

	public String getPseudo() {
		return pseudo;
	}

	public void setPseudo(String pseudo) {
		this.pseudo = pseudo;
	}

	private void receive(){
		try {
			ois = new ObjectInputStream(socket.getInputStream());
			while ((item = (ChatItem)ois.readObject()) != null) {
				display(item);
				System.out.println("Dans boucle 1 ");
			}
		} catch (ClassNotFoundException | IOException e) {
			System.out.println("Erreur -> " + e.getMessage());
		}
	}
	
	public void send(String message) throws IOException {
		ChatItem ci = new ChatItem(pseudo, message);
		oos.writeObject(ci);
		System.out.println(ci);
	}

	public void display(ChatItem chatItem) {
		userInterface.display(chatItem);
	}

}
