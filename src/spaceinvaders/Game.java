/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package spaceinvaders;

import java.awt.Graphics;
import java.awt.image.BufferStrategy;
import java.util.LinkedList;

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
    private boolean paused;
    private Player player;                  // to store the player
    private LinkedList<Alien> aliens;       // to store all the aliens
    private LinkedList<Bomb> alienShots;     // to store all alien shots
    private Shot shot;                      // to store the shot
    private KeyManager keyManager;          // to manage the keyboard

    private boolean moveDown;               // flag to move all instances of aliens down
    private int alienMoveCounter;           // to move the aliens on a certain time
    private int alienTickLimit;             // the limit in which the movement is done
    private int alienBombCounter;           // to spawn an alien bomb on a certain time
    private boolean gameEnded;

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
        this.alienShots = new LinkedList<Bomb>(); 
        this.alienTickLimit = 60;
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
    public KeyManager getKeyManager() {
        return keyManager;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public LinkedList<Alien> getAliens() {
        return aliens;
    }

    public void setAliens(LinkedList<Alien> aliens) {
        this.aliens = aliens;
    }

    public boolean moveDown() {
        return moveDown;
    }

    public void setMoveDown(boolean moveDown) {
        this.moveDown = moveDown;
    }

    public int getAlienMoveCounter() {
        return alienMoveCounter;
    }

    public void setAlienMoveCounter(int alienMoveCounter) {
        this.alienMoveCounter = alienMoveCounter;
    }

    public int getAlienTickLimit() {
        return alienTickLimit;
    }

    public void setAlienTickLimit(int alienTickLimit) {
        this.alienTickLimit = alienTickLimit;
    }

    /**
     * Gets the player's shot
     *
     * @return <code>Shot</code> shot
     */
    public Shot getShot() {
        return shot;
    }

    /**
     * Gets the game pause status
     *
     * @return <code>boolean</code> paused
     */
    public boolean isPaused() {
        return paused;
    }

    /**
     * Sets the game pause status
     *
     * @param paused
     */
    public void setPause(boolean paused) {
        this.paused = paused;
    }

    /**
     * Initialising the display window of the game
     */
    private void init() {
        display = new Display(title, width, height);
        display.getJframe().addKeyListener(keyManager);
        Assets.init();
        Assets.backgroundMusic.setLooping(true);
        Assets.backgroundMusic.play();
        player = new Player(getWidth() / 2 - 24, getHeight() - 64, 48, 48, 5, this);
        for (int i = 0; i < 5; i++) {
            int spacing = 30;
            for (int j = 0; j < 10; j++) {
                aliens.add(new Alien(PADDING_LEFT + 48 * j + spacing, PADDING_TOP + i * 48, 48, 48, this));
                spacing += 30;
            }
        }

    }

    @Override
    public void run() {
        //throw new UnsupportedOperationException("Not supported yet."); //To change bo dy of generated methods, choose Tools | Templates.
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

    public void playerShooting() {
        shot = new Shot(player.getX() + player.getWidth() / 2 - 4, player.getY() - 16, 8, 16);
        Assets.shot.play();
    }

    private void tick() {
        keyManager.tick();
        if (getKeyManager().p && getKeyManager().isPressable()) {
            setPause(!isPaused());
            getKeyManager().setPressable(false);
        }

        if (!isPaused()) {
            player.tick();
            setAlienMoveCounter(getAlienMoveCounter() + 1);
            alienBombCounter++;
            for (int i = 0; i < aliens.size(); i++) {
                Alien alien = aliens.get(i);
                alien.tick();

            }
            
            if (alienBombCounter == getAlienTickLimit() * 2) {
                int alienDropperIndex = (int)(Math.random() * (aliens.size() - 2));
                Alien alienDropper = aliens.get(alienDropperIndex);
                alienShots.add(new Bomb(alienDropper.getX() + alienDropper.getWidth() / 2,alienDropper.getY() + alienDropper.getHeight(), 16, 32));
                alienBombCounter = 0;
            }
            for (int i = 0; i < alienShots.size(); i++) {
                Bomb alienShot = alienShots.get(i);
                alienShot.tick();
            }
            
            if (shot != null) {
                shot.tick();
                boolean shotExists = true;
                for (int i = 0; i < aliens.size() && shotExists; i++) {
                    if (getShot().hits(aliens.get(i))) {
                        Assets.enemyDestroyed.play();
                        aliens.remove(i);
                        shot = null;
                        shotExists = false;
                    }
                }
                for (int i = 0; i < alienShots.size() && shotExists; i++) {
                    if (alienShots.get(i).hits(player)) {
                        Assets.enemyDestroyed.play();
                        gameEnded = true;
                    }
                    
                    if (getShot().hits(alienShots.get(i))) {
                        Assets.enemyDestroyed.play();
                        alienShots.remove(i);
                        shot = null;
                        shotExists = false;
                    }
                }
                if (shotExists && getShot().getY() <= 0) {
                    Assets.shotDestroyed.play();
                    shot = null;
                }
            }
            if (getAlienMoveCounter() >= getAlienTickLimit()) {
                setAlienMoveCounter(0);
            }
            /* IMPORTANTE que esto vaya despues del tick de alien*/
            //moves ALL aliens down and changes their direction
            if (moveDown()) {
                setMoveDown(false);
                for (int i = 0; i < aliens.size(); i++) {
                    Alien alien = aliens.get(i);
                    
                    alien.setY(alien.getY() + 48);
                    if (alien.getDirection() == Alien.Direction.LEFT) {
                        alien.setDirection(Alien.Direction.RIGHT);
                        alien.setX(alien.getX() + 24);
                    } else if (alien.getDirection() == Alien.Direction.RIGHT) {
                        alien.setDirection(Alien.Direction.LEFT);
                        alien.setX(alien.getX() - 24);
                    }
                }
                if (getAlienTickLimit() >= 20) {
                    setAlienTickLimit(getAlienTickLimit() - 10);
                }
            }
        }
    }

    private void render() {
        //get the buffer from the display
        bs = display.getCanvas().getBufferStrategy();
        /* 
         * If it is null, we define one with 3 buffers to display images of the game,
         * if not null, then we display every image of the game but after clearing the
         * Rectanlge, getting the graphic object from the buffer strategy element.	
         * Show the graphic and dispose it to the trash system
         */
        if (bs == null) {
            display.getCanvas().createBufferStrategy(3);
        } else {
            g = bs.getDrawGraphics();
            g.drawImage(Assets.background, 0, 0, width, height, null);
            player.render(g);
            for (int i = 0; i < alienShots.size(); i++) {
                Bomb alienShot = alienShots.get(i);
                alienShot.render(g);
            }
            for (int i = 0; i < aliens.size(); i++) {
                aliens.get(i).render(g);
            }
            if (shot != null) {
                shot.render(g);
            }
            bs.show();
            g.dispose();
        }
    }

    /**
     * Setting the thread for the game
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
