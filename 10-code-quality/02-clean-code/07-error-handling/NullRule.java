package chapter_7;

import chapter_6.obj_and_data_structure.Animal;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class NullRule {
    public void getExample() {
        int totalAnimal = 0;
        List<Animal> animals = getEmployees();
        if (animals != null) {
            for (Animal a : animals) {
                totalAnimal += 1;
            }
        }
    }
    // Đừng trả về null
    public List<Animal> getEmployees() {
        Animal a = new Animal() {
            @Override
            public void move() {
            }
        };
        List<Animal> animals = new ArrayList<>();
        animals.add(a);
        if (a != null) {
            return Collections.emptyList();
        } else {
            return animals;
        }
    }

    // Đừng truyền vào null
}
