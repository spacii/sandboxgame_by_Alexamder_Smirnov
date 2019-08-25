package company.Game;

import java.io.*;
import java.net.*;
import java.util.Enumeration;
import java.util.Scanner;

public class ClientEchoNew{

    private DatagramSocket datagramSocket;
    private DatagramPacket datagramPacket;
    private ByteArrayInputStream byteArrayInputStream;
    private ByteArrayOutputStream byteArrayOutputStream;
    private ObjectInputStream objectInputStream;
    private ObjectOutputStream objectOutputStream;
    private Scanner scanner;

    private int myPortForListening;

    private boolean aliveRuslt = false;

    public ClientEchoNew(){
        try {

            myPortForListening = 8188;

            datagramSocket = new DatagramSocket(myPortForListening);
            datagramSocket.setSoTimeout(50);
            byteArrayOutputStream = new ByteArrayOutputStream();
            objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String getMyIpForServer(){
        /*
        try{
            URL whatismyip = new URL("http://checkip.amazonaws.com");
            BufferedReader in = new BufferedReader(new InputStreamReader(
                    whatismyip.openStream()));

            String ip = in.readLine(); //you get the IP as a String
            System.out.println(ip);
            return ip;
        } catch (Exception e){
            e.printStackTrace();
        }
        return "";
        */
/*
        String ip = "";
        try {
            Enumeration<NetworkInterface> interfaces = NetworkInterface.getNetworkInterfaces();
            while (interfaces.hasMoreElements()) {
                NetworkInterface iface = interfaces.nextElement();
                // filters out 127.0.0.1 and inactive interfaces
                if (iface.isLoopback() || !iface.isUp())
                    continue;

                Enumeration<InetAddress> addresses = iface.getInetAddresses();
                while(addresses.hasMoreElements()) {
                    InetAddress addr = addresses.nextElement();
                    ip = addr.getHostAddress();
                    System.out.println(iface.getDisplayName() + " " + ip);
                }
            }
        } catch (SocketException e) {
            throw new RuntimeException(e);
        }
        return ip;
*/

        try {
            Enumeration<NetworkInterface> interfaces = NetworkInterface.getNetworkInterfaces();
            while (interfaces.hasMoreElements()) {
                NetworkInterface iface = interfaces.nextElement();
                if (iface.isLoopback() || !iface.isUp() || iface.isVirtual() || iface.isPointToPoint())
                    continue;

                Enumeration<InetAddress> addresses = iface.getInetAddresses();
                while(addresses.hasMoreElements()) {
                    InetAddress addr = addresses.nextElement();

                    final String ip = addr.getHostAddress();
                    if(Inet4Address.class == addr.getClass()) return ip;
                }
            }
        } catch (SocketException e) {
            throw new RuntimeException(e);
        }
        return null;

    }

    public String getServeInfo(String ip, int port){
            try{
                //String ask = "giveServeInfo::";
                String ask = "giveServeInfo::" + getMyIpForServer() + "::" + myPortForListening + "::";

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
            //String ask = "aliveCheck::";
            String ask = "aliveCheck::" + getMyIpForServer() + "::" + myPortForListening + "::";

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
                        System.out.println("!!!!!!!" + scanner.nextLine());
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
