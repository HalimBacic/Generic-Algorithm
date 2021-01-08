package point;

/**
 *    This class is used for calculation minimum of f(y) function.
 */
public class Point2D extends Point implements Comparable<Point2D>{

    public Point2D()
    {};

    public Point2D(Double y)
    {
        super(y);
    }

    @Override
    public int compareTo(Point2D p) {
        double temp = p.getZ() - getZ();

        if (temp < 0.0)
            return 1;
        else
            return -1;
    }

}
