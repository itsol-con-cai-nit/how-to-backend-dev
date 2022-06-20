package chapter_6.obj_and_data_structure;

import chapter_6.obj_and_data_structure.Animal;

public class Dog implements Animal {

    @Override
    public void move() {
        System.out.println("Running");
    }
}
