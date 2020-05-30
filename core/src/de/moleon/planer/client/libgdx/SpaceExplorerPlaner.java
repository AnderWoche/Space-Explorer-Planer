package de.moleon.planer.client.libgdx;

import java.util.Map;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Pools;

import de.moleon.planer.client.libgdx.monitor.Monitior;
import de.moleon.planer.client.libgdx.monitor.MonitorImpl;
import de.moleon.planer.client.libgdx.monitor.sections.MonitorSection;

public class SpaceExplorerPlaner extends ApplicationAdapter implements InputProcessor {

	private MonitorImpl monitior;

	private OrthographicCamera camera;

	private SpriteBatch batch;
	private ShapeRenderer shapeRenderer;

	@Override
	public void create() {
		this.monitior = new MonitorImpl();
		this.camera = new OrthographicCamera();

		this.batch = new SpriteBatch();
		this.shapeRenderer = new ShapeRenderer();

		this.camera.position.set(0, 0, 0);

//		ServerConnection serverConnection = new ServerConnection("catchadventure.ddns.net", 6334);
//		serverConnection.connect(this.monitior);

		Gdx.input.setInputProcessor(this);
	}

	private Vector2 lastPosition = new Vector2();
	private Vector2 calculateVec = new Vector2();

	private volatile Map.Entry<Long, Color>[] propertyEntries = null;

	@Override
	public void render() {
		Gdx.gl.glClearColor(1, 1, 1, 1);
		// Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		Gdx.gl.glClear(
				GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT | (Gdx.graphics.getBufferFormat().coverageSampling ? GL20.GL_COVERAGE_BUFFER_BIT_NV : 0));
		Gdx.gl.glHint(GL20.GL_LINE_LOOP, GL20.GL_NICEST);

		// this.shapeRenderer.setProjectionMatrix(this.camera.combined);
		// this.shapeRenderer.begin(ShapeType.Filled);
		// for (MonitorSection section : this.monitior.getLoadedSections().values()) {
		// section.drawShape(shapeRenderer);
		// }
		// this.shapeRenderer.end();

		this.batch.setProjectionMatrix(this.camera.combined);
		this.batch.begin();
		for (MonitorSection section : this.monitior.getLoadedSections().values()) {
			section.draw(batch, Gdx.graphics.getDeltaTime());
		}
		this.batch.end();

		Vector3 vec = this.camera.unproject(Pools.obtain(Vector3.class).set(Gdx.input.getX(), Gdx.input.getY(), 0));
		int x = (int) vec.x;
		int y = (int) vec.y;

		if (Gdx.input.isButtonPressed(Input.Buttons.LEFT)) {
			this.calculateVec.set(x, y);
			float lenght;
			do {
				this.calculateVec.sub(lastPosition);
				lenght = this.calculateVec.len();
				this.calculateVec.nor();
				this.calculateVec.scl(1.3F);

				lastPosition.add(calculateVec);

				this.calculateVec.set(x + this.calculateVec.x, y + this.calculateVec.y);

				this.drawPixel((int) lastPosition.x, (int) lastPosition.y, Color.BLACK);

			} while (lenght > 2);

			// Color color = this.monitior.getPixel(x, y);

			// if(color == Color.WHITE) {
			// this.drawPixel(x, y, Color.BLACK);
			// }

		}
		this.lastPosition.set(x, y);
		Pools.free(vec);

		float cameraScreenX = this.camera.position.x + this.camera.viewportWidth * this.camera.zoom;
		float cameraScreenY = this.camera.position.y + this.camera.viewportHeight * this.camera.zoom;
		Array<int[]> array = MonitorSection.GRID.getFieldsAroundToFillScreen(cameraScreenX, cameraScreenY, (int) (Gdx.graphics.getWidth() * this.camera.zoom),
				(int) (Gdx.graphics.getHeight() * this.camera.zoom));

		for (int[] fieldPos : array) {
			long id = this.monitior.convertToLongID(fieldPos[0], fieldPos[1]);

			MonitorSection loadedSection = this.monitior.getLoadedSections().get(id);
			if (loadedSection == null) {
				this.monitior.getOrLoadMonitorSectionFromField(fieldPos[0], fieldPos[1]);
			}
		}
		
		for(MonitorSection loadedSection : this.monitior.getLoadedSections().values()) {
//			if()
		}

	}

	private boolean containsSection(MonitorSection monitorSection) {
		for (MonitorSection loadedSection : this.monitior.getLoadedSections().values()) {
			if (loadedSection == monitorSection) {
				return true;
			}
		}
		return false;
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
	public void dispose() {
	}

	public static SpaceExplorerPlaner getInstance() {
		return (SpaceExplorerPlaner) Gdx.app.getApplicationListener();
	}

	public Monitior getMonitior() {
		return monitior;
	}

	@Override
	public boolean keyDown(int keycode) {
		return false;
	}

	@Override
	public boolean keyUp(int keycode) {
		return false;
	}

	@Override
	public boolean keyTyped(char character) {
		return false;
	}

	private Vector2 camPos = new Vector2();
	private Vector2 pressedPoint = new Vector2();

	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		if (Gdx.input.isButtonPressed(Input.Buttons.RIGHT)) {

			this.pressedPoint.set(screenX * camera.zoom, screenY * camera.zoom);

			this.camPos.set(this.camera.position.x, this.camera.position.y);

		}
		return false;
	}

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		return false;
	}

	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {
		if (Gdx.input.isButtonPressed(Input.Buttons.RIGHT)) {

			Vector2 vecLenght = Pools.obtain(Vector2.class);
			vecLenght.set(pressedPoint.x, pressedPoint.y);

			vecLenght.sub(screenX * camera.zoom, screenY * camera.zoom);

			float x = (this.camPos.x + vecLenght.x);
			float y = this.camPos.y + -vecLenght.y;

			this.camera.position.set(x, y, 0);
			this.camera.update();

			Pools.free(vecLenght);
		}
		return false;
	}

	@Override
	public boolean mouseMoved(int screenX, int screenY) {
		return false;
	}

	@Override
	public boolean scrolled(int amount) {
		if (amount == 1) {
			if (this.camera.zoom < 3) {
				this.camera.zoom += 0.03F;
				this.camera.update();
			}
		} else if (amount == -1) {
			if (this.camera.zoom > 1) {
				this.camera.zoom -= 0.03F;
				this.camera.update();
			}
		}
		return false;
	}
}
