package de.moleon.planer.client.libgdx.monitor;

import java.util.HashMap;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Pool;

/**
 * @author David Humann (Moldiy)
 */
public class MonitorImpl extends Monitior {
	
	private Pool<T>
	
	private HashMap<Long, Color> pixels = new HashMap<>();
	
	private Array<PixelChangeListener> pixelChangeListenerArray = new Array<>();
	
	
//	private OrthographicCamera camera = new OrthographicCamera();
//	private ShapeRenderer shapeRenderer = new ShapeRenderer();

	@Override
	public synchronized void setPixel(int x, int y) {
	}

	@Override
	public void registerChangeListener(PixelChangeListener pixelChangeListener) {
		this.pixelChangeListenerArray.add(pixelChangeListener);
	}
	
	private synchronized void notifyPixelChangedListener(int x, int y, Color color) {
		for(PixelChangeListener pixelChangeListener : this.pixelChangeListenerArray) {
			pixelChangeListener.pixelChanged(x, y, color);
		}
	}

	@Override
	public synchronized Color getPixel(int x, int y) {
		return null;
	}
	
	private long convertToLongID(int x, int y) {
		
	}
	
	
}
