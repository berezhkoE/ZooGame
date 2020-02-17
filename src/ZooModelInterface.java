public interface ZooModelInterface {
    void initialize();
    void next();
    void moveTo(String[] args);
    void moveFrom(String[] args);
    void setSize(int N);

    void registerObserver(ZooObserver o);
    void removeObserver(ZooObserver o);

    void getState();
}
