package dsmain;

public class Clock{
    private int value;
    public Clock(){
        value = 0;
    }
    public int getValue(){
        return value;
    }
    public void localStep(){
        value++;
    }
    public void sendEvent(){
        value++;
    }
    public void receiveEvent(int sentValue){
        if(value > sentValue){
            value++;
        }else{
            value = sentValue++;
        }
    }

}