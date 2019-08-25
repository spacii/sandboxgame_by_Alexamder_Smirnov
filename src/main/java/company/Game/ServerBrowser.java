package company.Game;

import company.engine.GameLoop;
import company.engine.Renderer;
import company.engine.gfx.Image;
import server.ServerEcho;

import java.awt.event.MouseEvent;
import java.util.ArrayList;

public class ServerBrowser {

    private int x = 65, currentY = 50;
    private ArrayList<ServerInBrowser> servers;
    private Image image = new Image("/ServerBrowser.jpg");
    private Image refresh_button = new Image("/refresh.jpg");
    private GameManager gameManager;
    private ClientEchoNew clientEchoNew;
    private int selectedServerIndex;


    ServerBrowser(GameManager gameManager){
        this.gameManager = gameManager;
        this.clientEchoNew = new ClientEchoNew();
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

            if((gameLoop.getInput().getMouseX() >= 405 && gameLoop.getInput().getMouseX() <= 615)
                    && (gameLoop.getInput().getMouseY() >= 60 && gameLoop.getInput().getMouseY() <= 110)){
                connectToSelectedServer();
            }
            if((gameLoop.getInput().getMouseX() >= 405 && gameLoop.getInput().getMouseX() <= 615)
                    && (gameLoop.getInput().getMouseY() >= 120 && gameLoop.getInput().getMouseY() <= 170)){
                addServer();
            }
            if((gameLoop.getInput().getMouseX() >= 405 && gameLoop.getInput().getMouseX() <= 615)
                    && (gameLoop.getInput().getMouseY() >= 180 && gameLoop.getInput().getMouseY() <= 230)){
                removeSelectedServer();
            }
            if((gameLoop.getInput().getMouseX() >= 405 && gameLoop.getInput().getMouseX() <= 615)
                    && (gameLoop.getInput().getMouseY() >= 240 && gameLoop.getInput().getMouseY() <= 290)){
                back();
            }
            if((gameLoop.getInput().getMouseX() >= 149 && gameLoop.getInput().getMouseX() <= 258)
                    && (gameLoop.getInput().getMouseY() >= 283 && gameLoop.getInput().getMouseY() <= 310)){
                refreshServers();
            }
        }
        /*
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
        */
    }

    public void render(Renderer renderer){
        renderer.drawImage(image, 0,0);
        renderer.drawImage(refresh_button,149,283);

        for(int i = 0; i < servers.size(); i++){
            if(i == selectedServerIndex){
                servers.get(i).render(renderer, true);
            }
            else{
                servers.get(i).render(renderer, false);
            }
        }
    }

    private void addServer(){
        gameManager.setGameStatus(5);
    }

    private void connectToSelectedServer(){
        System.out.println(servers.get(selectedServerIndex).getIp() + ":" + servers.get(selectedServerIndex).getPort());
        gameManager.connectToServer(servers.get(selectedServerIndex).getIp(), servers.get(selectedServerIndex).getPort());
    }

    private void removeSelectedServer(){
        if(servers.size() > 0){
            servers.remove(selectedServerIndex);

            for(int i = selectedServerIndex; i < servers.size(); i++){
                servers.get(i).setY(servers.get(i).getY()-70);
                currentY -= 70;
            }

            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void addServerInList(String ip, int port){
        servers.add(new ServerInBrowser(x,currentY, ip, port));
        currentY += 70;
        //serversUDP.add(new ClientEcho(ip, port));
    }

    private void back(){
        gameManager.setGameStatus(0);
    }

    public void refreshServers(){
        for(int i = 0; i < servers.size(); i++){
            servers.get(i).updateInfo(clientEchoNew);
        }
    }

    public ArrayList<ServerInBrowser> getServers(){
        return servers;
    }
}
