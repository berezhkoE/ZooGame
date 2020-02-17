public class ZooController implements ControllerInterface {
    ZooModelInterface model;
    ZooView view;

    public ZooController (ZooModelInterface model) {
        this.model = model;
        view = new ZooView(model, this);
        this.setSize(view.setSize());
        model.initialize();
        //read command
        readCommand(view.readCommand());
    }

    public void moveTo(String[] args) {
        model.moveTo(args);
        //read command
        readCommand(view.readCommand());
    }

    public void moveFrom(String[] args) {
        model.moveFrom(args);
        //read command
        readCommand(view.readCommand());
    }

    public void setSize(int N) {
        model.setSize(N);
    }

    public void next() {
        model.next();
        //read command
        readCommand(view.readCommand());
    }

    public void readCommand(String command) {
        if (command.startsWith("move") && command.split(" ")[3].equals("to")){
            moveTo(command.split(" "));
        } else if (command.startsWith("move") && command.split(" ")[3].equals("from")) {
            moveFrom(command.split(" "));
        } else if (command.equals("next")) {
            next();
        } else {
            System.out.println("Wrong command. Try again.");
            readCommand(view.readCommand());
        }
    }
}
