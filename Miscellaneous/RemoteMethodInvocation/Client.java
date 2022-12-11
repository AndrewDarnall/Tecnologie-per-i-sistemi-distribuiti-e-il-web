package RemoteMethodInvocation;

import java.rmi.registry.Registry;
import java.rmi.registry.LocateRegistry;

public class Client {

    public static void main(String[] args) {
        try{
            // Getting the registry
            Registry registry = LocateRegistry.getRegistry(null);

            // Lookup the registry for the remote object
            Hello stub = (Hello) registry.lookup("hello");

            // Calling the remote method
            stub.printMsg();

            System.out.println("Remote Method Invoked.");


        } catch(Exception e) {
            System.err.println("Client error:\t" + e.toString());
            e.printStackTrace();
        }
    }

}