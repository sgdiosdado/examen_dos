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
public class Alien extends Item {
    
    private Bomb bomb;
    private Game game;
    
    public Alien (int x, int y, int width, int height, Game game) {
        super(x, y, width, height);
        bomb = new Bomb(x, y, width, height);
        this.game = game;
    }
    
    public Bomb getBomb() {
        
        return bomb;
        
    }


    @Override
    public void tick() {
    }

    @Override
    public void render(Graphics g) {
        g.drawImage(Assets.alien, getX(), getY(), getWidth(), getHeight(), null);
    }
    
    // Bomb from the Alien
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
}
