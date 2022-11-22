public class Shared {
    int shdInt;

    public Shared() {
        shdInt = (int) (Math.random() * 11);
    }

    synchronized void write(int newValue){
        shdInt = newValue;
    }

    synchronized int read(){
        return shdInt;
    }
}