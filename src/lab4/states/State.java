package lab4.states;

import lab3.Part;
import lab4.PartModel;
import lab4.PartPool;

import java.util.ArrayList;
import java.util.List;

/**
 * @author
 */
public class State {

    public static State create() {
        PartPool pool = PartPool.getInstance();
        State state = new State();
        for (Part part : pool.getParts()) {
            PartModel partModel = new PartModel(part);
            state.parts.add(partModel);
        }
        return state;
    }

    public List<PartModel> parts;

    private int parent;

    public State() {
        this.parts = new ArrayList<>();
        parent = -1;
    }

    public void getNext() {

        for (int i = 0; i < parts.size(); i++) {
            PartModel part = parts.get(i);

            if (part.getProcessor() > 0) {

                List<Part> partList = part.getNext();

                for (int j = 0; j < partList.size(); j++) {

                    Part toPart = partList.get(j);
                    State next = clone();


                    next.parts.get(i).reset();
                    next.parts.get(i).addTaskToProcessor();

                    next.getByName(toPart.name).addTask();

                    StatePool.getInstance().checkState(next);
                    StatePool.getInstance().addDepend(this, next, part.getLamda() * part.getPosibility(j));
                }
            }
        }
    }


    public PartModel getByName(String name) {
        for (PartModel partModel : parts)
            if (partModel.getName().equals(name)) return partModel;
        return null;
    }

    public int getParent() {
        return parent;
    }

    public String toString() {
        StringBuffer s = new StringBuffer();
        for (PartModel model : parts) {
            s.append(model.getNameLine());
        }
        s.append("\n");
        for (PartModel model : parts) {
            s.append(model.toString() + " ");
        }
        s.append(parent+" "+StatePool.getInstance().indexOf(this));
        return s.toString();
    }

    public State clone() {
        State state = new State();
        state.parent = StatePool.getInstance().indexOf(this);
        for (int i = 0; i < parts.size(); i++)
            state.parts.add(parts.get(i).clone());
        return state;
    }


    public boolean equals(State state) {
        boolean res = true;

        for (int i = 0; res && i < parts.size(); i++) {
            res = parts.get(i).equals(state.parts.get(i));
        }

        return res;
    }
}
