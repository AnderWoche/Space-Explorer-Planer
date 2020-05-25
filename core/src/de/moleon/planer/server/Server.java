package de.moleon.planer.server;

import java.io.IOException;
import java.net.ServerSocket;

public class Server {
	
	private ServerSocket serverSocket;
	private int port;
	
	public Server(int port) {
		this.port = port;
	}
	
	public void start() {
		try {
			this.serverSocket = new ServerSocket(this.port);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public ServerSocket getServerSocket() {
		return serverSocket;
	}
}
