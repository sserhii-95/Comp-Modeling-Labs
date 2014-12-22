package k.voyage;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Siryy Sergiy
 *         created in 29.11.2014
 */
public class Test {

    static Window window;
    static Points points;

    static List<Point> getPath() {
        List<Point> p = Points.getPath(points.points);
        double length = 0;
        for (int i = 0; i < p.size() - 1; i++)
            length += Points.distance(p.get(i), p.get(i + 1));
        System.out.println(length);
        return p;
    }

    public static void main(String[] args) throws FileNotFoundException, InterruptedException {
        //String s = "src/k/voyage/test/input_05.txt";
        //File file = new File(s);
        //Scanner in = new Scanner(new FileInputStream(file));


        List<Point> points = new ArrayList<>();
//
//        int n = in.nextInt();
//        for (int i = 0; i < n; i++) {
//            points.add(new Point(in.nextDouble(), in.nextDouble()));
//        }

        int n = 500;
        for (int i = 0; i < n; i++)
            points.add(new Point(Math.random() * 3000 * Math.abs(Math.sin(3.14 * i / n / 2)), Math.random() * 3000 * Math.abs(Math.cos(3.14 * i / n))));

        Test.points = new Points(points);
    }
}
