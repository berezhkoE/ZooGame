import java.util.ArrayList;
import java.util.HashMap;

public class AnimalGroup extends Animalistic {
    String name;
    ArrayList<Animalistic> animals = new ArrayList<Animalistic>();

    public AnimalGroup (String name) {
        this.name = name;
    }

    public void add (Animalistic animal) {
        animals.add(animal);
    }

    public void remove (Animalistic animal) {
        animals.remove(animal);
    }

    public ArrayList<Animalistic> getAnimals () {
        return animals;
    }

    public HashMap<String, Integer> getFoodDemand() {
        return animals.get(0).getFoodDemand();
    }

    public String getName () {
        return name;
    }

    public void print () {
        System.out.print(name);
        System.out.print(" Quantity: " + animals.size());
        System.out.print(" Eat: " );
        for (String f: getFoodDemand().keySet()) {
            System.out.print(f + "; ");
        }
        System.out.print("\n             ");
    }
}
