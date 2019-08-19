package company.engine;

public abstract class AbstractGame {
    public abstract void update(GameLoop gameLoop, float dt);
    public abstract void render(GameLoop gameLoop, Renderer renderer);
}
