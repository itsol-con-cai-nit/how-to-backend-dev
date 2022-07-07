package _02_clean_code._08_boundaries.adapter_parttern;

public class MallardDuck implements Duck {
    @Override
    public void quack() {
        System.out.println("Quack");
    }

    @Override
    public void fly() {
        System.out.println("Duck fly");
    }
}

