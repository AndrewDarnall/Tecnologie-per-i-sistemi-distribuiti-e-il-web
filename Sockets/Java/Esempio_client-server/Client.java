public class Client {
    static private int port = 33333;
    static private String addr = "localhost";
    static private int rep = 5;

    // Utilizzo: Client [addr] [port] [numero ripetizioni]
    static private void getArgs(String[] args) {
        switch (args.length) {
            case 3:
                try {
                    rep = Integer.parseInt(args[2]);
                } catch (NumberFormatException e) {
                    rep = 5;
                }
            case 2:
                try {
                    port = Integer.parseInt(args[1]);
                } catch (NumberFormatException e) {
                    port = 33333;
                }
            case 1:
                addr = args[0];
            default:
                break;
        }
    }

    static public void main(String[] args) {
        getArgs(args);
        System.out.println(String.format("Il client comunicherà con il server di indirizzo %s e port %d con %d (x2) ripetizioni", addr, port, rep));        System.out.println("Inviare un messaggio al server");
        String input = System.console().readLine();
        if (input == null || input.equals("quit"))
            return;
        ThreadProva t1 = new ThreadProva(addr, port, input, true, rep);
        ThreadProva t2 = new ThreadProva(addr, port, input, false, rep);

        t1.start();
        t2.start();

        try {
            t1.join();
            t2.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }
}
