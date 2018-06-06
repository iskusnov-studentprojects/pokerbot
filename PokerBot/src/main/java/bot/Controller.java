package bot;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;

/**
 * Created by Sergey on 04.06.2018.
 */
public class Controller implements Runnable {
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Controller());
    }

    @Override
    public void run() {
        while (true) {
            /*serverSocket.setSoTimeout(10000);
            int serverPort = 4000;
            try {
                ServerSocket serverSocket = new ServerSocket(serverPort);
                // Подключение к порту. По сути, начало работы сервера.
                Socket server = serverSocket.accept();
                // Получение данных от клиента.
                BufferedReader fromClient = new BufferedReader(new InputStreamReader(server.getInputStream()));
                String line = fromClient.readLine();
                // Ответ клиенту.
                PrintWriter toClient = new PrintWriter(server.getOutputStream(), true);

            } catch (IOException e) {
                e.printStackTrace();
            }*/
            int serverPort = 5000;
            try (Socket socket = new Socket("127.0.0.1", serverPort)) {
                socket.setSoTimeout(10000);
                try (PrintWriter toServer = new PrintWriter(socket.getOutputStream(), true)) {
                    // Отправка данных на сервер
                    toServer.println("Hello from TEST");
                    // Ответ сервера
                    BufferedReader fromServer;
                    fromServer = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                    String line = fromServer.readLine();
                    fromServer.close();
                }
            } catch (UnknownHostException ex) {
                ex.printStackTrace();
            } catch (IOException e) {
                try {
                    if (e instanceof SocketTimeoutException) {
                        throw new SocketTimeoutException();
                    } else {
                        e.printStackTrace();
                    }
                } catch (SocketTimeoutException ste) {
                    System.out.println("Turn off the client by timeout");
                }
            }
        }
    }
}
