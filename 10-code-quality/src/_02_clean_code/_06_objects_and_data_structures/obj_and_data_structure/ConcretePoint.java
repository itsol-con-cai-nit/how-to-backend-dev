package _02_clean_code._06_objects_and_data_structures.obj_and_data_structure;

public interface ConcretePoint {
    double getX();
    double getY();
    void setCartesian(double x, double y);
    double getR();
    double getTheta();
    void setPolar(double r, double theta);
}
