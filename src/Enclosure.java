import java.util.ArrayList;
import java.util.HashMap;

public class Enclosure extends Animalistic {
    String name;

    ArrayList<Animalistic> animals = new ArrayList<>();
    HashMap<String, Integer> food = new HashMap<>();

    public Enclosure(String name) {
        this.name = name;
    }

    public void add (Animalistic animal) {
        animals.add(animal);
    }

    public void remove (Animalistic animal) {
        animals.remove(animal);
    }

    public String getName() {
        return name;
    }

    public ArrayList<Animalistic> getAnimals() {
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
        for (Animalistic animalistic : animals) {
            animalistic.print();
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
