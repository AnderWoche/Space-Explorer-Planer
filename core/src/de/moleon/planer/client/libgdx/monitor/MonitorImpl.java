package de.moleon.planer.client.libgdx.monitor;

import java.nio.ByteBuffer;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Pool;

import de.moleon.planer.client.libgdx.PixelChangeListener;
import de.moleon.planer.client.libgdx.monitor.sections.MonitorSection;
import de.moleon.planer.utils.SimpleGrid;

/**
 * @author David Humann (Moldiy)
 */
public class MonitorImpl extends Monitior {
	
	private Pool<ByteBuffer> byteBufferPool;
	
	
	private Map<Long, Color> pixels = Collections.synchronizedMap(new HashMap<Long, Color>());                             
	private Array<PixelChangeListener> pixelChangeListenerArray = new Array<>();
	
	private SimpleGrid grid = new SimpleGrid();
	
	private Pool<MonitorSection> monitorSectionPool;
	private HashMap<Long, MonitorSection> loadedSections = new HashMap<>();
	
	public MonitorImpl() {
		this.grid.setFieldSize(400, 400);
		this.monitorSectionPool = new Pool<MonitorSection>() {
			protected MonitorSection newObject() {
				return new MonitorSection();
			}
		};
		this.byteBufferPool = new Pool<ByteBuffer>() {
			@Override
			protected ByteBuffer newObject() {
				return ByteBuffer.allocate(8);
			}
		};
	}
	
	@Override
	public synchronized void setPixel(int x, int y, Color color) {
		long cords = this.convertToLongID(x, y);
		
		this.pixels.put(cords, color);
		
		int[] fieldPos = grid.getField(x, y);
		
		MonitorSection monitorSection = this.monitorSectionPool.obtain();
		
		monitorSection.laodSection(fieldPos[0], fieldPos[0]);
		
	}
	
	@Override
	public void setPixel(long xy, Color color) {
		this.pixels.put(xy, color);
	}
	
	public void setPixelWithNotifyListener(int x, int y, Color color) {
		long cords = this.convertToLongID(x, y);
		this.pixels.put(cords, color);
		this.notifyPixelChangedListener(cords, color);
	}
	
	public void setPixelWithNotifyListener(long xy, Color color) {
		this.pixels.put(xy, color);
		this.notifyPixelChangedListener(xy, color);
	}

	@Override
	public void registerChangeListener(PixelChangeListener pixelChangeListener) {
		this.pixelChangeListenerArray.add(pixelChangeListener);
	}
	
	private synchronized void notifyPixelChangedListener(long xy, Color color) {
		for(PixelChangeListener pixelChangeListener : this.pixelChangeListenerArray) {
			pixelChangeListener.pixelChanged(xy, color);
		}
	}

	@Override
	public Color getPixel(int x, int y) {
		long cords = this.convertToLongID(x, y);
		Color color = this.pixels.get(cords);
		
		if(color == null) {
			return Color.WHITE;
		}
		
		return color;
	}
	
	@Override
	public Color getPixel(long xy) {
		Color color = this.pixels.get(xy);
		
		if(color == null) {
			return Color.WHITE;
		}
		
		return color;
	}
	
	public long convertToLongID(int x, int y) {
		ByteBuffer buffer = this.byteBufferPool.obtain();
		buffer.rewind();
		buffer.putInt(x);
		buffer.putInt(y);
		
		buffer.rewind();
		
		long cordinates = buffer.getLong();
		
		this.byteBufferPool.free(buffer);
		
		return cordinates;
	}
	
	/**
	 * @return the byteBufferPool
	 */
	public Pool<ByteBuffer> getByteBufferPool() {
		return this.byteBufferPool;
	}
	
	public Set<Entry<Long, Color>> getAllPixels() {
		return this.pixels.entrySet();
	}

}
