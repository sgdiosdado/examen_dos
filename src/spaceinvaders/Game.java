/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package spaceinvaders;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;
import java.io.File;
import java.util.Formatter;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author inakijaneiro
 */
public class Game implements Runnable {
    
    //Constants
    public static final int BOARD_WIDTH = 358;
    public static final int BOARD_HEIGHT = 350;
    public static final int GROUND = 290;
    public static final int BOMB_HEIGHT = 5;
    public static final int ALIEN_HEIGHT = 12;
    public static final int ALIEN_WIDTH = 12;
    public static final int BORDER_RIGHT = 30;
    public static final int BORDER_LEFT = 5;
    public static final int GO_DOWN = 15;
    public static final int NUMBER_OF_ALIENS_TO_DESTROY = 24;
    public static final int CHANCE = 5;
    public static final int DELAY = 17;
    public static final int PLAYER_WIDTH = 15;
    public static final int PLAYER_HEIGHT = 10;
    public static final int PADDING_TOP = 10;
    public static final int PADDING_LEFT = 110;

    private BufferStrategy bs;              // to have several buffers when displaying
    private Graphics g;                     // to paint objects
    private Display display;                // to display in the game 
    private String title;                   // title of the window
    private int width;                      // width of the window
    private int height;                     // height of the window
    private Thread thread;                  // thread to create the game
    private boolean running;                // to set the game
    private Player player;
    private LinkedList<Alien> aliens;
    private Shot shot;
    private KeyManager keyManager;          // to manage the keyboard

    /**
     * to	create	title,	width	and	height	and	set	the	game	is	still	not	running
     *
     * @param	title	to	set	the	title	of	the	window
     * @param	width	to	set	the	width	of	the	window
     * @param	height	to	set	the	height	of	the	window
     */
    public Game(String title, int width, int height) {
        this.title = title;
        this.width = width;
        this.height = height;
        this.running = false;
        this.keyManager = new KeyManager();
        this.aliens = new LinkedList<Alien>();
    }

    /**
     * To get the width of the game window
     *
     * @return an <code>int</code> value with the width
     */
    public int getWidth() {
        return width;
    }

    /**
     * To get the height of the game window
     *
     * @return an <code>int</code> value with the height
     */
    public int getHeight() {
        return height;
    }
    
    /**
     * Gets the KeyManager instance
     * 
     * @return <code>KeyManager</code> keyManager
     */
    public KeyManager getKeyManager(){
        return keyManager;
    }
    
    /**
     * Initialising the display window of the game
     */
    private void init() {
        display = new Display(title, width, height);
        display.getJframe().addKeyListener(keyManager);
        Assets.init();
        player = new Player(getWidth()/2 - 48, getHeight() - 64, 48, 48, 5, this);
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 10; j++){
                aliens.add(new Alien(PADDING_LEFT + 48 * j, PADDING_TOP + i * 48, 48, 48, this));
            }
        }
        
    }

    @Override
    public void run() {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        init();
        // frames per second
        int fps = 60;
        // time for each tick in nano segs;
        double timeTick = 1000000000 / fps;
        // initializing delta
        double delta = 0;
        // define now to use inside the loop
        long now;
        // initializing last time to the computer time in nanoseconds
        long lastTime = System.nanoTime();
        while (running) {
            // setting the time now to the actual time
            now = System.nanoTime();
            // accumulating to delta the difference between times in timeTick units
            delta += (now - lastTime) / timeTick;
            //updating the last time
            lastTime = now;

            // if delta is positive we tick the game
            if (delta >= 1) {
                tick();
                render();
                delta--;
            }
        }
        stop();
    }

    private void tick() {
        keyManager.tick();
        player.tick();

    }

    private void render() {
        //get the buffer from the display
        bs = display.getCanvas().getBufferStrategy();
        /*	if	it	is	null,	we	define	one	with	3	buffers	to	display	images	of
	the	game,	if	not	null,	then	we	display every	image	of	the	game	but
	after	clearing	the	Rectanlge,	getting	the	graphic	object	from	the	
	buffer	strategy	element.	
	show	the	graphic	and	dispose	it	to	the	trash	system
         */
        if (bs == null) {
            display.getCanvas().createBufferStrategy(3);
        } else {
            g = bs.getDrawGraphics();
            g.drawImage(Assets.background, 0, 0, width, height, null);
            player.render(g);
            for (int i = 0; i < aliens.size(); i++) {
                aliens.get(i).render(g);
            }
            bs.show();
            g.dispose();
        }
    }

    /**
     * setting	the	thead	for	the	game
     */
    public synchronized void start() {
        if (!running) {
            running = true;
            thread = new Thread(this);
            thread.start();
        }
    }

    /**
     * stopping the thread
     */
    public synchronized void stop() {
        if (running) {
            running = false;
        }
        try {
            thread.join();
        } catch (InterruptedException ie) {
            ie.printStackTrace();
        }
    }
}
