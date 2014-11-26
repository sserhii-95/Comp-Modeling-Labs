package lab3;

import java.util.ArrayList;

/**
 * @author Sergiy Siryy
 */
public class MySystem {

    private Part head;  // CPU

    private double time; // Час життя системи

    public static ArrayList<Pair> problems = new ArrayList<Pair>();

    public MySystem(Part head, int problems, double time){
        this.head = head;
        this.time = time;

        for (int i = 0; i < problems; i++) {
            head.addTask();
//            System.out.println(head.queue);
        }
    }

    /**
     * Метод, який моделює роботу системи
     */
    public void run() {

        int k = 100000000;
        for (int i = 0; i < head.getProcessorsCount(); i++) {
            problems.add(new Pair(head, head.addTaskToProcessor(0)));
        }


        for (int i = 0; i < k; i++) {

            Pair p = problems.get(0);

            for (int j = 1; j < problems.size(); j++)        // search min time problem
                if (problems.get(j).time < p.time)
                    p = problems.get(j);

            problems.remove(p);
            p.part.reset();

            if (p.time >= time) break;

            Part next = p.part.getNext();

            next.addTask();
            if (next != p.part) {
                if (next.canWork()) {
                    double newtime = next.addTaskToProcessor(p.time);
                    problems.add(new Pair(next, newtime));
                }
            }

            if (p.part.canWork()) {
                double newtime = p.part.addTaskToProcessor(p.time);
                problems.add(new Pair(p.part, newtime));
            }
        }
    }

}

class Pair{
    Part part;
    double time;

    public Pair(Part part, double time) {
        this.part = part;
        this.time = time;
    }

    public String toString(){
        return (int)(time * 1000) / 1000.0 + "  " + part;
    }
}

