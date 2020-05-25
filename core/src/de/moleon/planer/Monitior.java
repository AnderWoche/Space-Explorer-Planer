package de.moleon.planer;

import com.badlogic.gdx.graphics.Color;

public interface Monitior {
	
	/**
	 * This method sets a pixel
	 */
	public void setPixel(int x, int y);
	
	/**
	 * This method register the change listener
	 */
	public void registerChangeListener(PixelChangeListener pixelChangeListener);
	
	/**
	 * This method returns the color of the pixel
	 */
	public Color getPixel(int x, int y);
}
