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
public class Alien extends Item {

    private boolean open;
    private Game game;
    private int speed;

    public enum Direction {
        RIGHT, LEFT
    }
    private Direction direction;

    public Alien(int x, int y, int width, int height, Game game) {
        super(x, y, width, height);
        this.game = game;
        this.direction = Direction.RIGHT;
        this.speed = 12;
    }
    
    public Alien(int x, int y, int width, int height, Game game, int direction) {
        super(x, y, width, height);
        this.game = game;
        this.direction = Direction.RIGHT;
        this.speed = 12;
        if (direction == 0) {
            this.direction = Direction.LEFT;
        } else if (direction == 1) {
            this.direction = Direction.RIGHT;
        }
    }

    /**
     * Gets the speed
     * 
     * @return speed
     */
    public int getSpeed() {
        return speed;
    }

    /**
     * Sets speed
     * 
     * @param speed 
     */
    public void setSpeed(int speed) {
        this.speed = speed;
    }
    
    /**
     * Gets the game
     * 
     * @return game
     */
    public Game getGame() {
        return game;
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
     * Gets the Direction of the alien
     * 
     * @return direction
     */
    public Direction getDirection() {
        return direction;
    }

    /**
     * Sets the direction of the alien
     * 
     * @param direction
     */
    public void setDirection(Direction direction) {
        this.direction = direction;
    }
    
    /**
     * Writes it's data in the saving file
     *
     * @param file
     */
    public void save(Formatter file) {
        file.format("%s%s", getX() + " ", getY() + " ");
        if (direction == Direction.LEFT) {
            file.format("%s", "0 ");
        } else if (direction == Direction.RIGHT) {
            file.format("%s", "1 ");
        }
    }
    
    /**
     * Loads it's necessary data from a file
     *
     * @param x
     * @param y
     */
    public void load(int x, int y) {
        setX(x);
        setY(y);
    }

    @Override
    public void tick() {

        if (game.getAlienMoveCounter() == game.getAlienTickLimit()) {
            open = !open;
            if (getDirection() == Direction.RIGHT) {
                setX(getX() + getSpeed());
            } else if (getDirection() == Direction.LEFT) {
                setX(getX() - getSpeed());
            }
        }

        // Collisions with the screen
        if (getX() + getWidth() > getGame().getWidth()) {
            game.setMoveDown(true);
        }
        if (getX() < 0) {
            game.setMoveDown(true);
        }

    }

    @Override
    public void render(Graphics g) {
        if (open) {
            g.drawImage(Assets.alien, getX(), getY(), getWidth(), getHeight(), null);
        } else {
            g.drawImage(Assets.alien2, getX(), getY(), getWidth(), getHeight(), null);
        }
    }
}
