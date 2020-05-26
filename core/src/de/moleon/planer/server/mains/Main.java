package de.moleon.planer.server.mains;

import de.moleon.planer.server.supers.Server;

/**
 * Copyright © 2020 Leon Rüsing - All Rights Reserved
 */
public class Main {
	
	private static Server server;
	
	/**
	 * This is the mainmethod which initalize the server
	 */
	public static void main(String[] args) {
		Main.server = new Server(6334);
		Main.server.start();
		
		/*
		ServerConnection serverConnection = new ServerConnection("catchadventure.ddns.net", 6334);
		serverConnection.connect();
		*/
	}
	
	public static Server getServer() {
		return server;
	}
}
