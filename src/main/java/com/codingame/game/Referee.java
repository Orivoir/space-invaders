package com.codingame.game;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.codingame.gameengine.core.AbstractPlayer.TimeoutException;
import com.codingame.gameengine.core.AbstractReferee;
import com.codingame.gameengine.core.SoloGameManager;
import com.codingame.gameengine.module.entities.*;
import com.google.inject.Inject;

public class Referee extends AbstractReferee {
	
    @Inject
    private SoloGameManager<Player> gameManager;
    
    @Inject
    private GraphicEntityModule graphicEntityModule;
    
    private int countEnemies = 0;

    private Text countEnemiesUI = null;
    private Sprite ennemyUI = null;

    private String[] enemiesSprites = null;
    private String[] playerSprites = null;
    private String[] explosionSprites = null;
    private String[] playerBulletSprites = null;
    private String[] enemiesBulletSprites = null;
    private ArrayList<SpriteAnimation> playerBullets = new ArrayList<>();
    private ArrayList<SpriteAnimation> enemiesBullets = new ArrayList<>();
    private SpriteAnimation player = null;
    private SpriteAnimation[] enemies = null;
    private final Point drawPoint = new Point((float) Constant.MARGIN_HORIZONTAL, (float) Constant.MARGIN_VERTICAL);
    private int offsetY = 0;
    @Override
    public void init() {

        // get test case
        countEnemies = Integer.parseInt(gameManager.getTestCaseInput().get(0).trim());

        // @TODO:
        // upgrade frame duration
        // upgrade timeout
        // upgrade timeout for each turn
        // upgrade max turns
        gameManager.setMaxTurns(200);
        gameManager.setFrameDuration(400);

        // mount initial view
        graphicEntityModule
                .createSprite()
                .setImage(Constant.BACKGROUND)
                .setX(0)
                .setY(0)
                .setZIndex(Constant.BACKGROUND_Z_INDEX);

        createBulletSprites();
        createExplosionSprites();
        createPlayer();
        createAreaMarker();

        upgradeCountEnemies();
        upgradeEnemiesRow(0);

        playerBullets.add(createPlayerBullet());
    }

    public void createExplosionSprites() {
        explosionSprites = graphicEntityModule.createSpriteSheetSplitter()
                .setSourceImage(Constant.EXPLOSION)
                .setImageCount(5)
                .setWidth(Constant.EXPLOSION_WIDTH / Constant.EXPLOSION_SCALE)
                .setHeight(Constant.EXPLOSION_HEIGHT / Constant.EXPLOSION_SCALE)
                .setOrigRow(0)
                .setOrigCol(0)
                .setImagesPerRow(5)
                .setName("explosion")
                .split();
    }
    public void createBulletSprites() {
        playerBulletSprites = graphicEntityModule.createSpriteSheetSplitter()
                .setSourceImage(Constant.PLAYER_BULLET)
                .setImageCount(2)
                .setWidth(Constant.PLAYER_BULLET_WIDTH / Constant.PLAYER_BULLET_SCALE)
                .setHeight(Constant.PLAYER_BULLET_HEIGHT / Constant.PLAYER_BULLET_SCALE)
                .setOrigRow(0)
                .setOrigCol(0)
                .setImagesPerRow(2)
                .setName("playerBullet")
                .split();

        enemiesBulletSprites = graphicEntityModule.createSpriteSheetSplitter()
                .setSourceImage(Constant.ENEMY_BULLET)
                .setImageCount(2)
                .setWidth(Constant.ENEMY_BULLET_WIDTH / Constant.ENEMY_BULLET_SCALE)
                .setHeight(Constant.ENEMY_BULLET_HEIGHT / Constant.ENEMY_BULLET_SCALE)
                .setOrigRow(0)
                .setOrigCol(0)
                .setImagesPerRow(2)
                .setName("enemyBullet")
                .split();

    }
    public SpriteAnimation createPlayerBullet() {
        return graphicEntityModule.createSpriteAnimation()
                .setImages(playerBulletSprites)
                .setScale(Constant.PLAYER_BULLET_SCALE)
                .setZIndex(Constant.BULLET_Z_INDEX)
                .setY(player.getY() - Constant.PLAYER_BULLET_HEIGHT)
                .setX((player.getX() + (Constant.PLAYER_WIDTH / 2)) - (Constant.PLAYER_BULLET_WIDTH / 2)  )
                .setLoop(true);
    }
    public SpriteAnimation createEnemyBullet(SpriteAnimation enemy) {
        return graphicEntityModule.createSpriteAnimation()
                .setImages(enemiesBulletSprites)
                .setScale(Constant.ENEMY_BULLET_SCALE)
                .setZIndex(Constant.BULLET_Z_INDEX)
                .setY(enemy.getY() + Constant.ENEMY_BULLET_HEIGHT)
                .setX((enemy.getX() + (Constant.ENEMY_WIDTH / 2)) - (Constant.ENEMY_BULLET_WIDTH / 2))
                .setLoop(true);
    }
    public void createAreaMarker() {
        if(Constant.IS_DEBUG) {
            graphicEntityModule.createRectangle()
                    .setX(0)
                    .setY((Constant.VH - Constant.CLOUD_HEIGHT))
                    .setWidth(Constant.VW)
                    .setHeight(Constant.VH)
                    .setFillColor(16711680); // full red
        }
        else {
            int countClouds = (int) Math.ceil((double) Constant.VW / (double) (Constant.CLOUD_WIDTH/2));

            Point drawCloudPoint = new Point(0f,(float) (Constant.VH - Constant.CLOUD_HEIGHT));

            offsetY = 30;

            for(int i = 0; i < countClouds; i++) {

                graphicEntityModule.createSprite()
                        .setImage(Constant.CLOUD)
                        .setX((int) drawCloudPoint.getX())
                        .setY((int) drawCloudPoint.getY());

                graphicEntityModule.createSprite()
                        .setImage(Constant.CLOUD_ALT)
                        .setX((int) drawCloudPoint.getX())
                        .setY((int) (drawCloudPoint.getY() - offsetY))
                        .setZIndex(-1);

                drawCloudPoint = drawCloudPoint.add(new Point((float) (Constant.CLOUD_WIDTH/2), 0f));
            }
        }
    }
    public void createPlayer() {
        playerSprites = graphicEntityModule.createSpriteSheetSplitter()
                .setSourceImage(Constant.PLAYER)
                .setImageCount(10)
                .setWidth(Constant.PLAYER_WIDTH / Constant.PLAYER_SCALE)
                .setHeight(Constant.PLAYER_HEIGHT / Constant.PLAYER_SCALE)
                .setOrigRow(0)
                .setOrigCol(0)
                .setImagesPerRow(5)
                .setName("player")
                .split();

        player = graphicEntityModule.createSpriteAnimation()
                .setImages(playerSprites)
                .setScale(Constant.PLAYER_SCALE)
                .setZIndex(Constant.PLAYER_Z_INDEX)
                .setY(Constant.VH - (Constant.PLAYER_HEIGHT + (Constant.MARGIN_VERTICAL/2)))
                .setX((Constant.VW / 2) - ((Constant.PLAYER_WIDTH + Constant.PLAYER_SCALE) / 2))
                .setLoop(true);
    }
    public SpriteAnimation createEnemy() {

        return graphicEntityModule.createSpriteAnimation()
                .setImages(getEnemiesSprites())
                .setScale(Constant.ENEMY_SCALE)
                .setZIndex(Constant.ENEMIES_Z_INDEX)
                .setLoop(true);
    }

    public void upgradeEnemiesRow(int turn) {

        int offsetX = (int) ((turn % Constant.ENEMY_COUNT_STEP) * Constant.ENEMY_STEP_SIZE);

        if((turn % Constant.ENEMY_COUNT_STEP) == 0 && turn > 0) {
            offsetY += Constant.ENEMY_STEP_SIZE;
        }

        Point nextEnemyPoint = new Point(drawPoint.getX(), drawPoint.getY());

        SpriteAnimation[] enemies = getEnemies();

        int countRows = (int) Math.ceil((double) enemies.length / Constant.COUNT_ENEMIES_BY_ROW);
        int index = 0;

        for(int i = 0; i < countRows; i++) {

            for(int j = 0, size = Constant.COUNT_ENEMIES_BY_ROW; j < size; j++) {

                if(index >= enemies.length) {
                    break;
                }

                enemies[index++]
                        .setX((int) (nextEnemyPoint.getX() + offsetX))
                        .setY((int) (nextEnemyPoint.getY() + offsetY));
                // increment next enemy point x
                nextEnemyPoint = nextEnemyPoint.add(new Point((Constant.ENEMY_MARGIN + Constant.ENEMY_WIDTH), 0f));
            }

            // increment next enemy point Y
            // reset next enemy point x
            nextEnemyPoint = nextEnemyPoint.add(new Point(0f, (Constant.ENEMY_MARGIN + Constant.ENEMY_WIDTH)));
            nextEnemyPoint.setX(drawPoint.getX());
        }
    }
    public void upgradeCountEnemies() {

        if(countEnemiesUI == null) {
            countEnemiesUI = graphicEntityModule.createText();
            countEnemiesUI
                    .setFontFamily(Constant.FONT_FAMILY)
                    .setFontSize(40)
                    .setFillColor(0xffffff) // text color white
                    .setFontWeight(Text.FontWeight.BOLD)
                    .setX(20)
                    .setY(20)
                    .setZIndex(Constant.TEXT_Z_INDEX);
        }

        if(ennemyUI == null) {
            ennemyUI = graphicEntityModule.createSprite();
            ennemyUI
                    .setImage(getEnemiesSprites()[0])
                    .setY(20)
                    .setZIndex(Constant.TEXT_Z_INDEX);
        }
        countEnemiesUI.setText(String.valueOf(getCountEnemies()));
        ennemyUI.setX(Constant.ENEMY_WIDTH + countEnemiesUI.getMaxWidth());
    }
    public void upgradeBullets() {

        upgradePlayerBullets();
    }

    public boolean checkCollision(
            SpriteAnimation bullet,
            int stepBullet,
            int bulletWidth,
            SpriteAnimation target
    ) {

        Point currentPointBullet = new Point(bullet.getX(), bullet.getY());
        Point lastPointBullet = new Point(bullet.getX(), bullet.getY() - stepBullet);

        int bulletHeight = (int) Math.abs(currentPointBullet.getY() - lastPointBullet.getY());

        return (
                target.getX() >= currentPointBullet.getX() && target.getX() <= (currentPointBullet.getX() + bulletWidth) &&
                target.getY() >= currentPointBullet.getY() && target.getY() <= (currentPointBullet.getY() + bulletHeight)
        );
    }

    public void disableBullet(SpriteAnimation bullet) {
        bullet.setVisible(false);
        graphicEntityModule.commitEntityState(0, bullet);
        bullet.setX(0, Curve.IMMEDIATE).setY(0, Curve.IMMEDIATE);
        bullet.pause();
    }

    public void upgradePlayerBullets() {

        for(SpriteAnimation bullet: playerBullets) {

            if(bullet.isVisible()) {
                bullet.setY(bullet.getY() - Constant.PLAYER_BULLET_STEP);

                for(SpriteAnimation enemy: enemies) {
                    if(enemy.isVisible()) {
                        if(checkCollision(
                                bullet,
                                Constant.PLAYER_BULLET_STEP,
                                Constant.PLAYER_BULLET_WIDTH,
                                enemy
                        )) {
                            /*
                            enemy.setVisible(false);
                            enemy.pause();
                             */

                            // @TODO: fix explosion animation and slice sprites sheet
                            enemy
                                    .setImages(explosionSprites)
                                    .setDuration(gameManager.getFrameDuration() * explosionSprites.length)
                                    .setLoop(true)
                                    .setPlaying(true);

                            disableBullet(bullet);
                        } else if(bullet.getY() <= 0) {
                            disableBullet(bullet);
                        }
                    }
                }
            }
        }
    }

    public int getCountEnemies() {

        if(enemies == null) {
            return countEnemies;
        } else {
            int count = 0;

            for(SpriteAnimation enemy: enemies) {
                if(enemy.isVisible()) {
                    count++;
                }
            }
            return count;
        }
    }
    public SpriteAnimation[] getEnemies() {

        if(enemies == null) {

            enemies = new SpriteAnimation[countEnemies];

            for(int i = 0; i < countEnemies; i++) {
                enemies[i] = createEnemy();
            }
        }

        return enemies;
    }
    public String[] getEnemiesSprites() {

        if(enemiesSprites == null) {
            enemiesSprites = graphicEntityModule.createSpriteSheetSplitter()
                    .setSourceImage(Constant.ENEMY)
                    .setImageCount(2)
                    .setWidth(Constant.ENEMY_WIDTH / Constant.ENEMY_SCALE)
                    .setHeight(Constant.ENEMY_HEIGHT / Constant.ENEMY_SCALE)
                    .setOrigRow(0)
                    .setOrigCol(0)
                    .setImagesPerRow(2)
                    .setName("enemy")
                    .split();
        }

        return enemiesSprites;
    }
    public SpriteAnimation getLastEnemy() {

        int indexReverse = 1;
        SpriteAnimation lastEnemyFind = null;

        for(int size = enemies.length; indexReverse < size; indexReverse++) {
            SpriteAnimation lastEnemy = enemies[enemies.length - indexReverse];

            if(lastEnemy.isVisible()) {
                lastEnemyFind = lastEnemy;
                break;
            }
        }

        return lastEnemyFind;
    }

    @Override
    public void gameTurn(int turn) {

        SpriteAnimation lastEnemy = getLastEnemy();

        if(lastEnemy == null) {
            gameManager.winGame("congratulation!");
        } else {

            if((lastEnemy.getY() + Constant.ENEMY_HEIGHT) >= (Constant.VH - Constant.CLOUD_HEIGHT)) {
                gameManager.loseGame("enemies ship have reached the area.");
            } else {

                // @TODO:
                // get player
                // give input to player
                // read output.s player
                // verify output

                // upgradePlayerPosition();
                upgradeEnemiesRow(turn);
                upgradeBullets();
                upgradeCountEnemies();
            }
        }
    }
}
