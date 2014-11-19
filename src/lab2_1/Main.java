package lab2_1;

import lab2_1.smo_types.FIFOType;
import lab2_1.smo_types.LIFOType;
import lab2_1.smo_types.PERType;
import lab2_1.smo_types.RRType;

import java.util.ArrayList;

/**
 * Created by Sergey on 02.11.2014.
 */
public class Main {

    public static void main(String[] args) {
        Times times = new Times(5000);

        LIFOType lifo = new LIFOType(times);
        PERType per = new PERType(times, 0.1);
        RRType rr = new RRType(times, 0.1);

        lifo.work();
        per.work();
        rr.work();

        ArrayList<MyTask> lpool = lifo.pool;
        ArrayList<MyTask> ppool = per.pool;
        ArrayList<MyTask> rpool = rr.pool;

/*
        for(int i = 0;; i++){
            System.out.println("-------");
            int k = 0;
            if (i < fpool.size()){
                k++;
                System.out.println("fifo "+fpool.get(i));
            }
            if (i < lpool.size()){
                k++;
                System.out.println("lifo "+lpool.get(i));
            }
            if (i < ppool.size()){
                k++;
                System.out.println("per "+ppool.get(i));
            }
            if (i < rpool.size()){
                k++;
                System.out.println("rr "+rpool.get(i));
            }
            if (k == 0) break;
        }
*/
        double[] k = {-1, -8, -1, 3, 5};

        System.out.println("LIFO: " + lifo.getFunc(k)+"\n");
        System.out.println("PER: " + per.getFunc(k)+"\n");
        System.out.println("RR: " + rr.getFunc(k)+"\n");
    }

}
