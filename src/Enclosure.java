import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

public class Enclosure extends Animalistic {
    String name;

    ArrayList<Animalistic> animals = new ArrayList<Animalistic>();
    HashMap<String, Integer> aList = new HashMap<String, Integer>();

    HashMap<String, Integer> food = new HashMap<String, Integer>();

    public Enclosure(String name) {
        this.name = name;
    }

    public void add (Animalistic animal) {
        animals.add(animal);
        if (!aList.containsKey(animal.getName())) {
            aList.put(animal.getName(), 0);
        }
        aList.put(animal.getName(), aList.get(animal.getName()) + 1);
    }

    public void remove (Animalistic animal) {
        animals.remove(animal);
        aList.put(animal.getName(), aList.get(animal.getName()) - 1);
        if (aList.get(animal.getName()) == 0) {
            aList.remove(animal.getName());
        }
    }

    public void remove (String aName, int N) {
        ArrayList<Animalistic> r = getAnimalsByName(aName, N);
        animals.removeAll(r);
        aList.put(aName, aList.get(aName) - N);
        if (aList.get(aName) <= 0) {
            aList.remove(aName);
        }
    }

    public ArrayList<Animalistic> getAnimalsByName(String aName) {
        ArrayList<Animalistic> result = new ArrayList<Animalistic>();
        for (Animalistic a: animals) {
            if (a.getName().equals(aName)) {
                result.add(a);
            }
        }
        return result;
    }

    public ArrayList<Animalistic> getAnimalsByName(String aName, int N) {
        ArrayList<Animalistic> result = getAnimalsByName(aName);
        int k = Math.min(result.size(), N);
        Random random = new Random();
        for (int i = 0; i < result.size() - k; i++) {
            result.remove(random.nextInt(result.size()));
        }
        return result;
    }

    public String getName() {
        return name;
    }

    public ArrayList<Animalistic> getAnimals() {
        return animals;
    }

    public HashMap<String, Integer> getAList() {
        return aList;
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
        for (String animal: aList.keySet()) {
            System.out.print(animal + ". ");
            System.out.print("Quantity: " + aList.get(animal));
            System.out.print(" Eat: " );
            for (String f: getAnimalsByName(animal).get(0).getFoodDemand().keySet()) {
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
