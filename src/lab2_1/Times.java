package lab2_1;

import static java.lang.Math.*;

/**
 * Created by Sergey on 30.10.2014.
 */
public class Times {


    public double l; // lamda
    public double m; // mu;
    public double d = 0.5; //
    private int n;

    private double times[];
    private double solves[];

    public Times(int n){
        this.n = n;
        this.l = 10;
        this.m = 5;
        // double r = l / m;
        // double t = 1 / (2 * m * (1 - r)) * (2 - r*(1-m*m/l/l));
        // System.err.println(t);
        times = new double[n];
        solves = new double[n];
        generateSolves();
        generateTimes();
    }


    public int size(){
        return times.length;
    }

    public MyTask get(int i){
        if (i < 0 || i >= n) return null;
        MyTask myTask = new MyTask(times[i], solves[i], i);
        return myTask;
    }

    private void generateTimes(){
        for(int i = 0; i < n; i++){
            times[i] = ((int)(- 1 / l * log(random()) * 1000) / 1000.0);
            if (i > 0) times[i] += times[i-1];
        }
    }


    private void generateSolves(){
        for(int i = 0; i < n; i++){
            solves[i] = 1 / m +   random() * (sqrt(d)) / 2 * (random() > 0.5 ? 1: -1);
        }
    }


}
