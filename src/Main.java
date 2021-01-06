import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.File;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{

        File file = new File("2d.txt");
        if(file.exists())
            file.delete();

        file = new File("3d.txt");
        if(file.exists())
            file.delete();

        Parent root = FXMLLoader.load(getClass().getResource("GenericAlgorithm.fxml"));
        primaryStage.setTitle("Generic Algorithm by Halim Bacic");
        primaryStage.setScene(new Scene(root, 624, 467));
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
