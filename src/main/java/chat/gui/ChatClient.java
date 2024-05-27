package chat.gui;

import chat.data.ClientService;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ChatClient extends Application {

    private TextArea chatInputArea;
    private TextArea chatLogsArea;
    private Button sendButton;

    private Socket clientSocket;
    private PrintWriter out;
    private BufferedReader in;

//    private ClientService clientService;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) {
        try {
            // 컨테이너 생성
            Pane pane = new Pane();

            // 채팅 내용
            chatLogsArea = new TextArea();
            chatLogsArea.setEditable(false);
            chatLogsArea.setLayoutX(25);
            chatLogsArea.setLayoutY(58);
            chatLogsArea.setPrefSize(350, 395);

            // 채팅 입력창
            chatInputArea = new TextArea();
            chatInputArea.setLayoutX(25);
            chatInputArea.setLayoutY(459);
            chatInputArea.setPrefSize(350, 83);
            chatInputArea.setPromptText("채팅 입력");

            // 메시지 전송 버튼
            sendButton = new Button("전송");
            sendButton.setLayoutX(310);
            sendButton.setLayoutY(555);
            sendButton.setPrefSize(65, 27);

            // 전송 버튼 클릭 이벤트
            sendButton.setOnAction(event -> {
                String message = chatInputArea.getText();
                if (!message.isEmpty()) {
                    out.println(message);
                    chatInputArea.clear();
                }
            });

            // 컨테이너에 등록
            pane.getChildren().addAll(chatLogsArea, chatInputArea, sendButton);

            // 화면에 표시
            Scene scene = new Scene(pane, 400, 600);
            stage.setTitle("Chat-Client");
            stage.setScene(scene);
            stage.show();

            // 서버와 연결
            serverConnect();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // 서버와 연결
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
                while (true) {
                    // 메인 스레드에서 전송 버튼 클릭 이벤트로 송신 처리
                    Thread.sleep(100);  // 너무 많은 루프를 돌지 않도록 잠시 대기
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
                    chatLogsArea.appendText(message + "\n");
                }
            } catch (Exception e) {
                System.out.println("서버 응답 오류 발생");
            }
        }
    }
}