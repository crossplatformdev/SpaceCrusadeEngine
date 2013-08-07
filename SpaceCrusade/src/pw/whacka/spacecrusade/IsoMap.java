package pw.whacka.spacecrusade;

import java.nio.FloatBuffer;
import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g3d.decals.CameraGroupStrategy;
import com.badlogic.gdx.graphics.g3d.decals.DecalBatch;
import com.badlogic.gdx.graphics.g3d.decals.GroupStrategy;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.BufferUtils;
import com.gdxuser.util.Billboard;
import com.gdxuser.util.Cube;
import com.gdxuser.util.DecalSprite;
import com.gdxuser.util.DemoWrapper;
import com.gdxuser.util.FloorGrid;
import com.gdxuser.util.GuOrthoCam;
import com.gdxuser.util.GuPerspCam;
import com.gdxuser.util.Log;
import com.gdxuser.util.MathUtil;
import com.gdxuser.util.MeshHelper;

public class IsoMap extends DemoWrapper {
	private static final Vector2 GRIDSIZE = new Vector2(16, 16);
	private static final float TILESIZE = 2f;
	private static final int NUMTILES = 5;
	private Cube cube;
	FloorGrid floor;
	private DecalBatch decalBatch;
	private SpriteBatch spriteBatch2d;
	private DecalSprite player;
	private Billboard cloud;
	private GroupStrategy strategy;
	// private DecalSprite[] walls = new DecalSprite[5];
	private DecalSprite wall;
	private ArrayList<DecalSprite> walls = new ArrayList<DecalSprite>();
	private ArrayList<DecalSprite> badges = new ArrayList<DecalSprite>();
	private ArrayList<DecalSprite> tiles = new ArrayList<DecalSprite>();
	private MeshHelper plane;
	private DecalSprite shadow;
	// camera
	private Camera cam;
	private GuOrthoCam oCam;
	private GuPerspCam pCam;
	private String camType = "ortho";
	private boolean debugRender = true;
	private Vector2 screenSize;
	private Vector2 last = new Vector2(0, 0);
	private Builder builder;

	@Override
	public void create() {
		Gdx.gl.glEnable(GL10.GL_DEPTH_TEST);
		Gdx.gl10.glDepthFunc(GL10.GL_LESS);

		builder = new Builder();
		builder.addIslands();
		builder.addBoats();

		screenSize = new Vector2(Gdx.graphics.getWidth(),
				Gdx.graphics.getHeight());

		oCam = new GuOrthoCam(screenSize.x, screenSize.y, GRIDSIZE);
		oCam.position.set(8, 5, -8);
		oCam.setTargetObj(builder.target);
		
		pCam = new GuPerspCam(50, 50, 50);

		cam = oCam; // so we can switch

		// put some basic furniture in
		floor = new FloorGrid(GRIDSIZE);

		String objfile = "data/3d/plane_tris.obj";
		plane = new MeshHelper(objfile);
		plane.scale(0.5f, 0.5f, 0.5f);
		plane.setPos(3, 1f, 5);
		plane.setColor(1, 1, 0);
		plane.setMotion(0,0,1, 0.02f);
		plane.setTexture();

		shadow = new DecalSprite().build("data/icons/shadow/128x64.png");
		shadow.sprite.setDimensions(1, 0.5f);
		shadow.sprite.setPosition(plane.pos.x + 1, 0.05f , plane.pos.z);
		shadow.sprite.rotateX(90);
		shadow.sprite.rotateZ(90);

		cube = new Cube();
		cube.scale(0.5f).setPos(0.5f, 0.5f, 0.5f).setColor(0, 1, 0);

		addWalls();

		// batches
		strategy = new CameraGroupStrategy(cam);
		decalBatch = new DecalBatch(strategy);
		spriteBatch2d = new SpriteBatch();
		spriteBatch2d.enableBlending();

		// some stuff that should face the camera
		badges = addBadges();
		addPlayer(); // after badges
		// addTiles();
		

		// 2d cloud sprite
		String imgPath = "data/icons/128/thunder.png";
		cloud = Billboard.make(imgPath);
		cloud.setMove(0.5f, 0f, 0f);
		cloud.wpos(2f, 1f, 2f);

		Gdx.input.setInputProcessor(this);
	}

	private void addPlayer() {
		// player = Billboard.make("data/players/full/128/avatar1.png");
		// player.wpos(2, 0, 2);

		player = new DecalSprite().build("data/players/full/128/avatar1.png");
		player.sprite.setDimensions(1, 1);
		player.sprite.setPosition(5, 0.5f, 5);
		badges.add(player);
	}

	private ArrayList<DecalSprite> addWalls() {
		// decals for walls
		/*
		 * wall = new DecalSprite().build("data/decals/256/blueflower.png");
		 * wall.sprite.setDimensions(2, 2); wall.sprite.setPosition(5, 1, 0);
		 * walls.add(wall);
		 */

		wall = new DecalSprite().build("data/decals/64/sakura.png");
		wall.sprite.setDimensions(2, 2);
		wall.sprite.setPosition(0, 1, GRIDSIZE.y);
		walls.add(wall);

		wall = new DecalSprite().build("data/decals/64/sakura.png");
		wall.sprite.setDimensions(2, 2);
		wall.sprite.setPosition(2, 1, GRIDSIZE.y);
		walls.add(wall);

		wall = new DecalSprite().build("data/decals/64/sakura.png");
		wall.sprite.setDimensions(2, 2);
		wall.sprite.setPosition(4, 1, GRIDSIZE.y);
		walls.add(wall);

		wall = new DecalSprite().build("data/decals/64/sakura.png");
		wall.sprite.rotateY(90);
		wall.sprite.setDimensions(2, 2);
		wall.sprite.setPosition(GRIDSIZE.x, 1, 1);
		walls.add(wall);

		wall = new DecalSprite().build("data/decals/64/sakura.png");
		wall.sprite.rotateY(90);
		wall.sprite.setDimensions(2, 2);
		wall.sprite.setPosition(GRIDSIZE.x, 1, 3);
		walls.add(wall);

		wall = new DecalSprite().build("data/decals/64/colormosaic.png");
		wall.sprite.rotateY(90);
		wall.sprite.setDimensions(2, 2);
		wall.sprite.setPosition(GRIDSIZE.x, 1, 3);
		walls.add(wall);

		wall = new DecalSprite().build("data/decals/64/colormosaic.png");
		wall.sprite.rotateY(90);
		wall.sprite.setPosition(GRIDSIZE.x, 1, 6);
		wall.sprite.setDimensions(2, 2);
		walls.add(wall);

		return walls;
	}

	private ArrayList<DecalSprite> addBadges() {

		for (int n = 0; n < 6; n++) {
			DecalSprite badge = new DecalSprite().build("data/badges/128/badge"
					+ n + ".png");
			badge.sprite.setDimensions(1, 1);
			float x = MathUtil.random(8) + 1;
			float y = MathUtil.random(8) + 1;
			if (x == 5 || y == 5) {
				continue;
			}
			badge.sprite.setPosition(x, 0.6f, y);

			// make the Badges always facing the camera
			badge.faceCamera(cam);

			badges.add(badge);
		}

		return badges;
	}

	// builds floor into tiles array
	private ArrayList<DecalSprite> addTiles() {
		float offset = 0.5f * TILESIZE;
		String[] tilenames = { "blue-mosaic", "grey-mosaic", "rusty-mosaic",
				"rusty", "hex", "hex" };
		for (int x = 0; x < NUMTILES; x++) {
			for (int y = 0; y < NUMTILES; y++) {
				String tilename = MathUtil.randomElem(tilenames);

				DecalSprite tile = new DecalSprite().build("data/tiles/128/"
						+ tilename + ".jpeg");
				tile.sprite.setDimensions(TILESIZE, TILESIZE);
				tile.sprite.rotateX(90);
				tile.sprite.setPosition(x * TILESIZE + offset, -0.01f, y
						* TILESIZE + offset);
				tiles.add(tile);
			}
		}
		return tiles;
	}

	@Override
	public void render() {
		GL10 gl = Gdx.app.getGraphics().getGL10();
		// gl.glClearColor(0.5f, 0.5f, 0.5f, 1);
		gl.glClear(GL10.GL_COLOR_BUFFER_BIT | GL10.GL_DEPTH_BUFFER_BIT);

		float delta = Gdx.graphics.getDeltaTime();

		cam.update();
		cam.apply(gl);

		setUpLighting(gl);
		// gl.glColor4f(1, 1, 0, 1f);
		plane.update(delta);
		plane.render(gl, GL10.GL_TRIANGLES);
		builder.render(gl, delta);
		
		shadow.sprite.setPosition(plane.pos.x + 1f, 0.1f, plane.pos.z + 0.5f);

		// the floor grid...
		gl.glPushMatrix();

		gl.glColor4f(0, 1, 0, 1f);
		floor.render(gl, GL10.GL_LINE_STRIP);
		gl.glColor4f(1, 0, 0, 1f);
		cube.render(gl, GL10.GL_TRIANGLES);

		gl.glPopMatrix();

		for (DecalSprite oneWall : walls) {
			decalBatch.add(oneWall.sprite);
		}

		for (DecalSprite oneBadge : badges) {
			oneBadge.faceCamera(cam);
			decalBatch.add(oneBadge.sprite);
		}

		for (DecalSprite tile : tiles) {
			decalBatch.add(tile.sprite);
		}
		
		decalBatch.add(shadow.sprite);

		decalBatch.flush();

		cam.update();
		cam.apply(gl);
		// decalBatch.add(player.sprite);
		decalBatch.flush();

		cam.update();
		cam.apply(gl);
		
		// turn off lighting for 2d sprites
		gl.glDisable(GL10.GL_LIGHTING);
		drawClouds(gl, delta);

		gl.glPopMatrix();

		oCam.handleKeys();

	}

	private void setUpLighting(GL10 gl) {
		// turns on lighting
		gl.glEnable(GL10.GL_LIGHTING);

		// Enable up to n=8 light sources: GL_LIGHTn
		gl.glEnable(GL10.GL_LIGHT0);
		gl.glEnable(GL10.GL_LIGHT1);
		gl.glEnable(GL10.GL_LIGHT2);

		// setting specular light color like a halogen spot
		FloatBuffer spotDir = BufferUtils.newFloatBuffer(8);
		spotDir.put(new float[] { -2, -2, -2 });
		float[] spotColor = new float[] { 0.9f, 0.9f, 0.9f, 1f };
		float[] spotPos = new float[] { 10, 3, 15, 1 };
		gl.glLightfv(GL10.GL_LIGHT0, GL10.GL_SPECULAR, spotColor, 0);
		gl.glLightfv(GL10.GL_LIGHT0, GL10.GL_POSITION, spotPos, 0);
		gl.glLightfv(GL10.GL_LIGHT0, GL10.GL_SPOT_DIRECTION, spotDir);

		// defines how light amount reduces if model gets away from light source
		// GL_CONSTANT_ATTENUATION, GL_LINEAR_ATTENUATION,
		// GL_QUADRATIC_ATTENUATION
		gl.glLightf(GL10.GL_LIGHT0, GL10.GL_CONSTANT_ATTENUATION, .05f);

		// spotlight
		// high exponent values makes light cone's center brighter
		FloatBuffer fb_pos = BufferUtils.newFloatBuffer(8);
		fb_pos.put(new float[] { 15, 15, 15, 1 });
		FloatBuffer fb_dir = BufferUtils.newFloatBuffer(8);
		fb_dir.put(new float[] { -2, -2, -2 });
		gl.glLightfv(GL10.GL_LIGHT1, GL10.GL_POSITION, fb_pos);
		gl.glLightfv(GL10.GL_LIGHT1, GL10.GL_SPOT_DIRECTION, fb_dir);
		float angle = 30;
		gl.glLightf(GL10.GL_LIGHT1, GL10.GL_SPOT_CUTOFF, angle);

		// ambient light
		float amb1 = 0.8f;
		float[] amb = new float[] { amb1, amb1, amb1, 1f };
		gl.glLightf(GL10.GL_LIGHT2, GL10.GL_CONSTANT_ATTENUATION, .05f);
		gl.glLightfv(GL10.GL_LIGHT2, GL10.GL_AMBIENT, amb, 0);

		// pitch black
		// gl.glLightfv(GL10.GL_LIGHT2, GL10.GL_AMBIENT, new float[]{0.005f,
		// 0.005f, 0.005f, 1f}, 0);

		// no effect
		// float global_ambient[] = { 0.5f, 0.5f, 0.5f, 1.0f };
		// gl.glLightfv(GL10.GL_LIGHT2, GL10.GL_LIGHT_MODEL_AMBIENT,
		// global_ambient, 0);

	}

	private void drawClouds(GL10 gl, float delta) {
		gl.glPushMatrix();
		cloud.project(cam);
		cloud.update(delta);

		spriteBatch2d.begin();
		cloud.setPosition(cloud.spos.x, cloud.spos.y);
		cloud.draw(spriteBatch2d, 0.8f);
		spriteBatch2d.end();
		gl.glPopMatrix();
	}

	public boolean touchDown(int x, int y, int pointer, int button) {
		Log.out("touched:" + x + ", " + y);
		return false;
	}

	// TODO - implement proper drag rotation
	@Override
	public boolean touchDragged(int x, int y, int pointer) {
		// Log.out("dragged:",x,y);
		float delta = Gdx.graphics.getDeltaTime();
		float dir;
		dir = x - last.x;
		Log.out("dir=" + dir);
		last.x = x;

//		if (x < last.x) {
//			dir = 1;
//		} else {
//			dir = -1;
//		}
//		last.x = x;

		spinCam(dir, delta);
		return false;
	}

	// FIXME - fix java too. ocam and pcam are subclasses of different types
	// so cannot share the same behavior unless we could monkeypatch
	// the parent class of both camera is abstrac tho. >,<
	// hence the ugly code below since polymorphism isnt real
	// interfaces dont help either so need some much more complex pattern
	// ruby modules + monkeypatch would fix this
	private void spinCam(float dir, float delta) {
		if (camType == "ortho") {
			oCam.spin(dir, delta);
		} else {
			pCam.spin(dir, delta);
		}
	}

	@Override
	public boolean keyDown(int keyCode) {
		boolean f = false;

		switch (keyCode) {

		case Keys.C:
			if (camType == "ortho") {
				camType = "persp";
				cam = pCam;
			} else {
				camType = "ortho";
				cam = oCam;
			}
			Log.out("set cam to:" + camType);
			f = true;
			break;

		case Keys.G:
			debugRender = !debugRender;
			f = true;
			break;

		case Keys.SPACE:
			f = true;
			break;

		default:
			Log.out("unknown key: super" + keyCode);
			return (super.keyDown(keyCode));
		}

		return f;
	}

}