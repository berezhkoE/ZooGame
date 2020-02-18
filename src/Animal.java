import java.util.HashMap;
import java.util.Random;

public class Animal extends Animalistic {
    String name;
    HashMap<String, Integer> foodDemand = new HashMap<String, Integer>();
    int maxAge;
    int currentAge;

    public Animal(String[] args) {
        this.name = args[0];
        for (int i = 1; i < args.length/2; i++) {
            this.foodDemand.put(args[i], Integer.parseInt(args[(args.length - 2)/2 + i]));
        }
        this.maxAge = Integer.parseInt(args[args.length-1]);
        Random random = new Random();
        this.currentAge = random.nextInt(maxAge);
    }

    public String getName() {
        return name;
    }

    public HashMap<String, Integer> getFoodDemand() {
        return foodDemand;
    }

    public int getMaxAge() {
        return maxAge;
    }

    public int getCurrentAge() {
        return currentAge;
    }

    public void setCurrentAge(int N) {
        this.currentAge = N;
    }
}
