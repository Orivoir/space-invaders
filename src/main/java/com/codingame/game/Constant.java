package com.codingame.game;

import com.codingame.gameengine.module.entities.World;

public class Constant {

    public static final boolean IS_DEBUG = false;

    public static final int VW = World.DEFAULT_WIDTH;
    public static final int VH = World.DEFAULT_HEIGHT;


    public static final int BULLET_Z_INDEX = 2;
    public static final int TEXT_Z_INDEX = 2;
    public static final int PLAYER_Z_INDEX = 1;
    public static final int ENEMIES_Z_INDEX = 1;
    public static final int BACKGROUND_Z_INDEX = -1;

    public static final int MARGIN_HORIZONTAL = 42;
    public static final int MARGIN_VERTICAL = 82;

    public static final String FONT_FAMILY = "sans-serif";
    public static final String BACKGROUND = "paralax_background.jpg";

    public static final int ENEMY_SCALE = 2;
    public static final int ENEMY_WIDTH = 32 * ENEMY_SCALE;
    public static final int ENEMY_HEIGHT = 32 * ENEMY_SCALE;
    public static final float ENEMY_MARGIN = (float) (ENEMY_WIDTH / 3);
    public static final float ENEMY_STEP_SIZE = (float) ENEMY_WIDTH / 2;
    public static final int ENEMY_COUNT_STEP = 6;
    public static final int COUNT_ENEMIES_BY_ROW = (int) Math.floor(
            ((double) (VW-(MARGIN_HORIZONTAL * 2)-(ENEMY_STEP_SIZE * ENEMY_COUNT_STEP)) / (ENEMY_WIDTH + ENEMY_MARGIN))
    );
    public static final String ENEMY = "space_shooter/spritesheets/enemy-big.png";


    public static final int PLAYER_SCALE = 4;
    public static final int PLAYER_WIDTH = 16 * PLAYER_SCALE;
    public static final int PLAYER_HEIGHT = 24 * PLAYER_SCALE;
    public static final String PLAYER = "space_shooter/spritesheets/ship.png";

    public static final int CLOUD_WIDTH = 256;
    public static final int CLOUD_HEIGHT = 103;
    public static final String CLOUD = "space_shooter/backgrounds/clouds.png";
    public static final String CLOUD_ALT = "space_shooter/backgrounds/clouds-transparent.png";



    public static final int PLAYER_BULLET_STEP = 48;
    public static final int PLAYER_BULLET_SCALE = 3;
    public static final int PLAYER_BULLET_WIDTH = 8 * PLAYER_BULLET_SCALE;
    public static final int PLAYER_BULLET_HEIGHT = 7 * PLAYER_BULLET_SCALE;
    public static final String PLAYER_BULLET = "space_shooter/spritesheets/player-bullets.png";


    public static final int ENEMY_BULLET_SCALE = 3;
    public static final int ENEMY_BULLET_WIDTH = 5 * ENEMY_BULLET_SCALE;
    public static final int ENEMY_BULLET_HEIGHT = 5 * ENEMY_BULLET_SCALE;
    public static final String ENEMY_BULLET = "space_shooter/spritesheets/enemy-bullets.png";


    public static final int EXPLOSION_SCALE = 4;
    public static final int EXPLOSION_WIDTH = 16 * ENEMY_BULLET_SCALE;
    public static final int EXPLOSION_HEIGHT = 16 * ENEMY_BULLET_SCALE;
    public static final String EXPLOSION = "space_shooter/spritesheets/explosion.png";
}
