package company.Game;

import company.engine.GameLoop;
import company.engine.Renderer;
import company.engine.gfx.ImageTile;

import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

public class Player extends Character {

    private int tileX, tileY;
    private float offX, offY;

    private boolean isGround = false;
    int jump = -5;

    float speed = 100, fallSpeed = 10;
    float fallDistance = 0;
    float temp = 0;

    public Player(int posX, int posY, int width, int height, int health) {
        super(posX, posY, width, height, health, new ImageTile("/testMe.png",16,16));
        this.tileX = posX;
        this.tileY = posY;
        this.offX = 0;
        this.offY = 0;

        //this.posX = posX * 16;
        //this.posY = posY * 16;
        this.width = 16;
        this.height = 16;
    }

    @Override
    public void update(GameLoop gameLoop, GameManager gameManager, float dt) {

        if(gameLoop.getInput().isButton(MouseEvent.BUTTON1)){
            gameManager.gameWorld.buildBlock((int)(gameLoop.getInput().getMouseX() + gameManager.camera.getOffX()), (int)(gameLoop.getInput().getMouseY() + gameManager.camera.getOffY()));
            if(gameManager.getGameStatus() == 3){
                gameManager.clientSocket.getPrintWriter().println("BlockPlaced::" + gameManager.gameWorld.getDestroyedId((int)(gameLoop.getInput().getMouseX() + gameManager.camera.getOffX()), (int)(gameLoop.getInput().getMouseY() + gameManager.camera.getOffY())) + "::");
            }
        }

        if(gameLoop.getInput().isButton(MouseEvent.BUTTON3)){
            gameManager.gameWorld.destroyBlock((int)(gameLoop.getInput().getMouseX() + gameManager.camera.getOffX()), (int)(gameLoop.getInput().getMouseY() + gameManager.camera.getOffY()));
            if(gameManager.getGameStatus() == 3){
                gameManager.clientSocket.getPrintWriter().println("BlockDestroyed::" + gameManager.gameWorld.getDestroyedId((int)(gameLoop.getInput().getMouseX() + gameManager.camera.getOffX()), (int)(gameLoop.getInput().getMouseY() + gameManager.camera.getOffY())) + "::");
            }
        }

        if(gameLoop.getInput().isKey(KeyEvent.VK_ESCAPE)){
            gameManager.setGameStatus(2);
        }

        if(gameLoop.getInput().isKey(KeyEvent.VK_G)){
            gameManager.gameWorld.getWorldsBlocks().set(270, new EmptyBlock((int)(gameManager.gameWorld.getWorldsBlocks().get(270).posX),(int)(gameManager.gameWorld.getWorldsBlocks().get(270).posY), 16,16));
        }

        if(gameLoop.getInput().isKey(KeyEvent.VK_D)){
            posX += dt * speed;
        }

        if(gameLoop.getInput().isKey(KeyEvent.VK_A)){
            posX -= dt * speed;
        }

        /*
        if(!gameManager.gameWorld.getCollision(posX-16, posY)){
            posX += dt * speed;
        }

        if(!gameManager.gameWorld.getCollision(posX+16, posY)){
            posX -= dt * speed;
        }
        */

        fallDistance += dt * fallSpeed;

        if(!gameManager.gameWorld.getCollision(posX, posY + 16)){
            fallDistance = 0;
            isGround = true;
        }

        if(!gameManager.gameWorld.getCollision(posX, posY - 16)){
            fallDistance += dt * fallSpeed;
            isGround = false;
        }

        if(gameLoop.getInput().isKey(KeyEvent.VK_W) && isGround){
            fallDistance = jump;
            isGround = false;
        }

        posY += fallDistance;

        temp += dt;
        if(temp > 3){
            temp = 0;
        }
/*
        if(gameLoop.getInput().isButton(MouseEvent.BUTTON1)){
            gameManager.gameWorld.buildBlock((int)(gameLoop.getInput().getMouseX() + gameManager.camera.getOffX()), (int)(gameLoop.getInput().getMouseY() + gameManager.camera.getOffY()));
        }

        if(gameLoop.getInput().isButton(MouseEvent.BUTTON3)){
            gameManager.gameWorld.destroyBlock((int)(gameLoop.getInput().getMouseX() + gameManager.camera.getOffX()), (int)(gameLoop.getInput().getMouseY() + gameManager.camera.getOffY()));
            System.out.println("Destroed");
        }

        if(gameLoop.getInput().isKey(KeyEvent.VK_ESCAPE)){
            gameManager.setGameStatus(2);
        }

        if(gameLoop.getInput().isKey(KeyEvent.VK_D)){
            if(!gameManager.gameWorld.getCollision(tileX , tileY)|| !gameManager.gameWorld.getCollision(tileX + 1, tileY + (int)Math.signum((int)offY))){
                if(offX < 0){
                    offX += dt * speed;
                    if(offX > 0){
                        offX = 0;
                    }
                }
                else{
                    offX = 0;
                }
            }
            else{
                offX += dt * speed;
            }
        }

        if(gameLoop.getInput().isKey(KeyEvent.VK_A)){
            if(!gameManager.gameWorld.getCollision(tileX , tileY) || !gameManager.gameWorld.getCollision(tileX , tileY + (int)Math.signum((int)offY))){
                if(offX > 0){
                    offX -= dt * speed;
                    if(offX < 0){
                        offX = 0;
                    }
                }
                else{
                    offX = 0;
                }
            }
            else{
                offX -= dt * speed;
            }
        }


        fallDistance += dt * fallSpeed;


        if(gameLoop.getInput().isKey(KeyEvent.VK_W) && isGround){
            fallDistance = jump;
            isGround = false;
        }

        offY += fallDistance;

        if(fallDistance < 0){
            if((!gameManager.gameWorld.getCollision(tileX, tileY ) || !gameManager.gameWorld.getCollision(tileX+ (int)Math.signum((int)offX), tileY)) && offY < 0){
                fallDistance = 0;
                offY = 1;
            }
        }

        if(fallDistance > 0){
            if((!gameManager.gameWorld.getCollision(tileX, tileY) || !gameManager.gameWorld.getCollision(tileX+ (int)Math.signum((int)offX), tileY )) && offY >= 0){
                fallDistance = 0;
                offY = 1;
                isGround = true;
            }
        }

        if(offY > 16/2){
           tileY += 16;
            offY -= 16;
        }

        if(offY < 16/2){
            tileY -= 16;
            offY += 16;
        }

        if(offX > 16/2){
            tileX += 16;
            offX -= 16;
        }

        if(offX < 16/2){
            tileX -= 16;
            offX += 16;
        }

        posX = tileX + offX;
        posY = tileY + offY;

        temp += dt;
        if(temp > 3){
            temp = 0;
        }

/*
        if(gameLoop.getInput().isKey(KeyEvent.VK_W)){
            this.setPosY((int)(getPosY() - dt * speed));
        }
        if(gameLoop.getInput().isKey(KeyEvent.VK_D)){
            this.setPosX((int)(getPosX() + dt * speed));
        }
        if(gameLoop.getInput().isKey(KeyEvent.VK_S)){
            this.setPosY((int)(getPosY() + dt * speed));
        }
        if(gameLoop.getInput().isKey(KeyEvent.VK_A)){
            this.setPosX((int)(getPosX() - dt * speed));
        }
*/

    }

    public void getCoords(ClientSocket clientSocket){
        clientSocket.getPrintWriter().println("giveMeCoords::");
    }

    public void sendMyCoords(ClientSocket clientSocket){
        clientSocket.getPrintWriter().println("coords::"+(int)posX+"::"+(int)posY+"::");
    }

    @Override
    public void render(GameLoop gameLoop, Renderer renderer) {
        renderer.drawImageTile(this.getImageTile(),(int)posX, (int)posY,(int)temp,6);
    }
}
