/**
 * 
 */
package pw.whacka.spacecrusade;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.TimeUtils;

/**
 * @author stduser
 * 
 */
public class Item extends Graphic {
	public float spawnTime;



	public Item() {
		super(sprite = "game/drop.png",
				  FRAME_ROWS = 1,
				  FRAME_COLS = 1,
				  animationPeriod = 0.000f,
				  posX = MathUtils.random(0, 800 - width),
				  posY = 480,
				  width = 48,
				  height = 60,
				  rotation = 0
			);
		spawnTime = 0.0f;
	}
	public Item(float angle) {
		super(sprite = "game/drop.png",
				  FRAME_ROWS = 1,
				  FRAME_COLS = 1,
				  animationPeriod = 0.000f,
				  posX = MathUtils.random(0, 800 - width),
				  posY = 480,
				  width = 48,
				  height = 60,
				  rotation = angle
			);
		spawnTime = 0.0f;
	}

	public void spawnRaindrop() {
		Rectangle shape = new Rectangle();
		shape.x = MathUtils.random(0, 800 - 48);
		shape.y = 480;
		shape.width = 60;
		shape.height = 48;
		this.shape = shape;
		spawnTime = TimeUtils.nanoTime();
	}

}
