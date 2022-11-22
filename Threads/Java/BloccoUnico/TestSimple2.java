public class TestSimple2 {
    public static void main (String[] args) {
        new MyThread2("\t\t\t\tHawaii").start(); //print indented
        new MyThread2("Cuba").start();
    }
}
