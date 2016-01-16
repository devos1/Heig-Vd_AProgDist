package devos;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.DatagramPacket;
import java.net.Socket;
import java.net.SocketAddress;
import java.util.HashMap;

/*
 * HANDLER THREAD CLASS
 * RECEPTION DES DONNEES
 */
public class Handler extends Thread {

	/*
	 * VARIABLES
	 */
	private Socket socket;
	private ObjectInputStream in;
	private ObjectOutputStream out;
	private ChatItem item;

	/*
	 * HASHMAP AVEC TOUS LES UTILISATEURS ET SOCKETADRESS
	 */
	private HashMap<String, Socket> clients;

	/*
	 * CONSTRUCTEUR
	 */
	public Handler(Socket socket) {
		this.socket = socket;
		clients = new HashMap<String, Socket>();
	}

	/*
	 * LECTURE DU FLUX TCP
	 */
	@Override
	public void run() {
		try {
			in = new ObjectInputStream(socket.getInputStream());

			while ((item = (ChatItem) in.readObject()) != null) {
				System.out.println(item.toString());
				String userName = item.getPseudoEmmeteur();
				if (clients.get(userName) == null) {
					clients.put(userName, socket);
				}
				send(item);
			}
		} catch (IOException | ClassNotFoundException e) {
			System.out.println("Erreur..." + e.getMessage());
		}
		System.out.println("Exit loop...");
	}

	private void send(ChatItem chatItem) throws IOException {
		for (Socket s : clients.values()) {
			out = new ObjectOutputStream(s.getOutputStream());
			out.writeObject(chatItem);
			//System.out.println(item);
		}
	}
}
