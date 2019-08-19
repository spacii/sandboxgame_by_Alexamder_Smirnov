package company.engine;

import java.awt.event.*;

public class Input implements KeyListener, MouseListener, MouseWheelListener, MouseMotionListener {

    private GameLoop gameLoop;

    private final int NUM_KEYS = 256;
    private boolean[] keys = new boolean[NUM_KEYS];
    private boolean[] keysLast = new boolean[NUM_KEYS]; // on last frame

    private final int NUM_BUTTONS = 5;
    private boolean[] buttons = new boolean[NUM_BUTTONS];
    private boolean[] buttonsLast = new boolean[NUM_BUTTONS]; // on last frame

    private int mouseX, mouseY;
    private int scroll;

    public Input(GameLoop gameLoop){
        this.gameLoop = gameLoop;
        mouseX = 0; mouseY = 0; scroll = 0;
        gameLoop.getWindow().getCanvas().addKeyListener(this);
        gameLoop.getWindow().getCanvas().addMouseListener(this);
        gameLoop.getWindow().getCanvas().addMouseWheelListener(this);
        gameLoop.getWindow().getCanvas().addMouseMotionListener(this);

    }

    public void update(){
        for(int i = 0; i < NUM_KEYS; i++){
            keysLast[i] = keys[i];
        }

        for(int i = 0; i < NUM_BUTTONS; i++){
            buttonsLast[i] = buttons[i];
        }
    }

    public boolean isKey(int keyCode){
        return keys[keyCode];
    }

    public boolean isKeyUp(int keyCode){
        return !keys[keyCode] && keysLast[keyCode];
    }

    public boolean isKeyDown(int keyCode){
        return keys[keyCode] && !keysLast[keyCode];
    }

    public boolean isButton(int button){
        return buttons[button];
    }

    public boolean isButtonUp(int button){
        return !buttons[button] && buttonsLast[button];
    }

    public boolean isButtonDown(int button){
        return buttons[button] && !buttonsLast[button];
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        keys[e.getKeyCode()] = true;
    }

    @Override
    public void keyReleased(KeyEvent e) {
        keys[e.getKeyCode()] = false;
    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {
        buttons[e.getButton()] = true;
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        buttons[e.getButton()] = false;
    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    @Override
    public void mouseWheelMoved(MouseWheelEvent e) {
        scroll = e.getWheelRotation();
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        mouseX = (int)(e.getX()/gameLoop.getScale());
        mouseY = (int)(e.getY()/gameLoop.getScale());
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        mouseX = (int)(e.getX()/gameLoop.getScale());
        mouseY = (int)(e.getY()/gameLoop.getScale());
    }

    public int getMouseX() {
        return mouseX;
    }

    public int getMouseY() {
        return mouseY;
    }

    public int getScroll() {
        return scroll;
    }
}
