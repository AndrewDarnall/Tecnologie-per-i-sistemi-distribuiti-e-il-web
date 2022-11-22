class MyThreadT2 extends Thread {
    Shared shMem;

    public MyThreadT2(Shared mem){
        shMem = mem;
    }

    public void run(){
        while (true){
            try {
                sleep(300);
            } catch (InterruptedException e) {
                e.printStackTrace();
            } 
            
            synchronized(shMem) {
                System.out.println("Thread T2:Sveglio T1");

                shMem.notify();
                if (shMem.read() == -1){
                    System.out.println("Thread T2:x è -1, termino");
                    break;
                }
            }
        }
    }
    
}