import java.util.Scanner;

public class ZooView implements ZooObserver {
    ZooModelInterface model;
    ControllerInterface controller;

    public ZooView (ZooModelInterface model, ControllerInterface controller) {
        this.model = model;
        this.controller = controller;
        model.registerObserver((ZooObserver)this);
    }

    public void update() {
        model.getState();
    }

    public int setSize() {
        System.out.print("Number of enclosures: ");
        Scanner in = new Scanner(System.in);
        return in.nextInt();
    }

    public String readCommand() {
        System.out.print("move/next: ");
        Scanner in = new Scanner(System.in);
        return in.nextLine();
    }
}
