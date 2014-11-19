package lab4;

import lab3.Part;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * @author Siryy Sergiy
 */
public class PartPool {

    /**
     * Returns instance of singleton
     */
    public static PartPool getInstance() {
        return partPool;
    }

    /**
     * Instance of singleton
     */
    private static PartPool partPool = new PartPool();

    /**
     * HashMap of parts
     */
    private HashMap<String, Part> parts;


    private PartPool() {
        parts = new HashMap<>();
    }

    /**
     * Adds part to parts.
     */
    public void addPart(Part part) {
        parts.put(part.name, part);
    }

    /**
     * Returns parts with name from parameters
     */
    public Part byName(String name) {
        return parts.get(name);
    }

    public List<Part> getParts() {
        List<Part> parts = new ArrayList<>();
        for (String s : this.parts.keySet())
            parts.add(byName(s));
        return parts;
    }

}
