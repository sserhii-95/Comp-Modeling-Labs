package lab3;

import java.util.ArrayList;

import static java.lang.Math.log;
import static java.lang.Math.random;

/**
 * @author Sergiy Siryy
 */
public class Part {

    static int num = 0;

    public String name;    // Назва присрою

    public int processorsCount;  // Кількість процессорів

    public int queue;      // Кількість фішок в черзі
    public int processor;  // Кількість задач в процесорів
    public int flag;       // Ресурс

    public double midTime; // Середній час обробки фішок


    public  ArrayList<Part> next;   // Список пристроїв до яких буде посилатись фішка
    public  ArrayList<Double> posibilities; // Ймовірності переходу для відповідних пристроїв

    private double[] processorsTimes;   // Часи обробки тичасової задачі на кожному процессорі
    private double[] processorsStats;   // умарні часи роботи на кожному процессорі

    private double time;    // Час системи ( Поки непотрібний )
    private double workTime[];

    public int solvedTasks;

    public void set(int queue, int processor) {
        this.queue = queue;
        this.processor = processor;
        this.flag = processorsCount - processor;
    }

    public Part(String name, double midTime){
        this(name, midTime, 1);

    }
    /**
     * Конструктор
     * @param name назва пристрою
     * @param midTime середній час обрахунку задач
     * @param processorsCount кількість процесів
     */
    public Part(String name, double midTime, int processorsCount){

        this.name = name;
        this.midTime = midTime;
        this.processorsCount = processorsCount;

        this.next = new ArrayList<>();
        this.posibilities = new ArrayList<>();

        this.processorsTimes = new double[processorsCount];
        this.processorsStats = new double[processorsCount];

        this.flag = processorsCount;
        this.time = 0;
        this.queue = 0;
        this.processor = 0;
        this.solvedTasks = 0;


    }

    /**
     * @return Наступний пристрій, га який буде передано фішку
     */
    public Part getNext(){
        double rand = random();
        for(int i = 0; i < posibilities.size(); i++){
            if (rand < posibilities.get(i)) return next.get(i);
            rand-= posibilities.get(i);
        }
        return null;
    }

    /**
     * Додає до списку наступних пристроїв пристрій з параметрів
     * @param next пристрій
     * @param possibility ймовірність
     */
    public void addNext(Part next, double possibility){
        this.next.add(next);
        this.posibilities.add(possibility);
    }

    /**
     * @return час обрахунку задачі
     */
    public double getTime(){
        return -midTime * log(random());
    }


    /**
     * Додає задачу в чергу
     */
    public void addTask(){
        queue++;
    }

    /**
     * @return {@code true}, якщо процессор вільний(повністю, або частково)
     */
    public boolean isProcessorFree(){
        return flag  > 0;
    }

    /**
     * @return {@code true}, якщо стан нестабільний (черга > 0 та процессор вільний)
     */
    public boolean canWork(){
        return (isProcessorFree() && queue > 0);
    }


    /**
     * @return час обробки задачі
     */
    public double addTaskToProcessor(double time){
        double workTime = getTime();
        if (canWork()){
            queue--;
            processorsTimes[processor] = time + workTime;

            if (processor == 1 && processorsTimes[1] > processorsTimes[0]) {

                double s = processorsTimes[1];
                processorsTimes[1] = processorsTimes[0];
                processorsTimes[0] = s;

                processorsStats[1] += processorsTimes[1] - (processorsTimes[0] - workTime);
                processorsStats[0] += workTime - (processorsTimes[0] - processorsTimes[1]);
            } else {
                processorsStats[processor] += workTime;
            }
            processor++;
            flag--;
        }

        //if ((time + workTime)*1.2 > this.time) this.time = (time + workTime)*1.2;
        if ((time + workTime) > this.time) this.time = (time + workTime);
        return time + workTime;
    }

    public void reset(){
        if (processor > 0) {
            flag++;
            solvedTasks++;
            processor--;
        }
    }

    public int getProcessorsCount() {
        return processorsCount;
    }

    public String stats(){
        String s = name + ": ";
        if (time  == 0) time = 1e-100;
        for(int i = 0; i < processorsCount; i++)
            s += " [" + (i + 1) + "] = " + processorsStats[i] / (time * 1.2) + ",";
        // s+="\n solved: "+solvedTasks;
        return s;
    }

    public String toString(){
        return (name + " <" + queue + ", " + processor + ", " + flag+">");
    }
}
