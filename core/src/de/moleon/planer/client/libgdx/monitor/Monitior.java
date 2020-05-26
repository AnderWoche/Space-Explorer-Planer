package de.moleon.planer.client.libgdx.monitor;

import com.badlogic.gdx.graphics.Color;

import de.moleon.planer.client.libgdx.PixelChangeListener;

public abstract class Monitior {
	
	/**
	 * This method sets a pixel
	 */
	public abstract void setPixel(int x, int y, Color color);
	
	public abstract void setPixel(long xy, Color color);
	
	/**
	 * This method register the change listener
	 */
	public abstract void registerChangeListener(PixelChangeListener pixelChangeListener);
	
	/**
	 * This method returns the color of the pixel
	 */
	public abstract Color getPixel(int x, int y);
	
	public abstract Color getPixel(long xy);
	
	
}
