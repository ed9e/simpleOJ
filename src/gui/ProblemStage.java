package gui;


import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class ProblemStage extends Application {
    Stage stage=new Stage();
    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("problem.fxml"));
        primaryStage.setTitle("Problem list");
        primaryStage.setScene(new Scene(root));
        StageManager.STAGE.get("OnlineJudge").close();
        StageManager.STAGE.remove("OnlineJudge");
        StageManager.STAGE.put("Problem",primaryStage);
        primaryStage.show();

    }

    public static void main(String[] args) {
        launch(args);
    }

    public void  showWindow() throws Exception {
        start(stage);
    }

}