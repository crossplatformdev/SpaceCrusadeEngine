package pw.whacka.spacecrusade;

import java.util.ArrayList;


import com.badlogic.gdx.graphics.GL10;
import com.gdxuser.util.MathUtil;

public class Builder {

	private static final int ISLAND_COUNT = 10;
	private static final int BOAT_COUNT = 3;
	private ArrayList<Island> islands = new ArrayList<Island>();
	public Island target;
	private ArrayList<Boat> boats = new ArrayList<Boat>();

	public void addIslands() {
		for (int i=0; i<ISLAND_COUNT; i++) {
			int x = MathUtil.random(16);
			int z = MathUtil.random(16);
			Island isle = new Island();
			isle.setPos(x,0,z);
			islands.add(isle);
		}
		
		target = new Island();
		target.setPos(8,0,8);
		islands.add(target);
	}

	public void addBoats() {
		for (int i=0; i<BOAT_COUNT; i++) {
			int x = MathUtil.random(16);
			int z = MathUtil.random(16);
			Boat boat = new Boat();
			boat.setPos(x,0,z);
			boats.add(boat);
		}		
	}

	public void render(GL10 gl, float delta) {
		for (Island isle : islands) {
			isle.render(gl, GL10.GL_TRIANGLES);
		}
		for (Boat boat : boats) {
			boat.render(gl, GL10.GL_TRIANGLES);
		}

	}



	
}
