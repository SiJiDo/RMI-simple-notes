package Test;
import RMI.User;
import java.rmi.Naming;

public class UserClient {
    public static void main(String[] args) throws Exception{
        String url = "rmi://127.0.0.1:4396/User";
        User userClient = (User)Naming.lookup(url);

        System.out.println(userClient.name("sijidou"));
        userClient.say("world");
        userClient.dowork("yingying");
    }

}
