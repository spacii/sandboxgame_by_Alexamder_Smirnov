package company.Game;

import company.engine.AbstractGame;
import company.engine.GameLoop;
import company.engine.Renderer;

import java.util.ArrayList;

public class GameManager extends AbstractGame {

    MainMenu mainMenu;
    ServerBrowser serverBrowser;
    ServerAdding serverAdding;
    EscapeMenu escapeMenu;
    Player player;
    GameWorld gameWorld;
    Camera camera;
    ClientSocket clientSocket;
    ArrayList<OtherPlayer> players;

    private int gameStatus;

    public GameManager(){
            mainMenu = new MainMenu(this);
            serverBrowser = new ServerBrowser(this);
            serverAdding = new ServerAdding(this);
            gameStatus = 0; // 0 - MainMenu, 1 - NewGame running, 2 - game pause, 3 - multiplayer, 4 - ServerBrowser, 5 - AddingServer
    }

    public Player getPlayer(){
        return player;
    }

    public void startNewGame(){
        gameWorld = new GameWorld();
        camera = new Camera();
        escapeMenu = new EscapeMenu(this);
        player = new Player(400,50,16,16, 100);
        gameWorld.generateWorld();
        gameStatus = 1;
    }

    public void connectToServer(String ip, int port){
        clientSocket = new ClientSocket(this, ip, port);
        clientSocket.getThread().start();
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("Connected to server");

        gameWorld = new GameWorld();
        camera = new Camera();
        //escapeMenu = new EscapeMenu(this);
        clientSocket.getPrintWriter().println("giveMeWorld::");
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        players = new ArrayList<>();
        clientSocket.getPrintWriter().println("giveMePlayersAndSendMeToPlayers::");
        player = new Player(400,50,16,16, 100);

        gameStatus = 3;
    }

    public void loadWorld(){
        gameWorld = new GameWorld();
        camera = new Camera();
        escapeMenu = new EscapeMenu(this);
        player = new Player(400,50,16,16, 100);
    }

    public void updateThisId(int id, int x, int y){
        for(int i = 0; i < players.size(); i++){
            if(players.get(i).getID() == id){
                players.get(i).setPosX(x);
                players.get(i).setPosY(y);
            }
        }
    }

    public void spawnNew(int id){
        players.add(new OtherPlayer(400,50,16,16, 100, id));
        System.out.println("New Spawned");
    }

    public void spawnOldFag(int id, int XXX, int YYY){
        players.add(new OtherPlayer(XXX,YYY,16,16, 100, id));
    }

    void deletePlayer(int disconnectedPlayerId){
        for(int i = 0; i < players.size(); i++){
            if(players.get(i).getID() == disconnectedPlayerId){
                players.remove(i);
            }
        }
    }

    @Override
    public void update(GameLoop gameLoop, float dt) {
        switch (gameStatus){
            case 0 :
                mainMenu.update(gameLoop);
                break;
            case 1 :
                player.update(gameLoop, this, dt);
                camera.update(gameLoop, this, dt);
                break;
            case 2 :
                escapeMenu.update(gameLoop);
                break;
            case 3 :
                player.update(gameLoop, this, dt);
                camera.update(gameLoop, this, dt);
                player.sendMyCoords(clientSocket);
                player.getCoords(clientSocket);
                for(int i = 0; i < players.size(); i++){
                    players.get(i).update(gameLoop,this, dt);
                }
                break; //
            case 4 :
                serverBrowser.update(gameLoop);
                break;
            case 5 :
                serverAdding.update(gameLoop);
                break;
            default:
                break;
        }
    }

    float temp = 0;

    @Override
    public void render(GameLoop gameLoop, Renderer renderer) {

        switch (gameStatus){
            case 0 :
                mainMenu.render(renderer);
                break;
            case 1 :
                camera.render(renderer);
                gameWorld.render(gameLoop,renderer);
                player.render(gameLoop, renderer);
                break;
            case 2 :
                renderer.setCamX(0);
                renderer.setCamY(0);
                escapeMenu.render(renderer);
                break;
            case 3 :
                camera.render(renderer);
                gameWorld.render(gameLoop,renderer);
                player.render(gameLoop, renderer);
                for(int i = 0; i < players.size(); i++){
                    players.get(i).render(gameLoop, renderer);
                }
                break;
            case 4 :
                serverBrowser.render(renderer);
                break;
            case 5 :
                serverAdding.render(renderer);
                break;
            default:
                break;

        }
    }

    public GameWorld getGameWorld() {
        return gameWorld;
    }

    public Camera getCamera() {
        return camera;
    }

    public int getGameStatus() {
        return gameStatus;
    }

    public void setGameStatus(int gameStatus) {
        this.gameStatus = gameStatus;
    }

    public ServerBrowser getServerBrowser(){
        return serverBrowser;
    }

    public void setGameWorld(GameWorld gameWorld) {
        this.gameWorld = gameWorld;
    }

    public ClientSocket getClientSocket(){
        return clientSocket;
    }

    public static void main(String[] args) {
        GameLoop gameLoop = new GameLoop(new GameManager());
        gameLoop.start();
    }
}
