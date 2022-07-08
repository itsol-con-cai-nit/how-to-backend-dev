package _02_clean_code._08_boundaries.adapter_parttern;

public class DuckTestDrive {
    public static void main(String[] args) {
        Duck mallard = new MallardDuck();
        Duck turkeyAdapter = new TurkeyAdapter(new Turkey());
        mallard.fly();
        turkeyAdapter.fly();
    }
}

