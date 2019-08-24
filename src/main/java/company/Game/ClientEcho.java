package company.Game;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.util.Scanner;

public class ClientEcho implements Runnable {

    private Thread thread;
    private DatagramSocket datagramSocket;
    private DatagramPacket datagramPacket;
    private ByteArrayInputStream byteArrayInputStream;
    private ByteArrayOutputStream byteArrayOutputStream;
    private ObjectInputStream objectInputStream;
    private ObjectOutputStream objectOutputStream;
    private Scanner scanner;

    private int currentCount;

    private String ip;
    private int port;

    public ClientEcho(String ip, int port) {
        try{
            thread = new Thread(this);
            datagramSocket = new DatagramSocket(8188);
            byteArrayOutputStream = new ByteArrayOutputStream();
            objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);

            this.ip = ip;
            this.port = port;

            thread.start();
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    private void setCurrentCount(int count){
        currentCount = count;
    }

    public int getCurrentCount(){
        return currentCount;
    }

    public void getPlayersCount(){
        try{
            String ask = "playersCount::";

            byteArrayOutputStream = new ByteArrayOutputStream();
            objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
            objectOutputStream.writeObject(ask);
            objectOutputStream.flush();

            byte[] Buf = byteArrayOutputStream.toByteArray();
            int length = Buf.length;
            byte[] data = new byte[4];

            for (int i = 0; i < 4; ++i) {
                int shift = i << 3; // i * 8
                data[3-i] = (byte)((length & (0xff << shift)) >>> shift);
            }

            //datagramPacket = new DatagramPacket(data, 4, InetAddress.getByName("localhost"), 3001);

            //datagramPacket = new DatagramPacket(data, 4, new InetSocketAddress("25.41.250.41",8189));
            //datagramSocket.send(datagramPacket); // Отправили размер.

            //datagramPacket = new DatagramPacket(Buf, Buf.length, InetAddress.getByName("localhost"), 3001);
            //datagramPacket = new DatagramPacket(Buf, Buf.length, new InetSocketAddress("25.41.250.41",8189));
            datagramPacket = new DatagramPacket(Buf, Buf.length, new InetSocketAddress(ip,port));
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
                        case "playersCount" :
                            int count = scanner.nextInt();
                            setCurrentCount(count);
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
