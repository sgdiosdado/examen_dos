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

    /**
     * Initializes the images of the game
     */
    public static void init() {
        background = ImageLoader.loadImage("/images/background.jpg");
        spritesheet = ImageLoader.loadImage("/images/spritesheet.png");
        
        player = spritesheet.getSubimage(144, 7, 15, 15);
        
        alien = spritesheet.getSubimage(168, 80, 16, 16);
        alien2 = spritesheet.getSubimage(145, 80, 16, 16);
        
    }

}
