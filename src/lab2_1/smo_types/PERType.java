package lab2_1.smo_types;

import lab2_1.MyTask;
import lab2_1.Times;

import java.util.ArrayList;
import java.util.LinkedList;

import static lab2_1.MyTask.*;
import static java.lang.Math.*;

/**
 * Created by Sergey on 04.10.2014.
 */
public class PERType extends Strategy {

    private double timeQvant;
    private ArrayList<LinkedList<MyTask>> queues;


    public PERType(Times times, double timeQvant){
        super(times);
        this.timeQvant = timeQvant;
        this.queues = new ArrayList<>();
        for(int i = 0; i < 1000; i++)
            queues.add(new LinkedList<MyTask>());
        time = 0;
    }

    @Override
    public void work() {

        int indx = 0;
        int queueIndex = 0;
        boolean emptyQueues = false;

        while(!emptyQueues) {
            emptyQueues = true;
            queueIndex = -1;

            for(int i = 0; i < queues.size(); i++)
                if (queues.get(i).size() > 0) {
                    emptyQueues = false;
                    queueIndex = i;
                }


            if (emptyQueues){
                MyTask task = times.get(indx);
                if (task != null){
                    queueIndex = 0;
                    queues.get(0).add(task);
                    time = task.time0;
                    emptyQueues = false;
                    indx++;
                }
            }


            if (!emptyQueues){

               MyTask task = queues.get(queueIndex).pollFirst();
               if (task.actual(time) > 0){
                   while(true){
                       MyTask task0 = times.get(indx);
                       if (task0 != null)
                           if (task0.time0 >= time && task0.time0 < min(min(time + timeQvant, task.time0 +task.solve), task.time0 + t1 + t2)) {
                               queues.get(0).add(task0);
                               indx++;
                               continue;
                           }
                       break;
                   }

                   task.solve(timeQvant);
                   if (queueIndex == 0) {
                       task.react(min(time, task.time0 + timeQvant));
                       //time = min(time, task.time0+timeQvant);
                   }
                   time+=timeQvant;
                   if (task.done()){
                       pool.add(task);
                       time = time - (task.solve1 - Math.min(task.solve, t1 + t2));
                       //time = min(min(time + timeQvant, task.time0 +task.solve), task.time0 + t1 + t2);
                       //time = min(min(time + timeQvant, task.time0 +task.solve), task.time0 + t1 + t2);
                       task.time1 = time;

                   } else {
                       queues.get(queueIndex+1).add(task);
                   }
               } else continue;
            }
        }
        calcStatistic();
    }

}
