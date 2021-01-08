package Comparators;


import point.Point3D;

import java.util.Comparator;

public class ComparePoint3D implements Comparator<Point3D> {
    @Override
    public int compare(Point3D o1, Point3D o2) {
        if(o1.getZ()>=o2.getZ())
            return 1;
        else
            return -1;
    }
}
