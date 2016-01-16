package devos;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketAddress;
import java.net.SocketException;
import java.util.HashMap;
import java.util.HashSet;

public class ChatServer {
	
	/*
	 * PORT SUR LEQUEL ECOUTE LE SERVEUR
	 */
	private static final int PORT = 8000;
	
	/*
	 * CONSTRUCTOR
	 */
	public ChatServer() throws SocketException {		
	}

	
	/*
	 * MAIN 
	 */
	public static void main(String[] args) throws IOException, ClassNotFoundException {
		System.out.println("Serveur écoute sur le port " + PORT);
		ServerSocket listener = new ServerSocket(PORT);
		try {
			while(true){
				// CREATION D'UN HANDLER PAR UTILISATEUR
				new Handler(listener.accept()).start();
			}
		} catch (Exception e) {
			System.out.println("Erreur -> " + e.getMessage());
		} finally {
			listener.close();
		}
		
		ChatServer chatServer = new ChatServer();
	}
}
