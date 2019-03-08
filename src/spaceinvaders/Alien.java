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
    
    public Alien (int x, int y, int width, int height) {
        super(x, y, width, height);
        bomb = new Bomb(x, y, width, height);
    }
    
    public Bomb getBomb() {
        
        return bomb;
        
    }


    @Override
    public void tick() {
    }

    @Override
    public void render(Graphics g) {
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
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }

        @Override
        public void render(Graphics g) {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }

    }
}
