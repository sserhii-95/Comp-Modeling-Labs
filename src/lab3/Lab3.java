package lab3;

import java.util.Arrays;
import java.util.List;

/**
 * @author Siryy Sergiy
 */
public class Lab3 {

    /* k = 100
CP:
	0:	 0.4559729060470314
	1:	 0.33234602058393203
RAM:
    0:  0.32268826040751264
	1:  0.09972979255829785
North Bridge:	 0.9976003781583227
South Bridge:	 0.4401590174814782
ISA:     0.0
AY: 	 0.19345032652418484
LPT:     0.0
COM:	 0.0
MD: 	 0.0
     */

    /* k = 3
CP:
	0:	 0.42868105046754906
	1:	 0.09864387703189197
RAM:
	0:	 0.27353398717888944
	1:	 0.03799160076231696
North Bridge: 0.8320432346035236
South Bridge: 0.29179175970286464
ISA: 0.0
AY:	 0.17393339689534357
LPT: 0.0
COM: 0.0
MD:	 0.0
     */

    public static void main(String[] args) {

        Part cpu = new Part("CP", 1, 2);
        Part ram = new Part("RAM", 1, 2);

        Part norht = new Part("North Bridge", 1);
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
        south.addNext(au, 0.4);
        south.addNext(isa, 0.05);
        south.addNext(md, 0.05);

        md.addNext(south, 1);

        au.addNext(cpu, 1);

        isa.addNext(south, 0.8);
        isa.addNext(com, 0.1);
        isa.addNext(lpt, 0.1);

        com.addNext(isa, 1);

        lpt.addNext(cpu, 1);


        MySystem system = new MySystem(cpu, 100, 7000);
        system.run();
        System.out.println("---//---");

        partList.forEach(System.out::println);

        System.out.println("---//---");

        int solve = 0;
        for (Part part : partList) {
            System.out.println(part.stats());
            solve += part.solvedTasks;
        }

        System.out.println("All: " + solve);
    }
}
