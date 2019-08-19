package company.Game;

import com.google.gson.Gson;
import company.engine.GameLoop;
import company.engine.Renderer;

import java.util.ArrayList;
import java.util.Map;

public class GameWorld{

    private int BS = 16; // Количество пикселей на один блок
    private int worldW = 30*BS, worldH = 15*BS; // Размер мира в пикселях
    private ArrayList<Block> worldsBlocks; // Блоки
    private ArrayList<Boolean> worldCollision;
    private ClientSocket clientSocket;

    public GameWorld(){
        worldsBlocks = new ArrayList<>();
        worldCollision = new ArrayList<>();
    }

    public GameWorld(ClientSocket clientSocket){
        worldsBlocks = new ArrayList<>();
        worldCollision = new ArrayList<>();
        this.clientSocket = clientSocket;
    }

    public void generateWorld(){
        for(int x = 0; x < worldW; x+=BS){ //0 16 32 48 64
            for(int y = 0; y < worldH/2; y+=BS){
                worldsBlocks.add(new EmptyBlock(x, y, BS,BS));
                worldCollision.add(true);
            }
        }

        for(int x = 0; x < worldW; x+=BS){
            for(int y =  worldH/2; y < worldH; y+=BS){
                worldsBlocks.add(new CobbleStoneBlock(x, y, 16,16));
                worldCollision.add(false);
            }
        }

        System.out.println("World Generated!");
    }

    public void loadWorldFromServer(ArrayList<Integer> ids, ArrayList<Float> x, ArrayList<Float> y){
        for(int i = 0; i < ids.size(); i++){
            switch (ids.get(i)){
                case 0 :
                    worldsBlocks.add(new EmptyBlock(x.get(i).intValue(), y.get(i).intValue(), BS, BS));
                    worldCollision.add(true);
                    break;
                case 1 :
                    worldsBlocks.add(new CobbleStoneBlock(x.get(i).intValue(), y.get(i).intValue(), BS, BS));
                    worldCollision.add(false);
                    break;
                default:
                    break;
            }
        }
    }

    public boolean getCollision(float x, float y){
        int temp_index = 0;
        if(x < 0 || x >= worldW || y < 0 || y >= worldH){
            return false;
        }

        for(int i = 0; i < worldsBlocks.size(); i++){
            if(((worldsBlocks.get(i).getPosX() <= x) && (worldsBlocks.get(i).getPosX() + 15 >= x))
                    && ((worldsBlocks.get(i).getPosY() <= y) && (worldsBlocks.get(i).getPosY() + 15 >= y))){
                temp_index = i;
            }
        }
        return worldCollision.get(temp_index);
    }

    public void buildBlock(int x, int y){

    }

    public void destroyBlock(int id){
        worldsBlocks.set(id, new EmptyBlock((int)worldsBlocks.get(id).posX, (int)worldsBlocks.get(id).posY, BS, BS));
        worldCollision.set(id, true);
    }

    public void destroyBlock(int x, int y){
        for(int i = 0; i < worldsBlocks.size(); i++){
            if((x >= worldsBlocks.get(i).posX && x <= worldsBlocks.get(i).posX+16) &&
                    (y >= worldsBlocks.get(i).posY && y <= worldsBlocks.get(i).posY+16)){
                worldsBlocks.set(i, new EmptyBlock((int)worldsBlocks.get(i).posX, (int)worldsBlocks.get(i).posY, BS, BS));
                worldCollision.set(i, true);
            }
        }
    }

    int getDestroyedId(int x, int y){
        for(int i = 0; i < worldsBlocks.size(); i++){
            if((x >= worldsBlocks.get(i).posX && x <= worldsBlocks.get(i).posX+16) &&
                    (y >= worldsBlocks.get(i).posY && y <= worldsBlocks.get(i).posY+16)){
                return i;
            }
        }
        return 0;
    }

    public void update(GameLoop gameLoop, float dt) {

    }

    float temp = 0;


    public void render(GameLoop gameLoop, Renderer renderer) {
        for (int i = 0; i < worldsBlocks.size(); i++) {
            worldsBlocks.get(i).render(gameLoop, renderer);
        }
    }

    public ArrayList<Block> getWorldsBlocks() {
        return worldsBlocks;
    }

    public void setWorldsBlocks(ArrayList<Block> worldsBlocks) {
        this.worldsBlocks = worldsBlocks;
    }

    public ArrayList<Boolean> getWorldCollision() {
        return worldCollision;
    }
}