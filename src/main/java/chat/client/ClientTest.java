package chat.client;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ClientTest {

    private Socket clientSocket;
    private PrintWriter out;
    private BufferedReader in;

    private static final ClientGUI gui = new ClientGUI();

    // gui + 서버와 연결
    private void serverConnect() {

        try {
            clientSocket = new Socket("127.0.0.1", 8585);
            out = new PrintWriter(clientSocket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            System.out.println("서버 연결 성공");

            // 스레드를 통해 송수신을 독립적으로 처리
            Thread userThread = new Thread(new UserHandler());
            Thread serverThread = new Thread(new ServerHandler());
            userThread.start();
            serverThread.start();

        } catch (Exception e) {
            System.out.println("서버 연결 실패");
            e.printStackTrace();
        }
    }

    // 사용자 입력에 대한 처리 (서버로 송신)
    private class UserHandler implements Runnable {

        @Override
        public void run() {
            try {
                String message = gui.userInputMessage();
                while ((message != null)) {
                    out.println(message);
                }

            } catch (Exception e) {
                System.out.println("사용자 입력 오류 발생");
            }
        }
    }

    // 서버로부터 수신받는 것에 대한 처리
    private class ServerHandler implements Runnable {

        @Override
        public void run() {
            try {
                String message;
                while ((message = in.readLine()) != null) {
                    gui.addChatLogs(message);
                }

            } catch (Exception e) {
                System.out.println("서버 응답 오류 발생");
            }
        }
    }

}
