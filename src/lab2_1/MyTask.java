package lab2_1;


/**
 *
 * @author Siryy Sergiy
 * created in 26.11.2014
 */
public class MyTask {

    public static double t1 = 3, t2 = 3;

    public double time0; // time of adding task to queue
    public double time1; // time of poping task from queue


    public double solve; // time of slowing

    public double solve1;

    private double react; // time of react

    public int fl;

    public MyTask(double time0, double solve, int fl){
        this(time0, solve);
        this.fl = fl;
    }

    public MyTask(double time0, double solve){
       this.time0 = time0;
       this.solve = solve;
       this.react = -1;
       this.solve1 = 0;
    }

    public void solve(double delta){
        solve1 += delta;
    }

    public double actual(double time){
        double t = time - time0;
        if (t < t1) return 1;
            else
        if (t < t1 + t2) return 1 - (t - t1) / t2;
            else
        return 0;
    }

    public boolean done(){
        return solve1 >= solve;
    }

    public void react(double time){
        if (react  < 0) {
            react = Math.abs(time - time0);
            if (time < time0) {
                 react /= 100;
            }
        }
    }


    public double timeInSystem(){
        return time1 - time0;
    }

    public double getReact(){
        return react;
    }

    public String toString(){
        return format(time0) + " - " + format(time1) + "| " + format(solve) + " " + fl;
    }

    private String format(double d){
        return String.format("%.4g", d);
    }
}
