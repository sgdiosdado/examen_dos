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
    
    private boolean open;
    private Bomb bomb;
    private Game game;
    public enum Direction {
        RIGHT, LEFT
    }
    private Direction direction;
    
    public Alien (int x, int y, int width, int height, Game game) {
        super(x, y, width, height);
        bomb = new Bomb(x, y, width, height);
        this.game = game;
        this.direction = Direction.RIGHT;
    }
    
    public Bomb getBomb() {
        return bomb;
    }
    
    public Game getGame() {
        return game;
    }

    public void setGame(Game game) {
        this.game = game;
    }

    public Direction getDirection() {
        return direction;
    }

    public void setDirection(Direction direction) {
        this.direction = direction;
    }

    @Override
    public void tick() {
        
        if (game.getAlienMoveCounter() == 60) {
            open = !open;
            if (getDirection() == Direction.RIGHT) {
                setX(getX() + 24);
            } else if (getDirection() == Direction.LEFT) {
                setX(getX() - 24);
            }
        }
        // Collisions with the screen
        if(getX() + getWidth() >= getGame().getWidth()){
            game.setMoveDown(true);
            setX(getGame().getWidth() - getWidth());
        }
        if(getX() <= 0){
            game.setMoveDown(true);
            setX(0);
        }
        
    }

    @Override
    public void render(Graphics g) {
        if (open) {
            g.drawImage(Assets.alien, getX(), getY(), getWidth(), getHeight(), null);
        }
        else {
            g.drawImage(Assets.alien2, getX(), getY(), getWidth(), getHeight(), null);
        }
    }
}
