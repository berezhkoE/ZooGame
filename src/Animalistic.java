import java.util.ArrayList;
import java.util.HashMap;

public abstract class Animalistic {
    public void add(Animalistic animal) {
        throw new UnsupportedOperationException();
    }

    public void remove(Animalistic animal) {
        throw new UnsupportedOperationException();
    }

    public void addFood(String name, int N) {
        throw new UnsupportedOperationException();
    }

    public void takeFood(String name, int N) {
        throw new UnsupportedOperationException();
    }

    public HashMap<String, Integer> getFoodStatus() {
        throw new UnsupportedOperationException();
    }

    public ArrayList<Animalistic> getAnimals()  {
        throw new UnsupportedOperationException();
    }


    public String getName() {
        throw new UnsupportedOperationException();
    }

    public HashMap<String, Integer> getFoodDemand() {
        throw new UnsupportedOperationException();
    }

    public int getMaxAge() {
        throw new UnsupportedOperationException();
    }

    public int getCurrentAge() {
        throw new UnsupportedOperationException();
    }

    public void setCurrentAge(int N) {
        throw new UnsupportedOperationException();
    }


    public void print() {
        throw new UnsupportedOperationException();
    }
}
