package RemoteMethodInvocation;

import java.rmi.registry.Registry;
import java.rmi.registry.LocateRegistry;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class Server extends Implementation {
 
    public Server() {}
    
    public static void main(String[] args) {
        try{
            Implementation object = new Implementation();

            // Exporting the remote object to the stub
            Hello stub = (Hello) UnicastRemoteObject.exportObject(object, 0);

            // Binding the remote object to the registry
            Registry registry = LocateRegistry.getRegistry();
            registry.bind("Hello",stub);

            // This seems to be a very early example of a SOAP API
            System.out.println("Server is ready");

        } catch(Exception e) {
            System.err.println("Server exception:\t" + e.toString());
            e.printStackTrace();
        }
    }

}