package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Graphics;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.*;

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
	Animation<TextureRegion> infilBase2Run;
	Animation<TextureRegion> infilBase3Run;
	Animation<TextureRegion> infilBase4Run;
	Animation<TextureRegion> infilBase5Run;

	ArrayList<Integer> components = new ArrayList<Integer>();


	@Override
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
		infil2Run = new Animation(0.3f, textureAtlas.findRegions("Infiltrator2Sprite"));
		infil2Run.setPlayMode(Animation.PlayMode.LOOP);
		infil3Run = new Animation(0.3f, textureAtlas.findRegions("Infiltrator3Sprite"));
		infil3Run.setPlayMode(Animation.PlayMode.LOOP);
		infilBase1Run = new Animation(0.3f, textureAtlas.findRegions("InfiltratorSprite"));
		infilBase1Run.setPlayMode(Animation.PlayMode.LOOP);
		infilBase2Run = new Animation(0.3f, textureAtlas.findRegions("InfiltratorSprite"));
		infilBase2Run.setPlayMode(Animation.PlayMode.LOOP);
		infilBase3Run = new Animation(0.3f, textureAtlas.findRegions("InfiltratorSprite"));
		infilBase3Run.setPlayMode(Animation.PlayMode.LOOP);
		infilBase4Run = new Animation(0.3f, textureAtlas.findRegions("InfiltratorSprite"));
		infilBase4Run.setPlayMode(Animation.PlayMode.LOOP);
		infilBase5Run = new Animation(0.3f, textureAtlas.findRegions("InfiltratorSprite"));
		infilBase5Run.setPlayMode(Animation.PlayMode.LOOP);

		camera = new OrthographicCamera();
		camera.setToOrtho(false, 641,700 );


		//	for i in textureAtlas.findRegions("InfiltratorSprite"){

		//	}
		//	Entity InfiltratorBase = new Entity([new Enemy(102, for i in textureAtlas.findRegions("InfiltratorSprite"), 102)] )
	}

	@Override
	public void render () {
		Gdx.gl.glClearColor(1, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		stateTime += Gdx.graphics.getDeltaTime();
		TextureRegion currentFrame = auberRun.getKeyFrame(stateTime);

		batch.begin();
		batch.draw(gameMap, 0, 0, 641, 480);

		batch.draw(auberRun.getKeyFrame(stateTime), 20, 440);
		batch.draw(infil1Run.getKeyFrame(stateTime), 600, 140);
		batch.draw(infil2Run.getKeyFrame(stateTime), 600, 180);
		batch.draw(infil3Run.getKeyFrame(stateTime), 600, 220);
		batch.draw(infilBase1Run.getKeyFrame(stateTime), 615, 140);
		batch.draw(infilBase2Run.getKeyFrame(stateTime), 615, 160);
		batch.draw(infilBase3Run.getKeyFrame(stateTime), 615, 180);
		batch.draw(infilBase4Run.getKeyFrame(stateTime), 615, 200);
		batch.draw(infilBase5Run.getKeyFrame(stateTime), 615, 220);

		batch.end();

	}

	@Override
	public void dispose () {
		batch.dispose();
		gameMap.dispose();
		textureAtlas.dispose();
	}


	public class Entity{
		Component[] components;
		int entityID;

		public Entity(){
		}
		public Entity(Component[] components1){
			this.components = components1;
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

	public class Component{
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

	}

	public class Character extends Component{
		TextureRegion sprites;

		public Character(){
		}
		public Character(int i, TextureRegion spriteSet){
			super(i);
			this.sprites = spriteSet;
		}

		public TextureRegion getSprites(){return sprites;}
		public void getSprites(TextureRegion newSprites){sprites = newSprites;}
	}

	public class Enemy extends Character{
		int enemyId;

		public Enemy(){
		}
		public Enemy(int i, TextureRegion spriteSet, int j){
			super(i, spriteSet);
			this.enemyId = j;
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
	}

	public class System extends Component{
		boolean active;
		
		public System() {
		}
		
		public System(int i,boolean activeBool) {
			super(i);
			this.active = activeBool;
		}
		
		public boolean isActive() {
			return this.active;
		}
		
		public void setActive(boolean activeBool) {
			this.active = activeBool;
		}
		
		
	}
	
	public class Player extends Character{
		public Player() {
			
		}
		
		public Player(int i, TextureRegion spriteSet) {
			super(i,spriteSet);
		}
		
		public void RoomTeleporters() {
			
		}
		
		public void Arrest(Enemy enemy) {
			
		}
	}
	
	public class Controller extends Component {
		public Controller() {
			
		}
		
		public Controller(int i) {
			super(i);
		}
	}
	
	public class AI extends Controller{
		public AI() {
			
		}
		
		public void PathToObjective(Point p) {
			
		}
		
		public void DecideObjective() {
			
		}
	}
	
	public class UserInput extends Controller{
		
	}
}
