import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketAddress;
import java.net.SocketException;

public class BalloonServer {

	private static int PORT = 8888;
	/*
	 * VARIABLES
	 */
	private DatagramSocket socket;
	private SocketAddress socketAddressPlayer1;
	private SocketAddress socketAddressPlayer2;
	private boolean fistPlayer = false;
	private Balloon balloon = null;
	
	/*
	 * MAIN METHOD
	 */
	public static void main(String[] args) throws ClassNotFoundException, IOException {
		BalloonServer ballonServer = new BalloonServer();
		ballonServer.receive();
	}
	
	/*
	 * CONSTRUCTEUR
	 */
	public BalloonServer() {
		// CREATION DATAGRAM SOCKET
		try {
			socket = new DatagramSocket(PORT);
		} catch (SocketException e) {
			e.printStackTrace();
			System.out.println("Erreur création datagramsocket : " + e.getMessage());
		}	
	}
	
	/*
	 * FONCTION POUR RECEVOIR LES PAQUETS EN UDP
	 */
	private void receive() throws IOException, ClassNotFoundException{
		System.out.println("Serveur écoute sur le port " + PORT);
		
		// CREATION BUFFER AVEC 256
		byte[] buffer = new byte[256];
		
		// CREATION DATAGRAM PACKET
		DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
		
		// BOUCLE POUR RECEVOIR EN CONTINU
		while(true){
			// RECEPTION PAQUET (BALLOON) SUR LE SOCKET
			socket.receive(packet);
			// TRAITEMENT FLUX
			ByteArrayInputStream bis = new ByteArrayInputStream(packet.getData());
			ObjectInputStream ois = new ObjectInputStream(bis);
			Balloon balloonRecu = (Balloon) ois.readObject();
			System.out.println("Ballon reçu de " + packet.getSocketAddress());
			if (balloon == null) {
				balloon = balloonRecu;
				System.out.println(balloon);
				balloon.changeSize(21);
				socketAddressPlayer1 = packet.getSocketAddress();
				System.out.println("Balloon envoyé à player 1...");
				send(balloon, socketAddressPlayer1);
			}else{
				if (packet.getSocketAddress().equals(socketAddressPlayer1)) {
					System.out.println("Balloon recu de player 1");
					balloon = balloonRecu;
					if (socketAddressPlayer2 != null) {
						// SI PLAYER 2 EXISTE ON ENVOIE LE BALLOON
						System.out.println("Balloon envoyé à player 2");
						send(balloon, socketAddressPlayer2);
					}else {
						System.out.println("Joueur 2 inexistant...");
					}
				}else if (packet.getSocketAddress().equals(socketAddressPlayer2)) {
					System.out.println("Balloon recu de player 2");
					balloon = balloonRecu;
					send(balloon, socketAddressPlayer1);
				}else{
					// CREATION SOCKET ADDRESS POUR PLAYER 2
					socketAddressPlayer2 = packet.getSocketAddress();
				}
			}
			
		}
	}
	
	private void send(Balloon b, SocketAddress s) throws IOException{
		// TRAITEMENT FLUX
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		ObjectOutputStream oos = new ObjectOutputStream(bos);
		oos.writeObject(b);
		byte[] buffer = bos.toByteArray();
		
		// CREATION PACKET POUR ENVOYER
		DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
		
		// ENVOI DU PACKET SUR LE SOCKET ADDRESS RECUPERE
		packet.setSocketAddress(s);
		socket.send(packet);
	}
}
