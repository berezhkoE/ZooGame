import java.util.HashMap;

public class Enclosure extends Animalistic {
    String name;
    HashMap<Animalistic, Integer> animals = new HashMap<Animalistic, Integer>();
    HashMap<String, Integer> food = new HashMap<String, Integer>();

    public Enclosure(String name) {
        this.name = name;
    }

    public void add(Animalistic animal, int N) {
        if (!animals.containsKey(animal)) {
            animals.put(animal, 0);
        }
        animals.put(animal, animals.get(animal) + N);
    }

    public void remove(Animalistic animal, int N) {
        animals.put(animal, animals.get(animal) - N);
        if (animals.get(animal) <= 0) {
            animals.put(animal, 0);
        }
    }

    public String getName() {
        return name;
    }

    public HashMap<Animalistic, Integer> getAnimals() {
        return animals;
    }

    public void addFood(String name, int N) {
        if (!food.containsKey(name)) {
            food.put(name, 0);
        }
        food.put(name, food.get(name) + N);
    }

    public void takeFood(String name, int N) {
        food.put(name, food.get(name) - N);
        if (food.get(name) <= 0) {
            food.remove(name);
        }
    }

    public HashMap<String, Integer> getFoodStatus() {
        return food;
    }

    public void print() {
        System.out.print("\nEnclosure " + getName());
        System.out.print(": ");
        for (Animalistic animal: animals.keySet()) {
            System.out.print(animal.getName() + ". ");
            System.out.print("Quantity: " + animals.get(animal));
            System.out.print(" Eat: " );
            for (String f: animal.getFoodDemand().keySet()) {
                System.out.print(f + "; ");
            }
            System.out.print("\n             ");
        }
        System.out.print("                                 Food: " );
        if (food.isEmpty()) {
            System.out.print("No");
        }
        for (String f: food.keySet()){
            System.out.print(food.get(f) + " " + f + "; ");
        }
        System.out.print("\n");
    }
}
