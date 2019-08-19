package company.Game;

import company.engine.GameLoop;
import company.engine.Renderer;
import company.engine.gfx.Image;

import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

public class EscapeMenu {
    private Image image = new Image("/MenuEscape.jpg");
    private GameManager gameManager;
    private GameLoop gameLoop;
    private boolean saveG1 = true;

    public EscapeMenu(GameManager gameManager){
        this.gameManager = gameManager;
    }

    public void update(GameLoop gameLoop){

        if(gameLoop.getInput().isKey(KeyEvent.VK_ESCAPE)){
            gameManager.setGameStatus(1);
        }

        if((gameLoop.getInput().getMouseX() >= 224 && gameLoop.getInput().getMouseX() <= 414)
                && (gameLoop.getInput().getMouseY() >= 62 && gameLoop.getInput().getMouseY() <= 127)){
            if(gameLoop.getInput().isButton(MouseEvent.BUTTON1)){
                exitToMainMenu();
            }
        }

        if((gameLoop.getInput().getMouseX() >= 224 && gameLoop.getInput().getMouseX() <= 414)
                && (gameLoop.getInput().getMouseY() >= 138 && gameLoop.getInput().getMouseY() <= 204)){
            if(gameLoop.getInput().isButton(MouseEvent.BUTTON1)){
                if(saveG1){
                    saveGame();
                    saveG1 = false;
                }
            }
        }

        if((gameLoop.getInput().getMouseX() >= 224 && gameLoop.getInput().getMouseX() <= 414)
                && (gameLoop.getInput().getMouseY() >= 216 && gameLoop.getInput().getMouseY() <= 281)){
            if(gameLoop.getInput().isButton(MouseEvent.BUTTON1)){
                exitGame();
            }
        }
    }

    public void render(Renderer renderer){
        renderer.drawImage(image, 0, 0);
    }

    public void exitToMainMenu(){
        gameManager.setGameStatus(0);
    }

    public void saveGame(){
        try{
            ArrayList<Integer> IDs = new ArrayList<>();
            ArrayList<Integer> Xs = new ArrayList<>();
            ArrayList<Integer> Ys = new ArrayList<>();
            for(int i = 0; i < gameManager.gameWorld.getWorldsBlocks().size(); i++){
                IDs.add(gameManager.gameWorld.getWorldsBlocks().get(i).getID());
                Xs.add((int)gameManager.gameWorld.getWorldsBlocks().get(i).getPosX());
                Ys.add((int)gameManager.gameWorld.getWorldsBlocks().get(i).getPosY());
            }

            FileOutputStream fos= new FileOutputStream("id.dat");
            ObjectOutputStream oos= new ObjectOutputStream(fos);
            oos.writeObject(IDs);
            oos.close();
            fos.close();

            FileOutputStream fos1= new FileOutputStream("x.dat");
            ObjectOutputStream oos1= new ObjectOutputStream(fos1);
            oos1.writeObject(Xs);
            oos1.close();
            fos1.close();

            FileOutputStream fos2= new FileOutputStream("y.dat");
            ObjectOutputStream oos2= new ObjectOutputStream(fos2);
            oos2.writeObject(Ys);
            oos2.close();
            fos2.close();

            System.out.println("com.company.Game Saved!");
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void exitGame(){
        System.exit(1);
    }
}
