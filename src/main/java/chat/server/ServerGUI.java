package chat.server;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class ServerGUI extends Application {

    private TextArea chatLogsArea;

    @Override
    public void start(Stage stage) {
        try {
            // 컨테이너 생성
            Pane pane = new Pane();

            // 채팅 내용
            chatLogsArea = new TextArea();
            chatLogsArea.setEditable(false);
            chatLogsArea.setLayoutX(25); chatLogsArea.setLayoutY(24);
            chatLogsArea.setPrefSize(350, 510);

            // 메시지 전송 버튼
            Button userList = new Button("이용자 목록");
            userList.setLayoutX(288); userList.setLayoutY(552);
            userList.setPrefSize(87, 30);

            // 공지사항 전송 버튼
            Button sendNoticeButton = new Button("공지사항 전송");
            sendNoticeButton.setLayoutX(182); sendNoticeButton.setLayoutY(552);
            sendNoticeButton.setPrefSize(99, 30);

            // 채팅내용 삭제 버튼
            Button chatLogsTrashButton = new Button("채팅내용 삭제");
            chatLogsTrashButton.setLayoutX(25); chatLogsTrashButton.setLayoutY(552);
            chatLogsTrashButton.setPrefSize(99, 30);
            chatLogsTrashButton.setOnAction(new ChatLogsClearGUI());

            // 컨테이너에 등록
            pane.getChildren().addAll(chatLogsArea, userList, sendNoticeButton, chatLogsTrashButton);

            // 화면에 표시
            Scene scene = new Scene(pane, 400, 600);
            stage.setTitle("Chat-Server");
            stage.setScene(scene);
            stage.show();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // 채팅내용삭제 클릭 시 나오는 GUI
    public class ChatLogsClearGUI implements EventHandler<ActionEvent> {

        @Override
        public void handle(ActionEvent event) {
            try {
                Pane pane = new Pane();
                Scene scene = new Scene(pane, 200, 100);
                Stage stage = new Stage();

                Label label = new Label("채팅 내용을 전부 삭제합니다.");
                label.setLayoutX(30);
                label.setLayoutY(20);

                Button yesButton = new Button("예");
                yesButton.setLayoutX(27);
                yesButton.setLayoutY(59);
                yesButton.setPrefSize(65,27);
                yesButton.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {
                        chatLogsArea.clear();
                    }
                });

                Button noButton = new Button("아니오");
                noButton.setLayoutX(110);
                noButton.setLayoutY(59);
                noButton.setPrefSize(65,27);
                noButton.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {
                        stage.close();
                    }
                });

                pane.getChildren().addAll(label, yesButton, noButton);

                stage.setTitle("채팅내용 삭제");
                stage.setScene(scene);
                stage.show();

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    // 받은 메시지를 채팅내용에 추가
    public void appendMessage(String message) {
        chatLogsArea.appendText(message + "\n");
    }


}
