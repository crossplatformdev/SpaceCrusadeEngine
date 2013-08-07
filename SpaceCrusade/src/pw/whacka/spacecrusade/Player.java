package pw.whacka.spacecrusade;

import java.util.Iterator;

import com.badlogic.gdx.utils.Array;

public class Player {

	public Array<Unit> units;
	private Iterator<Unit> unitIterator;
	
	public Player () {
		units = new Array<Unit>();
		units.add(new Unit());
		unitIterator = units.iterator();
	}


}
