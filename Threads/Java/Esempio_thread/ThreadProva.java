import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;

public class ThreadProva extends Thread {
    private static int nTot = 0;
    private int n;
    private String addr;
    private int port;
    private String m;
    private boolean toUpper;
    private int rep;

    ThreadProva(String addr, int port, String m, boolean toUpper, int rep) {
        ThreadProva.nTot++;
        this.n = ThreadProva.nTot;
        this.addr = addr;
        this.port = port;
        this.m = m;
        this.toUpper = toUpper;
        this.rep = rep;
    }

    @Override
    public void run() {
        try {
            for (int i = 0; i < rep; i++) {
                Socket server = new Socket(InetAddress.getByName(addr), port);
                BufferedReader in = new BufferedReader(new InputStreamReader(server.getInputStream()));
                PrintWriter out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(server.getOutputStream())),
                        true);
                if (toUpper)
                    out.println(m.toLowerCase());
                else
                    out.println(m.toUpperCase());
                System.out.println("Thread: " + n + "\tRisposta dal server: " + in.readLine());
                server.close();

                try {
                    sleep((long) (Math.random() * 10000));
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
