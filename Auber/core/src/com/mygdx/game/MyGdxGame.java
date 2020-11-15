package com.mygdx.game;

import com.badlogic.gdx.*;
import com.badlogic.gdx.Graphics;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.Input;

import java.awt.*;
import java.util.ArrayList;

public class MyGdxGame extends ApplicationAdapter {
	SpriteBatch batch;
	Texture gameMap;
	TextureAtlas textureAtlas;

	OrthographicCamera camera;

	float stateTime;
	Animation<TextureRegion> auberRun;
	Animation<TextureRegion> infil1Run;
	Animation<TextureRegion> infil2Run;
	Animation<TextureRegion> infil3Run;
	Animation<TextureRegion> infilBase1Run;
	Animation<TextureRegion> infil1Arrest;
	Animation<TextureRegion> infil2Arrest;
	Animation<TextureRegion> infil3Arrest;
	Animation<TextureRegion> infilBase1Arrest;

	Entity InfiltratorBase1;
	Entity InfiltratorBase2;
	Entity InfiltratorBase3;
	Entity InfiltratorBase4;
	Entity InfiltratorBase5;
	Entity Infiltrator1;
	Entity Infiltrator2;
	Entity Infiltrator3;

	Entity Auber;

	ArrayList<Integer> components = new ArrayList<Integer>();
	ArrayList<Entity> enemies = new ArrayList<Entity>();
	ArrayList<Animation> anims  = new ArrayList<Animation>();


	//@Override
	public void create () {
		batch = new SpriteBatch();
		gameMap = new Texture("GameMap.jpg");
		textureAtlas = new TextureAtlas("spriteSheet1.txt");

		Gdx.graphics.setContinuousRendering(true);
		Gdx.graphics.setFullscreenMode(Gdx.graphics.getDisplayMode());


		auberRun = new Animation(0.3f, textureAtlas.findRegions("AuberSprite"));
		auberRun.setPlayMode(Animation.PlayMode.LOOP);


		infil1Run = new Animation(0.3f, textureAtlas.findRegions("Infiltrator1Sprite"));
		infil1Run.setPlayMode(Animation.PlayMode.LOOP);
		infil1Arrest = new Animation(1, textureAtlas.findRegion("Infiltrator1SpriteArrest"));
		anims.add(infil1Run);
		anims.add(infil1Arrest);
		infil2Run = new Animation(0.3f, textureAtlas.findRegions("Infiltrator2Sprite"));
		infil2Run.setPlayMode(Animation.PlayMode.LOOP);
		infil2Arrest = new Animation(1, textureAtlas.findRegion("Infiltrator2SpriteArrest"));
		anims.add(infil2Run);
		anims.add(infil2Arrest);
		infil3Run = new Animation(0.3f, textureAtlas.findRegions("Infiltrator3Sprite"));
		infil3Run.setPlayMode(Animation.PlayMode.LOOP);
		infil3Arrest = new Animation(1, textureAtlas.findRegion("Infiltrator3SpriteArrest"));
		anims.add(infil3Run);
		anims.add(infil3Arrest);
		infilBase1Run = new Animation(0.3f, textureAtlas.findRegions("InfiltratorSprite"));
		infilBase1Run.setPlayMode(Animation.PlayMode.LOOP);
		infilBase1Arrest = new Animation(1, textureAtlas.findRegion("InfiltratorSpriteArrest"));
		anims.add(infilBase1Run);
		anims.add(infilBase1Arrest);


		camera = new OrthographicCamera();
		camera.setToOrtho(false, 641, 700);

        Component[] listA = new Component[]{new Enemy(101, auberRun, 101), new Location(201, new Point(20, 440))};
        Auber = new Entity(listA, 101);

		Component[] listB1 = new Component[]{new Enemy(102, infilBase1Run, 102), new Location(202, new Point(615, 140))};
		InfiltratorBase1 = new Entity(listB1, 102);
		enemies.add(InfiltratorBase1);
        Component[] listB2 = new Component[]{new Enemy(103, infilBase1Run, 103), new Location(203, new Point(615, 160))};
        InfiltratorBase2 = new Entity(listB2, 103);
		enemies.add(InfiltratorBase2);
        Component[] listB3 = new Component[]{new Enemy(104, infilBase1Run, 104), new Location(204, new Point(615, 180))};
        InfiltratorBase3 = new Entity(listB3, 104);
		enemies.add(InfiltratorBase3);
        Component[] listB4 = new Component[]{new Enemy(105, infilBase1Run, 105), new Location(205, new Point(615, 200))};
        InfiltratorBase4 = new Entity(listB4, 105);
		enemies.add(InfiltratorBase4);
        Component[] listB5 = new Component[]{new Enemy(106, infilBase1Run, 106), new Location(206, new Point(615, 220))};
        InfiltratorBase5 = new Entity(listB5, 106);
		enemies.add(InfiltratorBase5);
        Component[] listI1 = new Component[]{new Enemy(107, infil1Run, 107), new Location(207, new Point(600, 140))};
        Infiltrator1 = new Entity(listI1, 107);
		enemies.add(Infiltrator1);
        Component[] listI2 = new Component[]{new Enemy(108, infil2Run, 108), new Location(208, new Point(600, 180))};
        Infiltrator2 = new Entity(listI2, 108);
		enemies.add(Infiltrator2);
        Component[] listI3 = new Component[]{new Enemy(109, infil3Run, 109), new Location(209, new Point(600, 220))};
        Infiltrator3 = new Entity(listI3, 109);
		enemies.add(Infiltrator3);
	}

	@Override
	public void render () {
		Gdx.gl.glClearColor(1, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		stateTime += Gdx.graphics.getDeltaTime();
		TextureRegion currentFrame = auberRun.getKeyFrame(stateTime);

		if(Gdx.input.isKeyPressed(Input.Keys.A)){
			Auber.getComponent(1).setLocation(new Point((Auber.getComponent(1).getLocation().x) - 1, (Auber.getComponent(1).getLocation().y)));
		}
		if(Gdx.input.isKeyPressed(Input.Keys.D)){
			Auber.getComponent(1).setLocation(new Point((Auber.getComponent(1).getLocation().x) + 1, (Auber.getComponent(1).getLocation().y)));
		}
		if(Gdx.input.isKeyPressed(Input.Keys.W)){
			Auber.getComponent(1).setLocation(new Point((Auber.getComponent(1).getLocation().x), (Auber.getComponent(1).getLocation().y) + 1));
		}
		if(Gdx.input.isKeyPressed(Input.Keys.S)){
			Auber.getComponent(1).setLocation(new Point((Auber.getComponent(1).getLocation().x), (Auber.getComponent(1).getLocation().y) - 1));
		}
		if(Gdx.input.isKeyPressed(Input.Keys.SPACE)){
			int AubX = Auber.getComponent(1).getLocation().x;
			int AubY = Auber.getComponent(1).getLocation().y;
			Entity ent = new Entity();

			for(Entity e : enemies){
				if(Math.abs(AubX - (e.getComponent(1).getLocation().x)) < 20 && Math.abs(AubY - (e.getComponent(1).getLocation().y)) < 20){
					ent = e;
				}
			}
			if(ent.getEntityID() != -1){
				ent.getComponent(1).setLocation(new Point(20, 400));
				ent.getComponent(0).setSprites(anims.get(anims.indexOf(ent.getComponent(0).getSprites()) + 1));
				Auber.getComponent(1).setLocation(new Point(20, 440));
			}
		}
		if(Gdx.input.isKeyPressed(Input.Keys.E)){
			Auber.getComponent(1).setLocation(new Point(250, 360));
		}

		batch.begin();
		batch.draw(gameMap, 0, 0, 641, 480);

        batch.draw(Auber.getComponent(0).getSprites().getKeyFrame(stateTime), Auber.getComponent(1).getLocation().x, Auber.getComponent(1).getLocation().y);
		batch.draw(InfiltratorBase1.getComponent(0).getSprites().getKeyFrame(stateTime), InfiltratorBase1.getComponent(1).getLocation().x, InfiltratorBase1.getComponent(1).getLocation().y);
        batch.draw(InfiltratorBase2.getComponent(0).getSprites().getKeyFrame(stateTime), InfiltratorBase2.getComponent(1).getLocation().x, InfiltratorBase2.getComponent(1).getLocation().y);
        batch.draw(InfiltratorBase3.getComponent(0).getSprites().getKeyFrame(stateTime), InfiltratorBase3.getComponent(1).getLocation().x, InfiltratorBase3.getComponent(1).getLocation().y);
        batch.draw(InfiltratorBase4.getComponent(0).getSprites().getKeyFrame(stateTime), InfiltratorBase4.getComponent(1).getLocation().x, InfiltratorBase4.getComponent(1).getLocation().y);
        batch.draw(InfiltratorBase5.getComponent(0).getSprites().getKeyFrame(stateTime), InfiltratorBase5.getComponent(1).getLocation().x, InfiltratorBase5.getComponent(1).getLocation().y);
        batch.draw(Infiltrator1.getComponent(0).getSprites().getKeyFrame(stateTime), Infiltrator1.getComponent(1).getLocation().x, Infiltrator1.getComponent(1).getLocation().y);
        batch.draw(Infiltrator2.getComponent(0).getSprites().getKeyFrame(stateTime), Infiltrator2.getComponent(1).getLocation().x, Infiltrator2.getComponent(1).getLocation().y);
        batch.draw(Infiltrator3.getComponent(0).getSprites().getKeyFrame(stateTime), Infiltrator3.getComponent(1).getLocation().x, Infiltrator3.getComponent(1).getLocation().y);

		batch.end();

	}

	@Override
	public void dispose () {
        gameMap.dispose();
        textureAtlas.dispose();
        batch.dispose();
    }



	public class Entity{
		Component[] components;
		int entityID;

		public Entity(){
			this.entityID = -1;
		}
		public Entity(Component[] components1, int id){
			this.components = components1;
			this.entityID = id;
		}

		public Component getComponent(int i){
			return components[i];
		}

		public int getEntityID(){
			return entityID;
		}
		public void setEntityID(int id){
			entityID = id;
		}
	}

	public abstract class Component{
		int componentId;


		public Component(){
		}

		public Component(int i){
			componentId = i;
			components.add(i);
		}

		public int getComponentId(){
			return componentId;
		}
		public void setComponentId(int id){
			componentId = id;
		}

		public abstract Animation<TextureRegion> getSprites();
		public abstract Point getLocation();
		public abstract void setLocation(Point p);
		public abstract void setSprites(Animation<TextureRegion> anim);

	}

	public class Character extends Component {
		Animation<TextureRegion> sprites;

		public Character() {
		}

		public Character(int i, Animation<TextureRegion> spriteSet) {
			super(i);
			this.sprites = spriteSet;
		}

		public Animation<TextureRegion> getSprites() {
			return sprites;
		}
		public Point getLocation(){return null;}
		public void setLocation(Point p){}
		public void setSprites(Animation<TextureRegion> anim){}
	}

	public class Enemy extends Character{
		int enemyId;

		public Enemy(){
		}
		public Enemy(int i, Animation<TextureRegion> spriteSet, int j){
			super(i, spriteSet);
			this.enemyId = j;
		}

		public Animation<TextureRegion> getSprites() {
			return super.getSprites();
		}
		public Point getLocation(){return null;}
		public void setSprites(Animation<TextureRegion> anim){
			this.sprites = anim;
		}
	}


	public class Location extends Component{
		Point location;

		public Location(){
		}
		public Location(int i, Point loc){
			super(i);
			this.location = loc;
		}

		public Point getLocation(){
			return this.location;
		}
		public void setLocation(Point locIn){
			this.location = locIn;
		}
		public Animation<TextureRegion> getSprites(){return null;}
		public void setSprites(Animation<TextureRegion> anim){}
	}


	public abstract class Controller extends Component{
		public Controller(){}

	}

//	public class UserInputs extends Controller implements InputProcessor{
//		public UserInputs(){}
//
//		public boolean keyDown(int i){return Input.isKeyDown(i);}
//
//		public Animation<TextureRegion> getSprites() { return null; }
//		public Point getLocation(){return null;}
//	}
}
