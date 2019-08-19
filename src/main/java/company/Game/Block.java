package company.Game;

import company.engine.GameLoop;
import company.engine.Renderer;
import company.engine.gfx.Image;

public class Block extends GameObject {

    private Image texture;
    private int ID;

    public Block(){

    }

    @Override
    public void update(GameLoop gameLoop, GameManager gameManager, float dt) {

    }

    @Override
    public void render(GameLoop gameLoop, Renderer renderer) {

    }

    public Block(int posX, int posY, int width, int height, Image texture, int ID) {
        super(posX, posY, width, height);
        this.texture = texture;
        this.ID = ID;
    }

    public Image getTexture() {
        return texture;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }
}
