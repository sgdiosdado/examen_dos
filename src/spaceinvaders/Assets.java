/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package spaceinvaders;

import java.awt.image.BufferedImage;

/**
 *
 * @author antoniomejorado
 */
public class Assets {

    public static BufferedImage background;     // to store background image
    public static BufferedImage player;         // to store the player image
    public static BufferedImage alien;          // to store the alien image
    public static BufferedImage alien2;         // to store the second alien image
    public static BufferedImage spritesheet;    // to store the spritesheet    
    public static BufferedImage playerShot;     // to store the player's shot
    public static BufferedImage alienShot;      // to store the alien's shot
    public static BufferedImage pause;          // to store the pause image
    public static BufferedImage gameOverScreen; // to store the game over image
    public static BufferedImage life;           // to store the life image
    public static SoundClip shot;               // to store the shooting sound
    public static SoundClip enemyDestroyed;     // to store the enemy destroyed sound
    public static SoundClip shotDestroyed;      // to store the shot destroyed sound
    public static SoundClip backgroundMusic;    // to store the background music

    /**
     * Initializes the images of the game
     */
    public static void init() {
        background = ImageLoader.loadImage("/images/background.jpg");
        spritesheet = ImageLoader.loadImage("/images/spritesheet.png");
        pause = ImageLoader.loadImage("/images/pause.png");
        gameOverScreen = ImageLoader.loadImage("/images/gameOver.png");
        
        shot = new SoundClip("/sounds/shoot.wav");
        shotDestroyed = new SoundClip("/sounds/shotDestroyed.wav");
        enemyDestroyed = new SoundClip("/sounds/enemyDestroyed.wav");
        backgroundMusic = new SoundClip("/sounds/backgroundMusic.wav");

        player = spritesheet.getSubimage(144, 7, 15, 15);
        alien = spritesheet.getSubimage(168, 80, 16, 16);
        alien2 = spritesheet.getSubimage(145, 80, 16, 16);
        playerShot = spritesheet.getSubimage(358, 3, 3, 8);
        alienShot = spritesheet.getSubimage(350, 145, 3, 10);
        life = spritesheet.getSubimage(49, 9, 15, 14);
    }

}
