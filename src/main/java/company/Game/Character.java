package company.Game;

import company.engine.gfx.ImageTile;

public abstract class Character extends GameObject {

    private int health;
    private ImageTile imageTile;

    public Character(int posX, int posY, int width, int height, int health, ImageTile imageTile){
        super(posX, posY, width, height);
        this.health = health;
        this.imageTile = imageTile;
    }

    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public ImageTile getImageTile() {
        return imageTile;
    }
}
