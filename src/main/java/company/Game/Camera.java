package company.Game;

import company.engine.GameLoop;
import company.engine.Renderer;

public class Camera {

    private float offX, offY;

    private String targetTag;
    private GameObject target = null;

    public Camera(){

    }

    public void update(GameLoop gameLoop, GameManager gameManager, float dt){
        if(target == null){
            target = gameManager.getPlayer();
        }

        if(target == null){
            return;
        }

        float targetX = (target.getPosX() + target.getWidth() / 2) - gameLoop.getWidth() / 2;
        float targetY = (target.getPosY()+ target.getHeight() / 2) - gameLoop.getHeight() / 2;

        offX -= dt * (offX - targetX) * 10;
        offY -= dt * (offY - targetY) * 10;
    }

    public void render(Renderer renderer){
        renderer.setCamX((int)offX);
        renderer.setCamY((int)offY);
    }

    public float getOffX() {
        return offX;
    }

    public void setOffX(float offX) {
        this.offX = offX;
    }

    public float getOffY() {
        return offY;
    }

    public void setOffY(float offY) {
        this.offY = offY;
    }

    public String getTargetTag() {
        return targetTag;
    }

    public void setTargetTag(String targetTag) {
        this.targetTag = targetTag;
    }

    public GameObject getTarget() {
        return target;
    }

    public void setTarget(GameObject target) {
        this.target = target;
    }
}
