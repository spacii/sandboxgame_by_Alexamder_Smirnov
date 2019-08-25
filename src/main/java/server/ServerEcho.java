package server;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.util.ArrayList;
import java.util.Scanner;

public class ServerEcho implements Runnable {

    private Thread thread;
    private DatagramSocket datagramSocket;
    private DatagramPacket datagramPacket;
    private ByteArrayInputStream byteArrayInputStream;
    private ByteArrayOutputStream byteArrayOutputStream;
    private ObjectInputStream objectInputStream;
    private ObjectOutputStream objectOutputStream;
    private Scanner scanner;
    private ArrayList<ServerConnection> connections;
    private String serverName;
    private int serverPort;

    private String usersIp;
    private int usersPort;

    public ServerEcho(ArrayList<ServerConnection> connections, String serverName, int serverPort) {
        try{
            this.connections = connections;
            this.serverName = serverName;
            this.serverPort = serverPort;

            //usersIp = "25.41.250.41";
            usersPort = 8188;

            thread = new Thread(this);
            datagramSocket = new DatagramSocket(serverPort);
            byteArrayOutputStream = new ByteArrayOutputStream();
            objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
            thread.start();
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    public void sendServerStatus(String usersIp,int usersPort){
        try{
            String object = "isAlive::";

            byteArrayOutputStream = new ByteArrayOutputStream();
            objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
            objectOutputStream.writeObject(object);
            objectOutputStream.flush();

            byte[] Buf = byteArrayOutputStream.toByteArray();
            datagramPacket = new DatagramPacket(Buf, Buf.length, new InetSocketAddress(usersIp, usersPort)); //8188

            System.out.println("Отвечаю сюда: " + usersIp + ":" + usersPort);

            datagramSocket.send(datagramPacket);
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    public void sendServeInfo(String usersIp, int usersPort){
        try{
            String object = "serverInfo::" + connections.size() + "::" + serverName + "::";

            byteArrayOutputStream = new ByteArrayOutputStream();
            objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
            objectOutputStream.writeObject(object);
            objectOutputStream.flush();

            byte[] Buf = byteArrayOutputStream.toByteArray();
            datagramPacket = new DatagramPacket(Buf, Buf.length, new InetSocketAddress(usersIp, usersPort)); //8188
            System.out.println("Отвечаю сюда: " + usersIp + ":" + usersPort);
            datagramSocket.send(datagramPacket);
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        while(true){
            try{
                byte[] buffer = new byte[256];
                datagramPacket = new DatagramPacket(buffer, buffer.length );
                datagramSocket.receive(datagramPacket);

                byteArrayInputStream = new ByteArrayInputStream(buffer);
                objectInputStream = new ObjectInputStream(byteArrayInputStream);

                String temp = (String) objectInputStream.readObject();

                scanner = new Scanner(temp);
                scanner.useDelimiter("::");

                while (scanner.hasNext()){
                    String command = scanner.next();
                    switch (command) {
                        case "giveServeInfo" :
                            String ip1 = scanner.next();
                            int port1 = scanner.nextInt();
                            sendServeInfo(ip1, port1);
                            break;
                        case "aliveCheck" :
                            String ip2 = scanner.next();
                            int port2 = scanner.nextInt();
                            sendServerStatus(ip2, port2);
                            break;
                        default:
                            break;
                    }
                }

            } catch (Exception e){
                e.printStackTrace();
            }
        }
    }
}
