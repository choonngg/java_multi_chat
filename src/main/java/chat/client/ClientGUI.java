package chat.client;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class ClientGUI extends Application {

    private TextArea chatInputArea;
    private TextArea chatLogsArea;
    private Button sendButton;

    public void guiStart() {
        launch();
    }

    @Override
    public void start(Stage stage) {
        try {
            // 컨테이너 생성
            Pane pane = new Pane();

            // 채팅 내용
            chatLogsArea = new TextArea();
            chatLogsArea.setEditable(false);
            chatLogsArea.setLayoutX(25); chatLogsArea.setLayoutY(58);
            chatLogsArea.setPrefSize(350, 395);

            // 채팅 입력창
            chatInputArea = new TextArea();
            chatInputArea.setLayoutX(25); chatInputArea.setLayoutY(459);
            chatInputArea.setPrefSize(350, 83);
            chatInputArea.setPromptText("채팅 입력");

            // 메시지 전송 버튼
            sendButton = new Button("전송");
            sendButton.setLayoutX(310); sendButton.setLayoutY(555);
            sendButton.setPrefSize(65, 27);

            // 컨테이너에 등록
            pane.getChildren().addAll(chatLogsArea, chatInputArea, sendButton);

            // 화면에 표시
            Scene scene = new Scene(pane, 400, 600);
            stage.setTitle("Chat-Client");
            stage.setScene(scene);
            stage.show();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // 사용자 메시지 입력
    public String userInputMessage() {
        return chatInputArea.getText();
    }

    // 서버로부터 수신받은 메시지를 채팅 로그에 추가
    public void addChatLogs(String message) {
        chatLogsArea.appendText(message + "\n");
    }
}
