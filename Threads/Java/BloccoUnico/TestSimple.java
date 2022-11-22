public class TestSimple {
    public static void main (String[] args) {
        new MyThread("\t\t\t\tJamaica").start(); //print indented
        new MyThread("Fiji").start();
        // mancano join(), anche perche' non si sono salvati
        // riferimenti ai thread creati
    }
}
