**RMI笔记**

RMI（Remote Method Invocation），远程方法调用

JNDI（Java Naming and Directory Interface），Java 命名与目录接口

JNDI是注册表可以包含很多的RMI，举个例子就JNDI像个本子，RMI像本子上的记录，客户端调用RMI记录的时候会先去JNDI这个本子，然后从本子上找相应的RMI记录

**性质**

与JMX服务器之间的通信使用的协议就是rmi协议

rmi可以传输序列化的数据

**1.传输原理**

英文stub ：存根

英文skeletons ：框架

```
1.客户端 						       => 客户端本地的stub类
2.客户端本地的stub类把信息序列化 	   => 服务器端的skeletons类
3.服务器端的skeletons类把信息反序列化   => 服务器端的对应类进行处理
4.服务器端对应类处理完后 			  	 => 服务器端的skeletions类
5.skeletions类序列化数据 			 	 => 客户端本地的stub类
6.客户端本地的stub类把数据反序列化	  => 客户端

但在java 1.2版本后免去了3、5的步骤，直接在对应的类上进行序列化和反序列化
```

**2.关键类和函数**

ObjectOutputStream类的writeObject(Object obj)方法,将对象序列化成字符串数据

ObjectInputStream类的readObject(Object obj)方法，将字符串数据反序列化成对象

当然这些函数是rmi里面类会调用了，如果简单使用rmi还用不到这2个函数

**3.rmi协议的格式**

rmi://host:port/name

```
rmi 	协议头
host	对方ip或域名		（不填默认本地）
port	对方服务的端口号  （不填默认1099）
name	对方服务的类名
```

所需要的API

```
java.rmi			客户端所需要的类、接口和异常
java.rmi.server		服务器端所需要的类、接口和异常
java.rmi.registry	注册表的创建查找。命名远程对象的类、接口和异常
```

**4.rmi测试**

rmi首先要有服务器端，然后要有客户端

服务器端分为3个类：接口类，实现接口类，运行main的类

客户端只需要1个类：运行main的类

但是客户端要获取服务器端的接口类才行，本地写个一模一样的接口不行



服务器端的接口类

和普通的接口不同，它每个方法要抛出异常

```
package server;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface Hello extends Remote {
    public String sayhello(String name) throws RemoteException;
    public int add(int x, int y) throws RemoteException;
}
```

服务器端的接口实现类

要抛出异常

```
package server;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class HelloImpl extends UnicastRemoteObject implements Hello {
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
```

服务器端的运行类

LocateRegistry.createRegistry(4396)把4396端口号在JNDI中注册，将开启RMI的服务的端口

Naming.rebind()来实现将类和端口版本，开放出去

```
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
```

直接run就行，在1.5版本前还需要用rmic生成stub和skeletons，现在不需要要了，java自动生成



客户端运行类如下

该链接方法是使用的registry的子类来实现的

```
package client;

import server.Hello;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class Client1 {
    public static void main(String[] args) throws Exception{
        Registry registry = LocateRegistry.getRegistry("10.10.10.1",4396);
        Hello hello = (Hello)registry.lookup("Hello");
        String result = hello.sayhello("sijidou");
        int number = hello.add(777, 4396);
        System.out.println(result);
        System.out.println(number);
    }
}
```

还有种方法是使用Naming.lookup(url)，它有个比较明显的url格式

```
package client;

import server.Hello;

import java.rmi.Naming;

public class Client1 {
    public static void main(String[] args) throws Exception{
        String url = "rmi://10.10.10.1:4396/Hello";
        Hello hello = (Hello) Naming.lookup("Hello");
        String result = hello.sayhello("sijidou");
        int number = hello.add(777, 4396);
        System.out.println(result);
        System.out.println(number);
    }
}
```



**参考链接**

<https://www.jianshu.com/p/a947717ded70>

<https://blog.csdn.net/lmy86263/article/details/72594760>

http://www.importnew.com/20344.html

https://mogwailabs.de/blog/2019/03/attacking-java-rmi-services-after-jep-290/

