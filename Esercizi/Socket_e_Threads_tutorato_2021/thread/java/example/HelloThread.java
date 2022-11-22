public class HelloThread extends Thread {
    @Override
    public void run() {
        System.out.println("Hello world! I'm a standalone thread");
    } 
}
