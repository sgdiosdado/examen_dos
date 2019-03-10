/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package spaceinvaders;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.Formatter;

/**
 *
 * @author sergiodiosdado
 */
public class Player extends Item{
    
    private final int START_Y = 280;
    private final int START_X = 270;
    
    private int speed;
    private Game game;
    
    public Player(int x, int y, int width, int height, int speed, Game game){
        super(x, y, width, height);
        this.game = game;
        this.speed = speed;
    }

    /**
     * Gets the game context
     * 
     * @return <code>Game game</code>
     */
    public Game getGame() {
        return game;
    }
    
    /**
     * Gets the speed
     * 
     * @return <code>int</code> speed 
     */
    public int getSpeed(){
        return speed;
    }
    
    public Rectangle getHitbox() {
        return new Rectangle(getX(), getY(), getWidth(), getHeight());
    }
    
    /**
     * Writes it's data in the saving file
     *
     * @param file
     */
    public void save(Formatter file) {
        file.format("%s%s", getX() + " ", getY() + " ");
    }
    
    /**
     * Loads it's necessary data from a file
     *
     * @param x
     * @param y
     */
    public void load(int x, int y) {
        setX(x);
        setY(y);
    }
    
    /**
     * Ticker for the class
     */
    @Override
    public void tick() {
        
        // Moving player depending on flags
            if (getGame().getKeyManager().left) {
                setX(getX() - getSpeed());
            }
            if (getGame().getKeyManager().right) {
                setX(getX() + getSpeed());
            }
            if(getGame().getKeyManager().shoot && getGame().getShot() == null){
                getGame().playerShooting();
            }
        
        // Collisions with the screen
        if(getX() + getWidth() >= getGame().getWidth()){
            setX(getGame().getWidth() - getWidth());
        }
        if(getX() <= 0){
            setX(0);
        }
    }

    /**
     * Draws the player on the canvas
     * 
     * @param g 
     */
    @Override
    public void render(Graphics g) {
        g.drawImage(Assets.player, getX(), getY(), getWidth(), getHeight(), null);
    }
    
}
