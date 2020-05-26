package de.moleon.planer.client.libgdx.monitor.sections;

import java.io.File;

import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.Pool.Poolable;

/**
 * @author David Humann (Moldiy)
 */
public class MonitorSection implements Poolable {

	public static final File SAVE_FILE = new File(System.getProperty("user.home") + "/Space Explorer Planer/");
	public static final File SECTIONS_FILE = new File(SAVE_FILE, "/sections/");

	static {
		if (!SAVE_FILE.exists()) {
			SAVE_FILE.mkdirs();
		}
		if (!SECTIONS_FILE.exists()) {
			SECTIONS_FILE.mkdirs();
		}
	}

	private Texture texture;

	private Pixmap pixmap;

	public MonitorSection() {

	}
	
	public void laodSection(int x, int y) {
		File file = new File(SECTIONS_FILE, "section." + x + "." + y);
		if(file.exists()) {
			// LOAD
		}
	}

	@Override
	public void reset() {
		this.texture.dispose();
		this.pixmap.dispose();

		this.pixmap = null;
		this.texture = null;
	}

}
