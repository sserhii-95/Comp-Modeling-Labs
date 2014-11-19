package lab2_1.smo_types;


import lab2_1.MyTask;
import lab2_1.Times;

import static lab2_1.MyTask.*;
import static java.lang.Math.*;


import java.util.LinkedList;

/**
 * Created by Sergey on 01.10.2014.
 */
public class LIFOType extends Strategy {

    private LinkedList<MyTask> queue = new LinkedList<MyTask>();


    public LIFOType(Times times){
        super(times);
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
                MyTask task = queue.pollLast();

                if (task != null && task.actual(time) > 0){

                while(true){
                        MyTask task0 = times.get(indx);

                        if (task0 != null)
                            if (task0.time0 >= time && task0.time0 < min(time + task.solve, task.time0 + t1 + t2)) {
                                queue.add(task0);
                                indx++;
                                continue;
                            }
                        break;
                    }

                    task.solve(task.solve);
                    task.react(time);
                    time = min(time + task.solve, task.time0 + t1 + t2);
                    task.time1 = time;
                    pool.add(task);
                } else continue;
            }
        }
        calcStatistic();
    }

}
