package server;

import com.google.gson.Gson;
import company.Game.GameWorld;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Main2 {

    static private ArrayList<ServerConnection> connectionsList = new ArrayList<>();
    static private GameWorld gameWorldServer;


    static void spawnNewInAll(int id){
        for(int i = 0; i < connectionsList.size(); i++){
            if(connectionsList.get(i).getConnectionId() != id){
                connectionsList.get(i).getPrintWriter().println("newConnected::" + id + "::");
            }
        }
    }

    static void spawnOldInNew(int id){
        for(int i = 0; i < connectionsList.size(); i++){
            if(connectionsList.get(i).getConnectionId() != id){
                connectionsList.get(connectionsList.size()-1).getPrintWriter().println("oldFag::" + connectionsList.get(i).getConnectionId() + "::"
                + connectionsList.get(i).getPosX() + "::" + connectionsList.get(i).getPosY() + "::");
            }
        }
    }

    public static void main(String[] args) {
        try {
            ServerSocket serverSocket = new ServerSocket(8189);
            System.out.println("Server started");
            gameWorldServer = new GameWorld();
            gameWorldServer.generateWorld();

            while (true){
                Socket socket = serverSocket.accept();
                ServerConnection serverConnection = new ServerConnection(socket, connectionsList, gameWorldServer);
                connectionsList.add(serverConnection);
                connectionsList.get((connectionsList.size()-1)).getThread().start();

                //spawnNewInAll(connectionsList.get((connectionsList.size()-1)).getConnectionId());
                //spawnOldInNew(connectionsList.get((connectionsList.size()-1)).getConnectionId());

                System.out.println("Player connected");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}

class ServerConnection implements Runnable{
    private Thread thread;
    private Socket socket;
    private Scanner scanner;
    private PrintWriter printWriter;
    private int connectionId;
    private int posX, posY;
    private ArrayList<ServerConnection> connectionsList;
    private GameWorld gameWorldServer;

    ServerConnection(Socket socket, ArrayList<ServerConnection> connectionsList, GameWorld gameWorldServer){
        this.connectionsList = connectionsList;
        this.thread = new Thread(this);
        this.socket = socket;
        this.gameWorldServer = gameWorldServer;
        this.connectionId = (int)(Math.random() * 1000 + 0);
        try{
            this.scanner = new Scanner(this.socket.getInputStream());
            this.printWriter = new PrintWriter(this.socket.getOutputStream(), true);
            scanner.useDelimiter("::");
            this.printWriter.println("generatedID::"+connectionId+"::");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    Thread getThread() {
        return thread;
    }

    int getConnectionId() {
        return connectionId;
    }

    PrintWriter getPrintWriter() {
        return printWriter;
    }

    public int getPosX() {
        return posX;
    }

    public int getPosY() {
        return posY;
    }

    public void updateMyPos(int x, int y){
        posX = x;
        posY = y;
    }

    public void giveCoordsToPlayer(){
        for(int i = 0; i < connectionsList.size(); i++){
            if(connectionsList.get(i).getConnectionId() != connectionId){
                printWriter.println("otherPlayerCoords::"+ (int)connectionsList.get(i).getConnectionId() + "::" + (int)connectionsList.get(i).posX + "::" + (int)connectionsList.get(i).posY + "::");
            }
        }
    }

    public void giveWorldToPlayer(){
        Gson gson = new Gson();

        ArrayList<Integer> IDS = new ArrayList<>();
        ArrayList<Float> X = new ArrayList<>();
        ArrayList<Float> Y = new ArrayList<>();


        for(int i = 0; i < gameWorldServer.getWorldsBlocks().size(); i++){
            IDS.add(gameWorldServer.getWorldsBlocks().get(i).getID());
            X.add(gameWorldServer.getWorldsBlocks().get(i).posX);
            Y.add(gameWorldServer.getWorldsBlocks().get(i).posY);
        }

        String json_ids = gson.toJson(IDS);
        String json_X = gson.toJson(X);
        String json_Y = gson.toJson(Y);

        printWriter.println("blockFromServerWorld::" + json_ids + "::" + json_X + "::" + json_Y + "::");


        // Мне самому грустно от такой реализации, но по другому не получилось :с

        //String json = gson.toJson(gameWorldServer);
        //String json_blocks = gson.toJson(gameWorldServer.getWorldsBlocks());
        //String json_collisison = gson.toJson(gameWorldServer.getWorldCollision());

        //printWriter.println("blockFromServerWorld::" + json + "::");

        //printWriter.println("blockFromServerWorld::" + json_blocks + "::" + json_collisison + "::");
        /*
        try {
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
            objectOutputStream.writeObject(gameWorldServer);
        }
        catch (IOException e) {
            e.printStackTrace();
        }


        try {
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
            objectOutputStream.writeObject(gameWorldServer.getWorldsBlocks());
        }
        catch (IOException e) {
            e.printStackTrace();
        }

        try {
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
            objectOutputStream.writeObject(gameWorldServer.getWorldCollision());
        }
        catch (IOException e) {
            e.printStackTrace();
        }
*/
    }

    void spawnNewInAll(int id){
        for(int i = 0; i < connectionsList.size(); i++){
            if(connectionsList.get(i).getConnectionId() != id){
                connectionsList.get(i).getPrintWriter().println("newConnected::" + id + "::");
            }
        }
    }

    void spawnOldInNew(int id){
        for(int i = 0; i < connectionsList.size(); i++){
            if(connectionsList.get(i).getConnectionId() != id){
                connectionsList.get(connectionsList.size()-1).getPrintWriter().println("oldFag::" + connectionsList.get(i).getConnectionId() + "::"
                        + connectionsList.get(i).getPosX() + "::" + connectionsList.get(i).getPosY() + "::");
            }
        }
    }

    void destroyBlock(int id){
        for(int i = 0; i < connectionsList.size(); i++){
            if(connectionsList.get(i).getConnectionId() != connectionId){
                connectionsList.get(i).getPrintWriter().println("BlockDestroyed::" + id + "::");
            }
        }
    }

    void placeBlock(int id){
        for(int i = 0; i < connectionsList.size(); i++){
            if(connectionsList.get(i).getConnectionId() != connectionId){
                connectionsList.get(i).getPrintWriter().println("BlockPlaced::" + id + "::");
            }
        }
    }

    void deletePlayerFromOther(int disconnectedPlayerId){
        for(int i = 0; i < connectionsList.size(); i++){
            if(connectionsList.get(i).getConnectionId() != connectionId){
                connectionsList.get(i).getPrintWriter().println("DisconnectedPlayer::" + disconnectedPlayerId + "::");
            }
        }

        for(int i = 0; i < connectionsList.size(); i++){
            if(connectionsList.get(i).getConnectionId() == connectionId){
                connectionsList.remove(i);
                this.thread.stop();
            }
        }
    }

    @Override
    public void run() {
        while(scanner.hasNextLine()){
            while (scanner.hasNext()) {
                String command = scanner.next();
                switch (command) {
                    case "coords" :
                        int x = scanner.nextInt();
                        int y = scanner.nextInt();
                        updateMyPos(x, y);
                        break;
                    case "giveMeCoords" :
                        giveCoordsToPlayer();
                        break;
                    case "giveMeWorld" :
                        giveWorldToPlayer();
                        break;
                    case "giveMePlayersAndSendMeToPlayers" :
                        spawnNewInAll(connectionsList.get((connectionsList.size()-1)).getConnectionId());
                        spawnOldInNew(connectionsList.get((connectionsList.size()-1)).getConnectionId());
                        break;
                    case "BlockDestroyed" :
                        int id_des = scanner.nextInt();
                        destroyBlock(id_des);
                        gameWorldServer.destroyBlock(id_des);
                        break;
                    case "BlockPlaced" :
                        int id_placed = scanner.nextInt();
                        placeBlock(id_placed);
                        gameWorldServer.buildBlock(id_placed);
                        break;
                    case "Disconnected" :
                        deletePlayerFromOther(connectionId);
                        break;
                    default:
                        break;
                }
                scanner.nextLine();
                break;
            }
        }
    }
}
