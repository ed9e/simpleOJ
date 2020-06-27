package gui;


import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class SubmissionStage extends Application {
    Stage stage=new Stage();
    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("submission.fxml"));
        primaryStage.setTitle("Submission");
        primaryStage.setScene(new Scene(root));
        StageManager.STAGE.put("Submission",primaryStage);
        primaryStage.show();
    }
    public static void main(String[] args) {
        launch(args);
    }
    public void  showWindow() throws Exception {
        start(stage);
    }

}