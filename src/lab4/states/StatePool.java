package lab4.states;

import org.la4j.LinearAlgebra;
import org.la4j.linear.LinearSystemSolver;
import org.la4j.matrix.dense.Basic2DMatrix;
import org.la4j.vector.Vector;
import org.la4j.vector.dense.BasicVector;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Sergey
 */
public class StatePool {

    private static StatePool ourInstance = new StatePool();

    public static StatePool getInstance() {
        return ourInstance;
    }

    private List<State> states;
    private List<List<Pair>> uv;

    private StatePool() {
        states = new ArrayList<>();
        uv = new ArrayList<>();
    }


    public boolean isUniq(State state) {
        return indexOf(state) >= 0;
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
            System.out.println(states.get(i));
            states.get(i).getNext();
        }

        for (int i = 0; i < uv.size(); i++) {
            System.out.print(i + ": ");
            for (int j = 0; j < uv.get(i).size(); j++)
                System.out.print(uv.get(i).get(j).to + "(" + uv.get(i).get(j).l + ") ");
            System.out.println();
        }
    }


    public void solve() {
        double[][] matrix = new double[uv.size() + 1][uv.size()];
        for (int i = 0; i < uv.size(); i++) {
            for (int j = 0; j < uv.get(i).size(); j++)
                {
                    matrix[i][i] -= uv.get(i).get(j).l;
                    matrix[uv.get(i).get(j).to][i] += uv.get(i).get(j).l;
                }
        }

        for (int i = 0; i < uv.size(); i++)
            matrix[matrix.length - 1][i] = 1;


        Basic2DMatrix b = new Basic2DMatrix(matrix);
        LinearSystemSolver solver = b.withSolver(LinearAlgebra.LEAST_SQUARES);

        Vector v = new BasicVector(new BasicVector(matrix.length));
        v.set(v.length() - 1, 1);

        v = solver.solve(v, LinearAlgebra.DENSE_FACTORY);
        System.out.println(v);

        double[][] p = new double[uv.size()][4];
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