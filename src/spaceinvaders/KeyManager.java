/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package spaceinvaders;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 *
 * @author inakijaneiro
 */
public class KeyManager implements KeyListener {
    
    public boolean up;          // flag to move up the player
    public boolean down;        // flag to move down the player
    public boolean left;        // flag to move left the player
    public boolean right;       // flag to move right the player
    public boolean p;           // flag to pause the game
    public boolean g;           // flag to save the game
    public boolean c;           // flag to load the game
    public boolean movement;    // flag to denote the player has pressed/released a key
    private boolean pressable;  // flag to do a one time press that has lasting effect
    
    private boolean keys[]; // to store all the flags for every key

    public KeyManager() {
        keys = new boolean[256];
        this.pressable = true;
    }

    public boolean isPressable() {
        return pressable;
    }

    public void setPressable(boolean pressable) {
        this.pressable = pressable;
    }
    
    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
        // set true to every key pressed
        keys[e.getKeyCode()] = true;
        movement = true;
    }

    @Override
    public void keyReleased(KeyEvent e) {
        // set false to every key released
        keys[e.getKeyCode()] = false;
        movement = false;
        if (e.getKeyCode() == KeyEvent.VK_P){
            setPressable(true);
        }
    }
    
    /**
     * to enable or disable moves on every tick
     */
    public void tick() {
        up = keys[KeyEvent.VK_UP];
        down = keys[KeyEvent.VK_DOWN];
        left = keys[KeyEvent.VK_LEFT];
        right = keys[KeyEvent.VK_RIGHT];
        p = keys[KeyEvent.VK_P];
        g = keys[KeyEvent.VK_G];
        c = keys[KeyEvent.VK_C];
    }
    
}
