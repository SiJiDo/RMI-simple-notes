package server;

import java.rmi.Naming;
import java.rmi.registry.LocateRegistry;

public class Server1 {
    public static void main(String[] args) throws Exception{
        Hello hello = new HelloImpl();
        LocateRegistry.createRegistry(4396);
        String url = "rmi://10.10.10.1:4396/Hello";
        Naming.rebind(url, hello);
        System.out.println("rmi server is running ...");
    }
}
