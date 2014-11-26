package lab4;

import lab3.Part;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.stream.Collectors;

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
    private LinkedHashMap<String, Part> parts;


    private PartPool() {
        parts = new LinkedHashMap<>();
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
        return this.parts.values().stream().collect(Collectors.toList());
    }

}
