package _02_clean_code._06_objects_and_data_structures.obj_and_data_structure;

import chapter_6.obj_and_data_structure.Animal;

public class Fish implements Animal {
    @Override
    public void move() {
        System.out.println("Swimming");
    }
}
