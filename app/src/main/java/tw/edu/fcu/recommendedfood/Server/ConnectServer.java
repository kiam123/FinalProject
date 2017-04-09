package tw.edu.fcu.recommendedfood.Server;

import java.io.PrintStream;
import java.net.InetAddress;
import java.net.Socket;

/**
 * Created by kiam on 3/14/2017.
 */

public class ConnectServer extends Thread {
    Socket client = null;
    PrintStream writer;
    String ipAddress = "";
    int serverPort = 0;

    public ConnectServer(String ipAddress, int serverPort) {
        this.ipAddress = ipAddress;
        this.serverPort = serverPort;

    }

    @Override
    public void run() {
        // TODO Auto-generated method stub
        try {
            // IP為Server端
            InetAddress serverIp = InetAddress.getByName(ipAddress);//192.168.1.104
            client = new Socket(serverIp, serverPort);
            writer = new PrintStream(client.getOutputStream(),true,"UTF-8");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void sendMsgtoServer(String msg) {
        writer.println(msg);
        writer.flush();
    }

    public void closeSocket() {
        sendMsgtoServer("");
        sendMsgtoServer("CLOSE");

        try {
            writer.close();
            client.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
