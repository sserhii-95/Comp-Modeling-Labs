package lab4;

import lab3.Part;
import lab4.states.State;
import lab4.states.StatePool;

import java.util.Arrays;
import java.util.List;

/**
 * @author Siryy Sergiy
 */
public class Lab4 {

    public static void main(String[] args) {

        /*

        Part cpu = new Part("CP", 1);
        Part ram = new Part("RAM", 2.5);

        Part north = new Part("North Bridge", 2.3);
        Part south = new Part("South Bridge", 7.5);

        Part isa = new Part("ISA", 375);
        Part au = new Part("AY", 7.5);
        Part vp = new Part("AY", 7.5);
        Part lpt = new Part("LPT", 370);


        List<Part> partList = Arrays.asList(cpu, ram, north, south,
                isa, au, vp, lpt);


        cpu.addNext(cpu, 0.6);
        cpu.addNext(north, 0.4);

        north.addNext(cpu, 0.5);
        north.addNext(ram, 0.4);
        north.addNext(south, 0.1);

        ram.addNext(north, 1);

        south.addNext(north, 0.4);
        south.addNext(au, 0.2);
        south.addNext(isa, 0.05);
        south.addNext(vp, 0.35);

        au.addNext(cpu, 1);
        vp.addNext(cpu, 1);

        isa.addNext(south, 0.9);
        isa.addNext(lpt, 0.1);

        lpt.addNext(isa, 1);



        lpt.addNext(cpu, 1);

        int totalSolved = 0;

        for (Part part : partList) {
            PartPool.getInstance().addPart(part);

        }


        System.out.println("Lab3...");

        MySystem system = new MySystem(cpu, 3, 1000);
        system.run();
        */

/*
        for (Part part : partList) {
            System.out.println(part.stats());
            totalSolved+=part.solvedTasks;
        }

        System.out.println("Total: "+totalSolved);
*/

        Part p1 = new Part("a1", 1);
        Part p2 = new Part("a2", 3);
        p1.addNext(p2, 1);
        p2.addNext(p1, 1);

        p1.set(1, 1);
        p2.set(0, 1);


        List<Part> partList = Arrays.asList(p1, p2);

        for (Part part : partList) {
            PartPool.getInstance().addPart(part);

        }

        System.out.println("Lab4...");

        State state = State.create();
        StatePool.getInstance().checkState(state);

        StatePool.getInstance().createGraph();

        for (State st : StatePool.getInstance().states) {
            System.out.println(st);
        }

        StatePool.getInstance().solve();


    }
}
