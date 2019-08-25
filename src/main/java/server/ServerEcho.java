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

    public ServerEcho(ArrayList<ServerConnection> connections, String serverName) {
        try{
            this.connections = connections;
            this.serverName = serverName;
            thread = new Thread(this);
            datagramSocket = new DatagramSocket(8189);
            byteArrayOutputStream = new ByteArrayOutputStream();
            objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
            thread.start();
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    public void sendServerStatus(){
        try{
            String object = "isAlive::";

            byteArrayOutputStream = new ByteArrayOutputStream();
            objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
            objectOutputStream.writeObject(object);
            objectOutputStream.flush();

            byte[] Buf = byteArrayOutputStream.toByteArray();
            datagramPacket = new DatagramPacket(Buf, Buf.length, new InetSocketAddress("25.41.250.41",8188));
            datagramSocket.send(datagramPacket); // Отправили объект.
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    public void sendServeInfo(){
        try{
            String object = "serverInfo::" + connections.size() + "::" + serverName + "::";

            byteArrayOutputStream = new ByteArrayOutputStream();
            objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
            objectOutputStream.writeObject(object);
            objectOutputStream.flush();

            byte[] Buf = byteArrayOutputStream.toByteArray();
            int length = Buf.length;
            byte[] data = new byte[4];

            for (int i = 0; i < 4; ++i) {
                int shift = i << 3; // i * 8
                data[3-i] = (byte)((length & (0xff << shift)) >>> shift);
            }

           // datagramPacket = new DatagramPacket(data, 4, InetAddress.getByName("localhost"), 8189);
           // datagramPacket = new DatagramPacket(data, 4, new InetSocketAddress("25.41.250.41",8189));
           // datagramSocket.send(datagramPacket); // Отправили размер.

            //datagramPacket = new DatagramPacket(Buf, Buf.length, InetAddress.getByName("localhost"), 8188);
            datagramPacket = new DatagramPacket(Buf, Buf.length, new InetSocketAddress("25.41.250.41",8188));
            datagramSocket.send(datagramPacket); // Отправили объект.
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        while(true){
            try{
                /*
                byte[] data = new byte[4];
                datagramPacket = new DatagramPacket(data, data.length);
                datagramSocket.receive(datagramPacket);

                int len = 0;
                for (int i = 0; i < 4; ++i) {
                    len |= (data[3-i] & 0xff) << (i << 3);
                }
*/
                //byte[] buffer = new byte[len];
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
                            sendServeInfo();
                            break;
                        case "aliveCheck" :
                            sendServerStatus();
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
