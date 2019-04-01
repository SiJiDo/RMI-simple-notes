package server;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface Hello extends Remote {
    public String sayhello(String name) throws RemoteException;
    public int add(int x, int y) throws RemoteException;
}
