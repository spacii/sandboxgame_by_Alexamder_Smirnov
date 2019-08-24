package company.Game;

import company.engine.GameLoop;
import company.engine.Renderer;
import company.engine.gfx.Image;

import java.awt.event.MouseEvent;
import java.util.ArrayList;

public class ServerBrowser {

    private int x = 65, currentY = 50;
    private ArrayList<ServerInBrowser> servers;
    private Image image = new Image("/ServerBrowser.jpg");
    private GameManager gameManager;
    private int selectedServerIndex;

    ServerBrowser(GameManager gameManager){
        this.gameManager = gameManager;
        servers = new ArrayList<>();
    }

    public void update(GameLoop gameLoop){

        if(gameLoop.getInput().isButton(MouseEvent.BUTTON1)){
            for(int i = 0; i < servers.size(); i++){
                if((gameLoop.getInput().getMouseX() >= servers.get(i).getX() && gameLoop.getInput().getMouseX() <= servers.get(i).getX2())
                    && (gameLoop.getInput().getMouseY() >= servers.get(i).getY() && gameLoop.getInput().getMouseY() <= servers.get(i).getY2())){
                    selectedServerIndex = i;
                }
            }
        }

        if((gameLoop.getInput().getMouseX() >= 405 && gameLoop.getInput().getMouseX() <= 615)
                && (gameLoop.getInput().getMouseY() >= 83 && gameLoop.getInput().getMouseY() <= 133)){
            if(gameLoop.getInput().isButton(MouseEvent.BUTTON1)){
                connectToSelectedServer();
            }
        }

        if((gameLoop.getInput().getMouseX() >= 405 && gameLoop.getInput().getMouseX() <= 615)
                && (gameLoop.getInput().getMouseY() >= 150 && gameLoop.getInput().getMouseY() <= 195)){
            if(gameLoop.getInput().isButton(MouseEvent.BUTTON1)){
                addServer();
            }
        }

        if((gameLoop.getInput().getMouseX() >= 405 && gameLoop.getInput().getMouseX() <= 615)
                && (gameLoop.getInput().getMouseY() >= 212 && gameLoop.getInput().getMouseY() <= 256)){
            if(gameLoop.getInput().isButton(MouseEvent.BUTTON1)){
                back();
            }
        }
    }

    public void render(Renderer renderer){
        renderer.drawImage(image, 0,0);

        for(int i = 0; i < servers.size(); i++){
            servers.get(i).render(renderer);
            servers.get(i).render(renderer);
        }
    }

    private void addServer(){
        gameManager.setGameStatus(5);
    }

    private void connectToSelectedServer(){
        System.out.println(servers.get(selectedServerIndex).getIp() + ":" + servers.get(selectedServerIndex).getPort());
        gameManager.connectToServer(servers.get(selectedServerIndex).getIp(), servers.get(selectedServerIndex).getPort());
    }

    public void addServerInList(String ip, int port){
        servers.add(new ServerInBrowser(x,currentY, ip, port));
        currentY += 70;
    }

    private void back(){
        gameManager.setGameStatus(0);
    }

    public ArrayList<ServerInBrowser> getServers(){
        return servers;
    }
}
