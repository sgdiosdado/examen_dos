/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package spaceinvaders;

import java.awt.Graphics;
import java.awt.Rectangle;

/**
 *
 * @author inakijaneiro
 */
public class Shot extends Item {
    
    private int speed;          // to store the speed
    
    public Shot(int x, int y, int width, int height) {
        super(x, y, width, height);
        this.speed = 4;
    }

    /**
     * Gets the speed
     * 
     * @return <code>int</code> speed
     */
    public int getSpeed() {
        return speed;
    }

    /**
     * Sets the speed
     * 
     * @param int 
     */
    public void setSpeed(int speed) {
        this.speed = speed;
    }

    /**
     * Creates a Rectangle instance and simulates the "hit box" of the ball
     *
     * @return new Circle
     */
    public Rectangle getHitbox() {
        return new Rectangle(getX(), getY(), getWidth(), getHeight());
    }
    
    /**
     * Checks if there was a collision with another instance and returns a
     * boolean
     *
     * @param obj
     * @return <code>boolean</code>
     */
    public boolean hits(Object obj) {
        return (obj instanceof Shot && getHitbox().intersects(((Shot) (obj)).getHitbox())
                || obj instanceof Alien && getHitbox().intersects(((Alien) (obj)).getHitbox()));
    }
    
    @Override
    public void tick() {
        setY(getY() - getSpeed());
    }

    @Override
    public void render(Graphics g) {
        g.drawImage(Assets.playerShot, getX(), getY(), getWidth(), getHeight(), null);
    }
}
