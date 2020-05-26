package de.moleon.planer.client.network.traffic;

import java.io.IOException;

import com.badlogic.gdx.graphics.Color;

import de.moleon.planer.client.libgdx.PixelChangeListener;
import de.moleon.planer.client.libgdx.SpaceExplorerPlaner;
import de.moleon.planer.client.network.supers.ServerConnection;
import de.moleon.planer.global.ColorTranslator;

/**
 * Copyright © 2020 Leon Rüsing - All Rights Reserved
 */
public class TCPSender {
	
	private ServerConnection serverConnection;
	private PixelChangeListener pixelChangeListener = new PixelChangeListener() {

		@Override
		public void pixelChanged(long xy, Color color) {
			send("SET " + xy + " " + ColorTranslator.getInstance().getIDByColor(color));
		}
	};
	
	public TCPSender(ServerConnection serverConnection) {
		this.serverConnection = serverConnection;
		
		SpaceExplorerPlaner.getInstance().getMonitior().registerChangeListener(this.pixelChangeListener);
	}
	
	/**
	 * This method can send strings to server
	 */
	public void send(String string) {
		if(this.serverConnection.isConnected()) {
			try {
				this.serverConnection.getDos().writeUTF(string);
				this.serverConnection.getDos().flush();
			} catch (IOException e) {
				this.serverConnection.error();
			}
		}
	}
	
	public PixelChangeListener getPixelChangeListener() {
		return pixelChangeListener;
	}
}
