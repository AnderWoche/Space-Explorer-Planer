package de.moleon.planer.global;

import com.badlogic.gdx.graphics.Color;

/**
 * Copyright � 2020 Leon R�sing - All Rights Reserved
 */
public class ColorEntry {
	
	private Color color;
	private int id;
	
	public ColorEntry(Color color, int id) {
		this.color = color;
		this.id = id;
	}
	
	public Color getColor() {
		return color;
	}
	
	public int getId() {
		return id;
	}
}
