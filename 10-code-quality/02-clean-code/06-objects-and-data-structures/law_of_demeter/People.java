package chapter_6.law_of_demeter;

public class People {
    private Vehicle transport;

    public People(Vehicle transport) {
        this.transport = transport;
    }

    public void goToWork(){
        //  đi xe đạp
         if (this.transport instanceof Bicycle){
        if(this.transport.getWheel().getTension()>0.7){
            System.out.println("Go to work");
            this.transport.move();
        }else{
            System.out.println("Work at hone");
        } }

        // đi xe máy
        if (this.transport instanceof Motobike){
        if(this.transport.getWheel().getTension()>0.8 && this.transport.getEngine().isWork() ){
            System.out.println("Go to work");
            this.transport.move();
        }else{
            System.out.println("Work at hone");
        } }


    }

    public void goToWorkUpdated(){
        if(this.transport.canMove()){
            System.out.println("Go to work");
            this.transport.move();
        }else{
            System.out.println("Work at hone");
        }
    }



}
