package de.moleon.planer.client.libgdx;

import java.nio.ByteBuffer;
import java.util.Map.Entry;
import java.util.Set;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Pools;

import de.moleon.planer.client.libgdx.monitor.Monitior;
import de.moleon.planer.client.libgdx.monitor.MonitorImpl;
import de.moleon.planer.client.network.supers.ServerConnection;

public class SpaceExplorerPlaner extends ApplicationAdapter {
	
	private MonitorImpl monitior;
	
	private OrthographicCamera camera;
	
	private ShapeRenderer shapeRenderer;
	
	private ByteBuffer buffer = ByteBuffer.allocate(8);
	
	@Override
	public void create () {		
		this.monitior = new MonitorImpl();
		this.camera = new OrthographicCamera();
		this.shapeRenderer = new ShapeRenderer();
		
		this.camera.position.set(0, 0, 0);
		
		ServerConnection serverConnection = new ServerConnection("catchadventure.ddns.net", 6334);
		serverConnection.connect();
	}
	
	private Vector2 lastPosition = new Vector2();
	private Vector2 calculateVec = new Vector2();
	
	@Override
	public void render () {
		Gdx.gl.glClearColor(1, 1, 1, 1);
//		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		Gdx.gl.glClear(
				GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT | (Gdx.graphics.getBufferFormat().coverageSampling ? GL20.GL_COVERAGE_BUFFER_BIT_NV : 0));
		Gdx.gl.glHint(GL20.GL_LINE_LOOP, GL20.GL_NICEST);
		
		this.shapeRenderer.setProjectionMatrix(this.camera.combined);
		this.shapeRenderer.begin(ShapeType.Filled);
		
		Set<Entry<Long, Color>> pixels = this.monitior.getAllPixels();
		for(Entry<Long, Color> cordsAndColor : pixels) {
			long longCord = cordsAndColor.getKey();
			Color color = cordsAndColor.getValue();
			
			this.shapeRenderer.setColor(color);
			
			this.buffer.rewind();
			this.buffer.putLong(longCord);
			this.buffer.rewind();
			
			this.shapeRenderer.rect(this.buffer.getInt(), this.buffer.getInt(), 2F, 2F);
			
			
		}
		
		Vector3 vec = this.camera.unproject(Pools.obtain(Vector3.class).set(Gdx.input.getX(), Gdx.input.getY(), 0));
		int x = (int) vec.x;
		int y = (int) vec.y;
		
		if(Gdx.input.isButtonPressed(Input.Buttons.LEFT)) {
			this.calculateVec.set(x, y);
			float lenght;
			do {
				this.calculateVec.sub(lastPosition);
				lenght = this.calculateVec.len();
				this.calculateVec.nor();
				this.calculateVec.scl(1.3F);
				
				lastPosition.add(calculateVec);
				
				this.calculateVec.set(x + this.calculateVec.x, y + this.calculateVec.y);
				
				this.drawPixel((int)lastPosition.x, (int)lastPosition.y, Color.BLACK);
				
			} while(lenght > 2);
			
			
			Color color = this.monitior.getPixel(x, y);
			
			if(color == Color.WHITE) {
//				this.drawPixel(x, y, Color.BLACK);
			}
			
		}
		this.lastPosition.set(x, y);
		Pools.free(vec);
		
		shapeRenderer.end();
	}
	
	public void drawPixel(int x, int y, Color color) {
		this.monitior.setPixelWithNotifyListener(x, y, Color.BLACK);
	}
	
	@Override
	public void resize(int width, int height) {
		this.camera.viewportWidth = width;
		this.camera.viewportHeight = height;
		this.camera.update();
		super.resize(width, height);
	}
	
	@Override
	public void dispose () {
	}
	
	public static SpaceExplorerPlaner getInstance() {
		return (SpaceExplorerPlaner) Gdx.app.getApplicationListener();
	}
	
	public Monitior getMonitior() {
		return monitior;
	}
}
