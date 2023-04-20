package iwish.controllers;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class SocketController {
    private static final List<ClientController> clientControllers = new ArrayList<>();
    private static boolean isRunning = false;
    private static ServerSocket socket;

    public static boolean isRunning() {
        return isRunning;
    }

    public static void start() throws Exception {
        if (isRunning == false) {
            
            socket = new ServerSocket(5005);
            DatabaseController.connect();
            isRunning = true;
            // bta5od function run 3ashan t3mel el 7aga eli gowaha 
            // thread hna 5as b eno ystlem sockets mn kaza client
            Thread thread = new Thread(SocketController::run);
            thread.start();
        }
    }

    public static void run() {
        try {
            // byshof howa running wla la law el server sha3'al el socket mafto7 .. el server socket mafto7 haystlem el client socket eli haytb3atlo eli e7na 3malnah fl client 
            // client controller eli fl server
            while (isRunning == true) {
                // socket howa eli bystlem el connection eli gya mn el socketcontroller bta3 el client eli f makan connect
                Socket clientSocket = socket.accept();
                
                
                ClientController clientController;
                // SocketController ===clientSocket==> ClientController
                clientController = new ClientController(clientSocket);
                
                clientControllers.add(clientController);
                // de to handle the requests from one client
                Thread thread = new Thread(clientController::run);
                thread.start();
            }
        } catch (IOException exception) {
            stop();
        } catch (Exception exception) {
            SceneController.showErrorAlert(exception);
        }
    }

    public static void stop() {
        try {
            if (!isRunning) return;
            for (ClientController clientController : clientControllers) clientController.disconnect();
            DatabaseController.disconnect();
            socket.close();
            isRunning = false;
        } catch (Exception exception) {
            SceneController.showErrorAlert(exception);
        }
    }
}
