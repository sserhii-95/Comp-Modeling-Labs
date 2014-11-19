package lab4;

import lab3.Part;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Sergey
 */
public class PartModel {

    private Part part;      // reference on equal Part

    private String name;    // name

    private int flag;       // count of free processors
    private int queue;      // length of queue
    private int processor;  // count of problems in processor


    public PartModel(String name, int queue, int processor, int flag) {
        this.name = name;
        this.flag = flag;
        this.queue = queue;
        this.processor = processor;
        this.part = PartPool.getInstance().byName(name);
    }

    public PartModel(Part part) {
        this(part.name, part.queue, part.processor, part.flag);
    }

    public PartModel(String name) {
        this.name = name;
        this.part = PartPool.getInstance().byName(name);
        if (part == null)
            throw new NullPointerException("Part with name: " + name + " doest exists in PartPool");

        this.flag = part.flag;
        this.processor = part.processor;
        this.queue = part.queue;
    }


    public String toString() {
        return "<" + queue + ", " + processor + ", " + flag + ">";
    }

    public PartModel clone() {
        return new PartModel(name, queue, processor, flag);
    }

    public boolean equals(PartModel pm) {
        return name.equals(pm.name) && pm.processor == processor && pm.queue == queue;
    }

    public List<Part> getNext() {
        return new ArrayList<>(part.next);
    }


    public int getProcessorsCount() {
        return part.getProcessorsCount();
    }

    public String getName() {
        return name;
    }

    public String getNameLine(){
        String s = toString();
        String buf = name;
        if (buf.length() > s.length()) {
            buf = buf.substring(0, s.length()-3) + "...";
        }

        while(buf.length() <= s.length()) buf += " ";
        return buf;
    }

    public int getFlag() {
        return flag;
    }

    public int getQueue() {
        return queue;
    }

    public int getProcessor() {
        return processor;
    }

    public double getPosibility(int index) {
        return part.posibilities.get(index);
    }

    public double getLamda() {
        return 1 / part.midTime;
    }


    /**
     * Adds task to queue, if one of processor's cores is free,
     * then adds task to free core and returns true, else returns false
     */
    public boolean addTask() {
        queue++;
        if (flag > 0) {
            processor++;
            queue--;
            flag--;
            return true;
        } else
            return false;
    }

    /**
     * Resets one of the processor's cores, if it is busy
     */
    public void reset() {
        if (processor > 0) {
            processor--;
            flag++;
        }
    }

    public void addTaskToProcessor() {
        if (queue > 0 && flag > 0){
            queue--;
            processor++;
            flag--;
        }
    }
}
