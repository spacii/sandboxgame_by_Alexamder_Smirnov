package company.engine;

import company.Game.GameManager;

public class GameLoop implements Runnable{

    private Thread thread;

    private boolean running = false;
    private final double MAX_FPS = 1.0/60.0;

    private int width = 640, height = 360; //16:9
    private float scale = 2f;
    private String title = "game";

    private Window window;
    private Renderer renderer;
    private Input input;
    //private AbstractGame abstractGame;
    private GameManager abstractGame;

    public GameLoop(GameManager abstractGame){
        this.abstractGame = abstractGame;
    }

    public void start(){
        window = new Window(this);
        renderer = new Renderer(this);
        input = new Input(this);

        thread = new Thread(this);
        thread.run();

        window.getjFrame().requestFocus();
    }

    public void stop(){

    }

    public void run(){
        running = true;

        boolean render = false;
        double firstTime = 0;
        double lastTime = System.nanoTime() / 1000000000.0;
        double passedTime = 0;
        double unprocessedTime = 0;

        double frameTime = 0;
        int frames = 0;
        int fps = 0;

        while(running){
            render = false;

            firstTime = System.nanoTime() / 1000000000.0;
            passedTime = firstTime - lastTime;
            lastTime = firstTime;

            unprocessedTime += passedTime;
            frameTime += passedTime;

            while(unprocessedTime >= MAX_FPS){
                unprocessedTime -= MAX_FPS;
                render = true;

                input.update();
                abstractGame.update(this, (float)MAX_FPS);

                if(frameTime >= 1.0){
                    frameTime = 0;
                    fps = frames;
                    frames = 0;
                    System.out.println("FPS: " + fps);

                    if(abstractGame.getGameStatus() == 1){
                        System.out.println(input.getMouseX() + " " + input.getMouseY());
                    }
                }
            }

            if(render){
                renderer.clear();
                abstractGame.render(this, renderer);
                window.update();
                frames++;
            }
            else{
                try{
                    Thread.sleep(1);
                } catch (InterruptedException e){
                    e.printStackTrace();
                }
            }
        }

        dispose();
    }

    public void dispose(){

    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public float getScale() {
        return scale;
    }

    public void setScale(float scale) {
        this.scale = scale;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Window getWindow() {
        return window;
    }

    public Input getInput() {
        return input;
    }

    public GameManager getAbstractGame(){
        return abstractGame;
    }

    public Renderer getRenderer() {
        return renderer;
    }
}
