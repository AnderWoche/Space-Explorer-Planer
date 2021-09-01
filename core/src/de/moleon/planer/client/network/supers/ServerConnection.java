package de.moleon.planer.client.network.supers;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

import de.moleon.planer.client.libgdx.monitor.Monitior;
import de.moleon.planer.client.network.traffic.TCPListener;
import de.moleon.planer.client.network.traffic.TCPSender;

/**
 * Copyright � 2020 Leon R�sing - All Rights Reserved
 */
public class ServerConnection {
	
	private Socket socket;
	private DataInputStream dis;
	private DataOutputStream dos;

	private TCPListener tcpListener;
	private TCPSender tcpSender;

	private String host;
	private int port;

	private boolean connected;

	public ServerConnection(String host, int port) {
		this.port = port;
		this.host = host;
	}

	/**
	 * This method try to establish the connection to the server 
	 */
	public void connect(Monitior monitor) {
		try {
			this.socket = new Socket(this.host, this.port);
			this.socket.setReceiveBufferSize(Integer.MAX_VALUE);
			this.socket.setSendBufferSize(Integer.MAX_VALUE);
			
			this.dis = new DataInputStream(this.socket.getInputStream());
			this.dos = new DataOutputStream(this.socket.getOutputStream());

			this.tcpListener = new TCPListener(this, monitor);
			this.tcpListener.thread = new Thread(this.tcpListener);
			this.tcpListener.thread.start();

			this.tcpSender = new TCPSender(this, monitor);
			
			this.connected = true;
			
			System.out.println("[Client] Connected to server");
			
		} catch (IOException e) {
			this.error();
		}
	}

	/**
	 * This method will be call in case of an connection error
	 */
	public void error() {
		System.out.println("[Client] Connection lost");
		this.connected = false;
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
