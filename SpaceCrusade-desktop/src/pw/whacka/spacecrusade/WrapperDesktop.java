package pw.whacka.spacecrusade;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;

public class WrapperDesktop {
	public WrapperDesktop() {
		// TODO Auto-generated method stub
		new LwjglApplication(new IsoMap(), "IsoMap", 800, 480, true);
	
	}
}
