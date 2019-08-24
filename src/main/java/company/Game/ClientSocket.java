package company.Game;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.ArrayList;

import java.util.Scanner;

public class ClientSocket implements Runnable {

    private Thread thread;
    private Socket socket;
    private Scanner scanner;
    private static PrintWriter printWriter;
    private static int ClientID;
    private GameManager gameManager;
    ClientSocket(GameManager gameManager){
        thread = new Thread(this);
        this.gameManager = gameManager;
    }

    public Thread getThread() {
        return thread;
    }

    public PrintWriter getPrintWriter() {
        return printWriter;
    }

    public int getClientID() {
        return ClientID;
    }

    public void setClientID(int clientID) {
        ClientID = clientID;
    }



    //@Override
    public void run() {
        try{
            //socket = new Socket();
            //socket.connect(new InetSocketAddress("25.41.250.41",8189), 2000);
            socket = new Socket();
            socket.connect(new InetSocketAddress(InetAddress.getLocalHost(), 8189), 2000);

            scanner = new Scanner(socket.getInputStream());
            scanner.useDelimiter("::");
            printWriter = new PrintWriter(socket.getOutputStream(), true);

            while(scanner.hasNextLine()){
                while(scanner.hasNext()){
                    String command = scanner.next();
                    switch(command){
                        case "generatedID" :
                            int id = scanner.nextInt();
                            setClientID(id);
                            break;
                        case "otherPlayerCoords" :
                            int idOther = Integer.parseInt(scanner.next());
                            int x = Integer.parseInt(scanner.next());
                            int y = Integer.parseInt(scanner.next());
                            gameManager.updateThisId(idOther, x, y);
                            break;
                        case "newConnected" :
                            int newId = scanner.nextInt();
                            gameManager.spawnNew(newId);
                            break;
                        case "oldFag" :
                            int oldFagId = scanner.nextInt();
                            System.out.println(oldFagId);
                            int XXX = scanner.nextInt();
                            System.out.println(XXX);
                            int YYY = scanner.nextInt();
                            System.out.println(YYY);
                            gameManager.spawnOldFag(oldFagId, XXX, YYY);
                            break;
                        case "blockFromServerWorld" :
                            Gson gson = new Gson();
                            String ids = scanner.next();
                            String xx = scanner.next();
                            String yy = scanner.next();

                            ArrayList<Integer> IDS = new ArrayList<>();
                            ArrayList<Float> X = new ArrayList<>();
                            ArrayList<Float> Y = new ArrayList<>();

                            IDS = gson.fromJson(ids, new TypeToken<ArrayList<Integer>>(){}.getType());
                            X = gson.fromJson(xx, new TypeToken<ArrayList<Float>>(){}.getType());
                            Y = gson.fromJson(yy, new TypeToken<ArrayList<Float>>(){}.getType());


                            gameManager.gameWorld.loadWorldFromServer(IDS, X, Y);

                           // String blocks = scanner.next();
                           // String collision = scanner.next();

                           // ArrayList<Block> temp1;
                           // temp1 = gson.fromJson(blocks, new TypeToken<ArrayList<Block>>(){}.getType());

                           // ArrayList<Boolean> temp2;
                           // temp2 = gson.fromJson(collision, new TypeToken<ArrayList<Boolean>>(){}.getType());

                            //for(int i = 0; i < temp1.size(); i++){
                             //   gameManager.gameWorld.getWorldsBlocks().add(temp1.get(i));
                            //    gameManager.gameWorld.getWorldCollision().add(temp2.get(i));
                            //}

                            //gameManager.gameWorld = gson.fromJson(json, GameWorld.class);
                           //System.out.println("FUCkkk");
/*
                            try {
                                ObjectInputStream objectInputStream = new ObjectInputStream(socket.getInputStream());
                                try {
                                    Object object = objectInputStream.readObject();
                                    gameManager.gameWorld = (Ga meWorld) object;
                                } catch (ClassNotFoundException e) {
                                    e.printStackTrace();
                                }
                            } catch (IOException e) {
                                e.printStackTrace();
                            }


                            try {
                                ObjectInputStream objectInputStream = new ObjectInputStream(socket.getInputStream());
                                try {
                                    Object object = objectInputStream.readObject();
                                    ArrayList<Block> tempblocks = (ArrayList<Block>) object;
                                    for(int i = 0; i < tempblocks.size(); i++){
                                        gameManager.gameWorld.getWorldsBlocks().add(tempblocks.get(i));
                                    }
                                } catch (ClassNotFoundException e) {
                                    e.printStackTrace();
                                }
                            } catch (IOException e) {
                                e.printStackTrace();
                            }

                            try {
                                ObjectInputStream objectInputStream = new ObjectInputStream(socket.getInputStream());
                                try {
                                    Object object = objectInputStream.readObject();
                                    ArrayList<Boolean> tempcollision = (ArrayList<Boolean>) object;
                                    for(int i = 0; i < tempcollision.size(); i++){
                                        gameManager.gameWorld.getWorldCollision().add(tempcollision.get(i));
                                    }
                                } catch (ClassNotFoundException e) {
                                    e.printStackTrace();
                                }
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
*/
                            break;

                        case "BlockDestroyed" :
                            int id_des = scanner.nextInt();
                            gameManager.gameWorld.destroyBlock(id_des);
                            break;
                        case "BlockPlaced" :
                            int id_placed = scanner.nextInt();
                            gameManager.gameWorld.buildBlock(id_placed);
                            break;
                        case "DisconnectedPlayer" :
                            int disconnectedPlayerId = scanner.nextInt();
                            gameManager.deletePlayer(disconnectedPlayerId);
                            break;
                        default:
                            break;
                    }
                    scanner.nextLine();
                    break;
                }
            }
        } catch (Exception e){
            e.printStackTrace();
        }
    }
}
