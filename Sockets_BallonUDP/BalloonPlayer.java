import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;

public class BalloonPlayer {
	
	private static int PORT = 8888;
	/*
	 * VARIABLES
	 */
	private Balloon balloon;
	private UserInterface ui;
	private DatagramSocket socket;
	
	/*
	 * MAIN METHOD
	 */
	public static void main(String[] args) throws IOException, ClassNotFoundException {
		BalloonPlayer balloonPlayer = new BalloonPlayer();
		new BalloonJFrame(balloonPlayer);
		balloonPlayer.receive();
	}
	
	public BalloonPlayer() throws IOException {
		// POUR ENVOYER UN PREMIER MESSAGE, ON DONNE UN RAYON DE -1 
		balloon = new Balloon(-1);
		socket = new DatagramSocket();
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		ObjectOutputStream oos = new ObjectOutputStream(bos);
		oos.writeObject(balloon);
		byte[] buf = bos.toByteArray();
		DatagramPacket packet = new DatagramPacket(buf, buf.length,
				InetAddress.getByName("localhost"),PORT);
		socket.send(packet);
	}

	public Balloon getBalloon() {
		return balloon;
	}

	public void setUI(UserInterface ui) {
		this.ui=ui;
	}

	public void lance() throws IOException{
		if (balloon != null) {
			ByteArrayOutputStream bos = new ByteArrayOutputStream();
			ObjectOutputStream oos = new ObjectOutputStream(bos);
			oos.writeObject(balloon);
			byte[] buf = bos.toByteArray();
			DatagramPacket packet = new DatagramPacket(buf, buf.length,
					InetAddress.getByName("localhost"),PORT);
			socket.send(packet);
			balloon = null;
			ui.display();
		}else{
			System.out.println("Pas de balloon à envoyer...");
		}

	}
	
	private void receive() throws IOException, ClassNotFoundException{
		// CREATION BUFFER
		byte[] buffer = new byte[256];
		// CREATION PAQUET
		DatagramPacket packetReception = new DatagramPacket(buffer, buffer.length);
		
		// ECOUTE EN BOUCLE
		while(true){
			socket.receive(packetReception);
			ByteArrayInputStream bis = new ByteArrayInputStream(buffer);
			ObjectInputStream ois = new ObjectInputStream(bis);
			balloon = (Balloon) ois.readObject();
			System.out.println("Ballon reçu avec un rayon de " + balloon.getRayon());
			ui.display();
		}
	}
}
