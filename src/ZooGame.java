public class ZooGame {
    public static void main(String[] args) {
        ZooModelInterface model = new ZooModel();
        ControllerInterface controller = new ZooController(model);
    }
}

