package lab2_1.smo_types;

import com.sun.org.apache.xpath.internal.SourceTree;
import lab2_1.MyTask;
import lab2_1.Times;

import java.util.LinkedList;

import static lab2_1.MyTask.*;
import static java.lang.Math.*;

/**
 * Created by Sergey on 01.10.2014.
 */
public class RRType extends Strategy {

    private double timeQvant;
    private LinkedList<MyTask> queue = new LinkedList<MyTask>();


    public RRType(Times times, double timeQvant){
        super(times);
        this.timeQvant = timeQvant;
    }

    @Override
    public void work() {
        int indx = 0;
        boolean isEmpty = false;

        while(!isEmpty){
            isEmpty = queue.size() == 0;

            if (isEmpty){
                MyTask task = times.get(indx);
                if (task != null){
                    queue.add(task);
                    time = task.time0;
                    isEmpty = false;
                    indx++;
                }
            }


            if (!isEmpty){
                MyTask task = queue.pollFirst();
                if (task != null && task.actual(time) > 0) {

                while(true){
                        MyTask task0 = times.get(indx);
                        if (task0 != null) {
                            if (task0.time0 >= time && task0.time0 < min(min(time + timeQvant, task.time0+task.solve), task.time0 + t1 + t2)) {
                                queue.add(task0);
                                indx++;
                                continue;
                            }
                        }
                        break;
                    }

                    task.solve(timeQvant);
                    task.react(time);
                    time += timeQvant;
                    if (task.done()){
                        pool.add(task);
                        //time = time - task.solve1 + task.solve;
                        //time = time - (task.solve1 - Math.min(task.solve, t1 + t2));
                       //time = min(min(time + timeQvant, task.time0 +task.solve),1000000);
                        task.time1 = time;
                    } else
                        queue.add(task);
                }
            }
        }
        calcStatistic();
    }
}
