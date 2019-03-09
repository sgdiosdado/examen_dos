/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package spaceinvaders;

import java.awt.Graphics;

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

    @Override
    public void tick() {

        // Collisions with the screen
        if(getX() + getWidth() >= 700) {
            setX(700 - getWidth());
        }
        if(getX() <= 0){
            setX(0);
        }
    }

    @Override
    public void render(Graphics g) {
    }

}