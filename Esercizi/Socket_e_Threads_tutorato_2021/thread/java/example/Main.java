class Main {
    public static void main(String[] args) throws InterruptedException {
        // Inizializzazione con classe che estende Thread
        Thread thread = new HelloThread();

        // Inizializzazione con classe che implementa Runnable
        Thread runnableThread = new Thread(new HelloThreadRunnable());

        // Inizializzazione tramite espressione lambda
        Thread lambdaThread = new Thread(() -> {
            System.out.println("Lambda hello world!");
        });

        thread.start();
        runnableThread.start();
        lambdaThread.start();

        thread.join();
        runnableThread.join();
        lambdaThread.join();

        System.out.println("I'm the main thread, every thread has finished");
    }
}