package chat.server;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class ServerTest {

    private ServerSocket serverSocket;
    private List<PrintWriter> clientOutputs = new ArrayList<>();
    private ServerGUI gui;

    public ServerTest(ServerGUI gui) {
        this.gui = gui;
    }

    // 서버 시작 + 끊임없이 클라이언트와 연결 시도
    public void serverOn() {

        try {
            serverSocket = new ServerSocket(8585);
            System.out.println("서버 시작 성공");

            while (true) {
                clientConnect();
            }

        } catch (Exception e) {
            System.out.println("서버 시작 실패");
            e.printStackTrace();
        }

    }

    // 클라이언트와 연결 + 스레드를 통해 클라이언트 통신을 독립적으로 처리
    private void clientConnect() {
        try {
            Socket clientSocket = serverSocket.accept();
            System.out.println("클라이언트 연결 성공");

            Thread thread = new Thread(new ClientHandler(clientSocket));
            thread.start();

        } catch (Exception e) {
            System.out.println("클라이언트 연결 실패");
        }
    }

    // 클라이언트 통신 처리
    private class ClientHandler implements Runnable {

        private final Socket clientSocket;
        private PrintWriter out;
        private BufferedReader in;

        public ClientHandler(Socket clientSocket) {
            this.clientSocket = clientSocket;
        }

        @Override
        public void run() {
            try {
                out = new PrintWriter(clientSocket.getOutputStream(), true);
                in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

                // 클라이언트 출력스트림 리스트에 저장
                clientOutputs.add(out);

                // 메시지를 모든 클라이언트에게 전송
                String message;
                while ((message = in.readLine()) != null) {
                    gui.appendMessage(message);

                    for (PrintWriter writer : clientOutputs) {
                        writer.println(message);
                    }
                }

            } catch (Exception e) {
                System.out.println("클라이언트 통신 불량");
                e.printStackTrace();

            } finally {
                try {
                    clientOutputs.remove(out);
                    out.close();
                    in.close();
                    clientSocket.close();

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
