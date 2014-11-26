package lab2_1;


import lab2_1.smo_types.LIFOType;
import lab2_1.smo_types.PERType;
import lab2_1.smo_types.RRType;


/**
 * Created by Siryy Sergiy
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

        double[] k = {-1, -8, -1, 3, 5};

        System.out.println("LIFO: " + lifo.getFunc(k)+"\n");
        System.out.println("PER: " + per.getFunc(k)+"\n");
        System.out.println("RR: " + rr.getFunc(k)+"\n");
    }

}
