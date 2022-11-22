class MyThreadT1 extends Thread {

    Shared shMem;
    String ThreadName = "Thread T1:";
    int waitTime = 100;
    private int m;

    public MyThreadT1(Shared mem) {
        shMem = mem;
    }

    public void run() {
        while (true) {

            try {
                sleep(waitTime);
            } catch (InterruptedException e1) {
                e1.printStackTrace();
                return;
            }

            m = (int) (Math.random() * 11);
            System.out.println(ThreadName + "Genero m: " + m);

            synchronized (shMem) {

                System.out.println(ThreadName + "X è:" + shMem.read());
                if (shMem.read() == (-1)) {
                    break;
                }
                if (m == shMem.read()) {
                    System.out.println(ThreadName + "RISPOSTA CORRETTA");
                    shMem.write(-1);
                    break;
                }
                if (Math.abs(m - shMem.read()) > 5) {
                    try {
                        System.out.println(ThreadName + "RISPOSTA MOLTO SBAGLIATA");
                        shMem.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                        break;
                    }
                } else {
                    System.out.println(ThreadName + "RISPOSTA SBAGLIATA");
                }
            }

        }
    }
}