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
	Texture hurtMap;
	TextureAtlas textureAtlas;
	boolean hurt = false;
	BitmapFont font;

	OrthographicCamera camera;

	float stateTime;
	Animation<TextureRegion> auberRun;
	Animation<TextureRegion> auberRunHurt;
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
		hurtMap = new Texture("GameMapHurt.png");
		textureAtlas = new TextureAtlas("spriteSheet1.txt");
		font = new BitmapFont();

		Gdx.graphics.setContinuousRendering(true);
		Gdx.graphics.setFullscreenMode(Gdx.graphics.getDisplayMode());


		auberRun = new Animation(0.3f, textureAtlas.findRegions("AuberSprite"));
		auberRun.setPlayMode(Animation.PlayMode.LOOP);
		auberRunHurt = new Animation(0.5f, textureAtlas.findRegions("AuberHurtSprite"));
		auberRunHurt.setPlayMode(Animation.PlayMode.LOOP);


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

        Component[] listA = new Component[]{new Player(101, auberRun), new Location(201, new Point(20, 440))};
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

        for(Entity enemy : enemies){
            enemy.getComponent(0).DecideObjective();
        }
	}

	@Override
	public void render () {
		Gdx.gl.glClearColor(1, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		stateTime += Gdx.graphics.getDeltaTime();
		TextureRegion currentFrame = auberRun.getKeyFrame(stateTime);


		if(Auber.getComponent(0).getHealth() < 50) {
			Auber.getComponent(0).setSprites(auberRunHurt);
			hurt = true;
		}
		else if(Auber.getComponent(0).getHealth()>50){
			Auber.getComponent(0).setSprites(auberRun);
			hurt = false;
		}

		for(Entity e : enemies){
			if(checkCollide(Auber.getComponent(1).getLocation(), e.getComponent(1).getLocation(), 20) && ((int)(Math.random()*10) > 6) && !(e.getComponent(0).getCaught()))
			{
				Auber.getComponent(0).setHealth(Auber.getComponent(0).getHealth() - ((int)(Math.random()*5)));
			}
		}

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
				if(checkCollide(Auber.getComponent(1).getLocation(), e.getComponent(1).getLocation(), 20)){
					ent = e;
				}
			}
			if(ent.getEntityID() != -1){
				ent.getComponent(1).setLocation(new Point(20 + ((int)(Math.random()*60)), 400 - ((int)(Math.random()*40))));
				ent.getComponent(0).setSprites(anims.get(anims.indexOf(ent.getComponent(0).getSprites()) + 1));
				ent.getComponent(0).setCaught(true);
				Auber.getComponent(1).setLocation(new Point(20, 440));
			}
		}
		if(Gdx.input.isKeyPressed(Input.Keys.E)){
			if(checkCollide(Auber.getComponent(1).getLocation(), new Point(55, 250), 20)){
				Auber.getComponent(1).setLocation(new Point(455, 150));
			}
			else if(checkCollide(Auber.getComponent(1).getLocation(), new Point(460, 145), 20)){
				Auber.getComponent(1).setLocation(new Point(50, 255));
			}
			else if(checkCollide(Auber.getComponent(1).getLocation(), new Point(520, 440), 20)){
				Auber.getComponent(1).setLocation(new Point(513, 65));
			}
			else if(checkCollide(Auber.getComponent(1).getLocation(), new Point(515,60), 20)){
				Auber.getComponent(1).setLocation(new Point(517, 443));
			}
			else{
				Auber.getComponent(1).setLocation(new Point(250, 360));
				Auber.getComponent(0).setHealth(100);
			}
		}

        for(Entity enemy : enemies){

        	Point target = enemy.getComponent(0).GetObjective();

        	if(!enemy.getComponent(0).getCaught()){

       			if(enemy.getComponent(1).getLocation().x < target.x){
					enemy.getComponent(1).setX(enemy.getComponent(1).getLocation().x + 1);
				}
				if(enemy.getComponent(1).getLocation().x > target.x){
					enemy.getComponent(1).setX(enemy.getComponent(1).getLocation().x - 1);
				}
				if(enemy.getComponent(1).getLocation().y < target.y){
					enemy.getComponent(1).setY(enemy.getComponent(1).getLocation().y + 1);
				}
				if(enemy.getComponent(1).getLocation().y > target.y){
					enemy.getComponent(1).setY(enemy.getComponent(1).getLocation().y - 1);
				}
        	}
        }


        batch.begin();
		batch.draw(gameMap, 0, 0, 641, 480);
		font.draw(batch, Integer.toString(Auber.getComponent(0).getHealth()), 1, 479);

        batch.draw(Auber.getComponent(0).getSprites().getKeyFrame(stateTime), Auber.getComponent(1).getLocation().x, Auber.getComponent(1).getLocation().y);
		batch.draw(InfiltratorBase1.getComponent(0).getSprites().getKeyFrame(stateTime), InfiltratorBase1.getComponent(1).getLocation().x, InfiltratorBase1.getComponent(1).getLocation().y);
        batch.draw(InfiltratorBase2.getComponent(0).getSprites().getKeyFrame(stateTime), InfiltratorBase2.getComponent(1).getLocation().x, InfiltratorBase2.getComponent(1).getLocation().y);
        batch.draw(InfiltratorBase3.getComponent(0).getSprites().getKeyFrame(stateTime), InfiltratorBase3.getComponent(1).getLocation().x, InfiltratorBase3.getComponent(1).getLocation().y);
        batch.draw(InfiltratorBase4.getComponent(0).getSprites().getKeyFrame(stateTime), InfiltratorBase4.getComponent(1).getLocation().x, InfiltratorBase4.getComponent(1).getLocation().y);
        batch.draw(InfiltratorBase5.getComponent(0).getSprites().getKeyFrame(stateTime), InfiltratorBase5.getComponent(1).getLocation().x, InfiltratorBase5.getComponent(1).getLocation().y);
        batch.draw(Infiltrator1.getComponent(0).getSprites().getKeyFrame(stateTime), Infiltrator1.getComponent(1).getLocation().x, Infiltrator1.getComponent(1).getLocation().y);
        batch.draw(Infiltrator2.getComponent(0).getSprites().getKeyFrame(stateTime), Infiltrator2.getComponent(1).getLocation().x, Infiltrator2.getComponent(1).getLocation().y);
        batch.draw(Infiltrator3.getComponent(0).getSprites().getKeyFrame(stateTime), Infiltrator3.getComponent(1).getLocation().x, Infiltrator3.getComponent(1).getLocation().y);

        if(hurt){
        	batch.draw(hurtMap,0, 0, 641, 480);
		}
		batch.end();
	}

	@Override
	public void dispose () {
        gameMap.dispose();
        textureAtlas.dispose();
        batch.dispose();
    }

    public boolean checkCollide(Point p1, Point p2, int size){
		if(Math.abs(p1.x - p2.x) < size && Math.abs(p1.y - p2.y) < 20){
			return true;
		}
		else{
			return false;
		}
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
        public abstract void DecideObjective();
        public abstract Point GetObjective();
        public abstract boolean getCaught();
        public abstract void setCaught(boolean arrest);
        public abstract void setX(int x);
        public abstract void setY(int y);
        public abstract int getHealth();
        public abstract void setHealth(int healthIn);

	}

	public abstract class Character extends Component {
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
	}

	public class Enemy extends Character{
		int enemyId;
        Point Target;
        boolean isCaught;

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
		public void setLocation(Point loc){}
		public void setX(int x){}
		public void setY(int y){}
		public int getHealth(){return -1;}
		public void setHealth(int healthIn){}
		public void setSprites(Animation<TextureRegion> anim){
			this.sprites = anim;
		}
		public boolean getCaught(){return isCaught;}
		public void setCaught(boolean arrest){this.isCaught = arrest;}
        public void DecideObjective() {
            int randX = (int)(Math.random()*600);
            int randY = (int)(Math.random()*450);
            this.Target  = new Point(randX,randY);
        }
        public Point GetObjective() {
		    if(this.isCaught) {
                return null;
            }
		    else {
                return this.Target;
            }
        }
	}

	public class Player extends Character{
		int health = 100;

		public Player(){}
		public Player(int i, Animation<TextureRegion> spriteSet){
			super(i, spriteSet);
		}

		public Point getLocation(){return null;}
		public void setLocation(Point locIn){}
		public void setX(int x){}
		public void setY(int y){}
		public Animation<TextureRegion> getSprites(){return super.getSprites();}
		public void setSprites(Animation<TextureRegion> anim){this.sprites = anim;}
		public int getHealth(){return health;}
		public void setHealth(int healthIn){this.health = healthIn;}
		public void DecideObjective() {};
		public Point GetObjective() { return null;}
		public  boolean getCaught(){return false;}
		public void setCaught(boolean arrest){}

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
		public void setX(int x){ this.location.x = x;}
		public void setY(int y){this.location.y = y;}
		public Animation<TextureRegion> getSprites(){return null;}
		public void setSprites(Animation<TextureRegion> anim){}
        public void DecideObjective() {};
        public Point GetObjective() { return null;}
        public  boolean getCaught(){return false;}
        public void setCaught(boolean arrest){}
        public int getHealth(){return -1;}
        public void setHealth(int healthIn){}
	}
}