/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package spaceinvaders;

import java.awt.Graphics;

/**
 *
 * @author antoniomejorado
 */
public abstract class Item {
    protected int x;        // to store x position
    protected int y;        // to store y position
    protected int width;
    protected int height;
    
    /**
     * Sets the initial values to create the item
     * @param x <b>x</b> position of the object
     * @param y <b>y</b> position of the object
     */
    public Item(int x, int y, int width, int height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    /**
     * Gets x value
     * @return x 
     */
    public int getX() {
        return x;
    }

    /**
     * Gets y value
     * @return y 
     */
    public int getY() {
        return y;
    }
    
    /**
     * Gets width value
     * @return width
     */
    public int getWidth() {
        return width;
    }
    
    /**
     * Gets height value
     * @return height
     */
    public int getHeight() {
        return height;
    }

    /**
     * Sets x value
     * @param x to modify
     */
    public void setX(int x) {
        this.x = x;
    }

    /**
     * Sets y value
     * @param y to modify
     */
    public void setY(int y) {
        this.y = y;
    }

    /**
     * Sets width value
     * @param width to modify
     */
    public void setWidth(int width) {
        this.width = width;
    }
    
    /**
     * Sets height value
     * @param height to modify
     */
    public void setHeight(int height) {
        this.height = height;
    }
    
    /**
     * Updates positions of the item for every tick
     */
    public abstract void tick();
    
    /**
     * Paints the item
     * @param g <b>Graphics</b> object to paint the item
     */
    public abstract void render(Graphics g);
}
