package pw.whacka.spacecrusade;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.TimeUtils;

public class InputHandler extends InputAdapter {
	   
	
	//public Rectangle shape;
	public Vector3 touchPos;
	public boolean isHit = false;
	
	
	private float timer;
	

	InputHandler() {
		super();
		/*super(sprite,
				FRAME_ROWS = 2,
				FRAME_COLS = 2,
				animationPeriod = 0.033f,
				posX = 400,
				posY = 240,
				width = 15,
				height = 15,
				rotation = 0				
			);
		*/
		//Gdx.input.setCursorCatched(true);

		touchPos = new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0);
		//shape = new Rectangle (touchPos.x, touchPos.y, 15, 10);
		timer = 0.0f;
	}
	

	
	// Touch and press
	public boolean isTouched() {
		boolean ret = false;
		isHit = false;
		// Touchscreen event
		if (Gdx.input.isTouched()) {
			//touchPos.set(Gdx.input.getX(),Gdx.input.getY() , 0);
			//shape.x = Gdx.input.getX();
			//shape.y = 480 - Gdx.input.getY();
			//shape.width = Gdx.input.getDeltaX();
			//shape.height = Gdx.input.getDeltaY(); 

			//width = Gdx.input.getDeltaX();
			//height = Gdx.input.getDeltaY();
			TimeUtils.nanoTime();
			ret = true;
		}

		return ret;
	}
	
	// Touched once.
	public boolean justTouched() {
		boolean ret = false;
		if (Gdx.input.justTouched()) {
			timer = 0.0f;


			
			isHit = true;
			ret = true;
		}
		isHit = false;
		return ret;
	}
	
	public boolean onLongTouch () {
		boolean ret = false;
		if (Gdx.input.isTouched() && timer > 1000){
			timer = 0.0f;
			ret = true;
		}
		return ret;
	}
	
	public boolean onKeyboardEvent(int key) {
		// Keyboard event for browser and desktop
		boolean ret = false;
			if (Gdx.input.isKeyPressed(key)){
				timer = 0.0f;
				ret = true;
			}
		return ret;
	}
	
}
