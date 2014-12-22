import java.io.*;
import java.util.*;

import static java.lang.Math.sqrt;

public class is23_tkachuk_07 {

    static class Point {

        public double x;
        public double y;
        public int id;

        public Point(double x, double y, int id) {
            this.x = x;
            this.y = y;
            this.id = id;
        }

        public double getX() {
            return x;
        }

        public void setX(double x) {
            this.x = x;
        }

        public double getY() {
            return y;
        }

        public void setY(double y) {
            this.y = y;
        }

        @Override
        public String toString() {
            return "{" +
                    "x=" + x +
                    ", y=" + y +
                    '}';
        }
    }

    static final class Points {

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
            List<Point> rez = new ArrayList<Point>();
            if (points.size() == 0) {
                rez.addAll(points);
                return rez;
            }
            Comparator<Point> p = (o1, o2) -> compare(o1, o2) ? -1 : 1;

            Collections.sort(points, p);

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
            List<Point> points = new ArrayList<Point>(pointsCopy);
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

    }


    public static void main(String[] args) throws IOException {
        String s = args[0];//"src/k/voyage/test/input_05.txt";
        File file = new File(s);
        Scanner in = new Scanner(new FileInputStream(file));

        List<Point> points = new ArrayList<Point>();
        int n = in.nextInt();
        for (int i = 0; i < n; i++) {
            points.add(new Point(in.nextDouble(), in.nextDouble(), i));
        }

        List<Point> p = Points.getPath(points);
        double length = 0;
        for (int i = 0; i < p.size() - 1; i++)
            length += Points.distance(p.get(i), p.get(i + 1));

        BufferedWriter out = new BufferedWriter(new FileWriter("is23_tkachuk_07_output.txt"));
        out.write(length + "\n");
        int idx;
        for (idx = 0; idx < p.size() - 1 && p.get(idx).id != 0; idx++) ;

        for (int i = idx; i < p.size() - 1; i++)
            out.write(p.get(i).id + " ");
        for (int i = 0; i < idx + 1; i++)
            out.write(p.get(i).id + " ");
        out.write("\n");
        out.close();

    }
}

