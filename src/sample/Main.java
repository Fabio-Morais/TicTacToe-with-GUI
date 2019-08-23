package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        primaryStage.setTitle("Tic Tac Toe");
        primaryStage.getIcons().add(new Image("icon.png"));
        primaryStage.setScene(new Scene(root, 550, 600));
        primaryStage.show();
        //Controller controller = new Controller();

    }


    public static void main(String[] args) {
        launch(args);
    }
}
