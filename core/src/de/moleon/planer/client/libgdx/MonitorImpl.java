package de.moleon.planer.client.libgdx;

import java.nio.ByteBuffer;
import java.util.HashMap;
import java.util.Map.Entry;
import java.util.Set;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Pool;

/**
 * @author David Humann (Moldiy)
 */
public class MonitorImpl extends Monitior {
	
	private Pool<ByteBuffer> byteBufferPool;
	
	private HashMap<Long, Color> pixels = new HashMap<>();
	
	private Array<PixelChangeListener> pixelChangeListenerArray = new Array<>();
	
	
	public MonitorImpl() {
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
	}
	
	void setPixelWithNotifyListener(int x, int y, Color color) {
		long cords = this.convertToLongID(x, y);
		
		this.pixels.put(cords, color);
		
		this.notifyPixelChangedListener(x, y, color);
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
		long cords = this.convertToLongID(x, y);
		Color color = this.pixels.get(cords);
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
	
	Set<Entry<Long, Color>> getAllPixels() {
		return this.pixels.entrySet();
	}
	
}
