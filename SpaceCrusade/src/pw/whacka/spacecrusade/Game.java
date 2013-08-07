/**
 * 
 */
package pw.whacka.spacecrusade;
import java.util.Iterator;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.TimeUtils;

/**
 * @author stduser
 * 
 */
public class Game {
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public static float HEIGHT = Gdx.graphics.getHeight();
	public static float WIDTH = Gdx.graphics.getWidth(); 
	
	private Array<Graphic> displayedLayers;

	public Array<Item> itemsArray;
	public Array<UserDefined> userGraphics;
	public Array<Player> playersArray;
	public Array<Unit> unitsArray;
	public Map map;



	private static PerspectiveCamera camera;
	private SelectionHandler sh;
	//private Console console;
	private float timer;

	private Iterator<Item> itemsIterator;
	private Iterator<Player> playersIterator;	
	private Iterator<Unit> unitsIterator;

	private PerspectiveCamController controller;





	public Game() {
	
        //...Then set the window size or call pack...
       // setSize(300,300);

        //Set the window's location.
        //setLocation(xOffset*openFrameCount, yOffset*openFrameCount);
		
		//camera = new OrthographicCamera();
		//camera.setToOrtho(true, Game.WIDTH, Game.HEIGHT);
		camera = new PerspectiveCamera(45, WIDTH, HEIGHT);
		camera.near = 1;
		camera.far = 300;
		camera.position.set(0, 0, 5);
		camera.up.set(0, 0, 5);
		controller = new PerspectiveCamController(getCamera());
		Gdx.input.setInputProcessor(controller);
		
		sh = new SelectionHandler();
		
		itemsArray = new Array<Item>();
		itemsArray.add(new Item());
		playersArray = new Array<Player>();
		playersArray.add(new Player());
		unitsArray = new Array<Unit>();
		//unitsArray.add(new Unit());
		userGraphics = new Array<UserDefined>();
		
		
		playersIterator = playersArray.iterator();
		
		//selection = new Array<Unit>();
		
		itemsIterator = itemsArray.iterator();
		map = new Map();

		// prueba = new Graphic("data/libgdx.png", 2, 2, 0.025f, 400, 240, 128,
		// 128);
		//inputHandler = new InputHandler();
		timer = 0.0f;
	}

	public void render() {

		Gdx.gl.glClearColor(1, 1, 1, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT); // #14
		Gdx.gl.glEnable(GL20.GL_DEPTH_TEST);
		//obtainInput();

		// Cambiar el tiempo...
		
		if (TimeUtils.nanoTime() - timer > 1000000) {
			map.advanceMap();
		}

		if (TimeUtils.nanoTime() - timer > 50000000) {
			// if (itemsArray.iterator().hasNext()){
			itemsArray.add(new Item());
			// }
		}
		
		//map.render();
		sh.renderOn(Gdx.input.getX(), Gdx.input.getY(), sh.shape.width, sh.shape.height, 35);
		//sh.renderOn(Gdx.input.getX(),400 - Gdx.input.getY());
		//if (sh.inputHandler.isTouched()) sh.inputHandler.renderOn(sh.inputHandler.shape.x, sh.inputHandler.shape.y);
		// Renderizar unidades de los jugadores...
		for (Player player : playersArray){			
			for (Unit unit : player.units){
				unit.renderOn(unit.shape.x, 480 - unit.shape.y, 
						unit.shape.width, unit.shape.height,
						unit.shape.width/2, unit.shape.height/2);
									
				unitsArray.add(unit);
				if (TimeUtils.nanoTime() - timer > 330) {
					for (Ammo ammo : unit.getAmmo()) {
						ammo.moveMissile();
					}
				}			
				for (Item item : itemsArray) {
					item.renderRotating();
					item.shape.y -= 200 * Gdx.graphics.getDeltaTime();

					if (item.shape.overlaps(unit.shape)) {
						item.sound.play();
						Gdx.input.vibrate(100);
						itemsIterator.remove();
					}

					if (item.shape.y + 48 < 0)
						itemsIterator.remove();
				}

				if (unit.ammoIterator.hasNext()) {
					for (Ammo ammo : unit.getAmmo()) {
						ammo.renderRotating();
						if (ammo.shape.x + 21 > 800)
							unit.ammoIterator.remove();

						for (Item raindrop : itemsArray) {
							if (ammo.shape.overlaps(raindrop.shape)) {
								raindrop.sound.play();
								Gdx.input.vibrate(500);
								itemsIterator.remove();
								unit.ammoIterator.remove();
							}
						}
					}
				}
			}
		}

		
		
		
		sh.getInput(unitsArray);
		//sh.inputHandler.renderOn( 50, 50, 100, 200, 150, 150);
		getCamera().update();

		timer = TimeUtils.nanoTime();

	}

	public void dispose() {
		for(Player player : playersArray){
			for (Unit unit : player.units){
				unit.dispose();		
				for (Ammo ammo : unit.getAmmo()) {
					ammo.dispose();
				}
			}			
		}
		
		for (Item raindrop : itemsArray) {
			raindrop.dispose();
		}
		map.dispose();
		
		// prueba.dispose();
	}

	/**
	 * @return the camera
	 */
	public static PerspectiveCamera getCamera() {
		return camera;
	}

	/**
	 * @param camera
	 *            the camera to set
	 */
	public void setCamera(PerspectiveCamera camera) {
		Game.camera = camera;
	}

}
