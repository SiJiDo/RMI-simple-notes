package server;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class HelloImpl extends UnicastRemoteObject implements Hello {
//    private  static final long serialVersionUID = 3434060152387200042L;
    protected HelloImpl() throws RemoteException {
        super();
    }

    public String sayhello(String name){
        System.out.println("hello" + name);
        return "hello" + name;
    }

    public int add(int x, int y) {
        return x + y;
    }
}
