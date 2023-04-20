package iwish.controllers;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class SocketController {
    private static ObjectOutputStream outputStream;
    private static ObjectInputStream inputStream;
    private static Socket socket;

   public static Object sendRequest(Object request) throws Exception {
        SocketController.connect();
        // kalamy ma3 server 
        SocketController.getOutputStream().writeObject(request);
        SocketController.getOutputStream().flush();
        // ana k client mstany el response k inputstream 
        Object response = SocketController.getInputStream().readObject();
        // w han2fel el mokalma 
        SocketController.disconnect();

        return response;
    }

    public static void connect() {
        try {
            socket = new Socket("127.0.0.1", 5005);
            outputStream = new ObjectOutputStream(socket.getOutputStream());
            inputStream = new ObjectInputStream(socket.getInputStream());
        } catch (Exception exception) {
            SceneController.showErrorAlert(exception);
            System.exit(1);
        }
    }

    public static void disconnect() {
        try {
            inputStream.close();
            outputStream.close();
            socket.close();
        } catch (Exception exception) {
            SceneController.showErrorAlert(exception);
        }
    }
    
    
    
    public static ObjectOutputStream getOutputStream() {
        return outputStream;
    }

    public static ObjectInputStream getInputStream() {
        return inputStream;
    }
}
