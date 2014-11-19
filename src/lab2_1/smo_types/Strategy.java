package lab2_1.smo_types;



import lab2_1.MyTask;
import lab2_1.Times;

import java.util.ArrayList;

/**
 * Created by Sergey on 24.09.2014.
 */
public abstract class Strategy {

    public double[] params;

    protected double time;
    protected Times times;
    public ArrayList<MyTask> pool;


    public Strategy(Times times){
        this.pool = new ArrayList<>();
        this.params = new double[6];
        this.times = times;
        this.time = 0;

    }


    public abstract void work();

    public void calcStatistic(){
        double aveTimeSys = 0;
        for (MyTask task : pool) {
            aveTimeSys += task.timeInSystem();
        }
        aveTimeSys /= pool.size();

        double disTimeSys = 0;
        for (MyTask task : pool) disTimeSys += Math.pow(aveTimeSys - task.timeInSystem(), 2);

        disTimeSys /= pool.size();

        double aveSysReact = 0;
        for (MyTask task : pool) aveSysReact += task.getReact();
        aveSysReact /= pool.size();

        double ratioDoneUndone = 1f * pool.size() / times.size();

        double actualMark = 0;
        for (MyTask task : pool) actualMark += task.actual(task.time1);
        actualMark /= pool.size();

        double solve = 0;
        for(MyTask task: pool) solve+=task.solve1;
        solve/=pool.size();

        params[0] = aveTimeSys;
        params[1] = disTimeSys;
        params[2] = aveSysReact;
        params[3] = ratioDoneUndone;
        params[4] = actualMark;
        params[5] = solve;

    }

    public double getFunc(double... k){

        System.out.println("    Ave Time " + params[0]);
        System.out.println("    Dis Time " + params[1]);
        System.out.println("    Ave React Time " + params[2]);
        System.out.println("    Done / Undone " + pool.size());
        System.out.println("    Actual Mark " + params[4]);
        System.out.println("    Ave Solving Time " + params[5]);
        System.out.println("    Time "+time);

        return  k[0] * params[0]  +
                k[1] * params[1]  +
                k[2] * params[2]  +
                k[3] * params[3]  +
                k[4] * params[4];
    }
}
