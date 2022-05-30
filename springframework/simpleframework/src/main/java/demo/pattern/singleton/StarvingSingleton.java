package demo.pattern.singleton;

public class StarvingSingleton {
    private StarvingSingleton() {
    }

    private static final StarvingSingleton instance = new StarvingSingleton();

    public static StarvingSingleton getInstance() {
        return instance;
    }
}