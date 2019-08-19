package company.engine;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;

public class Window {
    private JFrame jFrame;
    private BufferedImage bufferedImage;
    private BufferStrategy bs;
    private Dimension dimension;
    private Canvas canvas;
    private Graphics g;

    public Window(GameLoop gameLoop) {
        bufferedImage = new BufferedImage(gameLoop.getWidth(), gameLoop.getHeight(), BufferedImage.TYPE_INT_RGB);
        dimension = new Dimension((int)(gameLoop.getWidth() * gameLoop.getScale()), (int)(gameLoop.getHeight()*gameLoop.getScale()));
        canvas = new Canvas();
        canvas.setPreferredSize(dimension);
        canvas.setMinimumSize(dimension);
        canvas.setMaximumSize(dimension);

        jFrame = new JFrame(gameLoop.getTitle());
        jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jFrame.setLayout(new BorderLayout());
        jFrame.add(canvas, BorderLayout.CENTER);
        jFrame.pack(); // Ставит фрейму размер канваса.
        jFrame.setLocationRelativeTo(null);
        jFrame.setResizable(false);
        jFrame.setVisible(true);

        canvas.createBufferStrategy(2);
        bs = canvas.getBufferStrategy();
        g = bs.getDrawGraphics();
    }

    public void update(){
        g.drawImage(bufferedImage,0,0, canvas.getWidth(), canvas.getHeight(), null);
        bs.show();
    }

    public BufferedImage getBufferedImage() {
        return bufferedImage;
    }

    public Canvas getCanvas() {
        return canvas;
    }

    public JFrame getjFrame() {
        return jFrame;
    }
}
