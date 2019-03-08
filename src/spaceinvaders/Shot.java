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
public class Shot extends Item {

    private final int H_SPACE = 6;
    private final int V_SPACE = 1;
    
    public Shot(int x, int y, int width, int height) {
        super(x, y, width, height);
        setX(x + H_SPACE);
        setY(y - V_SPACE);
    }

    @Override
    public void tick() {
    }

    @Override
    public void render(Graphics g) {
    }
}
