package genericalgorithm;

import Comparators.ComparePoint2D;
import Comparators.ComparePoint3D;
import point.Point;
import point.Point2D;
import point.Point3D;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.Random;

/**
 *
 * @param <T> can be Point3D or Point2D depending on the task
 */
public class Simulation<T extends Point> implements BinaryRepresentation {

    private Integer selectionNum;
    private Integer initialNum;
    private final Double crossCh;
    private final Double mutCh;
    private Integer iterNum;
    private Population<T> population;
    private Population<T> newGeneration = new Population<>();
    private ArrayList<Population<Point>> history = new ArrayList<>();
    private Comparator cmp;
    private Integer onTop;

    /**
     * Base constructor
     * @param population number of individuals in population
     * @param crossCh number between 0-1.0 which is chance for crossing in genetic algorithm
     * @param mutC number between 0-1.0 which is chance for mutation for new individuals after crossing
     * @param iterNum number of iteration for generic algorithm
     */
    public Simulation(Population<T> population, Double crossCh, Double mutC, Integer iterNum,Integer ontop) {
        double perc = population.getPopulation().size() / 1.5;
        selectionNum = (int) perc;
        initialNum=population.getPopulation().size();
        this.crossCh = crossCh;
        this.mutCh = mutC;
        this.population = population;
        this.iterNum = iterNum;
        this.onTop=ontop;
        if(population.getPopulation().get(0) instanceof Point3D)
            cmp=new ComparePoint3D();
        else if(population.getPopulation().get(0) instanceof Point2D)
            cmp=new ComparePoint2D();
    }

    /**
     * Method is used in DetailsControl, for table fill
     * @return history
     */
    public ArrayList<Population<Point>> getHistory() {
        return history;
    }

    public Population<T> getPopulation() {
        return population;
    }

    /**
     * Function for starting algorithm
     */
    public void startSimulation(String filename) throws InterruptedException, IOException {
        Integer topT=0;

        Population<T> addHistory = new Population<>(population);
        history.add((Population<Point>) addHistory);
        while (iterNum > 0) {
            Double bestValue = population.getPopulation().get(0).getZ();
            crossing();
            fitness();
            population.sortPopulation(cmp);
            selectBestInPopulation();
            iterNum--;

            if(bestValue==population.getPopulation().get(0).getZ())
                topT++;

            if(topT==this.onTop)
                iterNum=-1;
            addHistory = new Population<>(population);
            history.add((Population<Point>) addHistory);

            BufferedWriter writer=new BufferedWriter(new FileWriter(filename,true));
            writer.write(population.toString());
            writer.close();
        }
    }

    /**
     * This function select best candidates which continue exist in next generation.
     * A number that determines the number of candidates in next generation is given in constructor
     */
    protected void selectBestInPopulation()
    {
        Population<T> nextPopulation=new Population<>();
        for(int i=0;i<initialNum;i++)
            nextPopulation.getPopulation().add(population.getPopulation().get(i));
        population.getPopulation().clear();
        population.getPopulation().addAll(nextPopulation.getPopulation());
    }

    /**
     * Function which calculates fitness for each individual
     */
    protected void fitness() {
        for(T p : population.getPopulation())
        {
            Double ft = p.getZ()-population.getPopulation().get(0).getZ();
            p.setFitness(ft);
        }
    }

    /**
     * Function for implement crossing part of generic algorithm. In each generation, crossing is between every two individuals
     */
    protected void crossing() {
        for(int i=0;i<selectionNum;i++)
            newGeneration.getPopulation().add(population.getPopulation().get(i));

        for (int i = 0; i < population.getPopulation().size()-1; i++)
            for (int j = i + 1; j < population.getPopulation().size(); j++)
                if(tryCrossing(population.getPopulation().get(i), population.getPopulation().get(j)))
                    i++;

        for(T p : newGeneration.getPopulation())
            if(!population.getPopulation().contains(p))
                population.getPopulation().add(p);

        newGeneration.getPopulation().clear();
    }

    /**
     * Function which makes new point in population if there is crossing between individuals in generation
     * @param p1 first parent
     * @param p2 second parent
     */
    protected boolean tryCrossing(T p1, T p2) {
        Point point;
        Random random = new Random();
        double dbl=random.nextDouble();
        if (dbl >= this.crossCh) {
            point = makeNewPoint(p1, p2);
            if (random.nextDouble() >= this.mutCh)
                point = mutation((T) point);

            newGeneration.getPopulation().add((T) point);

            for(T p:population.getPopulation()) {
                if (p.getZ().doubleValue()==point.getZ().doubleValue()) {
                    newGeneration.getPopulation().remove((T) point);
                    return false;
                }
            }
            return true;
        }
        return false;
    }

    /**
     * Function implement crossing
     * @param p1 parent one
     * @param p2 parent two
     * @return new Point after crossing
     */
    protected T makeNewPoint(T p1, T p2) {
        String p1X = toBinary(p1.getX(), 10);
        String p1Y = toBinary(p1.getY(), 10);
        String p2X = toBinary(p2.getX(), 10);
        String p2Y = toBinary(p2.getY(), 10);

        String x = makeCross(p1X,p2X);
        String y = makeCross(p1Y,p2Y);

        Double xx=binaryToDouble(x);
        Double yy=binaryToDouble(y);

        Point point;
        if(p1 instanceof Point3D)
            point = new Point3D(xx,yy);
        else
            point = new Point2D(yy);
        return (T) point;

    }

    /**
     * Function for simulation crossing. Parameters is number like 011.1110010001 which is binary representation of point
     * @param one binary number which represent parent one point
     * @param two binary number which represent parent two point
     * @return binary number which is new point for new individual after crossing
     */
    protected String makeCross(String one,String two)
    {
        StringBuilder so=new StringBuilder(one);
        StringBuilder st=new StringBuilder(two);

        return st.substring(0, 1) + so.substring(1, 3) + st.substring(3, 4) + so.substring(4, 8) + st.substring(8, 12) + so.substring(12, 13) + st.substring(13);
    }

    /**
     * Function which implements mutation algorithm
     * @param point which will be mutated
     * @return point after mutation
     */
    protected T mutation(T point) {
        Point p;
        if(point.getClass().isAssignableFrom(Point3D.class))
            p = mutation3D((Point3D) point);
        else
            p= mutation2D((Point2D) point);

        return (T) p;

    }

    public Point3D mutation3D(Point3D p) {
        StringBuilder x = new StringBuilder(toBinary(p.getX(), 10));
        StringBuilder y = new StringBuilder(toBinary(p.getY(), 10));

        Random rnd = new Random();
        int k = rnd.nextInt(y.length() - 1 + 5);
        while (k == 4 || k < y.length() - 1)
        {
            if(k==0)
                if(x.charAt(0)=='+')
                    x.replace(0,1,"-");
                else
                    x.replace(0,1,"+");
            else {
                int poz=rnd.nextInt(y.length() - 1);
                if (x.charAt(poz) == '1')
                    x.replace(poz, poz+1, "0");
                else if(x.charAt(poz) == '0')
                    x.replace(poz, poz+1, "1");
            }
            k = rnd.nextInt(y.length() - 1 + 10);
        }

        k = rnd.nextInt(y.length() - 1 + 5);
        while (k == 4 || k < y.length() - 1)
        {
            if(k==0)
                if(y.charAt(0)=='+')
                    y.replace(0,1,"-");
                else
                    y.replace(0,1,"+");
            else {
                int poz=rnd.nextInt(y.length() - 1);
                if (y.charAt(poz) == '1')
                    y.replace(poz, poz+1, "0");
                else if(y.charAt(poz) == '0')
                    y.replace(poz, poz+1, "1");
            }
            k = rnd.nextInt(y.length() - 1 + 10);
        }

        Double xx=binaryToDouble(x.toString());
        Double yy=binaryToDouble(y.toString());

        return new Point3D(xx,yy);
    }

    public Point2D mutation2D(Point2D p) {
        StringBuilder y = new StringBuilder(toBinary(p.getY(), 10));

        Random rnd = new Random();
        int k = rnd.nextInt(y.length() - 1 + 5);
        while (k == 4 || k < y.length() - 1)
        {
            if(k==0)
                if(y.charAt(0)=='+')
                    y.replace(0,1,"-");
                else
                    y.replace(0,1,"+");
            else {
                int poz=rnd.nextInt(y.length() - 1);
                if (y.charAt(poz) == '1')
                    y.replace(poz, poz+1, "0");
                else if(y.charAt(poz) == '0')
                    y.replace(poz, poz+1, "1");
            }
            k = rnd.nextInt(y.length() - 1 + 10);
        }

        Double yy=binaryToDouble(y.toString());

        return new Point2D(yy);
    }


    @Override
    public String toBinary(double d, int precision) {
        long wholePart;
        double c=d;

        if(d>0)
            wholePart = (long) d;
        else {
            wholePart = (long) (-1 * d);
            c=d*(-1);
        }
        String rtrStr=wholeToBinary(wholePart) + '.' + fractionalToBinary(c - wholePart, precision);
        if(d>0)
            return "+"+rtrStr;
        else
            return "-"+rtrStr;
    }

    @Override
    public String wholeToBinary(long l) {
        StringBuilder binary = new StringBuilder();

        String whole = Long.toBinaryString(l);

        if (whole.length() == 1)
            binary.append("00").append(whole);
        else if (whole.length() == 2)
            binary.append("0").append(whole);
        else
            binary.append(whole);

        return binary.toString();
    }

    @Override
    public String fractionalToBinary(double num, int precision) {
        StringBuilder binary = new StringBuilder();
        while (num >= 0 && binary.length() < precision) {
            double r = num * 2;
            if (r >= 1) {
                binary.append(1);
                num = r - 1;
            } else {
                binary.append(0);
                num = r;
            }
        }
        return binary.toString();
    }

    @Override
    public Double binaryToDouble(String str) {


        String[] parts = str.split("\\.");

        StringBuilder part1 = new StringBuilder(parts[0]);
        StringBuilder part2 = new StringBuilder(parts[1]);

        part1.deleteCharAt(0);

        Double num = 0.0;
        int i = 0;
        int stepen = 2;

        while (i < 3) {
            Integer d = Integer.parseInt(String.valueOf(part1.charAt(i++)));
            num += d.doubleValue() * Math.pow(2, stepen--);
        }

        i = 0;

        while (i < 10) {
            Integer d = Integer.parseInt(String.valueOf(part2.charAt(i++)));
            num += d.doubleValue() * Math.pow(2, stepen--);
        }

        if(str.startsWith("-"))
            num*=-1;

        return num;
    }
}
