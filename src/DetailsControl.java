import genericalgorithm.Population;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import point.Point;

import java.util.ArrayList;

public class DetailsControl {

    @FXML
    private TableView<Point> table;

    @FXML
    private TableColumn<Point, Double> xCol;

    @FXML
    private TableColumn<Point, Double> yCol;

    @FXML
    private TableColumn<Point, Double> zCol;

    @FXML
    private TableColumn<Point, Double> fCol;

    @FXML
    private Button bNum;

    @FXML
    private Button fNum;

    @FXML
    private TextField num;

    @FXML
    private TextField generation;

    private ArrayList<Population<Point>> detailPopulation = new ArrayList<>();
    private Integer generationNum=0;
    private Double allFitness=0.0;

    public DetailsControl()
    {};

    public void init(ArrayList<Population<Point>> p, Integer num)
    {
        detailPopulation=p;
        fillTable(num);
        generationNum++;
    }

    public void fillTable(Integer num)
    {
        this.generationNum=num;
        table.getColumns().clear();
        Population<Point> points = (Population<Point>) detailPopulation.get(num);
        ObservableList data = FXCollections.observableList(points.getPopulation());

        xCol.setCellValueFactory(new PropertyValueFactory<Point,Double>("x"));
        yCol.setCellValueFactory(new PropertyValueFactory<Point,Double>("y"));
        zCol.setCellValueFactory(new PropertyValueFactory<Point,Double>("z"));
        fCol.setCellValueFactory(new PropertyValueFactory<Point,Double>("fitness"));

        Double value = 0.0;

        for (Point p : points.getPopulation())
            value+=p.getFitness();
        value/=points.getPopulation().size();

        generation.setText("Generation: "+generationNum+"         Generation fitness: "+value);

        table.setItems(data);
        table.getColumns().addAll(xCol,yCol,zCol,fCol);

        this.num.setText(num.toString());
    }

    public void nextPage()
    {
        bNum.setDisable(false);
        this.generationNum++;
        fillTable(this.generationNum);

        Integer actual = Integer.parseInt(num.getText());

        if(actual==detailPopulation.size()-1)
            fNum.setDisable(true);
    }

    public void previousPage()
    {
        fNum.setDisable(false);
        this.generationNum--;
        fillTable(this.generationNum);

        Integer actual = Integer.parseInt(num.getText());

        if(actual==0)
            bNum.setDisable(true);
    }

    public void go()
    {
        Integer actual = Integer.parseInt(num.getText());
        if(actual==0)
            bNum.setDisable(true);
        else
            bNum.setDisable(false);
        if(actual==detailPopulation.size()-1)
            fNum.setDisable(true);
        else
            fNum.setDisable(false);
        fillTable(actual);
    }

    public void last()
    {
        fNum.setDisable(true);
        bNum.setDisable(false);
        fillTable(detailPopulation.size()-1);
    }
}