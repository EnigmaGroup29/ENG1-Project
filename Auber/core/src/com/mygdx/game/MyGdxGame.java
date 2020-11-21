package com.mygdx.game;

import com.badlogic.gdx.*;
import com.badlogic.gdx.Graphics;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import java.awt.*;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;

import java.io.File;
public class MyGdxGame extends ApplicationAdapter {
	SpriteBatch batch;
	Sprite gameMap;
	Sprite hurtMap;
	Sprite infoScreen;
	Sprite paused;
	Sprite win;
	Sprite lose;
	Sprite confused;
	Sprite attack;
	Sprite down;
	TextureAtlas textureAtlas;
	boolean hurt = false;
	int damage = 0;
	BitmapFont font;
	Pixmap pixels;
	int charSpeed = 2;
	int teleDelay = 0;
	int gameState = 0;
	int arrestCount = 0;
	int isConfused = 0;
	int enemyInvis = 0;
	int exitDelay = 0;
	int systemsDown = 0;

	OrthographicCamera camera;
	Viewport viewport;

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

	ArrayList<Node> nodeList = new ArrayList<Node>();



	//@Override
	public void create () {
		batch = new SpriteBatch();
		gameMap = new Sprite(new Texture("GameMap1.jpg"));
		gameMap.setPosition(0,0);
		gameMap.setSize(1131,548);
		hurtMap = new Sprite(new Texture("GameMapHurt.png"));
		hurtMap.setPosition(0,0);
		hurtMap.setSize(1131, 548);
		infoScreen = new Sprite(new Texture("GameInfoScreen.png"));
		infoScreen.setPosition(0,0);
		infoScreen.setSize(1131,548);
		paused = new Sprite(new Texture("GameInfoPaused.png"));
		paused.setPosition(0,0);
		paused.setSize(1131,548);
		win = new Sprite(new Texture("GameWin.png"));
		win.setPosition(0,0);
		win.setSize(1131,548);
		lose = new Sprite(new Texture("GameLose.png"));
		lose.setPosition(0,0);
		lose.setSize(1131,548);
        confused = new Sprite(new Texture("GameMapConfused.png"));
        confused.setPosition(0,0);
        confused.setSize(1131,548);
        attack = new Sprite(new Texture("SystemAttack.png"));
        attack.setSize(30, 30);
        down = new Sprite(new Texture("SystemDown.png"));
        down.setSize(30, 30);
		textureAtlas = new TextureAtlas("spriteSheet1.txt");
		font = new BitmapFont();
		pixels = new Pixmap(Gdx.files.internal("GameMap1.jpg"));

		makeNodes();

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


		float aspectRatio = (float)Gdx.graphics.getHeight()/(float)Gdx.graphics.getWidth();

		camera = new OrthographicCamera(548 * aspectRatio, 548);
		camera.position.set(camera.viewportWidth/2,camera.viewportHeight/2,0);
		viewport = new FitViewport(1131, 548, camera);
		viewport.apply();

        Component[] listA = new Component[]{new Player(101, auberRun), new Location(201, new Point(30, 500))};
        Auber = new Entity(listA, 101);

		Component[] listB1 = new Component[]{new Enemy(102, infilBase1Run, 102, new Node (nodeList.get(25 - 1))), new Location(202, nodeList.get(25 - 1).getCoords())};
		InfiltratorBase1 = new Entity(listB1, 102);
		enemies.add(InfiltratorBase1);
		InfiltratorBase1.getComponent(0).DecideObjective();
        Component[] listB2 = new Component[]{new Enemy(103, infilBase1Run, 103, new Node (nodeList.get(29 - 1))), new Location(203, nodeList.get(29 - 1).getCoords())};
        InfiltratorBase2 = new Entity(listB2, 103);
		enemies.add(InfiltratorBase2);
		InfiltratorBase2.getComponent(0).DecideObjective();
        Component[] listB3 = new Component[]{new Enemy(104, infilBase1Run, 104, new Node (nodeList.get(32 - 1))), new Location(204, nodeList.get(32 - 1).getCoords())};
        InfiltratorBase3 = new Entity(listB3, 104);
		enemies.add(InfiltratorBase3);
		InfiltratorBase3.getComponent(0).DecideObjective();
		Component[] listB4 = new Component[]{new Enemy(105, infilBase1Run, 105, new Node (nodeList.get(33 - 1))), new Location(205, nodeList.get(33 - 1).getCoords())};
        InfiltratorBase4 = new Entity(listB4, 105);
		enemies.add(InfiltratorBase4);
		InfiltratorBase4.getComponent(0).DecideObjective();
        Component[] listB5 = new Component[]{new Enemy(106, infilBase1Run, 106, new Node(nodeList.get(30 - 1))), new Location(206, nodeList.get(30 - 1).getCoords())};
        InfiltratorBase5 = new Entity(listB5, 106);
		enemies.add(InfiltratorBase5);
		InfiltratorBase5.getComponent(0).DecideObjective();
        Component[] listI1 = new Component[]{new Enemy(107, infil1Run, 107, new Node(nodeList.get(24 - 1))), new Location(207, nodeList.get(24 - 1).getCoords())};
        Infiltrator1 = new Entity(listI1, 107);
		enemies.add(Infiltrator1);
		Infiltrator1.getComponent(0).DecideObjective();
        Component[] listI2 = new Component[]{new Enemy(108, infil2Run, 108, new Node(nodeList.get(35 - 1))), new Location(208, nodeList.get(35 - 1).getCoords())};
        Infiltrator2 = new Entity(listI2, 108);
		enemies.add(Infiltrator2);
		Infiltrator2.getComponent(0).DecideObjective();
        Component[] listI3 = new Component[]{new Enemy(109, infil3Run, 109, new Node(nodeList.get(34 - 1))), new Location(209, nodeList.get(34 - 1).getCoords())};
        Infiltrator3 = new Entity(listI3, 109);
		enemies.add(Infiltrator3);
		Infiltrator3.getComponent(0).DecideObjective();



        for(Entity enemy : enemies){
            enemy.getComponent(0).DecideObjective();
        }
	}

	@Override
	public void render () {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		stateTime += Gdx.graphics.getDeltaTime();
		int AubX = Auber.getComponent(1).getLocation().x;
        int AubY = Auber.getComponent(1).getLocation().y;

        if(teleDelay > 0)
		{teleDelay -= 1;}
        if(exitDelay > 0)
        {exitDelay -= 1;}
        if(isConfused > 0)
        {isConfused -= 1;}
        if(enemyInvis > 0)
        {enemyInvis -= 1;}
        for(Entity enemy: enemies)
        {
            if(enemy.getComponent(0).getDelay() > 0)
            {
                enemy.getComponent(0).setDelay(enemy.getComponent(0).getDelay() - 1);
            }
            if(enemy.getComponent(0).getHacking() > 0)
			{
				enemy.getComponent(0).setHacking(enemy.getComponent(0).getHacking() - 1);
			}
        }

        if(arrestCount >= 8){
        	gameState = 3;
		}
        else if(Auber.getComponent(0).getHealth() <= 0 || systemsDown >= 15) {
            gameState = 4;
        }

        if(gameState == 0 || gameState == 2) {
			if (Gdx.input.isKeyPressed(Input.Keys.SPACE)) {
				gameState = 1;
			}
			else if(Gdx.input.isKeyPressed(Input.Keys.ESCAPE) && exitDelay <= 0){
			    System.exit(0);
            }
		}
        if(gameState == 3)
		{
			//win state
            if(Gdx.input.isKeyPressed(Input.Keys.ESCAPE)){
                System.exit(0);
            }
		}
        if(gameState == 4)
		{
			//lose state
            if(Gdx.input.isKeyPressed(Input.Keys.ESCAPE)){
                System.exit(0);
            }
		}
		if(gameState == 1) {
			if (Auber.getComponent(0).getHealth() <= 50) {
				Auber.getComponent(0).setSprites(auberRunHurt);
				hurt = true;
			} else if (Auber.getComponent(0).getHealth() > 50) {
				Auber.getComponent(0).setSprites(auberRun);
				hurt = false;
			}

			if(checkCollide(Auber.getComponent(1).getLocation(), Infiltrator1.getComponent(1).getLocation(), 60) && teleDelay <= 0 && !Infiltrator1.getComponent(0).getCaught())
			{
				teleDelay = 600;
			}

			for (Entity e : enemies) {
				if (checkCollide(Auber.getComponent(1).getLocation(), e.getComponent(1).getLocation(), 20) && ((int) (Math.random() * 10) > 6) && !(e.getComponent(0).getCaught())) {
					int preHealth = Auber.getComponent(0).getHealth();
					Auber.getComponent(0).setHealth(Auber.getComponent(0).getHealth() - ((int) (Math.random() * 5)));
					if (Auber.getComponent(0).getHealth() <= 50 && preHealth > 50) {
						damage = 1;
					}
					if(e == Infiltrator3)
                    {
                        isConfused = 300;
                    }
				}
			}

			if(checkCollide(Auber.getComponent(1).getLocation(), Infiltrator2.getComponent(1).getLocation(), 50) && !Infiltrator2.getComponent(0).getCaught() && enemyInvis <= 0){
			    enemyInvis = 420;
            }


			if (Gdx.input.isKeyPressed(Input.Keys.A) && !checkWalls(AubX - 1, AubY) && !checkWalls(AubX - 1, AubY + 5) && !checkWalls(AubX - 1, AubY + 10) && !checkWalls(AubX - 1, AubY + 15) && !checkWalls(AubX - 1, AubY + 20) && !checkWalls(AubX - 2, AubY) && !checkWalls(AubX - 2, AubY + 5) && !checkWalls(AubX - 2, AubY + 10) && !checkWalls(AubX - 2, AubY + 15) && !checkWalls(AubX - 2, AubY + 20)) {
				Auber.getComponent(1).setLocation(new Point((Auber.getComponent(1).getLocation().x) - charSpeed, (Auber.getComponent(1).getLocation().y)));
			}
			if (Gdx.input.isKeyPressed(Input.Keys.D) && !checkWalls(AubX + 21, AubY) && !checkWalls(AubX + 21, AubY + 5) && !checkWalls(AubX + 21, AubY + 10) && !checkWalls(AubX + 21, AubY + 15) && !checkWalls(AubX + 21, AubY + 20) && !checkWalls(AubX + 22, AubY) && !checkWalls(AubX + 22, AubY + 5) && !checkWalls(AubX + 22, AubY + 10) && !checkWalls(AubX + 22, AubY + 15) && !checkWalls(AubX + 22, AubY + 20)) {
				Auber.getComponent(1).setLocation(new Point((Auber.getComponent(1).getLocation().x) + charSpeed, (Auber.getComponent(1).getLocation().y)));
			}
			if (Gdx.input.isKeyPressed(Input.Keys.W) && !checkWalls(AubX, AubY + 21) && !checkWalls(AubX + 5, AubY + 21) && !checkWalls(AubX + 10, AubY + 21) && !checkWalls(AubX + 15, AubY + 21) && !checkWalls(AubX + 20, AubY + 21) && !checkWalls(AubX, AubY + 22) && !checkWalls(AubX + 5, AubY + 22) && !checkWalls(AubX + 10, AubY + 22) && !checkWalls(AubX + 15, AubY + 22) && !checkWalls(AubX + 20, AubY + 22)) {
				Auber.getComponent(1).setLocation(new Point((Auber.getComponent(1).getLocation().x), (Auber.getComponent(1).getLocation().y) + charSpeed));
			}
			if (Gdx.input.isKeyPressed(Input.Keys.S) && !checkWalls(AubX, AubY - 1) && !checkWalls(AubX + 5, AubY - 1) && !checkWalls(AubX + 10, AubY - 1) && !checkWalls(AubX + 15, AubY - 1) && !checkWalls(AubX + 20, AubY - 1) && !checkWalls(AubX, AubY - 2) && !checkWalls(AubX + 5, AubY - 2) && !checkWalls(AubX + 10, AubY - 2) && !checkWalls(AubX + 15, AubY - 2) && !checkWalls(AubX + 20, AubY - 2)) {
				Auber.getComponent(1).setLocation(new Point((Auber.getComponent(1).getLocation().x), (Auber.getComponent(1).getLocation().y) - charSpeed));
			}

			if (Gdx.input.isKeyPressed(Input.Keys.SPACE)) {
				if (checkCollide(Auber.getComponent(1).getLocation(), new Point(921, 510), 30) && teleDelay <= 0) {
					Auber.getComponent(1).setLocation(new Point(914, 80));
					teleDelay = 300;
				} else if (checkCollide(Auber.getComponent(1).getLocation(), new Point(914, 80), 30) && teleDelay <= 0) {
					Auber.getComponent(1).setLocation(new Point(921, 510));
					teleDelay = 300;
				} else if (checkCollide(Auber.getComponent(1).getLocation(), new Point(814, 178), 30) && teleDelay <= 0) {
					Auber.getComponent(1).setLocation(new Point(96, 296));
					teleDelay = 300;
				} else if (checkCollide(Auber.getComponent(1).getLocation(), new Point(96, 296), 30) && teleDelay <= 0) {
					Auber.getComponent(1).setLocation(new Point(814, 178));
					teleDelay = 300;
				} else {
					Entity ent = new Entity();
					for (Entity e : enemies) {
						if (checkCollide(Auber.getComponent(1).getLocation(), e.getComponent(1).getLocation(), 20) && !e.getComponent(0).getCaught()) {
							ent = e;
						}
					}
					if (ent.getEntityID() != -1) {
						ent.getComponent(1).setLocation(new Point(30 + ((int) (Math.random() * 60)), 450 - ((int) (Math.random() * 40))));
						ent.getComponent(0).setSprites(anims.get(anims.indexOf(ent.getComponent(0).getSprites()) + 1));
						ent.getComponent(0).setCaught(true);
						arrestCount += 1;
						Auber.getComponent(1).setLocation(new Point(30, 500));
					}
				}
			}
			if (Gdx.input.isKeyPressed(Input.Keys.E) && teleDelay <= 0) {
				Auber.getComponent(1).setLocation(new Point(480, (int) (camera.viewportHeight - 120)));
				Auber.getComponent(0).setHealth(100);
				teleDelay = 300;
			}
			if (Gdx.input.isKeyPressed(Input.Keys.P)) {
				try {

					Path path = Paths.get("test.mp4");
					Desktop.getDesktop().open(new File(String.valueOf(path)));
				} catch (IOException e) {
					e.printStackTrace();
				}




			}
			if (Gdx.input.isKeyPressed(Input.Keys.ESCAPE)){
				gameState = 2;
                exitDelay = 30;
			}

			for (Entity enemy : enemies) {

				Node n = enemy.getComponent(0).getCurrent();
				if(enemy.getComponent(0).getObjective() == null) {
					enemy.getComponent(0).DecideObjective();
				}
				Node n1 = enemy.getComponent(0).getObjective();

				if(enemy.getComponent(0).getHacking() == 1)
				{
					enemy.getComponent(0).getCurrent().setStatus(4);
					enemy.getComponent(0).DecideObjective();
					systemsDown += 1;

				}

				if (n1.getName() != -1 && enemy.getComponent(0).getDelay() <= 0 && n != n1 && enemy.getComponent(0).getHacking() <= 0) {
					if (checkCollide(enemy.getComponent(1).getLocation(), n1.getCoords(), 6)) {
						if (n1.getStatus() == 2)
						{
							n1.setStatus(3);
							enemy.getComponent(0).setHacking(1500);
							enemy.getComponent(0).setPrevious(n);
							enemy.getComponent(0).setCurrent(n1);
						}
						else
						{
							enemy.getComponent(0).setPrevious(n);
							enemy.getComponent(0).setCurrent(n1);
							enemy.getComponent(0).DecideObjective();
							n1 = enemy.getComponent(0).getObjective();
							enemy.getComponent(0).setDelay(5);
						}
					}
					enemy.getComponent(0).setTargeting(true);
				} else {
					enemy.getComponent(0).DecideObjective();
					n1 = enemy.getComponent(0).getObjective();
				}

				Point target = n1.getCoords();

				if (!enemy.getComponent(0).getCaught() && enemy.getComponent(0).getTargeting() && enemy.getComponent(0).getDelay() <=0 && enemy.getComponent(0).getHacking() <= 0) {

					int enX = enemy.getComponent(1).getLocation().x;
					int enY = enemy.getComponent(1).getLocation().y;

					if (enemy.getComponent(1).getLocation().x < target.x&& !checkWalls(enemy.getComponent(1).getLocation().x + 21, enemy.getComponent(1).getLocation().y)&& !checkWalls(enemy.getComponent(1).getLocation().x + 21, enemy.getComponent(1).getLocation().y + 5)&& !checkWalls(enemy.getComponent(1).getLocation().x + 21, enemy.getComponent(1).getLocation().y + 10)&& !checkWalls(enemy.getComponent(1).getLocation().x + 21, enemy.getComponent(1).getLocation().y + 15)&& !checkWalls(enemy.getComponent(1).getLocation().x + 21, enemy.getComponent(1).getLocation().y + 20)&& !checkWalls(enemy.getComponent(1).getLocation().x + 22, enemy.getComponent(1).getLocation().y)&& !checkWalls(enemy.getComponent(1).getLocation().x + 22, enemy.getComponent(1).getLocation().y + 5)&& !checkWalls(enemy.getComponent(1).getLocation().x + 22, enemy.getComponent(1).getLocation().y + 10)&& !checkWalls(enemy.getComponent(1).getLocation().x + 22, enemy.getComponent(1).getLocation().y + 15)&& !checkWalls(enemy.getComponent(1).getLocation().x + 22, enemy.getComponent(1).getLocation().y + 20)) {
						enemy.getComponent(1).setLocation(new Point(enX + charSpeed, enY));
					}
					if (enemy.getComponent(1).getLocation().x > target.x&& !checkWalls(enemy.getComponent(1).getLocation().x - 1, enemy.getComponent(1).getLocation().y)&& !checkWalls(enemy.getComponent(1).getLocation().x - 1, enemy.getComponent(1).getLocation().y + 5)&& !checkWalls(enemy.getComponent(1).getLocation().x - 1, enemy.getComponent(1).getLocation().y + 10)&& !checkWalls(enemy.getComponent(1).getLocation().x - 1, enemy.getComponent(1).getLocation().y + 15)&& !checkWalls(enemy.getComponent(1).getLocation().x - 1, enemy.getComponent(1).getLocation().y + 20)&& !checkWalls(enemy.getComponent(1).getLocation().x - 2, enemy.getComponent(1).getLocation().y)&& !checkWalls(enemy.getComponent(1).getLocation().x - 2, enemy.getComponent(1).getLocation().y + 5)&& !checkWalls(enemy.getComponent(1).getLocation().x - 2, enemy.getComponent(1).getLocation().y + 10)&& !checkWalls(enemy.getComponent(1).getLocation().x - 2, enemy.getComponent(1).getLocation().y + 15)&& !checkWalls(enemy.getComponent(1).getLocation().x - 2, enemy.getComponent(1).getLocation().y + 20)) {
						enemy.getComponent(1).setLocation(new Point(enX - charSpeed, enY));
					}
					if (enemy.getComponent(1).getLocation().y < target.y&& !checkWalls(enemy.getComponent(1).getLocation().x, enemy.getComponent(1).getLocation().y + 21) && !checkWalls(enemy.getComponent(1).getLocation().x + 5, enemy.getComponent(1).getLocation().y + 21) && !checkWalls(enemy.getComponent(1).getLocation().x + 10, enemy.getComponent(1).getLocation().y + 21) && !checkWalls(enemy.getComponent(1).getLocation().x + 15, enemy.getComponent(1).getLocation().y + 21) && !checkWalls(enemy.getComponent(1).getLocation().x + 20, enemy.getComponent(1).getLocation().y + 22) && !checkWalls(enemy.getComponent(1).getLocation().x, enemy.getComponent(1).getLocation().y + 22) && !checkWalls(enemy.getComponent(1).getLocation().x + 5, enemy.getComponent(1).getLocation().y + 22) && !checkWalls(enemy.getComponent(1).getLocation().x + 10, enemy.getComponent(1).getLocation().y + 22) && !checkWalls(enemy.getComponent(1).getLocation().x + 15, enemy.getComponent(1).getLocation().y + 22) && !checkWalls(enemy.getComponent(1).getLocation().x + 20, enemy.getComponent(1).getLocation().y + 22)) {
						enemy.getComponent(1).setLocation(new Point(enX, enY + charSpeed));
					}
					if (enemy.getComponent(1).getLocation().y > target.y&& !checkWalls(enemy.getComponent(1).getLocation().x, enemy.getComponent(1).getLocation().y - 1) && !checkWalls(enemy.getComponent(1).getLocation().x + 5, enemy.getComponent(1).getLocation().y - 1) && !checkWalls(enemy.getComponent(1).getLocation().x + 10, enemy.getComponent(1).getLocation().y - 1) && !checkWalls(enemy.getComponent(1).getLocation().x + 15, enemy.getComponent(1).getLocation().y - 1) && !checkWalls(enemy.getComponent(1).getLocation().x + 20, enemy.getComponent(1).getLocation().y - 1) && !checkWalls(enemy.getComponent(1).getLocation().x, enemy.getComponent(1).getLocation().y - 2) && !checkWalls(enemy.getComponent(1).getLocation().x + 5, enemy.getComponent(1).getLocation().y - 2) && !checkWalls(enemy.getComponent(1).getLocation().x + 10, enemy.getComponent(1).getLocation().y - 2) && !checkWalls(enemy.getComponent(1).getLocation().x + 15, enemy.getComponent(1).getLocation().y - 2) && !checkWalls(enemy.getComponent(1).getLocation().x + 20, enemy.getComponent(1).getLocation().y - 2)) {
						enemy.getComponent(1).setLocation(new Point(enX, enY - charSpeed));
					}
				}
			}
		}

		camera.update();
        batch.setProjectionMatrix(camera.combined);
        batch.begin();
		gameMap.draw(batch);

		if(gameState == 3)
		{
			win.draw(batch);
		}
		else if(gameState == 4)
		{
			lose.draw(batch);
		}
		else {
			font.draw(batch, Integer.toString(Auber.getComponent(0).getHealth()), 1, 547);

			batch.draw(Auber.getComponent(0).getSprites().getKeyFrame(stateTime), Auber.getComponent(1).getLocation().x, Auber.getComponent(1).getLocation().y);
			batch.draw(InfiltratorBase1.getComponent(0).getSprites().getKeyFrame(stateTime), InfiltratorBase1.getComponent(1).getLocation().x, InfiltratorBase1.getComponent(1).getLocation().y);
			batch.draw(InfiltratorBase2.getComponent(0).getSprites().getKeyFrame(stateTime), InfiltratorBase2.getComponent(1).getLocation().x, InfiltratorBase2.getComponent(1).getLocation().y);
			batch.draw(InfiltratorBase3.getComponent(0).getSprites().getKeyFrame(stateTime), InfiltratorBase3.getComponent(1).getLocation().x, InfiltratorBase3.getComponent(1).getLocation().y);
			batch.draw(InfiltratorBase4.getComponent(0).getSprites().getKeyFrame(stateTime), InfiltratorBase4.getComponent(1).getLocation().x, InfiltratorBase4.getComponent(1).getLocation().y);
			batch.draw(InfiltratorBase5.getComponent(0).getSprites().getKeyFrame(stateTime), InfiltratorBase5.getComponent(1).getLocation().x, InfiltratorBase5.getComponent(1).getLocation().y);
			batch.draw(Infiltrator1.getComponent(0).getSprites().getKeyFrame(stateTime), Infiltrator1.getComponent(1).getLocation().x, Infiltrator1.getComponent(1).getLocation().y);
			if(enemyInvis <= 0) {
                batch.draw(Infiltrator2.getComponent(0).getSprites().getKeyFrame(stateTime), Infiltrator2.getComponent(1).getLocation().x, Infiltrator2.getComponent(1).getLocation().y);
            }
			batch.draw(Infiltrator3.getComponent(0).getSprites().getKeyFrame(stateTime), Infiltrator3.getComponent(1).getLocation().x, Infiltrator3.getComponent(1).getLocation().y);

			for(Node n : nodeList)
			{
				if(n.getStatus() == 3)
				{
					Sprite attack1 = new Sprite(attack);
					attack1.setPosition(n.getCoords().x, n.getCoords().y);
					attack1.draw(batch);
				}
				if(n.getStatus() == 4)
				{
					Sprite down1 = new Sprite(down);
					down1.setPosition(n.getCoords().x, n.getCoords().y);
					down1.draw(batch);
				}
			}

			if (hurt) {
				hurtMap.draw(batch);
			}
			switch (damage) {
				case (1):
					gameMap.translateX(5);
					damage = 2;
					break;
				case (2):
					damage = 3;
					break;
				case (3):
					gameMap.translateX(-10);
					damage = 4;
					break;
				case (4):
					damage = 5;
					break;
				case (5):
					gameMap.translateX(5);
					damage = 0;
					break;
				default:
					break;
			}
			if(isConfused > 0)
            {
                confused.draw(batch);
            }
		}

		if (gameState == 0 || gameState == 2) {
			infoScreen.draw(batch);
			if (gameState == 2) {
				paused.draw(batch);
			}
		}

		batch.end();
	}

	@Override
	public void dispose () {
        gameMap.getTexture().dispose();
        textureAtlas.dispose();
        batch.dispose();
    }

	public void resize(int width, int height) {
		viewport.update(width, height);
		camera.position.set(camera.viewportWidth / 2, camera.viewportHeight / 2, 0);
	}

    public boolean checkCollide(Point p1, Point p2, int size){
		if(Math.abs(p1.x - p2.x) < size && Math.abs(p1.y - p2.y) < 20){
			return true;
		}
		else{
			return false;
		}
	}
	public boolean checkWalls(int x, int yIn){
			int y = 548 - yIn;
			if (pixels.getPixel(x, y) == 0x000000ff) {
				return true;
			}
			else {
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
        public abstract Node getObjective();
        public abstract boolean getCaught();
        public abstract void setCaught(boolean arrest);
        public abstract void setX(int x);
        public abstract void setY(int y);
        public abstract int getHealth();
        public abstract void setHealth(int healthIn);
        public abstract void setCurrent(Node n);
        public abstract void setTargeting(boolean targeting);
        public abstract boolean getTargeting();
        public abstract void setDelay(int delay);
        public abstract int getDelay();
        public abstract Node getCurrent();
        public abstract int getHacking();
        public abstract void setHacking(int toHack);
        public abstract void setTarget(Node n);
        public abstract void setPrevious(Node n);
        public abstract Node getPrevious();

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
        Node Target;
        boolean isCaught;
        Node current;
        boolean isTargeting;
        int moveDelay = 0;
        int isHacking;
        Node previous;

		public Enemy(){
		}
		public Enemy(int i, Animation<TextureRegion> spriteSet, int j, Node current){
			super(i, spriteSet);
			this.enemyId = j;
			this.current = current;
		}
		public Enemy(int i, Animation<TextureRegion> spriteSet, int j)
		{
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
		public void setCurrent(Node n){
			this.current = n;
		}
        public void DecideObjective()
		{
			this.Target = new Node();
			ArrayList<Node> nodes = nodeList;
			ArrayList<Node> nodeChoice = new ArrayList<Node>();
			ArrayList<Node> nodeChoice1 = new ArrayList<Node>();
            for(int n : current.getNeighbours()){
            	for (Node node : nodes){
            		if (node.getName() == n && node.getStatus() != 3){
						nodeChoice.add(node);
					}
				}
			}
            for(Node nodeSelect: nodeChoice){
                if(nodeSelect.getStatus() == 2){
                    this.Target = nodeSelect;
                    break;
                }
                else if(nodeSelect != previous && nodeSelect.getStatus() != 4)
                {
					nodeChoice1.add(nodeSelect);
				}
            }
            if(this.Target.getName() == -1 && !nodeChoice1.isEmpty())
			{
				this.Target = nodeChoice1.get((int)((Math.random()*nodeChoice1.size() + 1)) - 1);
			}
            else if(this.Target.getName() == -1)
			{
				this.Target = previous;
			}
        }
        public Node getObjective() {
		    if(this.isCaught) {
                return new Node();
            }
		    else {
                return this.Target;
            }
        }
        public void setTargeting(boolean targeting){this.isTargeting = targeting;}
        public boolean getTargeting(){return isTargeting;}
        public void setDelay(int delay){this.moveDelay = delay;}
        public int getDelay(){return moveDelay;}
        public Node getCurrent(){return current;}
        public int getHacking(){return isHacking;}
        public void setHacking(int toHack){this.isHacking = toHack;}
        public void setTarget(Node n){this.Target = n;}
        public void setPrevious(Node n){this.previous = n;}
        public Node getPrevious(){return previous;}
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
		public Node getObjective() { return null;}
		public  boolean getCaught(){return false;}
		public void setCaught(boolean arrest){}
		public void setCurrent(Node n){}
		public void setTargeting(boolean targeting){}
		public boolean getTargeting(){return false;}
		public void setDelay(int delay){}
		public int getDelay(){return -1;}
		public Node getCurrent(){return new Node();}
		public int getHacking(){return -1;}
		public void setHacking(int toHack){}
		public void setTarget(Node n){}
		public void setPrevious(Node n){};
		public Node getPrevious(){return new Node();}
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
        public Node getObjective() { return null;}
        public  boolean getCaught(){return false;}
        public void setCaught(boolean arrest){}
        public int getHealth(){return -1;}
        public void setHealth(int healthIn){}
        public void setCurrent(Node n){}
		public void setTargeting(boolean targeting){}
		public boolean getTargeting(){return false;}
        public void setDelay(int delay){}
		public Node getCurrent(){return new Node();}
		public int getDelay(){return -1;}
		public int getHacking(){return -1;}
		public void setHacking(int toHack){}
		public void setTarget(Node n){}
		public void setPrevious(Node n){};
		public Node getPrevious(){return new Node();}
	}




	private class Node{
	    private int name;
	    private	Point coords;
	    private int status;
	    private ArrayList<Integer> neighbours;

	    public Node(){this.name = -1;}
	    public Node(int name, Point coords, int status, ArrayList neighbours){
	        this.name = name;
	        this.coords = coords;
	        this.status = status;
	        this.neighbours = neighbours;
        }
        public Node(Node n){
	    	this.name = n.getName();
	    	this.coords = n.getCoords();
	    	this.status = n.getStatus();
	    	this.neighbours = n.getNeighbours();
		}

        public int getName(){return name;}
        public void setName(int name1){this.name = name1;}
        public Point getCoords(){return coords;}
        public void setCoords(Point coords1){this.coords = coords1;}
        public int getStatus(){return status;}
        public void setStatus(int status1){this.status = status1;}
        public ArrayList<Integer> getNeighbours(){return neighbours;}
        public void setNeighbours(ArrayList<Integer> neighbours1){this.neighbours = neighbours1;}
    }

    public void makeNodes(){
		nodeList.add(new Node(1, new Point(250, 510), 1, new ArrayList<Integer>(Arrays.asList(2, 3))));
		nodeList.add(new Node(2, new Point(250, 430), 2, new ArrayList<Integer>(Arrays.asList(1))));
		nodeList.add(new Node(3, new Point(325, 500), 1, new ArrayList<Integer>(Arrays.asList(1,4))));
		nodeList.add(new Node(4, new Point(325, 370), 1, new ArrayList<Integer>(Arrays.asList(3, 5, 6, 45))));
		nodeList.add(new Node(5, new Point(170, 360), 2, new ArrayList<Integer>(Arrays.asList(4))));
		nodeList.add(new Node(6, new Point(50, 370), 1, new ArrayList<Integer>(Arrays.asList(4, 7))));
		nodeList.add(new Node(7, new Point(50, 250), 1, new ArrayList<Integer>(Arrays.asList(6, 9, 10))));
		nodeList.add(new Node(8, new Point(40, 30), 2, new ArrayList<Integer>(Arrays.asList(9))));
		nodeList.add(new Node(9, new Point(250, 30), 1, new ArrayList<Integer>(Arrays.asList(7, 8, 11))));
		nodeList.add(new Node(10, new Point(250, 300), 2, new ArrayList<Integer>(Arrays.asList(7))));
		nodeList.add(new Node(11, new Point(310, 30), 1, new ArrayList<Integer>(Arrays.asList(9, 12, 13))));
		nodeList.add(new Node(12, new Point(340, 200), 1, new ArrayList<Integer>(Arrays.asList(45, 11))));
		nodeList.add(new Node(13, new Point(350, 30), 2, new ArrayList<Integer>(Arrays.asList(11))));
		nodeList.add(new Node(14, new Point(420, 300), 1, new ArrayList<Integer>(Arrays.asList(45, 15, 22, 36))));
		nodeList.add(new Node(15, new Point(420, 360), 1, new ArrayList<Integer>(Arrays.asList(14, 17, 18))));
		nodeList.add(new Node(16, new Point(420, 500), 2, new ArrayList<Integer>(Arrays.asList(17))));
		nodeList.add(new Node(17, new Point(550, 500), 1, new ArrayList<Integer>(Arrays.asList(15, 16, 19))));
		nodeList.add(new Node(18, new Point(570, 370), 2, new ArrayList<Integer>(Arrays.asList(15))));
		nodeList.add(new Node(19, new Point(750, 500), 1, new ArrayList<Integer>(Arrays.asList(17, 20, 21))));
		nodeList.add(new Node(20, new Point(870, 500), 2, new ArrayList<Integer>(Arrays.asList(19))));
		nodeList.add(new Node(21, new Point(860, 350), 1, new ArrayList<Integer>(Arrays.asList(19, 24))));
		nodeList.add(new Node(22, new Point(660, 300), 1, new ArrayList<Integer>(Arrays.asList(14, 23, 24))));
		nodeList.add(new Node(23, new Point(660, 420), 2, new ArrayList<Integer>(Arrays.asList(22))));
		nodeList.add(new Node(24, new Point(860, 300), 1, new ArrayList<Integer>(Arrays.asList(21, 22, 25))));
		nodeList.add(new Node(25, new Point(1080, 300), 1, new ArrayList<Integer>(Arrays.asList(24, 26, 29))));
		nodeList.add(new Node(26, new Point(1080, 380), 1, new ArrayList<Integer>(Arrays.asList(25, 27, 28))));
		nodeList.add(new Node(27, new Point(940, 350), 2, new ArrayList<Integer>(Arrays.asList(26))));
		nodeList.add(new Node(28, new Point(1080, 510), 2, new ArrayList<Integer>(Arrays.asList(26))));
		nodeList.add(new Node(29, new Point(1090, 110), 1, new ArrayList<Integer>(Arrays.asList(25, 30, 32))));
		nodeList.add(new Node(30, new Point(1090, 70), 1, new ArrayList<Integer>(Arrays.asList(29, 31))));
		nodeList.add(new Node(31, new Point(950, 30), 2, new ArrayList<Integer>(Arrays.asList(30))));
		nodeList.add(new Node(32, new Point(900, 120), 1, new ArrayList<Integer>(Arrays.asList(29, 33))));
		nodeList.add(new Node(33, new Point(870, 110), 1, new ArrayList<Integer>(Arrays.asList(32, 34, 42))));
		nodeList.add(new Node(34, new Point(870, 30), 1, new ArrayList<Integer>(Arrays.asList(33, 35))));
		nodeList.add(new Node(35, new Point(430, 30), 1, new ArrayList<Integer>(Arrays.asList(34, 36))));
		nodeList.add(new Node(36, new Point(430, 170), 1, new ArrayList<Integer>(Arrays.asList(14, 35, 37))));
		nodeList.add(new Node(37, new Point(500, 170), 1, new ArrayList<Integer>(Arrays.asList(36, 42, 44))));
		nodeList.add(new Node(39, new Point(820, 220), 1, new ArrayList<Integer>(Arrays.asList(40, 42))));
		nodeList.add(new Node(40, new Point(870, 220), 1, new ArrayList<Integer>(Arrays.asList(39, 41))));
		nodeList.add(new Node(41, new Point(1000, 180), 2, new ArrayList<Integer>(Arrays.asList(40))));
		nodeList.add(new Node(42, new Point(820, 100), 1, new ArrayList<Integer>(Arrays.asList(33, 37, 39, 43))));
		nodeList.add(new Node(43, new Point(710, 170), 2, new ArrayList<Integer>(Arrays.asList(42))));
		nodeList.add(new Node(44, new Point(510, 90), 2, new ArrayList<Integer>(Arrays.asList(37))));
		nodeList.add(new Node(45, new Point(340, 250), 1, new ArrayList<Integer>(Arrays.asList(4, 12, 14))));

	}
}