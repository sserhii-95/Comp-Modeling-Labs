package lab4.states;

import org.la4j.LinearAlgebra;
import org.la4j.linear.LinearSystemSolver;
import org.la4j.matrix.dense.Basic2DMatrix;
import org.la4j.vector.Vector;
import org.la4j.vector.dense.BasicVector;

import java.util.ArrayList;
import java.util.List;

import static java.lang.Math.exp;

/**
 * @author Siryy Sergiy
 */
public class StatePool {

    private static StatePool ourInstance = new StatePool();

    public static StatePool getInstance() {
        return ourInstance;
    }

    public List<State> states;
    private List<List<Pair>> uv;
    private final int type; // 0 if linear, 1 if discrete

    private StatePool() {
        states = new ArrayList<>();
        uv = new ArrayList<>();
        type = 0;
    }

    public int indexOf(State state) {
        for (int i = 0; i < states.size(); i++) {
            if (states.get(i).equals(state)) return i;
        }
        return -1;
    }


    public State checkState(State state) {
        int i = indexOf(state);
        if (i >= 0) return states.get(i);

        states.add(state);
        uv.add(new ArrayList<>());
        return state;
    }

    public void addDepend(State from, State to, double l) {
        int i = indexOf(from);
        int j = indexOf(to);
        uv.get(i).add(new Pair(j, l));
    }


    public void createGraph() {
        for (int i = 0; i < states.size(); i++) {
            states.get(i).getNext();
        }
    }


    public void solve() {
        double[][] matrix = new double[uv.size() + 1][uv.size()];
        for (int i = 0; i < uv.size(); i++) {
            for (int j = 0; j < uv.get(i).size(); j++) {
                matrix[i][i] -= uv.get(i).get(j).l;
                matrix[uv.get(i).get(j).to][i] += uv.get(i).get(j).l;
            }
        }

        for (int i = 0; i < uv.size(); i++)
            matrix[matrix.length - 1][i] = 1;



        double delta = 1e-3;

        double[][] p = new double[uv.size()][4];


        if (type == 1) {

            double[][] mat = new double[matrix.length - 1][matrix[0].length];
            for (int i = 0; i < mat.length; i++) {
                for (int j = 0; j < mat[0].length; j++)
                    if (matrix[i][j] != 0) {
                        mat[i][j] = 1 - exp(-delta * matrix[i][j]);
                    }
            }

            for (int i = 0; i < mat.length; i++) {
                matrix[i][i] = 0;
                for (int j = 0; j < mat[i].length; j++)
                    matrix[i][i] -= mat[i][j];
                for (int j = 0; j < mat[i].length; j++)
                    if (mat[i][j] != 0)
                        matrix[i][j] = mat[i][j];
            }

        }


        Basic2DMatrix b = new Basic2DMatrix(matrix);
        LinearSystemSolver solver = b.withSolver(LinearAlgebra.SOLVER);

        Vector v1 = new BasicVector(new BasicVector(matrix.length));
        v1.set(v1.length() - 1, 1);

        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[i].length; j++) {
                System.out.print("p" + (j + 1) + "*" + (int) (matrix[i][j] * 1000) / 1000.0 + (j < matrix[i].length - 1 ? " + " : " = "));
            }
            System.out.println(v1.get(i));
        }


        Vector v = solver.solve(v1, LinearAlgebra.DENSE_FACTORY);

        for (int i = 0; i < v.length(); i++)
            System.out.println("p[" + i + "] = " + v.get(i));

///        System.out.println(v1);

        for (int i = 0; i < states.size(); i++) {
            State state = states.get(i);
            for (int j = 0; j < state.parts.size(); j++)
                p[j][state.parts.get(j).getProcessor()] += v.get(i);
        }

        for (int i = 0; i < states.get(0).parts.size(); i++) {
            System.out.print(states.get(0).parts.get(i).getName() + ": ");
            for (int j = 1; j <= states.get(0).parts.get(i).getProcessorsCount(); j++)
                System.out.print("[" + j + "] = " + p[i][j] + ",");
            System.out.println();
        }

    }


}


class Pair {
    int to;
    double l;

    public Pair(int to, double l) {
        this.to = to;
        this.l = l;
    }
}