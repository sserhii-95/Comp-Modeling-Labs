package lab4;

import lab3.MySystem;
import lab3.Part;
import lab4.states.State;
import lab4.states.StatePool;


import java.util.Arrays;
import java.util.List;

/**
 * @author Sergey
 */
public class Lab4 {

    public static void main(String[] args) {

        Part cpu = new Part("CP", 1,2);
        Part ram = new Part("RAM", 2.5,2);

        Part norht = new Part("North Bridge", 2.3);
        Part south = new Part("South Bridge", 7.5);

        Part isa = new Part("ISA", 375);
        Part au = new Part("AY", 7.5);
        Part lpt = new Part("LPT", 375);
        Part com = new Part("COM", 375);
        Part md = new Part("MD", 375);

        List<Part> partList = Arrays.asList(cpu, ram, norht, south,
                isa, au, lpt, com, md);

        cpu.addNext(cpu, 0.6);
        cpu.addNext(norht, 0.4);

        norht.addNext(cpu, 0.5);
        norht.addNext(ram, 0.4);
        norht.addNext(south, 0.1);

        ram.addNext(norht, 1);

        south.addNext(norht, 0.5);
        south.addNext(au, 0.49);
        south.addNext(isa, 0.05);
        south.addNext(md, 0.05);

        md.addNext(south, 1);

        au.addNext(cpu, 1);

        isa.addNext(south, 0.8);
        isa.addNext(com, 0.1);
        isa.addNext(lpt, 0.1);

        com.addNext(isa, 1);

        lpt.addNext(cpu, 1);

        MySystem system = new MySystem(cpu, 3, 0);
        system.run();


        for (Part part : partList)
            PartPool.getInstance().addPart(part);


        State state = State.create();
        StatePool.getInstance().checkState(state);

        StatePool.getInstance().createGraph();
        StatePool.getInstance().solve();

    }
}
