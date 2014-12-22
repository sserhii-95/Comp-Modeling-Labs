package k.voyage;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import static java.lang.Math.sqrt;

/**
 * @author Siryy Sergiy
 *         created in 29.11.2014
 */
public final class Points {

    private static class PointArrayList extends ArrayList<Point> {

        public PointArrayList(Point first) {
            super();
            add(first);
        }

        public Point getB(int i) {
            return get(size() - i);
        }

        public Point popBack() {
            Point p = getB(1);
            remove(size() - 1);
            return p;
        }

    }

    private static double sqr(double x) {
        return x * x;
    }

    public static double distance(Point p1, Point p2) {
        return sqrt(sqr(p1.x - p2.x) + sqr(p1.y - p2.y));
    }

    public static boolean compare(Point p1, Point p2) {
        return (p1.x < p2.x || (p1.x == p2.x && p1.y < p2.y));
    }

    public static boolean cw(Point a, Point b, Point c) {
        return a.x * (b.y - c.y) + b.x * (c.y - a.y) + c.x * (a.y - b.y) < 0;
    }

    public static boolean ccw(Point a, Point b, Point c) {
        return a.x * (b.y - c.y) + b.x * (c.y - a.y) + c.x * (a.y - b.y) > 0;
    }


    public static List<Point> getConvexHull(List<Point> points) {
        List<Point> rez = new ArrayList<>();
        if (points.size() == 0) {
            rez.addAll(points);
            return rez;
        }
        points.sort(new Comparator<Point>() {
            @Override
            public int compare(Point o1, Point o2) {
                return Points.compare(o1, o2) ? -1 : 1;
            }
        });

        Point first = points.get(0);
        Point last = points.get(points.size() - 1);

        PointArrayList up = new PointArrayList(first),
                down = new PointArrayList(first);

        for (int i = 1; i < points.size(); i++) {
            if (i == points.size() - 1 || cw(first, points.get(i), last)) {
                while (up.size() >= 2 && !cw(up.getB(2), up.getB(1), points.get(i))) {
                    up.popBack();
                }
                up.add(points.get(i));
            }
            if (i == points.size() - 1 || ccw(first, points.get(i), last)) {
                while (down.size() >= 2 && !ccw(down.getB(2), down.getB(1), points.get(i))) {
                    down.popBack();
                }
                down.add(points.get(i));
            }
        }

        for (int i = 0; i < up.size(); i++)
            rez.add(up.get(i));
        for (int i = down.size() - 2; i > 0; i--)
            rez.add(down.get(i));
        return rez;
    }

    public static List<Point> getPath(List<Point> pointsCopy) {
        List<Point> points = new ArrayList<>(pointsCopy);
        List<Point> hull = getConvexHull(points);
        hull.add(hull.get(0));
        points.removeAll(hull);

        while (points.size() > 0) {

            double min = Double.MAX_VALUE;
            int minIndex = -1;
            int minIndexHull = -1;

            for (int i = 0; i < points.size(); i++) {
                for (int j = 1; j < hull.size(); j++) {
                    double dist = distance(points.get(i), hull.get(j - 1)) +
                            distance(points.get(i), hull.get(j)) -
                            distance(hull.get(j - 1), hull.get(j));
                    if (dist < min) {
                        min = dist;
                        minIndex = i;
                        minIndexHull = j;
                    }
                }
            }

            hull.add(minIndexHull, points.get(minIndex));
            points.remove(minIndex);
        }

        return hull;
    }


    public List<Point> points;
    private List<Point> hull;

    public Points(List<Point> points) {
        this.points = points;
    }

    public List<Point> getNext() {
        if (hull == null) {
            hull = getConvexHull(points);
            hull.add(hull.get(0));
        } else {
            double min = Double.MAX_VALUE;
            int minIndex = -1;
            int minIndexHull = -1;

            for (int i = 0; i < points.size(); i++) {
                for (int j = 1; j < hull.size(); j++) {
                    double dist = distance(points.get(i), hull.get(j - 1)) +
                            distance(points.get(i), hull.get(j)) -
                            distance(hull.get(j - 1), hull.get(j));
                    if (dist < min) {
                        min = dist;
                        minIndex = i;
                        minIndexHull = j;
                    }
                }
            }

            if (minIndex > -1) {
                hull.add(minIndexHull, points.get(minIndex));
                points.remove(minIndex);
            }
        }
        return hull;
    }
}
