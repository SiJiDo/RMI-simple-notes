package client;

import server.Hello;

import java.rmi.Naming;
//import java.rmi.registry.LocateRegistry;
//import java.rmi.registry.Registry;

public class Client1 {
    public static void main(String[] args) throws Exception{
        String url = "rmi://10.10.10.1:4396/Hello";
        //Registry registry = LocateRegistry.getRegistry("10.10.10.1",4396);
        Hello hello = (Hello) Naming.lookup("Hello");
        
        String result = hello.sayhello("sijidou");
        int number = hello.add(777, 4396);
        System.out.println(result);
        System.out.println(number);
    }
}
