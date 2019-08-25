package company.Game;

import company.engine.GameLoop;
import company.engine.Renderer;
import company.engine.gfx.Image;

import java.awt.event.MouseEvent;

public class NoteAboutProject {

    private Image image = new Image("/Note.jpg");
    private GameManager gameManager;

    private String info = "Для запуска сервера использовать server_start.bat.";

    public NoteAboutProject(GameManager gameManager){
        this.gameManager = gameManager;
    }

    public void update(GameLoop gameLoop){
        if(gameLoop.getInput().isButton(MouseEvent.BUTTON1)){
            if((gameLoop.getInput().getMouseX() >= 132 && gameLoop.getInput().getMouseX() <= 483)
                    && (gameLoop.getInput().getMouseY() >= 300 && gameLoop.getInput().getMouseY() <= 340)){
                gameManager.setGameStatus(0);
            }
        }
    }

    public void render(Renderer renderer){
        renderer.drawImage(image, 0,0);
    }
}
