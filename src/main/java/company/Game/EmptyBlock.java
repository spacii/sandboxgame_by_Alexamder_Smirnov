package company.Game;

import company.engine.GameLoop;
import company.engine.Renderer;
import company.engine.gfx.Image;

public class EmptyBlock extends Block {

    static Image image = new Image("/EmptyBlock16x16.jpg");

    public EmptyBlock(){

    }

    public EmptyBlock(int posX, int posY, int width, int height) {
        super(posX, posY, width, height, image, 0);
    }

    @Override
    public void update(GameLoop gameLoop, GameManager gameManager, float dt) {

    }

    @Override
    public void render(GameLoop gameLoop, Renderer renderer) {
        renderer.drawImage(getTexture(),(int)posX,(int)posY);
    }
}
