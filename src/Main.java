import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("GenericAlgorithm.fxml"));
        primaryStage.setTitle("Generic Algorithm by Halim Bacic");
        primaryStage.setScene(new Scene(root, 624, 467));
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
