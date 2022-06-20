package chapter_1;

public class ReadableExample {

    public void runExample() {
        // code smell 1
        int a = 12;
        for (int i = 0; i < 10; i++) {
            System.out.println(" Not format code yet ??!!");
        }
        // Mac
        //CMD + Option + L
        //// Windows
        //Ctrl + Alt + L

        // code smell 2
        String u = "";
        if (u == "abc124") {
            if (u.length() > 10) {
                //do some thing
            }
        }

    }


}
