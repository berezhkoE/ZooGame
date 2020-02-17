import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Random;

public class ZooModel implements ZooModelInterface {
    ArrayList<ZooObserver> zooObservers = new ArrayList<ZooObserver>();
    ArrayList<Enclosure> enclosures = new ArrayList<Enclosure>();
    ArrayList<String[]> animalsData = new ArrayList<String[]>();
    HashMap<String, Integer> storage = new HashMap<String, Integer>();
    int size = 0;

    public void initialize() {
        readAnimalsData();
        setUpZoo();
        fillStorage();
        notifyZooObservers();
    }

    public void next() {
        updateZoo();
        notifyZooObservers();
    }

    public void moveTo(String[] args) {
        int n = Integer.parseInt(args[1]);
        String food = args[2];
        int e = Integer.parseInt(args[4]);
        if (storage.get(food) >= n) {
            System.out.printf("Putting %d %s into cage %d... \n", n, food, e);
            enclosures.get(e - 1).addFood(food, n);
            storage.put(food, storage.get(food) - n);
        } else {
            System.out.println("There is not enough " + food + " in the storage.");
        }
        notifyZooObservers();
    }

    public void setSize(int N) {
        this.size = N;
    }

    public void registerObserver(ZooObserver o) {
        zooObservers.add(o);
    }

    public void removeObserver(ZooObserver o) {
        int i = zooObservers.indexOf(o);
        if (i >= 0) {
            zooObservers.remove(o);
        }
    }

    public void getState() {
        System.out.println("Current Zoo state here");
        for (Enclosure e: enclosures) {
            e.print();
        }
        System.out.println("\nStorage:");
        System.out.println(storage);
    }

    public void notifyZooObservers() {
        for (ZooObserver zooObserver : zooObservers) {
            ((ZooObserver) zooObserver).update();
        }
    }

    public void setUpZoo() {
        System.out.println("Setting up Zoo...");
        Random random = new Random();
        for (int i = 0; i < size; i++) {
            Enclosure e = new Enclosure(Integer.toString(i+1));
            HashSet<Integer> pos = new HashSet<Integer>();
            for (int j = 0; j < random.nextInt(5) + 1; j++) {
                pos.add(random.nextInt(animalsData.size()));
            }
            for (Integer j: pos){
                e.add(new Animal(animalsData.get(j)), random.nextInt(30) + 1);
            }
            enclosures.add(e);
        }
    }

    public void updateZoo() {
        System.out.println("Updating Zoo state...");

        for (Enclosure e: enclosures) {
            feedAnimals(e);
            extraFeed(e);
            removeFood(e);
            //increaseAge(e);
        }
    }

    public void feedAnimals(Enclosure e) {
        HashMap<Animalistic, Integer> as = e.getAnimals();
        for (Animalistic a: as.keySet()) {
            int l = 0;

            for (String s : a.getFoodDemand().keySet()) {
                if (as.get(a) > l && e.getFoodStatus().containsKey(s)) {
                    l += e.getFoodStatus().get(s) / a.getFoodDemand().get(s);
                    if (l > as.get(a)) {
                        l = as.get(a);
                    }
                    e.takeFood(s, l*a.getFoodDemand().get(s));
                }
            }
            if (as.get(a) > l) {
                System.out.println(as.get(a) - l + " " + a.getName() + " starved to death.");
                e.remove(a, as.get(a) - l);
            }
        }
    }

    public void extraFeed(Enclosure e) {
        HashMap<Animalistic, Integer> as = e.getAnimals();
        for (Animalistic a: as.keySet()) {
            int l = 0;

            for (String s : a.getFoodDemand().keySet()) {
                if (as.get(a) > l && e.getFoodStatus().containsKey(s)) {
                    l += e.getFoodStatus().get(s) / (a.getFoodDemand().get(s)*2);
                    if (l > as.get(a)) {
                        l = as.get(a);
                    }
                    e.takeFood(s, l*2*a.getFoodDemand().get(s));
                }
            }
            if (l > 0) {
                Random r = new Random();
                int n = 0;
                for (int i = 0; i < l; i++) {
                    double d = r.nextDouble();
                    if (d < 0.3)
                        n++;
                }
                System.out.println(n + " " + a.getName() + " spawned in Enclosure " + e.getName());
                e.add(a, n);
            }
        }
    }

    public void removeFood(Enclosure e) {
        for (String s: e.getFoodStatus().keySet()){
            e.takeFood(s, e.getFoodStatus().get(s));
        }
    }

    public void increaseAge(Enclosure e) {
        HashMap<Animalistic, Integer> as = e.getAnimals();
        for (Animalistic a: as.keySet()) {
            a.setCurrentAge(a.getCurrentAge() + 1);
        }
    }

    public void moveFrom(String[] args) {
        int n = Integer.parseInt(args[1]);
        String food = args[2];
        int e = Integer.parseInt(args[4]);
        if (enclosures.get(e - 1).getFoodStatus().containsKey(food) && enclosures.get(e - 1).getFoodStatus().get(food) > n) {
            System.out.println("Putting " + n + food + " from cage " + e + " into storage...");
            enclosures.get(e - 1).takeFood(food, n);
            storage.put(food, storage.get(food) + n);
        } else {
            System.out.println("Please try again");
        }
        notifyZooObservers();
    }

    public void readAnimalsData() {
        try(BufferedReader br = new BufferedReader(new FileReader("animalsData.txt")))
        {
            String s;
            while((s=br.readLine())!=null){
                animalsData.add(s.split(" "));
            }
        }
        catch(IOException ex){

            System.out.println(ex.getMessage());
        }
    }

    public void fillStorage() {
        System.out.println("Filling up storage.");
        try(BufferedReader br = new BufferedReader(new FileReader("storageData.txt")))
        {
            String s;
            while((s=br.readLine())!=null){
                storage.put(s.split(" ")[0], Integer.parseInt(s.split(" ")[1]));
            }
        }
        catch(IOException ex){

            System.out.println(ex.getMessage());
        }
    }
}
