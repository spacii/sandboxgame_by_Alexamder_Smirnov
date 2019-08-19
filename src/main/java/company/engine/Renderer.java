package company.engine;

import company.engine.gfx.Image;
import company.engine.gfx.ImageTile;

import java.awt.image.DataBufferInt;

public class Renderer {

    private int pW, pH;
    private int[] p;

    private int camX, camY;

    public Renderer(GameLoop gameLoop){
        pW = gameLoop.getWidth();
        pH = gameLoop.getHeight();
        p = ((DataBufferInt)gameLoop.getWindow().getBufferedImage().getRaster().getDataBuffer()).getData();
    }

    public void clear(){
        for (int i = 0; i < p.length; i++) {
            p[i] = 0;
        }
    }

    public void setPixel(int x, int y, int value){
        //if((x < 0 || x >= pW || y < 0 || y >= pH) || value == 0xffff00ff){

        //x -= camX;
        //y -= camY;

        if((x < 0 || x >= pW || y < 0 || y >= pH) || ((value >> 24) & 0xff) == 0){
            return;
        }
        p[x + y * pW] = value;
    }

    public void drawImage(Image image, int offX, int offY){

        offX -= camX;
        offY -= camY;

        if(offX < -image.getW()){return;}
        if(offY < -image.getH()){return;}
        if(offX >= pW){return;}
        if(offY >= pH){return;}



        int newX = 0;
        int newY = 0;
        int newWidth = image.getW();
        int newHeight = image.getH();

        if(offX < 0){
            newX -= offX;
        }

        if(offY < 0){
            newY -= offY;
        }

        if(newWidth + offX >= pW){
            newWidth -= (newWidth+offX-pW);
        }

        if(newHeight + offY >= pH){
            newHeight -= (newHeight+offY-pH);
        }

        for (int y = newY; y < newHeight; y++) { //y
            for (int x = newX; x < newWidth; x++) {//x
                setPixel(x + offX, y + offY, image.getP()[x + y * image.getW()]);
            }
        }
    }

    public void drawImageTile(ImageTile image, int offX, int offY, int tileX, int tileY){

        offX -= camX;
        offY -= camY;

        if(offX < -image.getTileW()){return;}
        if(offY < -image.getTileH()){return;}
        if(offX >= pW){return;}
        if(offY >= pH){return;}

        int newX = 0;
        int newY = 0;
        int newWidth = image.getTileW();
        int newHeight = image.getTileH();

        if(offX < 0){
            newX -= offX;
        }

        if(offY < 0){
            newY -= offY;
        }

        if(newWidth + offX >= pW){
            newWidth -= (newWidth+offX-pW);
        }

        if(newHeight + offY >= pH){
            newHeight -= (newHeight+offY-pH);
        }

        for (int y = newY; y < newHeight; y++) { //y
            for (int x = newX; x < newWidth; x++) {//x
                setPixel(x + offX, y + offY, image.getP()[(x + tileX * image.getTileW()) + (y + tileY * image.getTileH()) * image.getW()]);
            }
        }
    }

    public int getCamX() {
        return camX;
    }

    public void setCamX(int camX) {
        this.camX = camX;
    }

    public int getCamY() {
        return camY;
    }

    public void setCamY(int camY) {
        this.camY = camY;
    }
}
