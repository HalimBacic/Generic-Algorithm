package point;

/**
 * This class is used in calculation minimum for f(x,y) function
 */
public class Point3D extends Point implements Comparable<Point3D>{

    public Point3D(Double x, Double y) {
        super(x,y);
    }

    public Point3D getPoint() {
        return this;
    }
    @Override
    public int compareTo(Point3D p) {
        double temp = p.getZ() - getZ();

        if (temp > 0.0)
            return 1;
        else
            return -1;
    }
}
