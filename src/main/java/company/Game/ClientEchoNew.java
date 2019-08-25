package company.Game;

import java.io.*;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetSocketAddress;
import java.net.SocketException;
import java.util.Scanner;

public class ClientEchoNew{

    private DatagramSocket datagramSocket;
    private DatagramPacket datagramPacket;
    private ByteArrayInputStream byteArrayInputStream;
    private ByteArrayOutputStream byteArrayOutputStream;
    private ObjectInputStream objectInputStream;
    private ObjectOutputStream objectOutputStream;
    private Scanner scanner;

    private boolean aliveRuslt = false;

    public ClientEchoNew(){
        try {
            datagramSocket = new DatagramSocket(8188);
            datagramSocket.setSoTimeout(50);
            byteArrayOutputStream = new ByteArrayOutputStream();
            objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String getServeInfo(String ip, int port){
            try{
                String ask = "giveServeInfo::";

                byteArrayOutputStream = new ByteArrayOutputStream();
                objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
                objectOutputStream.writeObject(ask);
                objectOutputStream.flush();

                byte[] Buf = byteArrayOutputStream.toByteArray();
                datagramPacket = new DatagramPacket(Buf, Buf.length, new InetSocketAddress(ip,port));
                datagramSocket.send(datagramPacket);

            } catch (Exception e){
                e.printStackTrace();
            }

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
                    case "serverInfo" :
                        return scanner.nextLine();
                    default:
                        return "0";
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "0";
    }

    public boolean isServerAlive(String ip, int port){
        try{
            String ask = "aliveCheck::";

            byteArrayOutputStream = new ByteArrayOutputStream();
            objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
            objectOutputStream.writeObject(ask);
            objectOutputStream.flush();

            byte[] Buf = byteArrayOutputStream.toByteArray();
            datagramPacket = new DatagramPacket(Buf, Buf.length, new InetSocketAddress(ip,port));
            datagramSocket.send(datagramPacket);
        } catch (Exception e) {
            e.printStackTrace();
        }

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
                    case "isAlive" :
                        return true;
                    default:
                        return false;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
}
