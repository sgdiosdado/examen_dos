/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package spaceinvaders;

import java.awt.Graphics;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.image.BufferStrategy;
import java.io.File;
import java.util.Formatter;
import java.util.LinkedList;
import java.util.Scanner;
import javax.sound.sampled.FloatControl;

/**
 *
 * @author inakijaneiro
 */
public class Game implements Runnable {

    // Constants
    public static final int PADDING_TOP = 10;
    public static final int PADDING_LEFT = 110;

    private Thread drawer;                  // Thread to draw
    private BufferStrategy bs;              // to have several buffers when displaying
    private Graphics g;                     // to paint objects
    private Display display;                // to display in the game 
    private String title;                   // title of the window
    private int width;                      // width of the window
    private int height;                     // height of the window
    private Thread thread;                  // thread to create the game
    private boolean running;                // to set the game
    private boolean paused;                 // to store the pause flag
    private boolean gameEnded;              // to store if the game is over
    private int score;                      // to store the score
    private int lives;                      // to store lives;
    private Player player;                  // to store the player
    private LinkedList<Alien> aliens;       // to store all the aliens
    private LinkedList<Bomb> alienShots;     // to store all alien shots
    private Shot shot;                      // to store the shot
    private KeyManager keyManager;          // to manage the keyboard
    private Formatter file;                 // to store the saved game file.
    private Scanner scanner;

    private boolean moveDown;               // flag to move all instances of aliens down
    private int alienMoveCounter;           // to move the aliens on a certain time
    private int alienTickLimit;             // the limit in which the movement is done
    private int alienBombCounter;           // to spawn an alien bomb on a certain time

    /**
     * To create title,	width and height and set the game is still not running
     *
     * @param title to set the title of the window
     * @param width to set the width of the window
     * @param height to set the height of the window
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
        this.lives = 3;
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

    /**
     * Gets the Player instance
     *
     * @return <code>Player</code> player
     */
    public Player getPlayer() {
        return player;
    }

    /**
     * Gets the score
     *
     * @return <code>int</code> score
     */
    public int getScore() {
        return score;
    }

    /**
     * Sets the score
     *
     * @param score
     */
    public void setScore(int score) {
        this.score = score;
    }

    /**
     * Sets the player instance
     *
     * @param player
     */
    public void setPlayer(Player player) {
        this.player = player;
    }

    /**
     * Gets the Alien linked-list instance
     *
     * @return <code>LinkedList<Alien></code> aliens
     */
    public LinkedList<Alien> getAliens() {
        return aliens;
    }

    /**
     * Sets the Aliens linked-list instance
     *
     * @param aliens
     */
    public void setAliens(LinkedList<Alien> aliens) {
        this.aliens = aliens;
    }

    /**
     * Gets moveDown value
     *
     * @return <boolean>moveDown</boolean>
     */
    public boolean moveDown() {
        return moveDown;
    }

    /**
     * Sets moveDown value
     *
     * @param moveDown
     */
    public void setMoveDown(boolean moveDown) {
        this.moveDown = moveDown;
    }

    /**
     * Gets the alienMoveCounter value
     *
     * @return <code>int</code> alienMoveCounter
     */
    public int getAlienMoveCounter() {
        return alienMoveCounter;
    }

    /**
     * Sets alienMoveCounter value
     *
     * @param alienMoveCounter
     */
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
     * Gets lives
     * 
     * @return <code>int</code> lives
     */
    public int getLives() {
        return lives;
    }

    /**
     * Sets lives
     * 
     * @param lives 
     */
    public void setLives(int lives) {
        this.lives = lives;
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

    public int getAlienBombCounter() {
        return alienBombCounter;
    }

    public void setAlienBombCounter(int alienBombCounter) {
        this.alienBombCounter = alienBombCounter;
    }
    
    /**
     * Calls every save method from the classes and writes the score and lives
     * on a file
     */
    private void save(){
        try{
            file = new Formatter("game.txt");
        } catch (Exception e) {
            System.out.println("Hubo un problema con el guardado");
        }
        
        if (shot != null) {
            file.format("%s", "1 ");
            shot.save(file);
        }
        else {
            file.format("%s", "0 ");
        }
        player.save(file);
        file.format("%s", getScore() + " ");
        //file.format("%s", getLives() + " ");
        file.format("%s%s%s", getAlienMoveCounter() + " ", getAlienTickLimit() + " ", getAlienBombCounter() + " ");
        file.format("%s", alienShots.size() + " ");

        // Iterates over all the powerUps and write them in the file
        for (int i = 0; i < alienShots.size(); i++) {
            Bomb alienShot = alienShots.get(i);

            alienShot.save(file);
        }
        file.format("%s", aliens.size() + " ");

        // Iterates over all the bricks and write them in the file
        for (int i = 0; i < aliens.size(); i++) {
            Alien alien = aliens.get(i);

            alien.save(file);
        }
        if (gameEnded) {
            file.format("%s", "1 ");
        }
        else {
            file.format("%s", "0 ");
        }
        
        file.close();
    }
    
    /**
     * Reads the saved-game file and uses the load methods of the classes to
     * restore the saved game
     */
    private void load() {
        try {
            int x, y, xspeed, yspeed, width, height;
            String type;
            scanner = new Scanner(new File("game.txt"));
            
            // 1 indicates there is a shot in the save file
            if (scanner.nextInt() == 1) {
            
                // Loads shot attributes
                x = scanner.nextInt();
                y = scanner.nextInt();

                if (shot != null) {
                    shot.load(x, y); 
                }
                else {
                    shot = new Shot(x, y, 8, 16);
                }
            } else {
                if (shot != null) {
                    shot = null;
                }
            }

            // Loads player attributes
            x = scanner.nextInt();
            y = scanner.nextInt();
            player.load(x, y);

            // Loads score and lives
            x = scanner.nextInt();
            //y = scanner.nextInt();
            setScore(x);
            //setLives(y);
            x = scanner.nextInt();
            setAlienMoveCounter(x);
            x = scanner.nextInt();
            setAlienTickLimit(x);
            x = scanner.nextInt();
            setAlienBombCounter(x);

            // Loads powerUps
            int numOfAlienShots = scanner.nextInt();
            alienShots.clear();
            for (int i = 0; i < numOfAlienShots; i++) {
                x = scanner.nextInt();
                y = scanner.nextInt();
                alienShots.add(new Bomb(x, y, 16, 32));
            }

            // Loads methbricks attributes
            int numOfAliens = scanner.nextInt();
            aliens.clear();
            for (int i = 0; i < numOfAliens; i++) {
                x = scanner.nextInt();
                y = scanner.nextInt();
                int direction = scanner.nextInt();
                aliens.add(new Alien(x, y, 48, 48, this, direction));
            }
            
            x = scanner.nextInt();
            if (x == 1) {
                gameEnded = true;
            } else if (x == 0) {
                gameEnded = false;
            }
            
        } catch (Exception e) {
            System.out.println("Hubo un problema con  carga.");
        }

    }

    /**
     * Initialising the display window of the game
     */
    private void init() {
        display = new Display(title, width, height);
        display.getJframe().addKeyListener(keyManager);
        Assets.init();
        Assets.backgroundMusic.setLooping(true);
        Assets.backgroundMusic.setVolume(-20.0f);
        Assets.backgroundMusic.play();
        player = new Player(getWidth() / 2 - 24, getHeight() - 64, 48, 48, 5, this);
        for (int i = 0; i < 5; i++) {
            int spacing = 30;
            for (int j = 0; j < 10; j++) {
                aliens.add(new Alien(PADDING_LEFT + 48 * j + spacing, PADDING_TOP + i * 48, 48, 48, this));
                spacing += 30;
            }
        }
        setScore(0);

    }

    @Override
    public void run() {
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
        Assets.shot.setVolume(-20.0f);
        Assets.shot.play();
    }

    private void tick() {
        keyManager.tick();
        if (getKeyManager().p && getKeyManager().isPressable()) {
            setPause(!isPaused());
            getKeyManager().setPressable(false);
        }
        
        if (getKeyManager().g) {
            save();
        }
        
        if (getKeyManager().c) {
            load();
        }

        if (!isPaused() && !gameEnded) {
            player.tick();
            setAlienMoveCounter(getAlienMoveCounter() + 1);
            alienBombCounter++;
            if(lives == 0){
                gameEnded = true;
            }
            for (int i = 0; i < aliens.size(); i++) {
                Alien alien = aliens.get(i);
                alien.tick();
                if (alien.getY() + alien.getHeight() >= getHeight() - 2 * getPlayer().getHeight()) {
                    gameEnded = true;
                }

            }
            
            if (alienBombCounter == getAlienTickLimit() * 2) {
                int alienDropperIndex = (int)(Math.random() * (aliens.size()));
                Alien alienDropper = aliens.get(alienDropperIndex);
                alienShots.add(new Bomb(alienDropper.getX() + alienDropper.getWidth() / 2,alienDropper.getY() + alienDropper.getHeight(), 16, 32));
                alienBombCounter = 0;
            }
            for (int i = 0; i < alienShots.size(); i++) {
                Bomb alienShot = alienShots.get(i);
                alienShot.tick();
                if (alienShots.get(i).hits(player)) {
                        setLives(getLives() - 1);
                        alienShots.remove(i);
                        Assets.enemyDestroyed.play();
                }
            }
            
            if (shot != null) {
                shot.tick();
                boolean shotExists = true;
                for (int i = 0; i < aliens.size() && shotExists; i++) {
                    if (getShot().hits(aliens.get(i))) {
                        Assets.enemyDestroyed.setVolume(-20.0f);
                        Assets.enemyDestroyed.play();
                        aliens.remove(i);
                        setScore(getScore() + 10);
                        shot = null;
                        shotExists = false;
                    }
                }
                for (int i = 0; i < alienShots.size() && shotExists; i++) {   
                    if (getShot().hits(alienShots.get(i))) {
                        Assets.enemyDestroyed.play();
                        alienShots.remove(i);
                        shot = null;
                        shotExists = false;
                    }
                }
                if (shotExists && getShot().getY() <= 0) {
                    Assets.shotDestroyed.setVolume(-20.0f);
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
            g.setFont(new Font("Dialog", Font.BOLD, 24));
            g.setColor(Color.WHITE);
            g.drawString("Score: " + getScore(), 12, 24);
            g.setColor(Color.white);
            g.drawLine(0, getHeight() - 2 * getPlayer().getHeight(), getWidth(), getHeight() - 2 * getPlayer().getHeight());
            player.render(g);
            for (int i = 0; i < alienShots.size(); i++) {
                Bomb alienShot = alienShots.get(i);
                alienShot.render(g);
            }
            for (int i = 0; i < aliens.size(); i++) {
                aliens.get(i).render(g);
            }
            for (int i = 0; i < lives; i++) {
                    g.drawImage(Assets.life, getWidth() - 40 - 32 * i, 4, 32, 32, null); // EL -5 es estetico
                }
            if (shot != null) {
                shot.render(g);
            }
            if (isPaused()) {
                g.drawImage(Assets.pause, 0, 0, width, height, null);
            }
            if(gameEnded){
                g.drawImage(Assets.gameOverScreen, 0, 0, width, height, null);
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
