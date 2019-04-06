package RMI;

import java.rmi.Naming;
import java.rmi.registry.LocateRegistry;

public class UserServer {
    public static void main(String[] args) throws Exception{
        String url = "rmi://10.10.10.1:4396/User";
        User user = new UserImpl();
        LocateRegistry.createRegistry(4396);
        Naming.bind(url,user);
        System.out.println("the rmi is running ...");
    }
}
