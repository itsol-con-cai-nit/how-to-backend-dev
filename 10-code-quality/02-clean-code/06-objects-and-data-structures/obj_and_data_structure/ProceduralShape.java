package chapter_6.obj_and_data_structure;

public class ProceduralShape {
    public final double PI = 3.141592653589793;
    // Các dòng code sử dụng phương pháp cấu trúc dữ liệu giúp dễ dàng thêm các hàm mới mà không cần phải thay đổi cấu
    // trúc của dữ liệu hiện tại.

    //Code theo cấu trúc dữ liệu làm bạn khó thêm dữ liệu mới vì phải thay đổi toàn bộ hàm.
    public double area(Object shape) throws NoSuchShapeException
    {
        if (shape instanceof Square) {
            Square s = (Square)shape;
            return s.side * s.side;
        }else if (shape instanceof Rectangle) {
            Rectangle r = (Rectangle)shape;
            return r.height * r.width;
        }
        else if (shape instanceof Circle) {
            Circle c = (Circle)shape;
            return PI * c.radius * c.radius;
        }
        throw new NoSuchShapeException();
    }

    private class NoSuchShapeException extends Exception {

    }
}


class Point {
    double x;
    double y;
}
 class Square{
    public Point topLeft;
    public double side;
}
class Rectangle {
    public Point topLeft;
    public double height;
    public double width;
}

class Circle {
    public Point center;
    public double radius;
}
