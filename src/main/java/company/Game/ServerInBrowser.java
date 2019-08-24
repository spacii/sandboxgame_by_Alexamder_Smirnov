package company.Game;

import company.engine.GameLoop;
import company.engine.Renderer;
import company.engine.gfx.Image;

import java.awt.event.MouseEvent;

public class ServerInBrowser {

    private int x, y, port, sizeX = 272, sizeY = 50;
    private String ip;
    private Image image = new Image("/ServerInBrowser.png");

    //private ClientEcho clientEcho;

    ServerInBrowser(int x, int y, String ip, int port){
        this.x = x;
        this.y = y;
        this.port = port;
        this.ip = ip;

        //clientEcho = new ClientEcho();
    }

    public void update(GameLoop gameLoop){
        if((gameLoop.getInput().getMouseX() >= 405 && gameLoop.getInput().getMouseX() <= 615)
                && (gameLoop.getInput().getMouseY() >= 275 && gameLoop.getInput().getMouseY() <= 313)){
            if(gameLoop.getInput().isButton(MouseEvent.BUTTON1)){
            }
        }
    }

    public void render(Renderer renderer, int count){
        renderer.drawImage(image, x,y);
        renderer.drawText(ip + ":" + port, x+10, y+10, 0xff00ffff);

        String countStr = "PLAYERS: " + count + "/INF.";
        renderer.drawText(countStr,x+10, y+20, 0xff00ffff );
    }

    int getX2(){
        return x + sizeX;
    }

    int getY2(){
        return y + sizeY;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getPort() {
        return port;
    }

    public String getIp() {
        return ip;
    }
}
