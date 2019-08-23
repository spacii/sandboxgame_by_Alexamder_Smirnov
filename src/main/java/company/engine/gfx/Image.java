package company.engine.gfx;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.Serializable;

public class Image implements Serializable {
    private int w, h;
    private int[] p;

    public Image(){

    }

    public Image(String path){
        BufferedImage bufferedImage = null;
        try {
            //bufferedImage = ImageIO.read(new FileInputStream(path));
            bufferedImage = ImageIO.read(Image.class.getResource(path));
        } catch (IOException e) {
            e.printStackTrace();
        }
        w = bufferedImage.getWidth();
        h = bufferedImage.getHeight();
        p = bufferedImage.getRGB(0,0, w, h,null,0, w);

        bufferedImage.flush();
    }

    public int getW() {
        return w;
    }

    public void setW(int w) {
        this.w = w;
    }

    public int getH() {
        return h;
    }

    public void setH(int h) {
        this.h = h;
    }

    public int[] getP() {
        return p;
    }

    public void setP(int[] p) {
        this.p = p;
    }
}
