package de.moleon.planer.server.supers;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.LinkedList;

import de.moleon.planer.server.networktraffic.TCPListener;
import de.moleon.planer.server.networktraffic.TCPSender;

/**
 * Copyright © 2020 Leon Rüsing - All Rights Reserved
 */
public class ClientConnection {
	
	public static LinkedList<ClientConnection> list = new LinkedList<ClientConnection>();
	
	private Socket socket;
	private DataInputStream dis;
	private DataOutputStream dos;

	private TCPListener tcpListener;
	private TCPSender tcpSender;
	
	private boolean connected;

	/**
	 * Constructor try to establish a connection to client
	 */
	public ClientConnection(Socket socket) {
		try {
			this.socket = socket;
			this.socket.setReceiveBufferSize(Integer.MAX_VALUE);
			this.socket.setSendBufferSize(Integer.MAX_VALUE);
			
			this.dis = new DataInputStream(socket.getInputStream());
			this.dos = new DataOutputStream(socket.getOutputStream());

			this.tcpListener = new TCPListener(this);
			this.tcpListener.running = true;
			this.tcpListener.thread = new Thread(this.tcpListener);
			this.tcpListener.thread.start();

			this.tcpSender = new TCPSender(this);

			this.connected = true;
			
			ClientConnection.list.add(this);	
			System.out.println("[Server] CONNECTED: " + this.socket.getInetAddress() + ":" + this.socket.getPort());
			
			this.tcpSender.send("<PACKETNAME>");
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * This method will be call when a error occures
	 */
	@SuppressWarnings("deprecation")
	public void error() {
		System.out.println("[Server] EXIT: " + this.socket.getInetAddress() + ":" + this.socket.getPort());
		ClientConnection.list.remove(this);

		this.connected = false;

		this.tcpListener.running = false;
		this.tcpListener.thread.stop();

		try {
			this.dis.close();
			this.dos.close();
			this.socket.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public boolean isConnected() {
		return connected;
	}

	public void setConnected(boolean connected) {
		this.connected = connected;
	}

	public Socket getSocket() {
		return socket;
	}

	public void setSocket(Socket socket) {
		this.socket = socket;
	}

	public DataInputStream getDis() {
		return dis;
	}

	public void setDis(DataInputStream dis) {
		this.dis = dis;
	}

	public DataOutputStream getDos() {
		return dos;
	}

	public void setDos(DataOutputStream dos) {
		this.dos = dos;
	}

	public TCPListener getTcpListener() {
		return tcpListener;
	}

	public void setTcpListener(TCPListener tcpListener) {
		this.tcpListener = tcpListener;
	}

	public TCPSender getTcpSender() {
		return tcpSender;
	}

	public void setTcpSender(TCPSender tcpSender) {
		this.tcpSender = tcpSender;
	}
}
