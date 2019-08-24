package company.Game;

import company.engine.GameLoop;
import company.engine.Renderer;
import company.engine.gfx.Image;

import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

public class ServerAdding {
    private Image image = new Image("/ServerAdding.jpg");
    private GameManager gameManager;
    private String ip = "", port = "";
    private int sleepTime = 150;
    private boolean ipSelected = false, portSelected = false;

    ServerAdding(GameManager gameManager){
        this.gameManager = gameManager;
    }

    public void update(GameLoop gameLoop){

        if(ipSelected){
            try{
                if(gameLoop.getInput().isKey(KeyEvent.VK_1)){ ip += 1; Thread.sleep(sleepTime);}
                if(gameLoop.getInput().isKey(KeyEvent.VK_2)){ ip += 2; Thread.sleep(sleepTime);}
                if(gameLoop.getInput().isKey(KeyEvent.VK_3)){ ip += 3; Thread.sleep(sleepTime);}
                if(gameLoop.getInput().isKey(KeyEvent.VK_4)){ ip += 4; Thread.sleep(sleepTime);}
                if(gameLoop.getInput().isKey(KeyEvent.VK_5)){ ip += 5; Thread.sleep(sleepTime);}
                if(gameLoop.getInput().isKey(KeyEvent.VK_6)){ ip += 6; Thread.sleep(sleepTime);}
                if(gameLoop.getInput().isKey(KeyEvent.VK_7)){ ip += 7; Thread.sleep(sleepTime);}
                if(gameLoop.getInput().isKey(KeyEvent.VK_8)){ ip += 8; Thread.sleep(sleepTime);}
                if(gameLoop.getInput().isKey(KeyEvent.VK_9)){ ip += 9; Thread.sleep(sleepTime);}
                if(gameLoop.getInput().isKey(KeyEvent.VK_0)){ ip += 0; Thread.sleep(sleepTime);}
                if(gameLoop.getInput().isKey(KeyEvent.VK_PERIOD)){ ip += "."; Thread.sleep(sleepTime);}
                if(gameLoop.getInput().isKey(KeyEvent.VK_BACK_SPACE)){ ip = ip.substring(0, ip.length()-1); Thread.sleep(sleepTime);}
            } catch (Exception e){}
        }

        if(portSelected){
            try{
                if(gameLoop.getInput().isKey(KeyEvent.VK_1)){ port += 1; Thread.sleep(sleepTime);}
                if(gameLoop.getInput().isKey(KeyEvent.VK_2)){ port += 2; Thread.sleep(sleepTime);}
                if(gameLoop.getInput().isKey(KeyEvent.VK_3)){ port += 3; Thread.sleep(sleepTime);}
                if(gameLoop.getInput().isKey(KeyEvent.VK_4)){ port += 4; Thread.sleep(sleepTime);}
                if(gameLoop.getInput().isKey(KeyEvent.VK_5)){ port += 5; Thread.sleep(sleepTime);}
                if(gameLoop.getInput().isKey(KeyEvent.VK_6)){ port += 6; Thread.sleep(sleepTime);}
                if(gameLoop.getInput().isKey(KeyEvent.VK_7)){ port += 7; Thread.sleep(sleepTime);}
                if(gameLoop.getInput().isKey(KeyEvent.VK_8)){ port += 8; Thread.sleep(sleepTime);}
                if(gameLoop.getInput().isKey(KeyEvent.VK_9)){ port += 9; Thread.sleep(sleepTime);}
                if(gameLoop.getInput().isKey(KeyEvent.VK_0)){ port += 0; Thread.sleep(sleepTime);}
                if(gameLoop.getInput().isKey(KeyEvent.VK_BACK_SPACE)){ port = port.substring(0, port.length()-1); Thread.sleep(sleepTime);}
            } catch (Exception e){}
        }



        // Активация ввода IP
        if(gameLoop.getInput().isButton(MouseEvent.BUTTON1)){
            if((gameLoop.getInput().getMouseX() >= 167 && gameLoop.getInput().getMouseX() <= 473)
                    && (gameLoop.getInput().getMouseY() >= 128 && gameLoop.getInput().getMouseY() <= 148)){
                ipSelected = true;
            }
            else{
                if(ipSelected){
                    ipSelected = false;
                }
            }
        }

        // Активация ввода PORT
        if(gameLoop.getInput().isButton(MouseEvent.BUTTON1)){
            if((gameLoop.getInput().getMouseX() >= 167 && gameLoop.getInput().getMouseX() <= 473)
                    && (gameLoop.getInput().getMouseY() >= 153 && gameLoop.getInput().getMouseY() <= 172)){
                portSelected = true;
            }
            else{
                if(portSelected){
                    portSelected = false;
                }
            }
        }

        // Нажатие на "Добавить сервер"
        if((gameLoop.getInput().getMouseX() >= 108 && gameLoop.getInput().getMouseX() <= 319)
                && (gameLoop.getInput().getMouseY() >= 275 && gameLoop.getInput().getMouseY() <= 313)){
            if(gameLoop.getInput().isButton(MouseEvent.BUTTON1)){
                addServer(ip, Integer.parseInt(port));
            }
        }

        // Нажатие на "Назад"
        if((gameLoop.getInput().getMouseX() >= 330 && gameLoop.getInput().getMouseX() <= 540)
                && (gameLoop.getInput().getMouseY() >= 275 && gameLoop.getInput().getMouseY() <= 313)){
            if(gameLoop.getInput().isButton(MouseEvent.BUTTON1)){
                back();
            }
        }
    }

    public void render(Renderer renderer){
        renderer.drawImage(image, 0,0);
        renderer.drawText(ip,205, 135, 0xff00ffff);
        renderer.drawText(port,225, 161, 0xff00ffff);
    }

    private void addServer(String ip, int port){
        //gameManager.getServerBrowser().getServers().add(new ServerInBrowser(66,50));
        //gameManager.getServerBrowser().addServerInList("25.41.250.41", 8189);
        gameManager.getServerBrowser().addServerInList(ip, port);
        back();
    }

    private void back(){
        ipSelected = false;
        portSelected = false;

        ip = "";
        port = "";

        gameManager.setGameStatus(4);
    }
}
