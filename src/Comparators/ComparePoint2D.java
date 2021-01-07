package Comparators;

import point.Point2D;

import java.util.Comparator;

public class ComparePoint2D implements Comparator<Point2D> {

    @Override
    public int compare(Point2D o1, Point2D o2) {
        if(o1.getZ()<o2.getZ())
            return 1;
        else
            return -1;
    }
}
