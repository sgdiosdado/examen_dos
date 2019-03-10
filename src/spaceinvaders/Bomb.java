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
 * @author inakijaneiro
 */
public class Bomb extends Item {

    private boolean destroyed;

    public Bomb(int x, int y, int width, int height) {
        super(x, y, width, height);
        setDestroyed(true);
    }

    public void setDestroyed(boolean destroyed) {

        this.destroyed = destroyed;
    }

    public boolean isDestroyed() {
        return destroyed;
    }
    
    public Rectangle getHitbox() {
        return new Rectangle(getX(), getY(), getWidth(), getHeight());
    }
    
    public boolean hits(Object obj) {
        return (obj instanceof Player && getHitbox().intersects(((Player) (obj)).getHitbox()));
    }
    
    /**
     * Writes it's data in the saving file
     *
     * @param file
     */
    public void save(Formatter file) {
        file.format("%s%s", getX() + " ", getY() + " ");
    }
    
    @Override
    public void tick() {
        setY(getY() + 4);
    }

    @Override
    public void render(Graphics g) {
        g.drawImage(Assets.alienShot, getX(), getY(), getWidth(), getHeight(), null);
    }

}