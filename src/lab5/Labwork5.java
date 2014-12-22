package lab5;

import org.apache.commons.math3.optim.PointValuePair;
import org.apache.commons.math3.optim.linear.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

import static org.apache.commons.math3.optim.nonlinear.scalar.GoalType.MINIMIZE;

/**
 * @author Siryy Sergiy
 *         created in 29.11.2014
 */
public class Labwork5 {

    private final int pointsCount = 6; // Count of points

    private int left;   // left bound
    private int right;  // right bound
    private int[] x;    // positions of points

    // for brut
    private int minRez = Integer.MAX_VALUE; // min value of F() for points
    private int[] m;                        // point that have min value


    public Labwork5(int left, int right) {
        this.left = left;
        this.right = right;
        this.x = new int[pointsCount];
        this.m = new int[pointsCount];
        this.x[0] = left;
        this.x[pointsCount - 1] = right;
    }

    /**
     * Solves this problem using next algorithm:
     * <p>
     * All points with indexes from <code>i</code> to <code>pointsCount - 1</code>
     * moving to right on one point, and calc function for this points.
     * If result is less that current, saves this result,
     * else returns old positions, and increments <code>i</code>.
     * <p>
     * For all <code>i</code> from <code>1</code> to <code>pointsCount - 1</code>.
     */
    public void solve() {
        for (int i = 0; i < pointsCount - 1; i++) {
            x[i] = left + i;
        }
        int min = getF();

        for (int i = 1; i < pointsCount - 1; i++) {

            if (x[pointsCount - 2] + 1 == x[pointsCount - 1]) break;

            for (int j = i; j < pointsCount - 1; j++)
                x[j]++;

            int buf = getF();
            if (buf < min) {
                min = buf;
                i--;
            } else {
                for (int j = i; j < pointsCount - 1; j++)
                    x[j]--;
            }
        }

        System.out.println("ALGO1:\n f = " + min + ", for " + Arrays.toString(x));
    }

    public void solve1() {
        for (int i = 1; i < pointsCount - 1; i++) {
            x[i] = right - i - 1;
        }


        for (int q = left; q < right - pointsCount; q++) {

            for (int i = 1; i < pointsCount - 1; i++)
                x[i] = q + i;


            Arrays.sort(x);
            int min = getF();

            for (int i = 1; i < pointsCount - 1; i++) {

                if (x[i] - 1 == x[i - 1]) continue;

                x[i]--;

                int buf = getF();
                if (buf < min) {
                    min = buf;
                    i--;
                } else {
                    x[i]++;
                }
            }

            System.out.println("ALGO:\n f = " + min + ", for " + Arrays.toString(x));
        }
    }

    public void solve2() {
        for (int i = 1; i < pointsCount - 1; i++)
            x[i] = i;

        for (int r = 0; r < 3; r++)
            for (int i = pointsCount - 2; i > 0; i--) {
                int k = x[i];
                int rez = getF();
                for (int j = x[i - 1] + 1; j < x[i + 1]; j++) {
                    x[i] = j;
                    if (getF() < rez) {
                        rez = getF();
                        k = x[i];
                    }
                }

                x[i] = k;
            }

        System.out.println("ALGO2:\n f = " + getF() + ", for " + Arrays.toString(x));
    }


    public void brutSolve() {

        rec(1);
        System.out.println("BRUT:\n f = " + minRez + ", for " + Arrays.toString(m));
    }


    public int getF(int x) {
        switch (x) {
            case 1:
                return getF2();
            case 2:
                return getF3();
            case 3:
                return getF4();
            case 5:
                return getF5();
        }
        return 0;
    }

    public int getF2() {
        int sum = 0;

        sum += x[1] * x[1];       // f1
        sum += 2 * (x[2] - x[1]); //f2

        sum = 0;
        int i = 1;
        sum += (i % 3 + 1) * (x[i] - x[i - 1]) * (x[i] - x[i - 1]);
        i = 2;
        sum += (i % 3 + 1) * (x[i] - x[i - 1]) * (x[i] - x[i - 1]);

        return sum;
    }

    public int getF3() {
        int sum = 0;
        sum += 2 * (x[2] - x[1]); //f2
        sum += x[3] - x[2] < 2 ? (x[3] - x[2]) * (x[3] - x[2]) : 2 * x[3] - x[2]; //f3

        sum = 0;
        int i = 2;
        sum += (i % 3 + 1) * (x[i] - x[i - 1]) * (x[i] - x[i - 1]);
        i = 3;
        sum += (i % 3 + 1) * (x[i] - x[i - 1]) * (x[i] - x[i - 1]);

        return sum;
    }

    public int getF4() {
        int sum = 0;
        sum += x[3] - x[2] < 2 ? (x[3] - x[2]) * (x[3] - x[2]) : 2 * x[3] - x[2]; //f3
        sum += x[4] - x[3] < 3 ? x[3] : x[4]; // f4

        sum = 0;
        int i = 3;
        sum += (i % 3 + 1) * (x[i] - x[i - 1]) * (x[i] - x[i - 1]);
        i = 4;
        sum += (i % 3 + 1) * (x[i] - x[i - 1]) * (x[i] - x[i - 1]);

        return sum;
    }


    public int getF5() {
        int sum = 0;

        sum += x[4] - x[3] < 3 ? x[3] : x[4]; // f4
        sum += x[4] > 18 ? 2 : 5;

        sum = 0;
        int i = 4;
        sum += (i % 3 + 1) * (x[i] - x[i - 1]) * (x[i] - x[i - 1]);
        i = 5;
        sum += (i % 3 + 1) * (x[i] - x[i - 1]) * (x[i] - x[i - 1]);

        return sum;
    }


    private void rec(int h) {
        if (h == pointsCount - 1) {
            int j = getF();
            if (j < minRez) {
                minRez = j;
                System.arraycopy(x, 0, m, 0, x.length);
            }
            return;
        }

        for (int xi = x[h - 1] + 1; xi < right; xi++) {
            x[h] = xi;
            rec(h + 1);
        }

    }


    public double getF(double[] x) {
        int sum = 0;
        sum += x[1] * x[1];       // f1
        sum += 2 * (x[2] - x[1]); //f2
        sum += x[3] - x[2] < 2 ? (x[3] - x[2]) * (x[3] - x[2]) : 2 * x[3] - x[2]; //f3
        sum += x[4] - x[3] < 3 ? x[3] : x[4]; // f4
        sum += x[4] > 18 ? 2 : 5;
        return sum;
    }

    public int getF() {
        Arrays.sort(x);
        int sum = 0;
        sum += x[1] * x[1];       // f1
        sum += 2 * (x[2] - x[1]); //f2
        sum += x[3] - x[2] < 2 ? (x[3] - x[2]) * (x[3] - x[2]) : 2 * x[3] - x[2]; //f3
        sum += x[4] - x[3] < 3 ? x[3] : x[4]; // f4
        sum += x[4] > 18 ? 2 : 5;

        /*
        for (int i = 1; i < pointsCount; i++)
            sum += (i % 3 + 1) * (x[i] - x[i - 1]) * (x[i] - x[i - 1]);
            */
        return sum;
    }


    public void solveMath() {

        double buf = 100000;
        double[] ans = null;

        // 1) x1 + 2(x2 - x1) + (x3 - x2) + x3 + 2
        //    x1 > 0 x2 > x1 x3 > x2 x4 > x3 x4 < 20
        //    x3 - x2 < 2 x4 - x3 < 3 x4 > 18
        {
            LinearObjectiveFunction lof1 = new LinearObjectiveFunction(new double[]{-1, 1, 2, 0}, 2);
            Collection list = new ArrayList();
            list.add(new LinearConstraint(new double[]{1, 0, 0, 0}, Relationship.GEQ, 1));
            list.add(new LinearConstraint(new double[]{-1, 1, 0, 0}, Relationship.GEQ, 1));
            list.add(new LinearConstraint(new double[]{0, -1, 1, 0}, Relationship.GEQ, 1));
            list.add(new LinearConstraint(new double[]{0, -1, 1, 0}, Relationship.LEQ, 1));
            list.add(new LinearConstraint(new double[]{0, 0, -1, 1}, Relationship.GEQ, 1));
            list.add(new LinearConstraint(new double[]{0, 0, -1, 1}, Relationship.LEQ, 2));
            list.add(new LinearConstraint(new double[]{0, 0, 0, 1}, Relationship.EQ, 19));

            PointValuePair optimize = new SimplexSolver().optimize(lof1, new LinearConstraintSet(list), MINIMIZE);


            double[] x = new double[6];
            x[1] = optimize.getPoint()[0];
            x[2] = optimize.getPoint()[1];
            x[3] = optimize.getPoint()[2];
            x[4] = optimize.getPoint()[3];
            x[0] = 0;
            x[5] = 20;
            if (getF(x) < buf) {
                ans = x;
                buf = getF(x);
            }
            System.out.println(getF(x) + ":" + Arrays.toString(x));
        }

        // 2) x1 + 2(x2 - x1) + (x3 - x2) + x3 + 5
        //    x1 > 0 x2 > x1 x3 > x2 x4 > x3 x4 < 20
        //    x3 - x2 < 2 x4 - x3 < 3 x4 <= 18

        {
            LinearObjectiveFunction lof1 = new LinearObjectiveFunction(new double[]{-1, 1, 2, 0}, 5);
            Collection list = new ArrayList();
            list.add(new LinearConstraint(new double[]{1, 0, 0, 0}, Relationship.GEQ, 1));
            list.add(new LinearConstraint(new double[]{-1, 1, 0, 0}, Relationship.GEQ, 1));
            list.add(new LinearConstraint(new double[]{0, -1, 1, 0}, Relationship.GEQ, 1));
            list.add(new LinearConstraint(new double[]{0, -1, 1, 0}, Relationship.LEQ, 1));
            list.add(new LinearConstraint(new double[]{0, 0, -1, 1}, Relationship.GEQ, 1));
            list.add(new LinearConstraint(new double[]{0, 0, -1, 1}, Relationship.LEQ, 2));
            list.add(new LinearConstraint(new double[]{0, 0, 0, 1}, Relationship.LEQ, 18));

            PointValuePair optimize = new SimplexSolver().optimize(lof1, new LinearConstraintSet(list), MINIMIZE);


            double[] x = new double[6];
            x[1] = optimize.getPoint()[0];
            x[2] = optimize.getPoint()[1];
            x[3] = optimize.getPoint()[2];
            x[4] = optimize.getPoint()[3];
            x[0] = 0;
            x[5] = 20;
            if (getF(x) < buf) {
                ans = x;
                buf = getF(x);
            }
            System.out.println(getF(x) + ":" + Arrays.toString(x));
        }

        // 3) x1 + 2(x2 - x1) + (x3 - x2) + x4 + 2
        //    x1 > 0 x2 > x1 x3 > x2 x4 > x3 x4 < 20
        //    x3 - x2 < 2 x4 - x3 >= 3 x4 > 18
        {
            LinearObjectiveFunction lof1 = new LinearObjectiveFunction(new double[]{-1, 1, 1, 1}, 2);
            Collection list = new ArrayList();
            list.add(new LinearConstraint(new double[]{1, 0, 0, 0}, Relationship.GEQ, 1));
            list.add(new LinearConstraint(new double[]{-1, 1, 0, 0}, Relationship.GEQ, 1));
            list.add(new LinearConstraint(new double[]{0, -1, 1, 0}, Relationship.GEQ, 1));
            list.add(new LinearConstraint(new double[]{0, -1, 1, 0}, Relationship.LEQ, 1));
            list.add(new LinearConstraint(new double[]{0, 0, -1, 1}, Relationship.GEQ, 3));
            list.add(new LinearConstraint(new double[]{0, 0, 0, 1}, Relationship.EQ, 19));

            PointValuePair optimize = new SimplexSolver().optimize(lof1, new LinearConstraintSet(list), MINIMIZE);


            double[] x = new double[6];

            x[1] = optimize.getPoint()[0];
            x[2] = optimize.getPoint()[1];
            x[3] = optimize.getPoint()[2];
            x[4] = optimize.getPoint()[3];
            x[0] = 0;
            x[5] = 20;

            if (getF(x) < buf) {
                ans = x;
                buf = getF(x);
            }

            System.out.println(getF(x) + ":" + Arrays.toString(x));
        }

        // 4) x1 + 2(x2 - x1) + (x3 - x2) + x4 + 5
        //    x1 > 0 x2 > x1 x3 > x2 x4 > x3 x4 < 20
        //    x3 - x2 < 2 x4 - x3 >= 3 x4 <= 18
        {
            LinearObjectiveFunction lof1 = new LinearObjectiveFunction(new double[]{-1, 1, 1, 1}, 5);
            Collection list = new ArrayList();
            list.add(new LinearConstraint(new double[]{1, 0, 0, 0}, Relationship.GEQ, 1));
            list.add(new LinearConstraint(new double[]{-1, 1, 0, 0}, Relationship.GEQ, 1));
            list.add(new LinearConstraint(new double[]{0, -1, 1, 0}, Relationship.GEQ, 1));
            list.add(new LinearConstraint(new double[]{0, -1, 1, 0}, Relationship.LEQ, 1));
            list.add(new LinearConstraint(new double[]{0, 0, -1, 1}, Relationship.GEQ, 3));
            list.add(new LinearConstraint(new double[]{0, 0, 0, 1}, Relationship.LEQ, 18));

            PointValuePair optimize = new SimplexSolver().optimize(lof1, new LinearConstraintSet(list), MINIMIZE);


            double[] x = new double[6];
            x[1] = optimize.getPoint()[0];
            x[2] = optimize.getPoint()[1];
            x[3] = optimize.getPoint()[2];
            x[4] = optimize.getPoint()[3];
            x[0] = 0;
            x[5] = 20;
            if (getF(x) < buf) {
                ans = x;
                buf = getF(x);
            }

            System.out.println(getF(x) + ":" + Arrays.toString(x));
        }

        // 5) x1 + 2(x2 - x1) + (2x3 - x2) + x3 + 2
        //    x1 > 0 x2 > x1 x3 > x2 x4 > x3 x4 < 20
        //    x3 - x2 >= 2 x4 - x3 < 3 x4 > 18

        {
            LinearObjectiveFunction lof1 = new LinearObjectiveFunction(new double[]{-1, 1, 2, 0}, 2);
            Collection list = new ArrayList();
            list.add(new LinearConstraint(new double[]{1, 0, 0, 0}, Relationship.GEQ, 1));
            list.add(new LinearConstraint(new double[]{-1, 1, 0, 0}, Relationship.GEQ, 1));
            list.add(new LinearConstraint(new double[]{0, -1, 1, 0}, Relationship.GEQ, 2));
            list.add(new LinearConstraint(new double[]{0, 0, -1, 1}, Relationship.GEQ, 1));
            list.add(new LinearConstraint(new double[]{0, 0, -1, 1}, Relationship.LEQ, 2));
            list.add(new LinearConstraint(new double[]{0, 0, 0, 1}, Relationship.EQ, 19));

            PointValuePair optimize = new SimplexSolver().optimize(lof1, new LinearConstraintSet(list), MINIMIZE);

            double[] x = new double[6];
            x[1] = optimize.getPoint()[0];
            x[2] = optimize.getPoint()[1];
            x[3] = optimize.getPoint()[2];
            x[4] = optimize.getPoint()[3];
            x[0] = 0;
            x[5] = 20;
            if (getF(x) < buf) {
                ans = x;
                buf = getF(x);
            }
            System.out.println(getF(x) + ":" + Arrays.toString(x));
        }

        // 6) x1 + 2(x2 - x1) + (2x3 - x2) + x3 + 5
        //    x1 > 0 x2 > x1 x3 > x2 x4 > x3 x4 < 20
        //    x3 - x2 >= 2 x4 - x3 < 3 x4 <= 18
        {
            LinearObjectiveFunction lof1 = new LinearObjectiveFunction(new double[]{-1, 1, 2, 0}, 5);
            Collection list = new ArrayList();
            list.add(new LinearConstraint(new double[]{1, 0, 0, 0}, Relationship.GEQ, 1));
            list.add(new LinearConstraint(new double[]{-1, 1, 0, 0}, Relationship.GEQ, 1));
            list.add(new LinearConstraint(new double[]{0, -1, 1, 0}, Relationship.GEQ, 2));
            list.add(new LinearConstraint(new double[]{0, 0, -1, 1}, Relationship.GEQ, 1));
            list.add(new LinearConstraint(new double[]{0, 0, -1, 1}, Relationship.LEQ, 2));
            list.add(new LinearConstraint(new double[]{0, 0, 0, 1}, Relationship.LEQ, 18));

            PointValuePair optimize = new SimplexSolver().optimize(lof1, new LinearConstraintSet(list), MINIMIZE);


            double[] x = new double[6];
            x[1] = optimize.getPoint()[0];
            x[2] = optimize.getPoint()[1];
            x[3] = optimize.getPoint()[2];
            x[4] = optimize.getPoint()[3];
            x[0] = 0;
            x[5] = 20;
            if (getF(x) < buf) {
                ans = x;
                buf = getF(x);
            }
            System.out.println(getF(x) + ":" + Arrays.toString(x));
        }

        // 7) x1 + 2(x2 - x1) + (2x3 - x2) + x4 + 2
        //    x1 > 0 x2 > x1 x3 > x2 x4 > x3 x4 < 20
        //    x3 - x2 >= 2 x4 - x3 >= 3 x4 > 18

        {
            LinearObjectiveFunction lof1 = new LinearObjectiveFunction(new double[]{-1, 1, 1, 1}, 2);
            Collection list = new ArrayList();
            list.add(new LinearConstraint(new double[]{1, 0, 0, 0}, Relationship.GEQ, 1));
            list.add(new LinearConstraint(new double[]{-1, 1, 0, 0}, Relationship.GEQ, 1));
            list.add(new LinearConstraint(new double[]{0, -1, 1, 0}, Relationship.GEQ, 2));
            list.add(new LinearConstraint(new double[]{0, 0, -1, 1}, Relationship.GEQ, 1));
            list.add(new LinearConstraint(new double[]{0, 0, -1, 1}, Relationship.GEQ, 3));
            list.add(new LinearConstraint(new double[]{0, 0, 0, 1}, Relationship.EQ, 19));

            PointValuePair optimize = new SimplexSolver().optimize(lof1, new LinearConstraintSet(list), MINIMIZE);


            double[] x = new double[6];
            x[1] = optimize.getPoint()[0];
            x[2] = optimize.getPoint()[1];
            x[3] = optimize.getPoint()[2];
            x[4] = optimize.getPoint()[3];
            x[0] = 0;
            x[5] = 20;
            if (getF(x) < buf) {
                ans = x;
                buf = getF(x);
            }
            System.out.println(getF(x) + ":" + Arrays.toString(x));
        }


        // 8) x1 + 2(x2 - x1) + (2x3 - x2) + x4 + 5
        //    x1 > 0 x2 > x1 x3 > x2 x4 > x3 x4 < 20
        //    x3 - x2 >= 2 x4 - x3 >= 3 x4 <= 18
        {
            LinearObjectiveFunction lof1 = new LinearObjectiveFunction(new double[]{-1, 1, 1, 1}, 5);
            Collection list = new ArrayList();
            list.add(new LinearConstraint(new double[]{1, 0, 0, 0}, Relationship.GEQ, 1));
            list.add(new LinearConstraint(new double[]{-1, 1, 0, 0}, Relationship.GEQ, 1));
            list.add(new LinearConstraint(new double[]{0, -1, 1, 0}, Relationship.GEQ, 1));
            list.add(new LinearConstraint(new double[]{0, -1, 1, 0}, Relationship.LEQ, 1));
            list.add(new LinearConstraint(new double[]{0, 0, -1, 1}, Relationship.GEQ, 3));
            list.add(new LinearConstraint(new double[]{0, 0, 0, 1}, Relationship.LEQ, 18));

            PointValuePair optimize = new SimplexSolver().optimize(lof1, new LinearConstraintSet(list), MINIMIZE);


            double[] x = new double[6];
            x[1] = optimize.getPoint()[0];
            x[2] = optimize.getPoint()[1];
            x[3] = optimize.getPoint()[2];
            x[4] = optimize.getPoint()[3];
            x[0] = 0;
            x[5] = 20;
            if (getF(x) < buf) {
                ans = x;
                buf = getF(x);
            }
            System.out.println(getF(x) + ":" + Arrays.toString(x));
        }

        System.out.println("f = " + getF(ans) + " " + Arrays.toString(ans));
    }

    public static void main(String[] args) {
        Labwork5 lab5 = new Labwork5(0, 20);
        lab5.solve2();
        lab5.brutSolve();
        lab5.solveMath();
        //   lab5.brutSolve();
    }
}
