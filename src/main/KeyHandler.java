package main;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyHandler implements KeyListener {

    GamePanel gp;
    public boolean  upPressed= false, leftPressed= false, rightPressed= false, downPressed= false, escapePressed = false,
                    endTurnPressed = false, endTurnReleased = false, movePressed = false, yKeyPressed = false, enterPressed = false;

    public KeyHandler(GamePanel gp){
        this.gp = gp;
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {

        if(e.getKeyCode() == KeyEvent.VK_Y){
            yKeyPressed = true;
        }
        if(e.getKeyCode() == KeyEvent.VK_E){
            endTurnReleased = false;
            endTurnPressed = true;
        }
        if(e.getKeyCode() == KeyEvent.VK_M){
            movePressed = true;
        }

        if(e.getKeyCode() == KeyEvent.VK_UP){
            upPressed = true;
        }
        if(e.getKeyCode() == KeyEvent.VK_DOWN){
            downPressed = true;
        }
        if(e.getKeyCode() == KeyEvent.VK_LEFT){
            leftPressed= true;
        }
        if(e.getKeyCode() == KeyEvent.VK_RIGHT){
            rightPressed = true;
        }
        if(e.getKeyCode() == KeyEvent.VK_ESCAPE){
            escapePressed = true;
        }
        if(e.getKeyCode() == KeyEvent.VK_ENTER){
            enterPressed = true;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {

        if(e.getKeyCode() == KeyEvent.VK_Y){
            yKeyPressed = false;
        }
        if(e.getKeyCode() == KeyEvent.VK_E){
            endTurnPressed = false;
            endTurnReleased = true;
        }
        if(e.getKeyCode() == KeyEvent.VK_M){
            movePressed = false;
        }
        if(e.getKeyCode() == KeyEvent.VK_UP){
            upPressed = false;
        }
        if(e.getKeyCode() == KeyEvent.VK_DOWN){
            downPressed = false;
        }
        if(e.getKeyCode() == KeyEvent.VK_LEFT){
            leftPressed= false;
        }
        if(e.getKeyCode() == KeyEvent.VK_RIGHT){
            rightPressed = false;
        }
        if(e.getKeyCode() == KeyEvent.VK_ESCAPE){
            if (gp.gameState == gp.playState)
                gp.gameState = gp.settingsState;
            else if (gp.gameState == gp.settingsState)
                gp.gameState = gp.playState;
            else if(gp.gameState == gp.castleState){
                gp.gameState = gp.playState;
                gp.player.justLeft = true;
            }
            escapePressed = false;
        }
        if(e.getKeyCode() == KeyEvent.VK_P){
            if(gp.gameState == gp.playState)
                gp.gameState = gp.pauseState;
            else if (gp.gameState == gp.pauseState)
                gp.gameState = gp.playState;

        }
        if(e.getKeyCode() == KeyEvent.VK_ENTER){
            enterPressed = false;
        }
    }
}
