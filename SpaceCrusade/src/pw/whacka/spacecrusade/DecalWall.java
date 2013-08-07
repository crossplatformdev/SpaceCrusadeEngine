package pw.whacka.spacecrusade;
import java.util.ArrayList;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g3d.decals.CameraGroupStrategy;
import com.badlogic.gdx.graphics.g3d.decals.DecalBatch;
import com.badlogic.gdx.graphics.g3d.decals.GroupStrategy;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.gdxuser.util.Billboard;
import com.gdxuser.util.Cube;
import com.gdxuser.util.DecalSprite;
import com.gdxuser.util.DemoWrapper;
import com.gdxuser.util.FloorGrid;
import com.gdxuser.util.GuOrthoCam;
import com.gdxuser.util.Log;

public class DecalWall extends DemoWrapper implements ApplicationListener {
        GL10 gl;
       
        private static final Vector2 fieldSize = new Vector2(10, 10);
        float w;
        float h;
        private GuOrthoCam oCam;
        private Cube cube;
        FloorGrid floor;
        private DecalBatch decalBatch;
        private SpriteBatch spriteBatch2d;
        private Billboard player;
        private Billboard cloud;
        private Vector3 ppos;
        private int ctr = 0;
        private GroupStrategy strategy;
        // private DecalSprite[] walls = new DecalSprite[5];
        private DecalSprite wall;
        private ArrayList<DecalSprite> walls = new ArrayList<DecalSprite>();
        private ArrayList<DecalSprite> badges = new ArrayList<DecalSprite>();


        @Override
        public void create() {
                gl = Gdx.gl10;
               
                gl.glEnable(GL10.GL_DEPTH_TEST);
                gl.glDepthFunc(GL10.GL_LESS);
                gl.glClearColor(0.5f, 0.5f, 0.5f, 1);

                w = Gdx.graphics.getWidth();
                h = Gdx.graphics.getHeight();

                oCam = new GuOrthoCam(w, h, fieldSize);
                // oCam.position.set(-GRID_SIZE, GRID_SIZE, GRID_SIZE * 2);
                // oCam.lookAt(GRID_SIZE / 2, 0, GRID_SIZE / 2);
                // oCam.lookAt(0, 0, 0);

                // put some basic furniture in
                floor = new FloorGrid(fieldSize);

                // floorMesh = new MeshHelper("data/3d/floorplan.obj");

                cube = new Cube();
                cube.scale(0.5f).setPos(0f, 0.5f, 0f).setColor(0, 1, 0);

                // decals for walls
                wall = new DecalSprite().build("images/aliendreadnaught.gif");
                wall.sprite.setDimensions(6, 6);
                wall.sprite.setPosition(5, 3, 0);
                walls.add(wall);

                wall = new DecalSprite().build("windowsmanager/background.png");
                wall.sprite.setDimensions(2, 2);
                wall.sprite.setPosition(0, 1, fieldSize.y);
                walls.add(wall);

                wall = new DecalSprite().build("windowsmanager/background.png");
                wall.sprite.setDimensions(2, 2);
                wall.sprite.setPosition(2, 1, fieldSize.y);
                walls.add(wall);

                wall = new DecalSprite().build("windowsmanager/background.png");
                wall.sprite.setDimensions(2, 2);
                wall.sprite.setPosition(4, 1, fieldSize.y);
                walls.add(wall);

                wall = new DecalSprite().build("windowsmanager/background.png");
                wall.sprite.rotateY(90);
                wall.sprite.setDimensions(2, 2);
                wall.sprite.setPosition(fieldSize.x, 1, 1);
                walls.add(wall);

                wall = new DecalSprite().build("windowsmanager/background.png");
                wall.sprite.rotateY(90);
                wall.sprite.setDimensions(2, 2);
                wall.sprite.setPosition(fieldSize.x, 1, 3);
                walls.add(wall);

                wall = new DecalSprite().build("images/Alieno.jpg");
                wall.sprite.rotateY(90);
                wall.sprite.setDimensions(2, 2);
                wall.sprite.setPosition(fieldSize.x, 3, 3);
                walls.add(wall);

                wall = new DecalSprite().build("images/Alieno.jpg");
                wall.sprite.rotateY(90);
                wall.sprite.setPosition(fieldSize.x, 2, 6);
                wall.sprite.setDimensions(4, 4);
                walls.add(wall);

               
                // some stuff that should face the camera
                badges = getBadges();
               

                // 2d player
                // player = new
                // DecalSprite().build("data/players/full/128/avatar1.png");
                // player.sprite.setDimensions(4, 4);
                // player.sprite.setPosition(4, 2, 2);

                player = Billboard.make("images/Alieno.jpg");
                player.wpos(2, 0, 2);

                // 2d cloud sprite
                String imgPath = "images/Alieno.jpg";
                cloud = Billboard.make(imgPath);
                cloud.setMove(1f,0f,0f);
                cloud.wpos(2f, 1f, 2f);

                // batches
                strategy = new CameraGroupStrategy(oCam);              
                decalBatch = new DecalBatch(strategy);
                spriteBatch2d = new SpriteBatch();
                spriteBatch2d.enableBlending();
        }

        private ArrayList<DecalSprite> getBadges() {
                for(int x=2; x<10; x+=2) {
                        DecalSprite badge = new DecalSprite().build("images/Alieno.jpg");
                        badge.sprite.setDimensions(1, 1);
                        badge.sprite.setPosition(x, 1, 6);

                        // make the Badges always facing the camera
                        badge.faceCamera(oCam);

                        badges.add(badge);
                }

                return badges;
        }

        @Override
        public void render() {
                gl.glClear(GL10.GL_COLOR_BUFFER_BIT | GL10.GL_DEPTH_BUFFER_BIT);

                float delta = Gdx.graphics.getDeltaTime();

                oCam.update();
                oCam.apply(gl);

                // the floor grid...
                gl.glPushMatrix();
                gl.glColor4f(0, 1, 0, 1f);
                floor.render(gl, GL10.GL_LINE_STRIP);
                gl.glColor4f(1, 0, 0, 1f);
                cube.render(gl, GL10.GL_LINE_STRIP);
                gl.glPopMatrix();
               
               
                for (DecalSprite oneWall : walls) {
                        decalBatch.add(oneWall.sprite);
                }

                for (DecalSprite oneBadge : badges) {
                        oneBadge.faceCamera(oCam);
                        decalBatch.add(oneBadge.sprite);
                }
               
                decalBatch.flush();

                oCam.push();

                oCam.update();
                oCam.apply(gl);
                // decalBatch.add(player.sprite);
                decalBatch.flush();

                oCam.pop();
                oCam.update();
                oCam.apply(gl);


                drawClouds(gl, delta);

                gl.glPopMatrix();
                oCam.handleKeys();

        }

        private void drawClouds(GL10 gl, float delta) {
                gl.glPushMatrix();
                cloud.project(oCam);
                cloud.update(delta);

                player.project(oCam);
                player.update(delta);

                if (ctr++ % 100 == 0) {
                        // Log.out("ppos: " + ppos + "  player:");
                        // Log.out("cld: " + cld);
                        // Log.out("player: " + player.sprite.getPosition() );
                }
                spriteBatch2d.begin();
                cloud.setPosition(cloud.spos.x, cloud.spos.y);
                cloud.draw(spriteBatch2d, 0.8f);

                player.setPosition(player.spos.x, player.spos.y);
                player.draw(spriteBatch2d, 1);

                spriteBatch2d.end();
                gl.glPopMatrix();
        }

        public boolean touchDown(int x, int y, int pointer, int button) {
                Log.out("touched:" + x + y);
                return false;
        }

        @Override
        public boolean keyDown(int keyCode) {
                switch (keyCode) {

                case Keys.I:
                        Log.out("Key Info (I):");
                        Log.out("ESC = quit demo");
                        Log.out("----------------------");
                        Log.out("A, D = move cam left / right");
                        Log.out("W, S = move cam forward / backward");
                        Log.out("Q, E = yaw (turn) cam left / right");
                        Log.out("U, J = pitch cam up / down");
                        Log.out("H, K = roll cam counter-clockwise / clockwise");
                        Log.out("N, M = orbit cam around origin (TODO: player) counter-clockwise / clockwise");
                        Log.out("C, SPACE = print cam / player position");
                        Log.out("----------------------");
                        break;

                case Keys.C:
                        Log.out("cam_pos:  " + oCam.position);
                        Log.out("cam_up:   " + oCam.up);
                        Log.out("cam_dir:  " + oCam.direction);
                        break;

                case Keys.SPACE:
                        Log.out("ppos:" + ppos);
                        break;
                }
                return (super.keyDown(keyCode));
        }
       
        @Override
        public void dispose()
        {
                //Everything that we change in the openglState needs to be reverted to it original state
                //or some other tests might break
                Gdx.gl10.glDisable(GL10.GL_DEPTH_TEST);
                Gdx.gl10.glClearColor(0, 0, 0, 1);
        }

}
