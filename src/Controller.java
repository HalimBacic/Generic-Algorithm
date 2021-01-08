import genericalgorithm.Population;
import genericalgorithm.Simulation;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import point.Point;
import point.Point2D;
import point.Point3D;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class Controller {

    @FXML
    private TextField population;

    @FXML
    private TextField cross;

    @FXML
    private TextField mutation;

    @FXML
    private TextField iteration;

    @FXML
    private Button run3d;

    @FXML
    private Button run2d;

    @FXML
    private Button details;

    @FXML
    private TextArea info;

    @FXML
    private TextField ontop;

    private ArrayList<Population<Point>> history=new ArrayList<>();

    public TextField getPopulation() {
        return population;
    }

    public TextField getCross() {
        return cross;
    }

    public TextField getMutation() {
        return mutation;
    }

    public TextField getIteration() {
        return iteration;
    }

    public TextField getonTop(){   return ontop; }

    public Button getRun3d() {
        return run3d;
    }

    public Button getRun2d() {
        return run2d;
    }

    public TextArea getInfo() {
        return info;
    }

    public void simulate3d() {
        File file = new File("3d.txt");
        if(file.exists())
            file.delete();

        getInfo().clear();
        history.clear();
        try {

            double cross = Double.parseDouble(getCross().getText());
            double mut = Double.parseDouble(getMutation().getText());
            Integer iter = Integer.valueOf(getIteration().getText());
            Integer pop=Integer.valueOf(getPopulation().getText());
            Integer onTop=Integer.valueOf(getonTop().getText());

            if(mut>1 || cross>1)
                throw new Exception("Mutation and crossing chance must be <=1");

            Population<Point3D> population = new Population<>(pop, Point3D.class);

            Simulation<Point3D> simulation = new Simulation<>(population, cross, mut, iter,onTop);

            simulation.startSimulation("3d.txt");

            Point p = simulation.getPopulation().getPopulation().get(0);

            getInfo().setText(p.toString());
            history=simulation.getHistory();
            details.setDisable(false);
        } catch(Exception e)
        {
            details.setDisable(true);
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Error.");
            alert.setHeaderText("There is an error.");
            alert.setContentText(e.getMessage());
            e.printStackTrace();
            alert.showAndWait();
        }
    }

    public void simulate2d() {
        File file = new File("2d.txt");
        if(file.exists())
            file.delete();

        getInfo().clear();
        history.clear();
        try {
            double cross = Double.parseDouble(getCross().getText());
            double mut = Double.parseDouble(getMutation().getText());
            Integer iter = Integer.valueOf(getIteration().getText());
            Integer pop=Integer.valueOf(getPopulation().getText());
            Integer onTop=Integer.valueOf(getonTop().getText());
            if(mut>1 || cross>1)
                throw new Exception("Mutation and crossing chance must be <=1");

            Population<Point2D> population = new Population<>(pop, Point2D.class);

            Simulation<Point2D> simulation = new Simulation<>(population, cross, mut, iter,onTop);

            simulation.startSimulation("2d.txt");

            Point p = simulation.getPopulation().getPopulation().get(0);

            getInfo().setText(p.toString());

            details.setDisable(false);
            history=simulation.getHistory();
        } catch(Exception e)
        {
            details.setDisable(true);
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Error.");
            alert.setHeaderText("There is an error.");
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }
    }

    public void showDetails() throws IOException {
        Stage primaryStage = new Stage();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("details.fxml"));
        Parent root = loader.load();
        DetailsControl detctrl=loader.getController();
        detctrl.init(history,0);
        primaryStage.setTitle("Generic Algorithm by Halim Bacic");
        primaryStage.setScene(new Scene(root, 718, 482));
        primaryStage.show();
    }
}
