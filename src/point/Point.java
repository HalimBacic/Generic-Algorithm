package point;

import function.Function;

import java.text.DecimalFormat;

/**
 * Main class for implementing Point
 */
public class Point implements Comparable<Point> {

    private Double x;
    private Double y;
    private Double z;
    private Double fitness=0.0;


    public Point(Double x, Double y)
    {
        setX(x);
        setY(y);
        setZ(Function.calculateFunction(x, y));
    }

    public Point(Double y)
    {
        setX(0.0);
        setY(y);
        setZ(Function.calculateFunctionPlane(y));
    }

    public void setX(Double x) {
        this.x = x;
    }

    public void setY(Double y) {
        this.y = y;
    }

    public void setZ(Double z) {
        this.z = z;
    }

    public Double getFitness() {
        return fitness;
    }

    public void setFitness(Double fitness) {
        this.fitness = fitness;
    }

    public Double getX() {
        return x;
    }

    public Double getY() {
        return y;
    }

    public Double getZ() {
        return z;
    }

    @Override
    public int compareTo(Point p) {
            double temp = p.getZ() - getZ();

            if (temp > 0.0)
                return 1;
            else
                return -1;
    }

    @Override
    public String toString() {
        DecimalFormat dc = new DecimalFormat("#.######");
        String returnStr = "          X                 Y                  Z            FITNESS\n";
        String x = dc.format(getX());
        String y = dc.format(getY());
        String z = dc.format(getZ());
        String f = dc.format(getFitness());
        returnStr += "   " + String.format("%10s", x) + "   " + String.format("%10s", y) + "  "
                + String.format("%10s", z) + "  " + String.format("%10s", f) + "\n";
        return returnStr;
    }
}
