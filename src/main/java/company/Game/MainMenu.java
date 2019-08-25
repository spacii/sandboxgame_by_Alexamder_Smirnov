package company.Game;

import company.engine.GameLoop;
import company.engine.Renderer;
import company.engine.gfx.Image;

import java.awt.event.MouseEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;

public class MainMenu {

    private Image image = new Image("/MainMenu.jpg");
    //private Image image = new Image((MainMenu.class.getResourceAsStream("/MainM.jpg").toString()));
    private GameManager gameManager;
    private boolean load1 = true;

    public MainMenu(GameManager gameManager){
        this.gameManager = gameManager;
    }

    public void update(GameLoop gameLoop){
        if(gameLoop.getInput().isButton(MouseEvent.BUTTON1)){
            if((gameLoop.getInput().getMouseX() >= 215 && gameLoop.getInput().getMouseX() <= 423)
                    && (gameLoop.getInput().getMouseY() >= 75 && gameLoop.getInput().getMouseY() <= 120)){
                startNewGame(gameManager);
            }
            if((gameLoop.getInput().getMouseX() >= 215 && gameLoop.getInput().getMouseX() <= 423)
                    && (gameLoop.getInput().getMouseY() >= 150 && gameLoop.getInput().getMouseY() <= 195)){
                openServerBrowser();
            }
            if((gameLoop.getInput().getMouseX() >= 215 && gameLoop.getInput().getMouseX() <= 423)
                    && (gameLoop.getInput().getMouseY() >= 225 && gameLoop.getInput().getMouseY() <= 270)){
                exitGame();
            }
        }
    }

    public void render(Renderer renderer){
        renderer.drawImage(image, 0,0);
    }

    public void startNewGame(GameManager gameManager){
        gameManager.startNewGame();
    }

    public void connectToServer(){
        //gameManager.connectToServer();
    }

    public void loadGame(){

    }

    private void openServerBrowser(){
        gameManager.setGameStatus(4);
    }

    private void exitGame(){
        System.exit(1);
    }
}
