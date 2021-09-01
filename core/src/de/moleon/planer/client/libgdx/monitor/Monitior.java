package de.moleon.planer.client.libgdx.monitor;

import com.badlogic.gdx.graphics.Color;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.Viewport;
import de.moleon.planer.client.libgdx.PixelChangeListener;

public abstract class Monitior extends Stage {

	public Monitior(Viewport viewport) {
		super(viewport);
	}

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
