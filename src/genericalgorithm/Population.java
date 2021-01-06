package genericalgorithm;

import point.Point;
import point.Point2D;
import point.Point3D;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.Buffer;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Random;

/**
 * @author Halim Bacic
 * This is Population class which represent Population in generic algorithm. Population is group of Point objects
 * @param <T> T can be Point2D or Point3D depending on which task solving
 */
public class Population<T extends Point> {

    private ArrayList<T> population = new ArrayList<>();
    private Integer generationNum=0;

    public Population()
    {}
    /**
     *
     * @param numOfPopulation number of individuals in population
     * @param T can be Point3D or Point2D
     */
    public Population(Integer numOfPopulation, Class<?> T) throws IOException {

        BufferedWriter writer;
        if (T.isAssignableFrom(Point3D.class)) {
            generatePopulationPoint3D(numOfPopulation);
            sortPopulation();
            writer=new BufferedWriter(new FileWriter("3d.txt",true));
            writer.write("INICIJALNO\n"+toString());
        } else {
            generatePopulationPoint2D(numOfPopulation);
            sortPopulation();
            writer=new BufferedWriter(new FileWriter("2d.txt",true));
            writer.write("INICIJALNO\n"+toString());
        }
        writer.close();
    }

    public ArrayList<T> getPopulation() {
        return population;
    }

    public void setPopulation(ArrayList<T> population) {
        this.population.addAll(population);
    }


    private void generatePopulationPoint3D(Integer numOfPopulation){
        while (numOfPopulation > 0) {
            Random random = new Random();
            Double x = (3 - random.nextInt(6)) * random.nextDouble();
            Double y = random.nextInt(4) * random.nextDouble();
            Point3D point = new Point3D(x, y);
            population.add((T) point);
            numOfPopulation--;
        }
    }

    private void generatePopulationPoint2D(Integer numOfPopulation){
        while (numOfPopulation > 0) {
            Random random = new Random();
            Double y = random.nextInt(4) * random.nextDouble();
            Point2D point = new Point2D(y);
            population.add((T) point);
            numOfPopulation--;
        }
    }

    /**
     * Function for sorting individuals in population. Sorting criterium is calculated fitness function
     */
    public void sortPopulation() throws IOException {
        population.sort((o1, o2) -> {
            if(o1.getFitness()>o2.getFitness())
                return 1;
            else if(o1.getFitness()<o2.getFitness())
                return -1;
            else
                return 0;
        });
    }

    @Override
    public String toString() {
        generationNum++;
        String returnStr = "GENERATION: " + generationNum + "\n";
        returnStr += "NUM:          X            Y            Z         FITNESS\n";
        int i = 1;
        DecimalFormat dc = new DecimalFormat("#.######");
        for (Point p : population) {
            String x = dc.format(p.getX());
            String y=dc.format(p.getY());
            String z=dc.format(p.getZ());
            String f=dc.format(p.getFitness());
            returnStr += String.format("%4s",i++) + "   " + String.format("%10s",x) + "   " + String.format("%10s",y) + "  "
                    + String.format("%10s",z) + "  " + String.format("%10s",f) + "\n";
        }
        returnStr+="\n\n";

        return returnStr;
    }
}