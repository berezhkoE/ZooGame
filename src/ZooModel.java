import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class ZooModel implements ZooModelInterface {
    ArrayList<ZooObserver> zooObservers = new ArrayList<>();
    ArrayList<Animalistic> enclosures = new ArrayList<>();
    ArrayList<String[]> animalsData = new ArrayList<>();
    HashMap<String, Integer> storage = new HashMap<>();
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
        for (Animalistic e: enclosures) {
            e.print();
        }
        System.out.println("\nStorage:");
        System.out.println(storage);
    }

    public void notifyZooObservers() {
        for (ZooObserver zooObserver : zooObservers) {
            zooObserver.update();
        }
    }

    public void setUpZoo() {
        System.out.println("Setting up Zoo...");
        Random random = new Random();
        Random r = new Random();
        for (int i = 0; i < size; i++) {
            Animalistic e = new Enclosure(Integer.toString(i+1));

            ArrayList<Integer> pos = new ArrayList<>();
            ArrayList<Integer> q = new ArrayList<>();
            for (int j = 0; j < random.nextInt(6) + 1; j++) {
                int type = random.nextInt(animalsData.size());
                if (!pos.contains(type)) {
                    pos.add(type);
                    q.add(r.nextInt(30) + 1);
                }
            }

            int t = 0;
            for (Integer po : pos) {
                int l = q.get(t);
                Animalistic ag = new AnimalGroup(animalsData.get(po)[0]);
                e.add(ag);
                for (int k = 0; k < l; k++) {
                    ag.add(new Animal(animalsData.get(po)));
                }
                t++;
            }
            enclosures.add(e);
        }
    }

    public void updateZoo() {
        System.out.println("Updating Zoo state...");

        for (Animalistic e: enclosures) {
            feedAnimals(e);
            extraFeed(e);
            removeFood(e);
            increaseAge(e);
        }
    }

    public void feedAnimals(Animalistic e) {
        ArrayList<Animalistic> al = e.getAnimals();
        ArrayList<Animalistic> d = new ArrayList<>();
        for (Animalistic a : al) {
            int l = 0;

            for (String s : a.getFoodDemand().keySet()) {
                if (al.size() > l && e.getFoodStatus().containsKey(s)) {
                    l += e.getFoodStatus().get(s) / a.getFoodDemand().get(s);
                    if (l > a.getAnimals().size()) {
                        l = a.getAnimals().size();
                    }
                    e.takeFood(s, l*a.getFoodDemand().get(s));
                }
            }
            if (a.getAnimals().size() > l) {
                int n = a.getAnimals().size() - l;
                System.out.println(n + " " + a.getName() + " starved to death.");
                Random random = new Random();
                for (int i = 0; i < n; i++) {
                    a.getAnimals().remove(random.nextInt(a.getAnimals().size()));
                }
            }
            if (a.getAnimals().size() == 0) {
                d.add(a);
            }
        }
        al.removeAll(d);
    }

    public void extraFeed(Animalistic e) {
        ArrayList<Animalistic> al = e.getAnimals();
        for (Animalistic a : al) {
            int l = 0;

            for (String s : a.getFoodDemand().keySet()) {
                if (a.getAnimals().size() > l && e.getFoodStatus().containsKey(s)) {
                    l += e.getFoodStatus().get(s) / (a.getFoodDemand().get(s)*2);
                    if (l > a.getAnimals().size()) {
                        l = a.getAnimals().size();
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
                if (n > 0) {
                    System.out.println(n + " " + a.getName() + " spawned in Enclosure " + e.getName());
                    for (int i = 0; i < n; i++) {
                        Animal animal = new Animal(getAnimalData(a.getName()));
                        animal.setCurrentAge(-1);
                        a.add(animal);
                    }
                }
            }
        }
    }

    public void removeFood(Animalistic e) {
        for (String s: e.getFoodStatus().keySet()){
            e.takeFood(s, e.getFoodStatus().get(s));
        }
    }

    public void increaseAge(Animalistic e) {
        ArrayList<Animalistic> al = e.getAnimals();
        for (Animalistic g: al) {
            ArrayList<Animalistic> l = new ArrayList<>();
            for (Animalistic a: g.getAnimals()) {
                a.setCurrentAge(a.getCurrentAge() + 1);
                if (a.getCurrentAge() >= a.getMaxAge()) {
                    Random r = new Random();
                    double d = r.nextDouble();
                    if (d < 0.5) {
                        l.add(a);
                    }
                }
            }
            if (l.size() > 0) {
                g.getAnimals().removeAll(l);
                System.out.println(l.size() + " " + g.getName() + " died of old age.");
            }
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

    public String[] getAnimalData (String name) {
        String[] result = new String[4];
        for (String[] animalData : animalsData) {
            if (animalData[0].equals(name)) {
                result = animalData;
            }
        }
        return result;
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
