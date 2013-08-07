/**
 * 
 */
package pw.whacka.spacecrusade;

import java.awt.event.MouseWheelEvent;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.FPSLogger;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g3d.decals.Decal;
import com.badlogic.gdx.graphics.g3d.decals.DecalBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;

/**
 * @author Elías
 * 
 */
public class Graphic implements InputProcessor {

	protected static MouseWheelEvent mouseEvent;
	protected Rectangle lastPosition;
	protected int scroll;
	protected int[] button;
	protected int key;
	protected String str;
	protected static long when;
	protected static int modifiers;
	protected static int clickCount;
	protected static boolean popupTrigger;
	protected static Graphic scrollType;

	// public Array<Rectangle> shapeArray;
	protected Rectangle shape;
	protected static String sprite = "data/libgdx.png";
	protected Sound sound;

	// Animation Spritesheet

	protected static float animationPeriod = 0.025f;
	protected static float width = 128;
	protected static float height = 128;
	protected static float posX = 400 - width / 2;
	protected static float posY = 240 - height / 2;
	protected static float originX = width / 2;
	protected static float originY = height / 2;
	protected static int FRAME_COLS = 2; // #1
	protected static int FRAME_ROWS = 2; // #2
	protected static float rotation;// Ángulo y velocidad rotación

	private Animation animation; // #3
	private Texture spriteSheet; // #4
	private TextureRegion[] animationFrames; // #5
	private SpriteBatch spriteBatch; // #6
	// private Decal spriteBatch; // #6
	private TextureRegion currentFrame; // #7

	protected float stateTime; // #8
	protected float lastTime;

	private float radioColision; // Para determinar colisión
	private float timer;
	private Decal spriteDecal;
	private DecalBatch decalBatch;
	private FPSLogger logger = new FPSLogger();

	public Graphic() {
		sound = Gdx.audio.newSound(Gdx.files.internal("game/drop.wav"));
		spriteSheet = new Texture(Gdx.files.internal(sprite)); // #9
		animationFrames = (new TextureRegion[FRAME_COLS * FRAME_ROWS]);
		// create a decal sprite
		spriteDecal = Decal.newDecal(posX, posY,
				new TextureRegion(spriteSheet), true);

		// Animation
		TextureRegion[][] tmp = TextureRegion.split(spriteSheet,
				spriteSheet.getWidth() / FRAME_COLS,
				spriteSheet.getHeight() / FRAME_ROWS); // #10

		int index = 0;
		for (int i = 0; i < FRAME_ROWS; i++) {
			for (int j = 0; j < FRAME_COLS; j++) {

				animationFrames[index++] = tmp[i][j];
			}
		}

		animation = new Animation(animationPeriod, animationFrames); // #11
		// spriteBatch = new SpriteBatch(); // #12
		// create a DecalBatch to render them with just once at startup
		decalBatch = new DecalBatch();
		str = new String();
		shape = new Rectangle();
		lastPosition = new Rectangle();
		shape.x = posX;
		shape.y = posY;
		shape.width = width;
		shape.height = height;
		lastPosition.x = shape.x;
		lastPosition.y = shape.y;
		timer = 0.0f;

		// addMouseWheelListener(this);

		rotation = 0;

		stateTime = 0.0f; // 13
	}

	public Graphic(String sprite, int FRAME_ROWS, int FRAME_COLS,
			float animationPeriod, float initialX, float initialY, float width,
			float height, float angle) {
		sound = Gdx.audio.newSound(Gdx.files.internal("game/drop.wav"));
		spriteSheet = new Texture(Gdx.files.internal(sprite)); // #9
		animationFrames = (new TextureRegion[FRAME_COLS * FRAME_ROWS]);
		// create a decal sprite
		spriteDecal = Decal.newDecal(posX, posY,
				new TextureRegion(spriteSheet), true);
		
		// Animation
		TextureRegion[][] tmp = TextureRegion.split(this.spriteSheet,
				this.spriteSheet.getWidth() / FRAME_COLS,
				this.spriteSheet.getHeight() / FRAME_ROWS); // #10

		int index = 0;
		for (int i = 0; i < FRAME_ROWS; i++) {
			for (int j = 0; j < FRAME_COLS; j++) {

				this.animationFrames[index++] = tmp[i][j];
			}
		}

		animation = new Animation(animationPeriod, this.animationFrames); // #11
		spriteBatch = new SpriteBatch(); // #12
		decalBatch = new DecalBatch();

		str = new String();
		shape = new Rectangle();
		lastPosition = new Rectangle(shape);
		this.shape.x = initialX;
		this.shape.y = initialY;
		this.shape.width = width;
		this.shape.height = height;
		lastPosition.x = shape.x;
		lastPosition.y = shape.y;

		radioColision = (shape.width + shape.height) / 4;
		rotation = 0;

		stateTime = 0.0f; // 13
	}

	/*
	 * public void render() { stateTime = (stateTime +
	 * Gdx.graphics.getDeltaTime()); // #15 currentFrame =
	 * animation.getKeyFrame(stateTime, true); // #16
	 * 
	 * spriteBatch.begin(); // 17
	 * 
	 * spriteBatch.draw(currentFrame, shape.x,Game.HEIGHT - shape.y, originX,
	 * originY, shape.width, shape.height, 1.0f, 1.0f, 0);
	 * 
	 * spriteBatch.end();
	 * 
	 * }
	 */
	public void render() {
		Game.getCamera().update();
		spriteDecal.setPosition(0, 0, 0);
		spriteDecal.lookAt(Game.getCamera().position, Game.getCamera().up);
		decalBatch.add(spriteDecal);
		decalBatch.flush();
		logger.log();
	}
	

	public void render(Vector3 position, Vector3 up) {
		Game.getCamera().update();
		spriteDecal.setPosition(0, 0, 0);
		spriteDecal.lookAt(position, up);
		decalBatch.add(spriteDecal);
		decalBatch.flush();
		logger.log();
	}

	public void renderOn(float x, float y) {
		stateTime = (stateTime + Gdx.graphics.getDeltaTime()); // #15
		currentFrame = animation.getKeyFrame(stateTime, true); // #16

		spriteBatch.begin(); // 17

		spriteBatch.draw(currentFrame, x, Game.HEIGHT - y, originX, originY,
				width, height, 1.0f, 1.0f, 0);

		spriteBatch.end();
	}

	public void renderOn(float x, float y, float width, float height) {
		stateTime = (stateTime + Gdx.graphics.getDeltaTime()); // #15
		currentFrame = animation.getKeyFrame(stateTime, true); // #16

		spriteBatch.begin(); // 17

		spriteBatch.draw(currentFrame, x, Game.HEIGHT - y, width / 2,
				height / 2, width, height, 1.0f, 1.0f, rotation);

		spriteBatch.end();
	}

	public void renderOn(float x, float y, float width, float height,
			float rotation) {
		stateTime = (stateTime + Gdx.graphics.getDeltaTime()); // #15
		currentFrame = animation.getKeyFrame(stateTime, true); // #16

		spriteBatch.begin(); // 17

		spriteBatch.draw(currentFrame, x, Game.HEIGHT - y, width / 2,
				height / 2, width, height, 1.0f, 1.0f, rotation);

		spriteBatch.end();
	}

	public void renderOn(float shapex, float shapey, float width, float height,
			float initialX, float initialY) {
		stateTime = (stateTime + Gdx.graphics.getDeltaTime()); // #15
		currentFrame = animation.getKeyFrame(stateTime, true); // #16

		spriteBatch.begin(); // 17

		spriteBatch.draw(currentFrame, shapex, Game.HEIGHT - shape.y, initialX,
				initialY, width, height, 1.0f, 1.0f, 0);

		spriteBatch.end();
	}

	public void renderRotating() {
		stateTime = (stateTime + Gdx.graphics.getDeltaTime()); // #15
		currentFrame = animation.getKeyFrame(stateTime, true); // #16

		spriteBatch.begin(); // 17

		spriteBatch.draw(currentFrame, shape.x, shape.y, width / 2, height / 2,
				shape.width, Game.HEIGHT - shape.height, 1.0f, 1.0f, rotation);

		spriteBatch.end();
	}

	public double distancia(Graphic g) {

		return Math.hypot(posX - g.posX, posY - g.posY);

	}

	public boolean verificaColision(Graphic g) {

		return (distancia(g) < (radioColision + g.radioColision));

	}

	public void moveUnit(Vector3 touchPos) {

		// Input left to shapeArray. (Backwards)
		if (shape.x < touchPos.x) {
			shape.x += 10 * Gdx.graphics.getDeltaTime();
		} else if (shape.x > touchPos.x) {
			shape.x -= 10 * Gdx.graphics.getDeltaTime();
		}
		if (shape.y > touchPos.y) {
			shape.y -= 250 * Gdx.graphics.getDeltaTime();
		} else if (shape.y < touchPos.y) {
			shape.y += 250 * Gdx.graphics.getDeltaTime();
		}
	}

	public void blockUnitMove() {
		shape.x += 0;
		shape.y += 0;
	}

	public void dispose() {
		spriteSheet.dispose();
	}

	public boolean keyDown(int keycode) {
		boolean ret = false;
		if (Gdx.input.isKeyPressed(keycode)) {
			timer = 0.0f;
			key = keycode;
			ret = true;
		}
		return ret;
	}

	public boolean keyUp(int keycode) {
		boolean ret;
		if (Gdx.input.isKeyPressed(keycode)) {
			ret = false;
		} else {
			key = keycode;
			ret = true;
		}
		return ret;
	}

	public boolean keyTyped(char character) {
		boolean ret = false;
		str += character;
		if (str.isEmpty())
			ret = true;
		return ret;
	}

	public boolean touchDown(int x, int y, int pointer, int button) {
		boolean ret = false;

		if (Gdx.input.isButtonPressed(button)) {
			lastPosition.x = x;
			lastPosition.y = y;
			for (int i = 0; i <= pointer; i++) {
				this.button[i] = button;
			}
			ret = true;
		}
		return ret;
	}

	public boolean touchUp(int x, int y, int pointer, int button) {
		boolean ret;

		if (Gdx.input.isButtonPressed(button)) {
			ret = false;
		} else {
			for (int i = 0; i <= pointer; i++) {
				this.button[i] = button;
			}
			ret = true;
			lastPosition.x = x;
			lastPosition.y = y;
		}
		return ret;
	}

	public boolean touchDragged(int x, int y, int pointer) {
		boolean ret = false;

		for (int i = 0; i <= pointer; i++) {
			ret = touchDown(x, y, pointer, button[i]);
			if (ret = true) {
				lastPosition.x = x;
				lastPosition.y = y;
			} else {
				ret = false;
			}
		}
		return ret;
	}

	public boolean touchMoved(int x, int y) {
		boolean ret = false;
		if (shape.x != x && shape.y != y) {
			ret = true;
		}
		return ret;
	}

	public boolean scrolled(int amount) {
		if (mouseEvent.getWheelRotation() > 0) {
			scroll = amount;
		}
		return false;
	}

	public boolean mouseMoved(int screenX, int screenY) {
		boolean ret = false;

		if (mouseEvent.getPoint().x != screenX
				|| mouseEvent.getPoint().y != screenY) {
			lastPosition.x = shape.x;
			lastPosition.y = shape.y;
			ret = true;
		}
		return ret;
	}
}
