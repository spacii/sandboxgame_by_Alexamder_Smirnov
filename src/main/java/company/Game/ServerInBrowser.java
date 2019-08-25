package company.Game;

import company.engine.GameLoop;
import company.engine.Renderer;
import company.engine.gfx.Image;

import java.awt.event.MouseEvent;
import java.util.Scanner;

public class ServerInBrowser {

    private int x, y, port, sizeX = 272, sizeY = 50;
    private String ip = "", serverName = "", players = "";
    private Image image = new Image("/serverInBrowser.png");
    private Image serverIcon = new Image("/defaultServerIcon.jpg");
    private Image selectedLine = new Image("/selectedServerLine.png");
    private boolean online = false;

    ServerInBrowser(int x, int y, String ip, int port){
        this.x = x;
        this.y = y;
        this.port = port;
        this.ip = ip;
    }

    public void update(GameLoop gameLoop){
//        if((gameLoop.getInput().getMouseX() >= 405 && gameLoop.getInput().getMouseX() <= 615)
//                && (gameLoop.getInput().getMouseY() >= 275 && gameLoop.getInput().getMouseY() <= 313)){
//            if(gameLoop.getInput().isButton(MouseEvent.BUTTON1)){
//            }
//        }
    }

    public void render(Renderer renderer, boolean isSelected){
        renderer.drawImage(image, x,y);
        renderer.drawImage(serverIcon, x+5, y+5);
        if(isSelected){
            renderer.drawImage(selectedLine, x-2, y+50);
        }

        renderer.drawText("STATUS:", x+60, y+20, 0xfffaf0be);
        renderer.drawText("PLAYERS:", x+60, y+30, 0xfffaf0be);

        if(online){
            renderer.drawText("ONLINE", x+95, y+20, 0xff47cb35);
            renderer.drawText(players + "/100", x+95, y+30, 0xff47cb35);
            renderer.drawText(serverName.toUpperCase(), x+60, y+10, 0xff56a0d3);
            renderer.drawText(ip + ":" + port, x+170, y+10, 0xffc95a49);
        }
        else{
            renderer.drawText(ip + ":" + port, x+60, y+10, 0xffc95a49);
            renderer.drawText("OFFLINE", x+95, y+20, 0xfff2003c);
            renderer.drawText("0/0", x+95, y+30, 0xfff2003c);
        }
    }

    public void updateInfo(ClientEchoNew clientEchoNew){
        online = clientEchoNew.isServerAlive(ip, port);
        if(online){
            Scanner scanner = new Scanner(clientEchoNew.getServeInfo(ip,port));
            scanner.useDelimiter("::");
            while (scanner.hasNext()){
                players = scanner.next();
                serverName = scanner.next();
            }
        }
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

    public void setY(int y) {
        this.y = y;
    }
}
